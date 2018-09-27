package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

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
