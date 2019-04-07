package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint

/**
 * A concrete implementation of the Filtering interface, which filters sectors/installed access points.
 *
 * @property threshold a value that indicates, how often a sector needs to occur in a row to become the next stable sector.
 * @property filteredSectors the previously filtered sectors.
 * @property currentSector the currently stable sector.
 */
class SectorLowPassFilter(val threshold: Long): Filtering<InstalledAccessPoint> {

    private var filteredSectors: MutableList<InstalledAccessPoint> = arrayListOf()
    private var currentSector: InstalledAccessPoint? = null

    /**
     * Filters input data by smoothing the output.
     *
     * @param input data that should be filtered
     * @return the filtered data
     */
    override fun filter(input: InstalledAccessPoint): InstalledAccessPoint {
        filteredSectors.add(input)
        currentSector = nextSector(input)
        return currentSector ?: input
    }

    /**
     * Gets the next sector based on [inputSector] and [filteredSectors].
     *
     * @return the next filtered sector.
     */
    private fun nextSector(inputSector: InstalledAccessPoint): InstalledAccessPoint {
        // If first sector
        if (filteredSectors.size == 1) return getInitialSector()

        // Check if input appears n times (FILTER_THRESHOLD) in list
        var isNextSector = true
        filteredSectors
                .reversed()
                .stream()
                .limit(threshold)
                .forEach {
                    isNextSector = isNextSector && inputSector == it
                }

        return if (isNextSector) {
            inputSector
        } else {
            currentSector ?: getInitialSector()
        }
    }

    /**
     * Gets the initial (last filtered) sector of the filter out of [filteredSectors].
     *
     * @return the initial sector.
     */
    private fun getInitialSector(): InstalledAccessPoint {
        return filteredSectors.last()
    }
}