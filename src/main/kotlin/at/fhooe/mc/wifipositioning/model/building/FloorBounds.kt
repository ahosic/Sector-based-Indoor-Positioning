package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Holds the bounds of a floor, which are used for drawing the sectors on the building plan.
 *
 * @property left the left bound
 * @property right the right bound
 * @property top the top bound
 * @property bottom the bottom bound
 * @property width the width of the floor
 * @property heigth the height of the floor
 */
data class FloorBounds(@JsonProperty("left") val left: Double,
                       @JsonProperty("right") val right: Double,
                       @JsonProperty("top") val top: Double,
                       @JsonProperty("bottom") val bottom: Double,
                       @JsonProperty("width") val width: Double,
                       @JsonProperty("height") val heigth: Double)