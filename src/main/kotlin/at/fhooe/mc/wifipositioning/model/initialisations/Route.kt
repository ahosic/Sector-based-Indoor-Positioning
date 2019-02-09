package at.fhooe.mc.wifipositioning.model.initialisations

import com.fasterxml.jackson.annotation.JsonProperty

data class Route(
        @JsonProperty("RouteIdentifier")
        val id: String,

        @JsonProperty("RouteDescription")
        val description: String,

        @JsonProperty("WaypointList")
        val waypointList: List<Waypoint>)