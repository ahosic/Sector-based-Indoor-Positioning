package at.fhooe.mc.wifipositioning.model.sectoring

import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.graphics.Matrix
import at.fhooe.mc.wifipositioning.model.simulation.Position

import java.awt.*

interface ISectoring {
    fun createSectors(floorManager: FloorManager, g: Graphics, m_tMatrix: Matrix)
    fun addCurrentPosition(position: Position)
}
