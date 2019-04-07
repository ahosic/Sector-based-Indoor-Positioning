package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.positioning.SectorEstimation
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * Holds information about the current application state.
 *
 * @property scannedAccessPoints the currently scanned access points
 * @property currentEstimation the last valid estimation
 * @property allowedSectors the currently allowed sectors
 * @property wayPointNumber the current waypoint number of the simulation
 * @property interpolationStep the current interpolation step of the simulation.
 */
data class ApplicationState(val scannedAccessPoints: List<ScannedAccessPoint>,
                            val currentEstimation: SectorEstimation?,
                            val allowedSectors: List<InstalledAccessPoint>?,
                            val wayPointNumber: Int,
                            val interpolationStep: Int)