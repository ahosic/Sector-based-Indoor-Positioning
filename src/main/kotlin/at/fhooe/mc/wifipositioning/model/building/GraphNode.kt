package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A sector node within a building graph.
 *
 * @property id of the sector
 * @property neighbors the sector's reachable, allowed neighbors
 */
data class GraphNode(@JsonProperty("id") val id: String,
                     @JsonProperty("neighbors") val neighbors: List<String>)