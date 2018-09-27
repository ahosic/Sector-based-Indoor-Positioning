package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
abstract class BasePositioning(val accessPointList: List<AccessPoint>) : IPositioning {
    abstract override fun calculatePosition(accessPointList: List<AccessPoint>): Position
}
