package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.simulation.recording.ScannedAccessPoint

/**
 * An interface for calculating positions.
 */
interface IPositioning {
    /**
     * Calculates a sector-based position using detected access points.
     *
     * @param scannedAccessPointList a list of detected access points.
     * @return a sector-based position
     */
    fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): Position
}
