package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.building.Position

class FloorManager(floor: Floor) {

    var floor: Floor? = null
        private set
    var widthMultiplikator: Double = 0.toDouble()
        private set
    var heightMultiplikator: Double = 0.toDouble()
        private set

    val offsetTopInPixel: Int
        get() = Math.round(floor!!.bounds.top * heightMultiplikator).toInt()

    val offsetLeftInPixel: Int
        get() = Math.round(floor!!.bounds.left * widthMultiplikator).toInt()

    val offsetBottomInPixel: Int
        get() = Math.round((floor!!.bounds.top + floor!!.bounds.heigth) * heightMultiplikator).toInt()

    val offsetRightInPixel: Int
        get() = Math.round((floor!!.bounds.left + floor!!.bounds.width) * widthMultiplikator).toInt()

    init {
        this.floor = floor
        calculateMultiplicators()
    }

    private fun calculateMultiplicators() {
        floor?.let { floor ->
            floor.floorImage?.let { floorImage ->
                widthMultiplikator = floorImage.width / (floor.bounds.width + floor.bounds.right + floor.bounds.left)
                heightMultiplikator = floorImage.height / (floor.bounds.heigth + floor.bounds.top + floor.bounds.bottom)
            }
        }
    }

    fun calculatePixelPositionFromMeter(x: Int, y: Int): Position {
        val xPos = (x * widthMultiplikator + floor!!.bounds.left * widthMultiplikator).toInt()
        val yPos = (y * heightMultiplikator + floor!!.bounds.top * heightMultiplikator).toInt()

        return Position(xPos, yPos)
    }

    fun changeFloor(floor: Floor) {
        this.floor = floor
        calculateMultiplicators()
    }
}
