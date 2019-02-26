package at.fhooe.mc.wifipositioning.model.building

import com.fasterxml.jackson.annotation.JsonProperty

data class InstalledAccessPoint(@JsonProperty("id") val id: String,
                                @JsonProperty("description") val description: String,
                                @JsonProperty("floorNumber") val floorNumber: Int,
                                @JsonProperty("bssid") val bssid: String,
                                @JsonProperty("position") val position: Position) {

    val fiveBytePrefix: String
        get() = bssid.substringBeforeLast(":")

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