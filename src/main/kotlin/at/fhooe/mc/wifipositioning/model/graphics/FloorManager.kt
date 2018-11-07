package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor

class FloorManager(floor: Floor) {

    var floor: Floor? = null
        private set
    var widthMultiplikator: Double = 0.toDouble()
        private set
    var heightMultiplikator: Double = 0.toDouble()
        private set

    val offsetTopInPixel: Int
        get() = Math.round(floor!!.offsetTop * heightMultiplikator).toInt()

    val offsetLeftInPixel: Int
        get() = Math.round(floor!!.offsetLeft * widthMultiplikator).toInt()

    val offsetBottomInPixel: Int
        get() = Math.round((floor!!.offsetTop + floor!!.floorHeight) * heightMultiplikator).toInt()

    val offsetRightInPixel: Int
        get() = Math.round((floor!!.offsetLeft + floor!!.floorWidth) * widthMultiplikator).toInt()

    init {
        this.floor = floor
        calculateMultiplicators()
    }

    private fun calculateMultiplicators() {
        floor?.let { floor ->
            floor.floorImage?.let { floorImage ->
                widthMultiplikator = floorImage.width / (floor.floorWidth + floor.offsetRight + floor.offsetLeft)
                heightMultiplikator = floorImage.height / (floor.floorHeight + floor.offsetTop + floor.offsetBottom)
            }
        }
    }

    fun calculatePixelPositionFromMeter(x: Int, y: Int): Position {
        val xPos = (x * widthMultiplikator + floor!!.offsetLeft * widthMultiplikator).toInt()
        val yPos = (y * heightMultiplikator + floor!!.offsetTop * heightMultiplikator).toInt()

        return Position(xPos, yPos)
    }

    fun changeFloor(floor: Floor) {
        this.floor = floor
        calculateMultiplicators()
    }
}
