package at.fhooe.mc.wifipositioning.model.slidingwindow

/**
 * An interface for a sliding window, which can also take selection restrictions into account.
 */
interface SlidingWindow<in Item, in Restriction, out Result> {
    /**
     * Adds [items] to the sliding window
     */
    fun add(items: List<Item>)

    /**
     * Gets the item with the best metric out of the sliding window taking [restriction] into account.
     *
     * @return the item with the best metric
     */
    fun getBestItem(restriction: List<Restriction>? = null): Result?
}