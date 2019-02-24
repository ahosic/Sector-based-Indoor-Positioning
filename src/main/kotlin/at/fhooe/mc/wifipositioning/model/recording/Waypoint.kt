package at.fhooe.mc.wifipositioning.model.recording

import at.fhooe.mc.wifipositioning.model.building.Position
import com.fasterxml.jackson.annotation.JsonProperty

class Waypoint(@JsonProperty("WaypointID") val id: Int,
               @JsonProperty("WaypointDescription") val description: String,
               @JsonProperty("Floor") val floor: Int,
               @JsonProperty("Position") val position: Position)