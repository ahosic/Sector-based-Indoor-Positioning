package at.fhooe.mc.wifipositioning.model.slidingwindow

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.positioning.AccessPointIdentificationMode
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import java.util.HashMap

class NewAccessPointSlidingWindow(private val windowSize: Int,
                                  private val mode: AccessPointIdentificationMode = AccessPointIdentificationMode.SIX_BYTE_IDENTIFICATION,
                                  private val filter: Filtering<ScannedAccessPoint>?) {

    private var accessPointLists: MutableList<List<ScannedAccessPoint>> = mutableListOf()
    private var allowedBSSIDs: List<String>? = null

    fun addAccessPoints(accessPoints: List<ScannedAccessPoint>) {
        if (accessPointLists.size > windowSize) {
            accessPointLists.removeAt(0)
        }

        accessPointLists.add(accessPoints)
    }

    fun getBestBSSID(allowedAccessPoints: List<InstalledAccessPoint>? = null): String? {
        val computations = computeMetric()
        setAllowedBSSIDs(allowedAccessPoints)

        // Find BSSID with best signal level computed by Metric
        val bestEntry = computations.entries
                .filter { entry -> isValidBSSID(entry.key.toLowerCase()) }
                .maxBy { entry -> entry.value.compute() }

        return bestEntry?.key
    }

    private fun computeMetric(): Map<String, Metric> {
        val computations = HashMap<String, Metric>()
        val allScannedAccessPoints = accessPointLists.flatten()

        // Generate Average Signal Levels per BSSID and store into Map
        for (scannedAccessPoint in allScannedAccessPoints) {
            var ap = scannedAccessPoint
            if (filter != null) {
                ap = filter.filter(scannedAccessPoint)
            }

            val computed = computations[ap.bssid]
            if (computed != null) {
                computed.add(ap.signalLevel.toDouble())
            } else {
                val metric = AverageMetric()
                metric.add(ap.signalLevel.toDouble())

                computations[ap.bssid] = metric
            }
        }

        return computations
    }

    private fun isValidBSSID(bssid: String): Boolean {
        return allowedBSSIDs?.any { bssid.startsWith(it) } ?: true
    }

    private fun setAllowedBSSIDs(allowedAccessPoints: List<InstalledAccessPoint>?) {
        if(allowedAccessPoints == null) return

        allowedBSSIDs = when (mode) {
            AccessPointIdentificationMode.SIX_BYTE_IDENTIFICATION -> allowedAccessPoints
                    .map { ap -> ap.bssid.toLowerCase() }
                    .toList()
            AccessPointIdentificationMode.FIVE_BYTE_IDENTIFICATION -> allowedAccessPoints
                    .map { ap -> ap.fiveBytePrefix.toLowerCase() }
                    .toList()
        }
    }

}