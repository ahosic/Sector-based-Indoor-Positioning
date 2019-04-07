package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Holds information about a scanned access point.
 *
 * @property bssid the BSSID address of the access point.
 * @property ssid the SSID of the access point
 * @property signalLevel the signal level (RSSI) of the Wi-Fi signal in dbM.
 * @property frequency the frequency of the Wi-Fi signal
 * @property capabilities the capabilities of the access point.
 * @property timestamp the timestamp of the detection.
 */
data class ScannedAccessPoint(@JsonProperty("bssid") val bssid: String,
                              @JsonProperty("ssid") val ssid: String,
                              @JsonProperty("signalLevel") var signalLevel: Int,
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