package at.fhooe.mc.wifipositioning.model.positioning

/**
 * An interface for filtering data.
 * @param T the type of object that should be filtered.
 */
interface IFilterable<T> {
    /**
     * Filters input data by smoothing the output.
     *
     * @param input data that should be filtered
     * @return the smoothed data
     */
    fun filter(input: T): T
}
