package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class Graph(@JsonProperty("entrances") val entrances: List<String>,
                 @JsonProperty("nodes") val nodes: List<GraphNode>)