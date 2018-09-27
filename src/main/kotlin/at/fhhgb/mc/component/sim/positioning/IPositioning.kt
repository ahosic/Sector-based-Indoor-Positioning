package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

interface IPositioning {
    fun calculatePosition(accessPointList: List<AccessPoint>): Position
}
