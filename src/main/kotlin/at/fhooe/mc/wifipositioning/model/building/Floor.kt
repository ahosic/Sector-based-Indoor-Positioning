package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty
import java.awt.image.BufferedImage

data class Floor(@JsonProperty("name") val name: String,
                 @JsonProperty("floorNumber") val floorNumber: Int,
                 @JsonProperty("bounds") val bounds: FloorBounds,
                 @JsonProperty("accessPoints") val accessPoints: List<InstalledAccessPoint>) {

    var floorImage: BufferedImage? = null
}