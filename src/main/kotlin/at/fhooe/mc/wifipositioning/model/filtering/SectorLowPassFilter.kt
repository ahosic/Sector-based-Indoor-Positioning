package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint

class SectorLowPassFilter(val threshold: Long): Filtering<InstalledAccessPoint> {

    private var estimatedSectors: MutableList<InstalledAccessPoint> = arrayListOf()
    private var currentSector: InstalledAccessPoint? = null

    override fun filter(input: InstalledAccessPoint): InstalledAccessPoint {
        estimatedSectors.add(input)
        currentSector = nextSector(input)
        return currentSector ?: input
    }

    private fun nextSector(inputSector: InstalledAccessPoint): InstalledAccessPoint {
        // If first sector
        if (estimatedSectors.size == 1) return getInitialSector()

        // Check if input appears n times (FILTER_THRESHOLD) in list
        var isNextSector = true
        estimatedSectors
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

    private fun getInitialSector(): InstalledAccessPoint {
        return estimatedSectors.last()
    }
}