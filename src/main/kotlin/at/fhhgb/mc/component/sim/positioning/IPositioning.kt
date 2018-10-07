package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

/**
 * An interface for calculating positions.
 */
interface IPositioning {
    /**
     * Calculates a sector-based position using detected access points.
     *
     * @param accessPointList a list of detected access points.
     * @return a sector-based position
     */
    fun calculatePosition(accessPointList: List<AccessPoint>): Position
}
