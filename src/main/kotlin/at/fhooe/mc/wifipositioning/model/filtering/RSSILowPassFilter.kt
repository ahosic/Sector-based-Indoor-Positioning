package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * A concrete implementation of the Filtering interface, which filters the RSSI of a scanned access point.
 *
 * @property alpha the smoothing factor of the filter
 * @property filteredAccessPoints the previously filtered scanned access points.
 */
class RSSILowPassFilter(val alpha: Float): Filtering<ScannedAccessPoint> {

    private var filteredAccessPoints: MutableList<ScannedAccessPoint> = mutableListOf()

    /**
     * Filters input data by smoothing the output.
     *
     * @param input data that should be filtered
     * @return the filtered data
     */
    override fun filter(input: ScannedAccessPoint): ScannedAccessPoint {
        val previous = filteredAccessPoints.lastOrNull { scanned -> scanned.bssid == input.bssid }

        if (previous == null) {
            filteredAccessPoints.add(input)
            return input
        }

        var output = input
        output.signalLevel = smoothSignalLevel(previous.signalLevel, input.signalLevel)

        filteredAccessPoints.add(output)

        return output
    }

    /**
     * Smoothens [newSignalLevel] based on [previousSignalLevel] and [alpha].
     *
     * @return the smoothened signal level (RSSI)
     */
    private fun smoothSignalLevel(previousSignalLevel: Int, newSignalLevel: Int): Int {
        return Math.round(previousSignalLevel + alpha * (newSignalLevel - previousSignalLevel))
    }
}