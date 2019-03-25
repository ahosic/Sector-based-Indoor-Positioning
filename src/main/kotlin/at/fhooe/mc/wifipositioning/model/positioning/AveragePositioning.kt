package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.model.slidingwindow.AccessPointSlidingWindow
import at.fhooe.mc.wifipositioning.model.slidingwindow.MetricType
import at.fhooe.mc.wifipositioning.model.slidingwindow.SlidingWindow

/**
 * A Positioning method that uses the access point with the best average signal strength at a specific point in time.
 *
 * @property building The building object containing all needed context information
 * @property windowSize the size of the sliding window
 *
 * @constructor Initializes a new Positioning Method.
 */
class AveragePositioning(private val building: Building,
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

    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        slidingWindow.add(scannedAccessPointList)

        val bssid = slidingWindow.getBestItem()

        if (bssid.isNullOrEmpty()) return null

        val accessPoint = findSector(bssid)
        accessPoint?.let {
            return SectorEstimation(listOf(it))
        }
        
        return null
    }

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
