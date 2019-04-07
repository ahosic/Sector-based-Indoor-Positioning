package at.fhooe.mc.wifipositioning.model.filtering

import at.fhooe.mc.wifipositioning.model.positioning.SectorEstimation

/**
 * A concrete implementation of the Filtering interface, which filters sector-based estimations.
 *
 * @property threshold a value that indicates, how often an estimation needs to occur in a row to become the next stable estimation.
 * @property filteredEstimations the previously filtered estimations.
 * @property currentEstimation the currently stable estimation.
 */
class EstimationLowPassFilter(val threshold: Long): Filtering<SectorEstimation> {

    private var filteredEstimations: MutableList<SectorEstimation> = arrayListOf()
    private var currentEstimation: SectorEstimation? = null

    /**
     * Filters input data by smoothing the output.
     *
     * @param input data that should be filtered
     * @return the filtered data
     */
    override fun filter(input: SectorEstimation): SectorEstimation {
        filteredEstimations.add(input)
        currentEstimation = nextEstimation(input)
        return currentEstimation ?: input
    }

    /**
     * Gets the next estimation based on [inputEstimation] and [filteredEstimations].
     *
     * @return the next filtered estimation.
     */
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

    /**
     * Gets the initial (last filtered) estimation of the filter out of [filteredEstimations].
     *
     * @return the initial estimation.
     */
    private fun getInitialEstimation(): SectorEstimation {
        return filteredEstimations.last()
    }
}