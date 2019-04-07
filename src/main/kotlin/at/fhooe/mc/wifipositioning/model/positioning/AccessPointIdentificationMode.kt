package at.fhooe.mc.wifipositioning.model.positioning

/**
 * The identification mode that is used, when a BSSID is compared with the BSSID of an installed access points.
 * If the last byte of the BSSID is dynamic, then only the first five bytes are compared, otherwise the full address is compared.
 */
enum class AccessPointIdentificationMode {
    FiveBytePrefixIdentification, FullAddressIdentification
}