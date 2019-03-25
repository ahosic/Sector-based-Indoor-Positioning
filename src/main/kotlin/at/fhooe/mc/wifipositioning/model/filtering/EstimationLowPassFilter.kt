package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.positioning.SectorEstimation

class EstimationLowPassFilter(val threshold: Long): Filtering<SectorEstimation> {

    private var filteredEstimations: MutableList<SectorEstimation> = arrayListOf()
    private var currentEstimation: SectorEstimation? = null

    override fun filter(input: SectorEstimation): SectorEstimation {
        filteredEstimations.add(input)
        currentEstimation = nextEstimation(input)
        return currentEstimation ?: input
    }

    private fun nextEstimation(inputEstimation: SectorEstimation): SectorEstimation {
        // If first sector
        if (filteredEstimations.size == 1) return getInitialEstimation()

        // Check if input appears n times (threshold) in list
        var isNextEstimation = true
        filteredEstimations
                .reversed()
                .stream()
                .limit(threshold)
                .forEach {
                    isNextEstimation = isNextEstimation && inputEstimation == it
                }

        return if (isNextEstimation) {
            inputEstimation
        } else {
            if(currentEstimation != null) {
                val sectors = currentEstimation!!.sectors.toMutableList()
                sectors.addAll(inputEstimation.sectors)
                return SectorEstimation(currentEstimation!!.sectors, inputEstimation.sectors)
            }

            return currentEstimation ?: getInitialEstimation()
        }
    }

    private fun getInitialEstimation(): SectorEstimation {
        return filteredEstimations.last()
    }
}