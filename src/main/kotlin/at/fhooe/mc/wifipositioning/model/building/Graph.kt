package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Defines the allowed, walkable paths within a building.
 *
 * @property entrances the allowed entrances to a building
 * @property nodes a list of sectors and its reachable neighbors
 */
data class Graph(@JsonProperty("entrances") val entrances: List<String>,
                 @JsonProperty("nodes") val nodes: List<GraphNode>)