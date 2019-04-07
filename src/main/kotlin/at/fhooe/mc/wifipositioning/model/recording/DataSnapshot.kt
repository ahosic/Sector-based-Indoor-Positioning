package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A snapshot during a recording containing multiple Wi-Fi data of multiple detected SSIDs
 *
 * @property wifiDataList a list of captured Wi-Fi data
 * @property timestamp the timestamp of the recording
 * @property wayPointID the associated waypoint ID of the recording
 * @property routeIdentifier the ID of the route
 */
data class DataSnapshot(@JsonProperty("WLanData") val wifiDataList: List<WifiData>,
                        @JsonProperty("TimeStamp") val timestamp: Long,
                        @JsonProperty("WaypointID") val wayPointID: Int,
                        @JsonProperty("RouteIdentifier") val routeIdentifier: String)