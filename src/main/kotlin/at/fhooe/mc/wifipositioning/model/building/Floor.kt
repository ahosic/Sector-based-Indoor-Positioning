package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty
import java.awt.image.BufferedImage

/**
 * Holds information about a floor of a building.
 *
 * @property name of the floor
 * @property floorNumber the number of the floor
 * @property bounds the bounds of the floor that are used for drawing the sectors on the building plan
 * @property accessPoints the floor's access points.
 * @property floorImage the image of the floor (extracted out of the building plan).
 */
data class Floor(@JsonProperty("name") val name: String,
                 @JsonProperty("floorNumber") val floorNumber: Int,
                 @JsonProperty("bounds") val bounds: FloorBounds,
                 @JsonProperty("accessPoints") val accessPoints: List<InstalledAccessPoint>) {

    var floorImage: BufferedImage? = null
}