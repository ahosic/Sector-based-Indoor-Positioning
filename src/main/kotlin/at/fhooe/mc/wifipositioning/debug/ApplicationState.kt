package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.positioning.SectorEstimation
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

data class ApplicationState(val scannedAccessPoints: List<ScannedAccessPoint>,
                            val currentEstimation: SectorEstimation?,
                            val allowedSectors: List<InstalledAccessPoint>?,
                            val wayPointNumber: Int,
                            val interpolationStep: Int)