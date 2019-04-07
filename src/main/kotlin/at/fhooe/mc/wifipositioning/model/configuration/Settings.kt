package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.model.positioning.PositioningType
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * An object, which holds the paths to the config files and the positioning type.
 *
 * @property floorsPath the path to the building plan image
 * @property buildingPath the path to the building file
 * @property walkRecordingPath the path to the walk recording file
 * @property routePath the path to the walk route that should be simulated
 * @property buildingGraphPath the path to the graph file, which defines the building's structure
 * @property positioningType the type of the positioning method
 */
data class Settings(@JsonProperty("floorsPath") val floorsPath: String,
                    @JsonProperty("buildingPath") val buildingPath: String,
                    @JsonProperty("walkRecordingPath") val walkRecordingPath: String,
                    @JsonProperty("routePath") val routePath: String,
                    @JsonProperty("buildingGraphPath") val buildingGraphPath: String,
                    @JsonProperty("positioningType") val positioningType: PositioningType)