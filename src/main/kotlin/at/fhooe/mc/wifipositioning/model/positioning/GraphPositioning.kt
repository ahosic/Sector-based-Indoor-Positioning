package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.building.NewInstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.simulation.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.positioning.AccessPointSlidingWindow
import kotlin.streams.toList

class GraphPositioning(private val installedAccessPoints: List<NewInstalledAccessPoint>, private val graph: List<BuildingGraphNode>, windowSize: Int) : IPositioning {

    private val history: MutableList<NewInstalledAccessPoint> = arrayListOf()
    private val filtering: IFilterable<NewInstalledAccessPoint>
    private val slidingWindow: AccessPointSlidingWindow

    init {
        filtering = SectorLowPassFilter(5)
        slidingWindow = AccessPointSlidingWindow(windowSize)
    }

    override fun calculatePosition(scannedAccessPoints: List<ScannedAccessPoint>): NewInstalledAccessPoint? {
        slidingWindow.addElement(scannedAccessPoints)

        var bssid = ""
        if (history.size == 0) {
            bssid = slidingWindow.bestAverageBSSID
        } else {
            // Get neighbors from last position
            val lastAccessPoint = history.last()
            val node = graph
                    .filter { node -> node.id == lastAccessPoint.id }
                    .firstOrNull()

            node?.let {
                val allowedAccessPoints = installedAccessPoints
                        .stream()
                        .filter { ap -> it.neighbors.contains(ap.id) }
                        .toList()

                bssid = slidingWindow.getBestAverageBSSID(allowedAccessPoints)
            }
        }

        // Retrieve sector
        val accessPoint = getAccessPoint(bssid)

        accessPoint?.let {
            val filteredPosition = filtering.filter(it)

            // Add position to history
            history.add(filteredPosition)
            return filteredPosition
        }

        return null
    }

    private fun getAccessPoint(bssid: String): NewInstalledAccessPoint? {
        bssid.trim().ifEmpty {
            return null
        }

        return installedAccessPoints
                .filter { ap -> ap.bssid.toLowerCase() == bssid }
                .firstOrNull()
    }
}