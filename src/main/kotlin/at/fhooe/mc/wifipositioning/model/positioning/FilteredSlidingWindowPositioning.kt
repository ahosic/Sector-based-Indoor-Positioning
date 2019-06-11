package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.SectorLowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.model.metrics.MetricType

/**
 * A Positioning method that uses a combination of SlidingWindowPositioning and a low pass-filter for the position estimation.
 *
 * @property positioning the method of positioning that is used before filtering (default SlidingWindowPositioning)
 * @property filtering the filter that that should be applied on the estimated sector (default SectorLowPassFilter)
 *
 * @constructor Initializes a new Positioning Method
 */
class FilteredSlidingWindowPositioning(building: Building, metricType: MetricType) : Positioning {
    private val positioning: Positioning
    private val filtering: Filtering<InstalledAccessPoint>

    init {
        positioning = SlidingWindowPositioning(building, 5, metricType)
        filtering = SectorLowPassFilter(3)
    }

    /**
     * Calculates a sector estimation using [scannedAccessPointList].
     *
     * @return an estimation of a sector
     */
    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        // Calculate Position
        val averagePos = positioning.estimateSector(scannedAccessPointList)

        // Filter Position
        averagePos?.let {
            if (it.sectors.isEmpty()) return null

            val filtered = filtering.filter(it.sectors.first())
            return SectorEstimation(listOf(filtered), null)
        }

        return null
    }
}
