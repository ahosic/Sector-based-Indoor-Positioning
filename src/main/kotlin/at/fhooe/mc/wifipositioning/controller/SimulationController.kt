package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.SimulationModel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.WindowEvent
import java.awt.Dimension
import javax.imageio.ImageIO

class SimulationController(val model: SimulationModel) : EventHandler<WindowEvent> {
    var dimensions: Dimension? = null
    set(value) {
        field = value
        dimensions?.let {
            model.setPanelSize(it.width, it.height)
            model.zoomToFit(true)
        }
    }

    init {
        ImageIO.scanForPlugins()
    }

    fun onPlay(event: ActionEvent) {
        model.toggleSimulation()
    }

    fun onStop(event: ActionEvent) {
        model.resetSimulation()
        model.resetImage()
    }

    override fun handle(event: WindowEvent?) {
        System.exit(0)
    }
}