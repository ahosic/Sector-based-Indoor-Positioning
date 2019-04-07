package at.fhooe.mc.wifipositioning.model.positioning

/**
 * The type of the Positioning.
 */
enum class PositioningType {
    StrongestRSSI,
    SlidingWindow,
    Filtered,
    Graphed
}