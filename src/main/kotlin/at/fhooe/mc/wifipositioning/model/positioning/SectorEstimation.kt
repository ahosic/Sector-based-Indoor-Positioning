package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import java.lang.StringBuilder

data class SectorEstimation(val sectors: List<InstalledAccessPoint>) {
    var accuracy: Int = 0
        get() = sectors.size

    override fun toString(): String {
        val builder = StringBuilder("Accuracy: $accuracy\n")
        builder.appendln("Sectors:")
        sectors.forEach {
            builder.appendln(it)
        }

        return builder.toString()
    }
}