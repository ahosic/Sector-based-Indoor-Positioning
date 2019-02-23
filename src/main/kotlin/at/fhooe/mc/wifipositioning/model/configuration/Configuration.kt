package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.model.positioning.PositioningType
import com.fasterxml.jackson.annotation.JsonProperty

data class Configuration(@JsonProperty("floorsPath") val floorsPath: String,
                         @JsonProperty("walkRecordingPath") val walkRecordingPath: String,
                         @JsonProperty("routePath") val routePath: String,
                         @JsonProperty("buildingGraphPath") val buildingGraphPath: String,
                         @JsonProperty("positioningType") val positioningType: PositioningType)