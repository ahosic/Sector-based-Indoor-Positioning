package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * A Positioning method that uses the strongest access point (highest signal strength) for the position estimation.
 *
 * @property building The building object containing all needed context information
 *
 * @constructor Initializes a new Positioning Method
 */
class StrongestAccessPointPositioning(val building: Building) : Positioning {

    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        for (scanned in scannedAccessPointList.sortedByDescending { it.signalLevel }) {
            val sector = findSector(scanned)
            sector?.let {
                return SectorEstimation(listOf(it), null)
            }
        }

        return null
    }

    private fun findSector(scannedAccessPoint: ScannedAccessPoint): InstalledAccessPoint? {
        scannedAccessPoint.bssid.trim().ifEmpty {
            return null
        }

        var searchedBSSID = scannedAccessPoint.bssid.toLowerCase()
        if(building.mode == AccessPointIdentificationMode.FiveBytePrefixIdentification) {
            searchedBSSID = searchedBSSID.substringBeforeLast(":")
        }

        return building.accessPoints
                .filter { ap -> ap.bssid.toLowerCase().startsWith(searchedBSSID)}
                .firstOrNull()
    }
}
