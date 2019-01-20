package at.fhooe.mc.wifipositioning.model.building

import at.fhooe.mc.wifipositioning.model.simulation.Position

data class InstalledAccessPoint (
        val id: String,
        val bssid: String,
        val position: Position,
        val floor: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as InstalledAccessPoint

        if (id != other.id) return false

        return true
    }
}