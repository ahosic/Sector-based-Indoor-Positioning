package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.building.Position

/**
 * Manages a floor with its bounds and provides means for calculating pixel positions on the building plan.
 *
 * @property widthMultiplier the multiplier for the width of the floor
 * @property heightMultiplier the multiplier for the height of the floor
 * @property offsetTopInPixel the top offset in pixels
 * @property offsetBottomInPixel the bottom offset in pixels
 * @property offsetLeftInPixel the left offset in pixels
 * @property offsetRightInPixel the right offset in pixels
 */
class FloorManager(var floor: Floor) {
    private var widthMultiplier: Double = 0.0
    private var heightMultiplier: Double = 0.0

    val offsetTopInPixel: Int
        get() = Math.round(floor.bounds.top * heightMultiplier).toInt()

    val offsetLeftInPixel: Int
        get() = Math.round(floor.bounds.left * widthMultiplier).toInt()

    val offsetBottomInPixel: Int
        get() = Math.round((floor.bounds.top + floor.bounds.heigth) * heightMultiplier).toInt()

    val offsetRightInPixel: Int
        get() = Math.round((floor.bounds.left + floor.bounds.width) * widthMultiplier).toInt()

    init {
        calculateMultipliers()
    }

    /**
     * Calculates the width and height multipliers.
     */
    private fun calculateMultipliers() {
        floor.floorImage?.let { floorImage ->
            widthMultiplier = floorImage.width / (floor.bounds.width + floor.bounds.right + floor.bounds.left)
            heightMultiplier = floorImage.height / (floor.bounds.heigth + floor.bounds.top + floor.bounds.bottom)
        }
    }

    /**
     * Calculates a pixel position from meters.
     *
     * @return a pixel-based position of a meter-based position
     */
    fun calculatePixelPositionFromMeter(x: Int, y: Int): Position {
        val xPos = (x * widthMultiplier + floor.bounds.left * widthMultiplier).toInt()
        val yPos = (y * heightMultiplier + floor.bounds.top * heightMultiplier).toInt()

        return Position(xPos, yPos)
    }

    /**
     * Changes the current floor and recalculates the width and height multipliers.
     */
    fun changeFloor(floor: Floor) {
        this.floor = floor
        calculateMultipliers()
    }
}
