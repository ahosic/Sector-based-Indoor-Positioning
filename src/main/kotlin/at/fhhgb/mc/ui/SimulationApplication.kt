package at.fhhgb.mc.ui

import at.fhhgb.mc.component.sim.controller.NewSimulationController
import at.fhhgb.mc.component.sim.model.SimulationModel
import at.fhhgb.mc.component.sim.view.MapView
import at.fhhgb.mc.controller.MainController
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.util.*

class SimulationApplication : Application(), Observer {
    var controller: NewSimulationController? = null

    private val mapView = MapView()
    private val playButton = Button("Play")

    init {
        val model = SimulationModel()
        controller = NewSimulationController(model)
        model.addObserver(this)
    }

    override fun start(stage: Stage) {
        val layout = createLayout()
        with(stage) {
            scene = Scene(layout)
            title = "Simulation"
            show()
        }
    }

    private fun createLayout() : Pane {
        val layout = VBox()
        layout.padding = Insets(8.0)
        layout.alignment = Pos.CENTER

        mapView.prefWidth = 800.0
        mapView.prefHeight = 500.0
        controller?.dimensions = Dimension(800, 500)

        playButton.setOnAction {
            controller?.onPlay(it)
        }

        layout.children.add(mapView)
        layout.children.add(playButton)

        return layout
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BufferedImage) {
            mapView.mapImage = arg
        }
    }
}