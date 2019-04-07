package at.fhooe.mc.wifipositioning.model.recording

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A collection of multiple detected access points for a specific SSID.
 *
 * @property ssid the common SSID of the detected access points
 * @property scannedAccessPoints a list of detected access points for a specific SSID.
 */
data class WifiData(@JsonProperty("ssid") val ssid: String,
                    @JsonProperty("accessPoints") val scannedAccessPoints: List<ScannedAccessPoint>)