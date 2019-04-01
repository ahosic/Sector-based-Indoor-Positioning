package at.fhooe.mc.wifipositioning.model.slidingwindow

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.filtering.Filtering
import at.fhooe.mc.wifipositioning.model.positioning.AccessPointIdentificationMode
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import java.util.HashMap

class AccessPointSlidingWindow(private val windowSize: Int,
                               private val mode: AccessPointIdentificationMode = AccessPointIdentificationMode.FullAddressIdentification,
                               private val filter: Filtering<ScannedAccessPoint>? = null,
                               private val metricType: MetricType): SlidingWindow<ScannedAccessPoint, InstalledAccessPoint, String> {

    private var accessPointLists: MutableList<List<ScannedAccessPoint>> = mutableListOf()
    private var allowedBSSIDs: List<String>? = null

    override fun add(items: List<ScannedAccessPoint>) {
        if (accessPointLists.size > windowSize) {
            accessPointLists.removeAt(0)
        }

        accessPointLists.add(items)
    }

    override fun getBestItem(restriction: List<InstalledAccessPoint>?): String? {
        val computations = computeMetric()
        setAllowedBSSIDs(restriction)

        // Find BSSID with best signal level computed by Metric
        val bestEntry = computations.entries
                .filter { entry -> isValidBSSID(entry.key.toLowerCase()) }
                .maxBy { entry -> entry.value.compute() }

        return bestEntry?.key
    }

    private fun computeMetric(): Map<String, Metric<Double, Double>> {
        val metrics = HashMap<String, Metric<Double, Double>>()
        val allScannedAccessPoints = accessPointLists.flatten()

        // Generate Average Signal Levels per BSSID and store into Map
        for (scannedAccessPoint in allScannedAccessPoints) {
            var ap = scannedAccessPoint
            if (filter != null) {
                ap = filter.filter(scannedAccessPoint)
            }

            var metric = metrics[ap.bssid]
            if (metric != null) {
                metric.add(ap.signalLevel.toDouble())
            } else {
                metric = getMetricFromType()
                metric.add(ap.signalLevel.toDouble())
                metrics[ap.bssid] = metric
            }
        }

        return metrics
    }

    private fun isValidBSSID(bssid: String): Boolean {
        return allowedBSSIDs?.any { bssid.startsWith(it) } ?: true
    }

    private fun setAllowedBSSIDs(allowedAccessPoints: List<InstalledAccessPoint>?) {
        if(allowedAccessPoints == null) return

        allowedBSSIDs = when (mode) {
            AccessPointIdentificationMode.FullAddressIdentification -> allowedAccessPoints
                    .map { ap -> ap.bssid.toLowerCase() }
                    .toList()
            AccessPointIdentificationMode.FiveBytePrefixIdentification -> allowedAccessPoints
                    .map { ap -> ap.fiveBytePrefix.toLowerCase() }
                    .toList()
        }
    }

    private fun getMetricFromType(): Metric<Double, Double> {
        return when(metricType) {
            MetricType.ArithmeticMean -> ArithmeticMeanMetric()
            MetricType.Median -> MedianMetric()
        }
    }

}