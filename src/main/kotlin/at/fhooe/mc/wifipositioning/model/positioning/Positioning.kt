package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * An interface for calculating positions.
 */
interface Positioning {
    /**
     * Calculates a sector-based position using detected access points.
     *
     * @param scannedAccessPointList a list of detected access points.
     * @return a sector-based position
     */
    fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation?
}
