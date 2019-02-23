package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class NewBuilding(@JsonProperty("name") val name: String,
                       @JsonProperty("floors") val floors: List<NewFloor>) {

    val allAccessPoints: List<NewInstalledAccessPoint>
        get() {
            val accessPointList = ArrayList<NewInstalledAccessPoint>()
            for (floor in floors) {
                floor.accessPoints.let { accessPoints ->
                    accessPointList.addAll(accessPoints)
                }
            }
            return accessPointList
        }
}