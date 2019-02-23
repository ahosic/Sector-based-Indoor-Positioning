package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty
import java.awt.image.BufferedImage

data class NewFloor(@JsonProperty("name") val name: String,
                    @JsonProperty("floorNumber") val floorNumber: Int,
                    @JsonProperty("bounds") val bounds: NewFloorBounds,
                    @JsonProperty("accessPoints") val accessPoints: List<NewInstalledAccessPoint>) {

    var floorImage: BufferedImage? = null
}