package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Holds information about an installed access point, which also defines a sector (one installed access point per sector).
 *
 * @property id the ID of the sector
 * @property description the description of the sector
 * @property floorNumber the number of the floor, where the access point is installed.
 * @property bssid the BSSID of the installed access point.
 * @property position the position of the installed access point on the building plan.
 * @property fiveBytePrefix the first five byte of the BSSID
 */
data class InstalledAccessPoint(@JsonProperty("id") val id: String,
                                @JsonProperty("description") val description: String,
                                @JsonProperty("floorNumber") val floorNumber: Int,
                                @JsonProperty("bssid") val bssid: String,
                                @JsonProperty("position") val position: Position) {

    val fiveBytePrefix: String
        get() = bssid.substringBeforeLast(":")

    override fun toString(): String {
        return "[$id] $description - $bssid"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as InstalledAccessPoint

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + floorNumber
        result = 31 * result + bssid.hashCode()
        result = 31 * result + position.hashCode()
        return result
    }
}