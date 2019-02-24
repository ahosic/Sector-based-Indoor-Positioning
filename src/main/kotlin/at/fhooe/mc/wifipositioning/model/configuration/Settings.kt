package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.model.positioning.PositioningType
import com.fasterxml.jackson.annotation.JsonProperty

data class Settings(@JsonProperty("floorsPath") val floorsPath: String,
                    @JsonProperty("buildingPath") val buildingPath: String,
                    @JsonProperty("walkRecordingPath") val walkRecordingPath: String,
                    @JsonProperty("routePath") val routePath: String,
                    @JsonProperty("buildingGraphPath") val buildingGraphPath: String,
                    @JsonProperty("positioningType") val positioningType: PositioningType)