package at.fhooe.mc.wifipositioning.model.simulation.recorder.network

import at.fhooe.mc.wifipositioning.model.simulation.Position

data class InstalledAccessPoint (
        val displayName: String,
        val bssid: String,
        val position: Position,
        val floor: Int
)