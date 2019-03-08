package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

data class ApplicationState(val scannedAccessPoints: List<ScannedAccessPoint>,
                            val currentSector: InstalledAccessPoint?,
                            val allowedSectors: List<InstalledAccessPoint>?)