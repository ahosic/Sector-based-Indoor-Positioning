package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint

class StrongestAccessPointPositioning(val allAccessPoints: List<AccessPoint>) : IPositioning {

    override fun calculatePosition(accessPointList: List<AccessPoint>): Position {
        for (scanned in accessPointList.sorted().reversed()) {
            allAccessPoints
                    .filter { it.bssid.toLowerCase() == scanned.bssid.toLowerCase() }
                    .firstOrNull()?.let {
                        return it.position
                    }
        }

        return Position(0, 0)
    }
}
