package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.positioning.AccessPointSlidingWindow

/**
 * A Positioning method that uses the access point with the best average signal strength at a specific point in time.
 *
 * @property allAccessPoints a list of all installed access points in the building.
 * @property windowSize the size of the sliding window
 * @property accessPointSlidingWindow the used sliding window
 *
 * @constructor Initializes a new Positioning Method.
 */
class AveragePositioning(val allAccessPoints: List<ScannedAccessPoint>, private val windowSize: Int) : IPositioning {

    private val accessPointSlidingWindow: AccessPointSlidingWindow = AccessPointSlidingWindow(windowSize)

    override fun calculatePosition(scannedAccessPointList: List<ScannedAccessPoint>): Position {
        accessPointSlidingWindow.addElement(scannedAccessPointList)

        val bssid = accessPointSlidingWindow.bestAverageBSSID.toLowerCase()

        val accessPoint = allAccessPoints
                .filter { ap -> ap.bssid.toLowerCase() == bssid }
                .firstOrNull()

        return accessPoint?.position ?: Position(0, 0)
    }
}
