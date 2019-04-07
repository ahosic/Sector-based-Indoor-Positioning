package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A simulated walk route within a building.
 *
 * @property id the ID of the route
 * @property description the description of the route
 * @property waypointList a list of waypoints along the route
 */
data class Route(@JsonProperty("RouteIdentifier") val id: String,
                 @JsonProperty("RouteDescription") val description: String,
                 @JsonProperty("WaypointList") val waypointList: List<Waypoint>)