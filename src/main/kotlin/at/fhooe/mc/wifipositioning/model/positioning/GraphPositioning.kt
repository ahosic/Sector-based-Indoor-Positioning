package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.debug.DebugLogEntry
import at.fhooe.mc.wifipositioning.debug.DebugLogEntryCategory
import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.SectorLowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import kotlin.streams.toList

class GraphPositioning(private val installedAccessPoints: List<InstalledAccessPoint>,
                       private val graph: List<BuildingGraphNode>,
                       windowSize: Int) : Positioning {

    private val history: MutableList<SectorEstimation> = arrayListOf()
    private val filtering: Filtering<InstalledAccessPoint>
    private val slidingWindow: AccessPointSlidingWindow
    private val mode: AccessPointIdentificationMode

    private val tag = "Graph Positioning"

    init {
        mode = AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION
        filtering = SectorLowPassFilter(5)
        slidingWindow = AccessPointSlidingWindow(windowSize, mode)
    }

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        slidingWindow.addElement(scannedAccessPointList)

        val allowedAccessPoints = getAllowedAccessPoints()
        App.debugger.log(DebugLogEntry(tag, "Allowed Access Points:", DebugLogEntryCategory.Positioning))
        printAccessPoints(allowedAccessPoints)

        val bssid = slidingWindow.getBestAverageBSSID(allowedAccessPoints)
        App.debugger.log(DebugLogEntry(tag, "Best BSSID: $bssid", DebugLogEntryCategory.Positioning))

        // Retrieve sector
        val accessPoints = getAccessPoints(bssid, allowedAccessPoints)
        //App.debugger.log(DebugLogEntry(tag, "Best Access Point: $accessPoint", DebugLogEntryCategory.Positioning))

        accessPoints?.let {
//            val filteredPosition = filtering.filter(it)
//            App.debugger.log(DebugLogEntry(tag, "Filtered Position: $filteredPosition", DebugLogEntryCategory.Positioning))

            if (accessPoints.isEmpty()) return null

            val estimation = SectorEstimation(accessPoints)

            // Add position to history
            history.add(estimation)

            return estimation
        }

        return null
    }

    private fun getAccessPoints(bssid: String, accessPoints: List<InstalledAccessPoint>): List<InstalledAccessPoint>? {
        bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = bssid.toLowerCase()
        if(mode == AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return accessPoints.filter { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}
    }

    private fun getAllowedAccessPoints(): List<InstalledAccessPoint> {
        if(history.size == 0) {
            return installedAccessPoints
                    .stream()
                    .filter { ap -> ap.id == "4-10" }
                    .toList()
        } else {
            // Get neighbors from last position
            val lastEstimation= history.last()
            val nodes = graph
                    .filter { node -> lastEstimation.sectors.any { it.id == node.id }}
                    .toList()

            return installedAccessPoints
                    .stream()
                    .filter { ap -> nodes.any { node -> node.neighbors.contains(ap.id) } }
                    .toList()
        }
    }

    private fun printAccessPoints(accessPoints: List<InstalledAccessPoint>) {
        accessPoints.forEach {
            App.debugger.log(DebugLogEntry(tag, it.toString(), DebugLogEntryCategory.Positioning))
        }
    }
}