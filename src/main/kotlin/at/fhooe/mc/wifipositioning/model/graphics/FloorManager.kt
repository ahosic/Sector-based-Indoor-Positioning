package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.building.NewFloor
import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor

class FloorManager(floor: NewFloor) {

    var floor: NewFloor? = null
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

    fun changeFloor(floor: NewFloor) {
        this.floor = floor
        calculateMultiplicators()
    }
}
