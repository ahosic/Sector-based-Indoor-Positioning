package at.fhooe.mc.wifipositioning.model.recording

import at.fhooe.mc.wifipositioning.model.building.Position
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A point within a simulated, walked route.
 *
 * @property id the ID of the waypoint
 * @property description the description of the waypoint
 * @property floor the floor of the waypoint
 * @property position the position of the waypoint on the building plan.
 */
class Waypoint(@JsonProperty("WaypointID") val id: Int,
               @JsonProperty("WaypointDescription") val description: String,
               @JsonProperty("Floor") val floor: Int,
               @JsonProperty("Position") val position: Position)