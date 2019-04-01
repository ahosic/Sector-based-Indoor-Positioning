package at.fhooe.mc.wifipositioning.model.building

import at.fhooe.mc.wifipositioning.model.positioning.AccessPointIdentificationMode
import com.fasterxml.jackson.annotation.JsonProperty

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