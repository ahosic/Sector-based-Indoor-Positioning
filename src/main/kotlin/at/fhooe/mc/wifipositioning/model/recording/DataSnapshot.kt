package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

data class DataSnapshot(@JsonProperty("WLanData") val wifiDataList: List<WifiData>,
                        @JsonProperty("TimeStamp") val timestamp: Long,
                        @JsonProperty("WaypointID") val wayPointID: Int,
                        @JsonProperty("RouteIdentifier") val routeIdentifier: String)