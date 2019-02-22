package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class NewFloor(@JsonProperty("name") val name: String,
                    @JsonProperty("floorNumber") val floorNumber: Int,
                    @JsonProperty("bounds") val bounds: NewFloorBounds,
                    @JsonProperty("accessPoints") val accessPoints: List<NewInstalledAccessPoint>)