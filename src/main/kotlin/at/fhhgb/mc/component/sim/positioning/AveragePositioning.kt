package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint
import java.util.Optional

/**
 * A Positioning method that uses the access point with the best average signal strength at a specific point in time.
 *
 * @property allAccessPoints a list of all installed access points in the building.
 * @property windowSize the size of the sliding window
 * @property accessPointSlidingWindow the used sliding window
 *
 * @constructor Initializes a new Positioning Method.
 */
class AveragePositioning(val allAccessPoints: List<AccessPoint>, private val windowSize: Int) : IPositioning {

    private val accessPointSlidingWindow: AccessPointSlidingWindow = AccessPointSlidingWindow(windowSize)

    override fun calculatePosition(accessPointList: List<AccessPoint>): Position {
        accessPointSlidingWindow.addElement(accessPointList)

        val bssid = accessPointSlidingWindow.bestAverageBSSID.toLowerCase()

        val accessPoint = allAccessPoints
                .filter { ap -> ap.bssid.toLowerCase() == bssid }
                .firstOrNull()

        return accessPoint?.position ?: Position(0, 0)
    }
}
