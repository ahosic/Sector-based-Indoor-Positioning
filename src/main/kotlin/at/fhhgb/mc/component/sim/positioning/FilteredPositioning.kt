package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

/**
 * A Positioning method that uses a combination of AveragePositioning and a low pass-filter for the position estimation.
 *
 * @property allAccessPoints a list of all installed access points in the building.
 * @property positioning the method of positioning that is used before filtering (default AveragePositioning)
 * @property filtering the filter that that should be applied on the estimated position (default PositionLowPassFilter)
 *
 * @constructor Initializes a new Positioning Method
 */
class FilteredPositioning(private val allAccessPoints: List<AccessPoint>) : IPositioning {
    private val positioning: IPositioning
    private val filtering: IFilterable<Position>

    init {
        positioning = AveragePositioning(allAccessPoints, 5)
        filtering = PositionLowPassFilter(0.04f)
    }

    override fun calculatePosition(accessPointList: List<AccessPoint>): Position {
        // Calculate Position
        val averagePos = positioning.calculatePosition(accessPointList)

        // Filter Position

        return filtering.filter(averagePos)
    }
}
