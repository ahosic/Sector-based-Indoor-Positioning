package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class GraphNode(@JsonProperty("id") val id: String,
                     @JsonProperty("neighbors") val neighbors: List<String>)