package at.fhooe.mc.wifipositioning.model.building

import at.fhooe.mc.wifipositioning.model.simulation.Position

data class InstalledAccessPoint (
        val id: String,
        val bssid: String,
        val position: Position,
        val floor: Int
)