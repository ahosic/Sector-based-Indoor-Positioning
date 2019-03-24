package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.debug.DebugLogEntry
import at.fhooe.mc.wifipositioning.debug.DebugLogEntryCategory
import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.EstimationLowPassFilter
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.RSSILowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import kotlin.streams.toList

class GraphPositioning(private val installedAccessPoints: List<InstalledAccessPoint>,
                       private val graph: List<BuildingGraphNode>,
                       windowSize: Int) : Positioning {

    private val history: MutableList<SectorEstimation> = arrayListOf()
    private val filtering: Filtering<SectorEstimation>
    private val slidingWindow: AccessPointSlidingWindow
    private val mode: AccessPointIdentificationMode

    private val tag = "Graph Positioning"

    init {
        mode = AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION
        filtering = EstimationLowPassFilter(4)
        slidingWindow = AccessPointSlidingWindow(windowSize, mode, RSSILowPassFilter(0.1f))
    }

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        slidingWindow.addElement(scannedAccessPointList)

        // Generate allowed path a user can go
        val allowedAccessPoints = getAllowedSectors()
        App.debugger.log(DebugLogEntry(tag, "Allowed Access Points:", DebugLogEntryCategory.Positioning))
        printAccessPoints(allowedAccessPoints)

        // Find best BSSID
        val bssid = slidingWindow.getBestAverageBSSID(allowedAccessPoints)
        App.debugger.log(DebugLogEntry(tag, "Best BSSID: $bssid", DebugLogEntryCategory.Positioning))

        // Retrieve sectors for best BSSID
        val sectors = getSectors(bssid, allowedAccessPoints)

        if (sectors.isNullOrEmpty()) return null

        // Create estimation
        val estimation = getEstimationFor(sectors)
        App.debugger.log(DebugLogEntry(tag, "Estimated Sector: $estimation", DebugLogEntryCategory.Positioning))

        // Filter estimation
        val filteredEstimation = filtering.filter(estimation)
        App.debugger.log(DebugLogEntry(tag, "Filtered Position: $filteredEstimation", DebugLogEntryCategory.Filtering))

        // Add position to history
        history.add(filteredEstimation)

        return filteredEstimation
    }

    private fun getEstimationFor(sectors: MutableList<InstalledAccessPoint>): SectorEstimation {
        // If more paths are possible, then add the current sector to the possible sectors
        if(sectors.size > 1) {
            // Find neighboring sectors
            val newAllowedSectors = mutableListOf<InstalledAccessPoint>()
            sectors.forEach {
                newAllowedSectors.addAll(getAllowedSectorsFor(it))
            }

            val lastEstimation = history.last()
            val allPaths = lastEstimation.sectors.filter {
                newAllowedSectors.contains(it)
            }

            sectors.addAll(allPaths)
        }

        // Generate estimation
        return SectorEstimation(sectors.toSet().toList())
    }

    private fun getSectors(bssid: String, accessPoints: List<InstalledAccessPoint>): MutableList<InstalledAccessPoint>? {
        bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = bssid.toLowerCase()
        if(mode == AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return accessPoints.filter { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}.toMutableList()
    }

    private fun getAllowedSectors(): List<InstalledAccessPoint> {
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

    private fun getAllowedSectorsFor(sector: InstalledAccessPoint): List<InstalledAccessPoint> {
        val nodes = graph
                .filter { node -> sector.id == node.id }
                .toList()

        return installedAccessPoints
                .stream()
                .filter { ap -> nodes.any { node -> node.neighbors.contains(ap.id) } }
                .toList()
    }

    private fun printAccessPoints(accessPoints: List<InstalledAccessPoint>) {
        accessPoints.forEach {
            App.debugger.log(DebugLogEntry(tag, it.toString(), DebugLogEntryCategory.Positioning))
        }
    }
}