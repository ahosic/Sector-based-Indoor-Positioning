package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

data class ScannedAccessPoint(@JsonProperty("bssid") val bssid: String,
                              @JsonProperty("ssid") val ssid: String,
                              @JsonProperty("signalLevel") val signalLevel: Int,
                              @JsonProperty("frequency") val frequency: Int,
                              @JsonProperty("capabilities") val capabilities: String,
                              @JsonProperty("timeStamp") val timestamp: Long): Comparable<ScannedAccessPoint> {

    override operator fun compareTo(o: ScannedAccessPoint): Int {
        return Integer.compare(signalLevel, o.signalLevel)
    }

    override fun toString(): String {
        return "$bssid [$signalLevel dbM]"
    }
}