package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.model.slidingwindow.AccessPointSlidingWindow
import at.fhooe.mc.wifipositioning.model.slidingwindow.MetricType
import at.fhooe.mc.wifipositioning.model.slidingwindow.SlidingWindow

/**
 * A Positioning method that uses the access point with the best metric at a specific point in time.
 *
 * @property building the building object containing all needed context information
 * @property windowSize the size of the sliding window
 * @property slidingWindow a sliding window, which computes the best access point with the best metric
 *
 * @constructor Initializes a new Positioning Method.
 */
class SlidingWindowPositioning(private val building: Building,
                               private val windowSize: Int,
                               metricType: MetricType) : Positioning {

    private val slidingWindow: SlidingWindow<ScannedAccessPoint, InstalledAccessPoint, String>

    init {
        slidingWindow = AccessPointSlidingWindow(
                windowSize,
                building.mode,
                null,
                metricType)
    }

    /**
     * Calculates a sector estimation using [scannedAccessPointList].
     *
     * @return an estimation of a sector
     */
    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        slidingWindow.add(scannedAccessPointList)

        val bssid = slidingWindow.getBestItem()

        if (bssid.isNullOrEmpty()) return null

        val accessPoint = findSector(bssid)
        accessPoint?.let {
            return SectorEstimation(listOf(it), null)
        }
        
        return null
    }

    /**
     * Finds a sector/installed access point with [bssid] as its BSSID.
     *
     * @return an installed access point with [bssid] as its BSSID.
     */
    private fun findSector(bssid: String): InstalledAccessPoint? {
        bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = bssid.toLowerCase()
        if(building.mode == AccessPointIdentificationMode.FiveBytePrefixIdentification) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return building.accessPoints
                .filter { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}
                .firstOrNull()
    }
}
