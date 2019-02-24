package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.building.Position

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

    private fun calculateMultipliers() {
        floor.floorImage?.let { floorImage ->
            widthMultiplier = floorImage.width / (floor.bounds.width + floor.bounds.right + floor.bounds.left)
            heightMultiplier = floorImage.height / (floor.bounds.heigth + floor.bounds.top + floor.bounds.bottom)
        }
    }

    fun calculatePixelPositionFromMeter(x: Int, y: Int): Position {
        val xPos = (x * widthMultiplier + floor.bounds.left * widthMultiplier).toInt()
        val yPos = (y * heightMultiplier + floor.bounds.top * heightMultiplier).toInt()

        return Position(xPos, yPos)
    }

    fun changeFloor(floor: Floor) {
        this.floor = floor
        calculateMultipliers()
    }
}
