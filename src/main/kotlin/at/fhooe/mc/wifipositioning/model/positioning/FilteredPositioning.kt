package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.filtering.SectorLowPassFilter
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * A Positioning method that uses a combination of AveragePositioning and a low pass-filter for the position estimation.
 *
 * @property allAccessPoints a list of all installed access points in the building.
 * @property positioning the method of positioning that is used before filtering (default AveragePositioning)
 * @property filtering the filter that that should be applied on the estimated position (default PositionLowPassFilter)
 *
 * @constructor Initializes a new Positioning Method
 */
class FilteredPositioning(private val allAccessPoints: List<InstalledAccessPoint>) : Positioning {
    private val positioning: Positioning
    private val filtering: Filtering<InstalledAccessPoint>

    init {
        positioning = AveragePositioning(allAccessPoints, 5)
        filtering = SectorLowPassFilter(10)
    }

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        // Calculate Position
        val averagePos = positioning.calculatePosition(scannedAccessPointList)

        // Filter Position
        averagePos?.let {
            if (it.sectors.isEmpty()) return null

            val filtered = filtering.filter(it.sectors.first())
            return SectorEstimation(listOf(filtered))
        }

        return null
    }
}
