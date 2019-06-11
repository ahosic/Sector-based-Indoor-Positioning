package at.fhooe.mc.wifipositioning.model.graphics

import at.fhooe.mc.wifipositioning.model.building.Person
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.Waypoint

import java.awt.*
import java.util.ArrayList

/**
 * This class defines the visual representation of GeoObject types.
 */
object DrawingContext {
    fun drawVoronoi(polygon: Polygon, graphics: Graphics, matrix: Matrix, containsPerson: Boolean, isInTransition: Boolean, accuracy: Int) {
        val g2 = graphics as Graphics2D
        val poly = matrix.multiply(polygon)
        val thickness = 2f
        val oldStroke = g2.stroke
        g2.stroke = BasicStroke(thickness)
        g2.color = Color(0, 0, 0)
        g2.drawPolygon(poly)

        if (containsPerson) {
            g2.color = when(accuracy) {
                1 -> Color(0, 255, 0, 80)
                2 -> Color(255, 255, 200, 80)
                else -> Color(255, 0, 0, 80)
            }
            g2.fillPolygon(poly)
        }

        if (isInTransition) {
            g2.color = when(accuracy) {
                1 -> Color(0, 255, 0, 40)
                2 -> Color(255, 255, 200, 40)
                else -> Color(255, 0, 0, 40)
            }
            g2.fillPolygon(poly)
        }

        g2.stroke = oldStroke
    }

    fun drawAccessPoint(accessPoint: InstalledAccessPoint, x: Int, y: Int, graphics: Graphics, matrix: Matrix) {
        val p = Point(x, y)
        val p2 = matrix.multiply(p)

        graphics.font = graphics.font.deriveFont(12f)
        val fontMetrics = graphics.getFontMetrics(graphics.font)
        val idWidth = fontMetrics.stringWidth(accessPoint.id)

        val textPosY = p2.y - 2 - (fontMetrics.height / 2)

        graphics.color = Color.white
        graphics.fillRect(p2.x - 1, textPosY - fontMetrics.height + 2, idWidth + 2, fontMetrics.height)

        graphics.color = Color(0x00E676)
        graphics.drawString(accessPoint.id, p2.x, textPosY)
        graphics.fillOval(p2.getX().toInt() - 5, p2.getY().toInt() - 5, 10, 10)
    }

    fun drawPerson(person: Person, graphics: Graphics, matrix: Matrix) {
        val p = Point(person.x, person.y)
        val p2 = matrix.multiply(p)
        graphics.color = Color(255, 0, 255)
        graphics.fillOval(p2.getX().toInt() - 10, p2.getY().toInt() - 10, 20, 20)
    }

    fun drawActualPosition(person: Person, graphics: Graphics, matrix: Matrix) {
        val p = Point(person.x, person.y)
        val p2 = matrix.multiply(p)
        graphics.color = Color(0xFF1744)
        graphics.fillOval(p2.getX().toInt() - 10, p2.getY().toInt() - 10, 20, 20)
    }

    fun drawWayPointPosition(waypointPosition: Waypoint, graphics: Graphics, matrix: Matrix) {
        val p = Point(waypointPosition.position.x, waypointPosition.position.y)
        val p2 = matrix.multiply(p)
        graphics.color = Color(0x00B0FF)
        graphics.fillOval(p2.getX().toInt() - 5, p2.getY().toInt() - 5, 10, 10)
    }


    fun drawGrid(rectangle: Rectangle, graphics: Graphics, matrix: Matrix, containsPerson: Boolean) {
        val rectangle2 = matrix.multiply(rectangle)
        val g2 = graphics as Graphics2D
        g2.color = Color(0, 0, 0)
        val thickness = 2f
        val oldStroke = g2.stroke
        g2.stroke = BasicStroke(thickness)
        g2.drawRect(rectangle2.getX().toInt(), rectangle2.getY().toInt(), rectangle2.getWidth().toInt(), rectangle2.getHeight().toInt())
        if (containsPerson) {
            g2.color = Color(255, 255, 200, 80)
            g2.fillRect(rectangle2.getX().toInt(), rectangle2.getY().toInt(), rectangle2.getWidth().toInt(), rectangle2.getHeight().toInt())
        }
        g2.stroke = oldStroke
    }

    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int, graphics: Graphics, matrix: Matrix) {
        val p1 = matrix.multiply(Point(x1, y1))
        val p2 = matrix.multiply(Point(x2, y2))

        graphics.color = Color(0, 0, 0)
        graphics.drawLine(p1.x, p1.y, p2.x, p2.y)
    }


    fun drawWayPointLine(waypointPositionList: ArrayList<Waypoint>, graphics: Graphics, matrix: Matrix) {
        val xPoints = IntArray(waypointPositionList.size)
        val yPoints = IntArray(waypointPositionList.size)
        val nPoints = waypointPositionList.size
        var i = 0
        for (waypointPosition in waypointPositionList) {
            val p = Point(waypointPosition.position.x, waypointPosition.position.y)
            val p2 = matrix.multiply(p)
            xPoints[i] = p2.getX().toInt()
            yPoints[i] = p2.getY().toInt()
            i++
        }

        graphics.color = Color(0x00B0FF)
        graphics.drawPolyline(xPoints, yPoints, nPoints)
    }

    fun drawCells(rectangle: Rectangle, graphics: Graphics, matrix: Matrix, containsPerson: Boolean) {
        val g2 = graphics as Graphics2D
        g2.color = Color(0, 0, 0)
        val thickness = 2f
        val oldStroke = g2.stroke
        g2.stroke = BasicStroke(thickness)

        val rect2 = matrix.multiply(rectangle)
        g2.drawRect(rect2.getX().toInt(), rect2.getY().toInt(), rect2.getWidth().toInt(), rect2.getHeight().toInt())

        if (containsPerson) {
            g2.color = Color(255, 255, 200, 80)
            g2.fillRect(rect2.getX().toInt(), rect2.getY().toInt(), rect2.getWidth().toInt(), rect2.getHeight().toInt())
        }
        g2.stroke = oldStroke
    }
}
