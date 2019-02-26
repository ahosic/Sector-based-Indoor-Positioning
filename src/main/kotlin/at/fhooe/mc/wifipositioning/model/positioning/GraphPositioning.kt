package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.SectorLowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import kotlin.streams.toList

class GraphPositioning(private val installedAccessPoints: List<InstalledAccessPoint>,
                       private val graph: List<BuildingGraphNode>,
                       windowSize: Int) : Positioning {

    private val history: MutableList<InstalledAccessPoint> = arrayListOf()
    private val filtering: Filtering<InstalledAccessPoint>
    private val slidingWindow: AccessPointSlidingWindow
    private val mode: AccessPointIdentificationMode

    init {
        mode = AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION
        filtering = SectorLowPassFilter(5)
        slidingWindow = AccessPointSlidingWindow(windowSize, mode)
    }

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): InstalledAccessPoint? {
        slidingWindow.addElement(scannedAccessPointList)

        val allowedAccessPoints = getAllowedAccessPoints()
        val bssid = slidingWindow.getBestAverageBSSID(allowedAccessPoints)

        // Retrieve sector
        val accessPoint = getAccessPoint(bssid, allowedAccessPoints)

        accessPoint?.let {
            val filteredPosition = filtering.filter(it)

            // Add position to history
            history.add(filteredPosition)
            return filteredPosition
        }

        return null
    }

    private fun getAccessPoint(bssid: String, accessPoints: List<InstalledAccessPoint>): InstalledAccessPoint? {
        bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = bssid.toLowerCase()
        if(mode == AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return accessPoints.firstOrNull { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}
    }

    private fun getAllowedAccessPoints(): List<InstalledAccessPoint> {
        if(history.size == 0) {
            return installedAccessPoints
                    .stream()
                    .filter { ap -> ap.id == "4-10" }
                    .toList()
        } else {
            // Get neighbors from last position
            val lastAccessPoint = history.last()
            val node = graph
                    .filter { node -> node.id == lastAccessPoint.id }
                    .first()

            return installedAccessPoints
                    .stream()
                    .filter { ap -> node.neighbors.contains(ap.id) }
                    .toList()
        }
    }
}