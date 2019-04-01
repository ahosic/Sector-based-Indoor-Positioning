package at.fhooe.mc.wifipositioning.model.slidingwindow

interface SlidingWindow<in Item, in Restriction, out Result> {
    fun add(items: List<Item>)
    fun getBestItem(restriction: List<Restriction>? = null): Result?
}