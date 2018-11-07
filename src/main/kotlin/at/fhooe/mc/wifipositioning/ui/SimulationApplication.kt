package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.controller.SimulationController
import at.fhooe.mc.wifipositioning.model.simulation.SimulationModel
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
    var controller: SimulationController? = null

    private val mapView = MapView()
    private val playButton = Button("Play")
    private val stylePath: String

    init {
        stylePath = javaClass.classLoader.getResource("mainStyle.css").toExternalForm()

        val model = SimulationModel()
        controller = SimulationController(model)
        model.addObserver(this)
    }

    override fun start(stage: Stage) {
        val layout = createLayout()

        with(stage) {
            scene = Scene(layout)
            scene.stylesheets.add(stylePath)
            title = "Simulation"
            show()
        }
    }

    private fun createLayout() : Pane {
        val layout = VBox()
        layout.styleClass.add("mainLayout")
        layout.padding = Insets(8.0)
        layout.alignment = Pos.CENTER

        initMapView()
        initButtons()

        layout.children.addAll(mapView, playButton)

        return layout
    }

    private fun initMapView() {
        mapView.styleClass.add("mapView")
        mapView.prefWidth = 800.0
        mapView.prefHeight = 500.0
        controller?.dimensions = Dimension(800, 500)
    }

    private fun initButtons() {
        playButton.styleClass.add("playButton")
        playButton.setOnAction {
            controller?.onPlay(it)
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BufferedImage) {
            mapView.mapImage = arg
        }
    }
}