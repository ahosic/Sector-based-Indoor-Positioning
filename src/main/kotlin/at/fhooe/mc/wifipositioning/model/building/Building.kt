package at.fhooe.mc.wifipositioning.model.building

import at.fhooe.mc.wifipositioning.model.positioning.AccessPointIdentificationMode
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Holds context-information about the building.
 *
 * @property name the name of the building
 * @property ssid the SSID of the building's access points
 * @property mode the identification mode that should be used when BSSIDs are compared.
 * @property floors a list of the building's floors
 * @property accessPoints a list of all access points of the building.
 */
data class Building(@JsonProperty("name") val name: String,
                    @JsonProperty("ssid") val ssid: String,
                    @JsonProperty("identificationMode") val mode: AccessPointIdentificationMode,
                    @JsonProperty("floors") val floors: List<Floor>) {

    val accessPoints: List<InstalledAccessPoint>
        get() {
            val accessPointList = ArrayList<InstalledAccessPoint>()
            for (floor in floors) {
                floor.accessPoints.let { accessPoints ->
                    accessPointList.addAll(accessPoints)
                }
            }
            return accessPointList
        }
}