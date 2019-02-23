package at.fhooe.mc.wifipositioning.model.sectoring

import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.graphics.Matrix
import at.fhooe.mc.wifipositioning.model.building.Position
import at.fhooe.mc.wifipositioning.model.sectoring.quadtree.AbstractNodeElement
import at.fhooe.mc.wifipositioning.model.sectoring.quadtree.point.PointNode
import at.fhooe.mc.wifipositioning.model.sectoring.quadtree.point.PointQuadTree
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext

import java.awt.*
import java.util.Vector

class QuadTreeSectors : ISectoring {
    protected var tree: PointQuadTree<String>? = null
    protected var selectedElements = Vector<AbstractNodeElement<String>>()
    private var position: Position? = null
    private var matchingSector = false

    override fun createSectors(floorManager: FloorManager, g: Graphics, m_tMatrix: Matrix) {
        val tree = PointQuadTree<String>(Point(floorManager.offsetLeftInPixel, floorManager.offsetTopInPixel),
                Dimension(floorManager.offsetRightInPixel - floorManager.offsetLeftInPixel, floorManager.offsetBottomInPixel - floorManager.offsetTopInPixel))

        for (accessPoint in floorManager.floor!!.accessPoints!!) {
            val pixelPosition = floorManager.calculatePixelPositionFromMeter(accessPoint.position.x, accessPoint.position.y)
            tree.insert(pixelPosition.x, pixelPosition.y, accessPoint.bssid)
        }

        val rootNode = tree.rootNode
        drawQuadTree(rootNode, g, m_tMatrix)
        matchingSector = false
    }


    private fun drawQuadTree(node: PointNode<String>, g: Graphics, matrix: Matrix) {
        val bounds = node.bounds
        val startCoordinates = node.startCoordinates

        // Draw subnodes
        val subNodes = node.subNodes
        for (subNode in subNodes.values) {
            drawQuadTree(subNode, g, matrix)
        }
        // Draw node bounds
        val rect = Rectangle(startCoordinates.x, startCoordinates.y, bounds.width, bounds.height)
        if (position != null && !matchingSector && rect.contains(Point(position!!.x, position!!.y))) {
            DrawingContext.drawCells(rect, g, matrix, true)
            matchingSector = true
        } else {
            DrawingContext.drawCells(rect, g, matrix, false)
        }
    }

    override fun addCurrentPosition(position: Position) {
        this.position = position
    }
}
