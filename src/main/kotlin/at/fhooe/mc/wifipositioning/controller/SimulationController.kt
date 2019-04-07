package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.SimulationModel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.WindowEvent
import java.awt.Dimension
import javax.imageio.ImageIO

/**
 * The controller of the simulation view.
 *
 * @property model the simulation model, which manages the simulation.
 * @property dimensions the dimensions of the viewport.
 */
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

    /**
     * Toggles the simulation between start and pause.
     */
    fun onPlay(event: ActionEvent) {
        model.toggleSimulation()
    }

    /**
     * Stops the simulation, resets the building plan and reloads the configuration.
     */
    fun onStop(event: ActionEvent) {
        model.resetSimulation()
        model.reloadConfiguration()
        model.resetImage()
    }

    /**
     * Terminates the application, when the application's window gets closed.
     */
    override fun handle(event: WindowEvent?) {
        System.exit(0)
    }
}