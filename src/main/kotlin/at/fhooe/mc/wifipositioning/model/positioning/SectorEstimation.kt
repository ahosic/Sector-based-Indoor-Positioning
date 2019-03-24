package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import java.lang.StringBuilder

data class SectorEstimation(val sectors: List<InstalledAccessPoint>) {
    var accuracy: Int = 0
        get() = sectors.size



    override fun toString(): String {
        val builder = StringBuilder("Estimation (Accuracy: $accuracy): ")

        for (sector in sectors) {
            builder.append(sector.id)
            if (sector != sectors.last()) {
                builder.append(", ")
            }
        }

        return builder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SectorEstimation

        return sectors.containsAll(other.sectors) && this.accuracy == other.accuracy
    }

    override fun hashCode(): Int {
        return sectors.hashCode()
    }
}