package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * A Positioning method that uses the strongest access point (highest signal strength) for the position estimation.
 *
 * @property building the building object containing all needed context information
 * @constructor Initializes a new Positioning Method
 */
class StrongestRSSIPositioning(val building: Building) : Positioning {

    /**
     * Calculates a sector estimation using [scannedAccessPointList].
     *
     * @return an estimation of a sector
     */
    override fun estimateSector(scannedAccessPointList: List<ScannedAccessPoint>): SectorEstimation? {
        for (scanned in scannedAccessPointList.sortedByDescending { it.signalLevel }) {
            val sector = findSector(scanned)
            sector?.let {
                return SectorEstimation(listOf(it), null)
            }
        }

        return null
    }

    /**
     * Finds a sector/installed access point based on the BSSID of [scannedAccessPoint].
     *
     * @return an installed access point with the corresponding BSSID of [scannedAccessPoint].
     */
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
