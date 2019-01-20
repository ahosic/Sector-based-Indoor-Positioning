package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.simulation.recording.ScannedAccessPoint

/**
 * A Positioning method that uses the strongest access point (highest signal strength) for the position estimation.
 *
 * @property allAccessPoints a list of all installed access points in the building.
 *
 * @constructor Initializes a new Positioning Method
 */
class StrongestAccessPointPositioning(val allAccessPoints: List<InstalledAccessPoint>) : IPositioning {

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): InstalledAccessPoint? {
        for (scanned in scannedAccessPointList.sorted().reversed()) {
            allAccessPoints
                    .filter { it.bssid.toLowerCase() == scanned.bssid.toLowerCase() }
                    .firstOrNull()?.let {
                        return it
                    }
        }

        return null
    }
}
