package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class Building(@JsonProperty("name") val name: String,
                    @JsonProperty("ssid") val ssid: String,
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