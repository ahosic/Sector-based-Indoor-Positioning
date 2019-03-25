package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

class RSSILowPassFilter(val alpha: Float): Filtering<ScannedAccessPoint> {

    private var filteredAccessPoints: MutableList<ScannedAccessPoint> = mutableListOf()

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

    private fun smoothSignalLevel(previousSignalLevel: Int, newSignalLevel: Int): Int {
        return Math.round(previousSignalLevel + alpha * (newSignalLevel - previousSignalLevel))
    }
}