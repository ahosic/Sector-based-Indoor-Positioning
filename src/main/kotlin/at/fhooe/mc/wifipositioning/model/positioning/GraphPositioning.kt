package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.debug.DebugLogEntry
import at.fhooe.mc.wifipositioning.debug.DebugLogEntryCategory
import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.Graph
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.EstimationLowPassFilter
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.RSSILowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.model.slidingwindow.AccessPointSlidingWindow
import at.fhooe.mc.wifipositioning.model.metrics.MetricType
import at.fhooe.mc.wifipositioning.model.slidingwindow.SlidingWindow

/**
 * A Positioning Method that uses a combination of a sliding window, low pass-filters, a history and a graph for estimating a sector within a building.
 *
 * @property building the building object containing all needed context information
 * @property graph a graph of sector IDs, which define the allowed walking paths through a building
 * @property history a list of previous visited sectors
 * @property filtering the filter that that should be applied on the estimated sector (default EstimationLowPassFilter)
 * @property slidingWindow a sliding window, which computes the best access point with the best metric
 *
 * @constructor Initializes a new Positioning Method
 */
class GraphPositioning(private val building: Building,
                       private val graph: Graph,
                       windowSize: Int,
                       metricType: MetricType) : Positioning {

    private val history: MutableList<SectorEstimation> = arrayListOf()
    private val filtering: Filtering<SectorEstimation>
    private val slidingWindow: SlidingWindow<ScannedAccessPoint, InstalledAccessPoint, String>

    private val tag = "Graph Positioning"

    init {
        filtering = EstimationLowPassFilter(7)
        slidingWindow = AccessPointSlidingWindow(
                windowSize,
                building.mode,
                RSSILowPassFilter(0.1f),
                metricType)
    }

    /**
     * Calculates a sector estimation using [scannedAccessPointList].
     *
     * @return an estimation of a sector
     */
    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        slidingWindow.add(scannedAccessPointList)

        // Generate allowed path a user can go
        val allowedAccessPoints = getAllowedSectors()
        App.debugger.log(DebugLogEntry(tag, "Allowed Access Points:", DebugLogEntryCategory.Positioning))
        printAccessPoints(allowedAccessPoints)

        // Find best BSSID
        val bssid = slidingWindow.getBestItem(allowedAccessPoints)
        App.debugger.log(DebugLogEntry(tag, "Best BSSID: $bssid", DebugLogEntryCategory.Positioning))

        if (bssid == null) return null

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

    /**
     * Creates an estimation out of [sectors].
     *
     * @return a sector estimation made out of [sectors].
     */
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
        return SectorEstimation(sectors.toSet().toList(), null)
    }

    /**
     * Gets a list of sectors based on [bssid] out of [accessPoints].
     *
     * @return a list of installed access points (sectors)
     */
    private fun getSectors(bssid: String, accessPoints: List<InstalledAccessPoint>): MutableList<InstalledAccessPoint>? {
        bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = bssid.toLowerCase()
        if(building.mode == AccessPointIdentificationMode.FiveBytePrefixIdentification) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return accessPoints.filter { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}.toMutableList()
    }

    /**
     * Gets the currently allowed sectors based on the history of visited sectors.
     *
     * @return a list of installed access points (sectors)
     */
    private fun getAllowedSectors(): List<InstalledAccessPoint> {
        if(history.size == 0) {
            return building.accessPoints
                    .filter { ap -> graph.entrances.contains(ap.id) }
        } else {
            // Get neighbors from last position
            val lastEstimation= history.last()
            val nodes = graph.nodes
                    .filter { node -> lastEstimation.sectors.any { it.id == node.id }}
                    .toList()

            return building.accessPoints
                    .filter { ap -> nodes.any { node -> node.neighbors.contains(ap.id) } }
                    .toList()
        }
    }

    /**
     * Gets the allowed sectors that can be visited from [sector].
     *
     * @return a list of installed access points (sectors)
     */
    private fun getAllowedSectorsFor(sector: InstalledAccessPoint): List<InstalledAccessPoint> {
        val nodes = graph.nodes
                .filter { node -> sector.id == node.id }

        return building.accessPoints
                .filter { ap -> nodes.any { node -> node.neighbors.contains(ap.id) } }
    }

    /**
     * Prints a list of access points out to the app debugger.
     */
    private fun printAccessPoints(accessPoints: List<InstalledAccessPoint>) {
        accessPoints.forEach {
            App.debugger.log(DebugLogEntry(tag, it.toString(), DebugLogEntryCategory.Positioning))
        }
    }
}