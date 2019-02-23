package at.fhooe.mc.wifipositioning.model.positioning

import at.fhooe.mc.wifipositioning.model.building.NewInstalledAccessPoint

class SectorLowPassFilter(val threshold: Long): IFilterable<NewInstalledAccessPoint> {

    private var estimatedSectors: MutableList<NewInstalledAccessPoint> = arrayListOf()
    private var currentSector: NewInstalledAccessPoint? = null

    override fun filter(input: NewInstalledAccessPoint): NewInstalledAccessPoint {
        estimatedSectors.add(input)
        currentSector = nextSector(input)
        return currentSector ?: input
    }

    private fun nextSector(inputSector: NewInstalledAccessPoint): NewInstalledAccessPoint {
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

    private fun getInitialSector(): NewInstalledAccessPoint {
        return estimatedSectors.last()
    }
}