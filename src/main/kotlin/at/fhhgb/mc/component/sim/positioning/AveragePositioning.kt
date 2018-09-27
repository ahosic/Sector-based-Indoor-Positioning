package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint
import java.util.Optional

class AveragePositioning(val allAccessPoints: List<AccessPoint>, private val windowSize: Int) : IPositioning {

    private val accessPointSlidingWindow: AccessPointSlidingWindow

    init {
        accessPointSlidingWindow = AccessPointSlidingWindow(windowSize)
    }

    override fun calculatePosition(accessPointList: List<AccessPoint>): Position {
        accessPointSlidingWindow.addElement(accessPointList)

        val bssid = accessPointSlidingWindow.bestAverageBSSID.toLowerCase()

        val accessPoint = allAccessPoints
                .filter { ap -> ap.bssid.toLowerCase() == bssid }
                .firstOrNull()

        return accessPoint?.position ?: Position(0, 0)
    }
}
