package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import java.lang.StringBuilder

/**
 * A sector-based estimation, which is used by the positioning methods as an output.
 *
 * @property sectors the estimated, stable sectors
 * @property inTransition the sectors that might become stable in the future.
 * @property accuracy indicates the accuracy of the estimation (min == 1 == best accuracy)
 */
data class SectorEstimation(val sectors: List<InstalledAccessPoint>, val inTransition: List<InstalledAccessPoint>?) {
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

        if (inTransition != null && other.inTransition != null) {
            if (!inTransition.containsAll(other.inTransition)) return false
            return sectors.containsAll(other.sectors) && this.accuracy == other.accuracy
        } else if (inTransition == null && other.inTransition == null) {
            return sectors.containsAll(other.sectors) && this.accuracy == other.accuracy
        }

        return false
    }

    override fun hashCode(): Int {
        return sectors.hashCode()
    }
}