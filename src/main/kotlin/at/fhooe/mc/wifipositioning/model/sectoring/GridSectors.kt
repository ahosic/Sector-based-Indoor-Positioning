package at.fhooe.mc.wifipositioning.model.sectoring

import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.graphics.Matrix
import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext

import java.awt.*

class GridSectors : ISectoring {

    internal var position: Position? = null
    private val xGrid = 3
    private val yGrid = 3

    override fun createSectors(floorManager: FloorManager, g: Graphics, m_tMatrix: Matrix) {
        val floor = floorManager.floor
        val cellHeight = (floor!!.floorHeight * floorManager.heightMultiplikator / yGrid).toInt()
        val cellWidth = (floor.floorWidth * floorManager.widthMultiplikator / xGrid).toInt()
        var y = floorManager.offsetTopInPixel

        while (y < floorManager.offsetBottomInPixel - cellHeight / 2) {
            var x = floorManager.offsetLeftInPixel

            while (x < floorManager.offsetRightInPixel - cellWidth / 2) {
                val rectangle = Rectangle(x, y, cellWidth, cellHeight)

                if (position != null && rectangle.contains(Point(position!!.x, position!!.y))) {
                    DrawingContext.drawGrid(rectangle, g, m_tMatrix, true)
                } else {
                    DrawingContext.drawGrid(rectangle, g, m_tMatrix, false)
                }

                x += cellWidth
            }

            y += cellHeight
        }
    }

    override fun addCurrentPosition(position: Position) {
        this.position = position
    }
}
