package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class FloorBounds(@JsonProperty("left") val left: Double,
                       @JsonProperty("right") val right: Double,
                       @JsonProperty("top") val top: Double,
                       @JsonProperty("bottom") val bottom: Double,
                       @JsonProperty("width") val width: Double,
                       @JsonProperty("height") val heigth: Double)