package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

data class WifiData(@JsonProperty("ssid") val ssid: String,
                    @JsonProperty("accessPoints") val scannedAccessPoints: List<ScannedAccessPoint>)