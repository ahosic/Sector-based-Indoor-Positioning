package at.fhooe.mc.wifipositioning.model.building

import at.fhooe.mc.wifipositioning.model.simulation.Position
import com.fasterxml.jackson.annotation.JsonProperty

data class NewInstalledAccessPoint(@JsonProperty("id") val id: String,
                                   @JsonProperty("description") val description: String,
                                   @JsonProperty("floorNumber") val floorNumber: Int,
                                   @JsonProperty("bssid") val bssid: String,
                                   @JsonProperty("position") val position: Position)