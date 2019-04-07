package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * An interface for a positioning method
 */
interface Positioning {
    /**
     * Calculates a sector estimation using [scannedAccessPointList].
     *
     * @return an estimation of a sector
     */
    fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation?
}
