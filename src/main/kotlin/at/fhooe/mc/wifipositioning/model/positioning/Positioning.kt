package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * An interface for a positioning algorithm
 */
interface Positioning {
    /**
     * Calculates a sector estimation using detected access points.
     *
     * @param scannedAccessPointList a list of detected access points.
     * @return an estimation of a sector
     */
    fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation?
}
