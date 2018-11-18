package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.controller.SimulationController
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.model.simulation.SimulationModel
import com.jfoenix.controls.JFXButton
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.util.*
import kotlinx.coroutines.*


class SimulationApplication : Application(), Observer {
    var controller: SimulationController? = null

    private val mapView = MapView()
    private val playButton = JFXButton()
    private val pauseButton = JFXButton()
    private val stopButton = JFXButton()
    private val settingsButton = JFXButton()
    private val stylePath: String

    private var isPlaying = false

    init {
        stylePath = javaClass.classLoader.getResource("mainStyle.css").toExternalForm()
        initializeApplication()
    }

    override fun start(stage: Stage) {
        val layout = createLayout()

        with(stage) {
            scene = Scene(layout)
            scene.stylesheets.add(stylePath)
            title = "Simulation"
            stage.onCloseRequest = controller

            show()
        }
    }

    private fun createLayout() : Pane {
        val root = VBox()
        root.styleClass.add("mainLayout")
        root.padding = Insets(8.0)
        root.alignment = Pos.CENTER_RIGHT

        initMapView()
        initButtons()

        val buttonPane = HBox()
        buttonPane.alignment = Pos.CENTER
        buttonPane.children.addAll(settingsButton, playButton, stopButton)

        root.children.addAll(mapView, buttonPane)

        return root
    }

    private fun initMapView() {
        mapView.styleClass.add("mapView")
        mapView.prefWidth = 800.0
        mapView.prefHeight = 500.0
        controller?.dimensions = Dimension(800, 500)
    }

    private fun initButtons() {
        playButton.styleClass.addAll("playButton", "play")
        playButton.setOnAction {
            GlobalScope.launch {
                togglePlayingState()
                controller?.onPlay(it)
            }
        }

        settingsButton.styleClass.add("settingsButton")
        stopButton.styleClass.add("stopButton")
        stopButton.setOnAction {
            GlobalScope.launch {
                if(isPlaying) {
                    togglePlayingState()
                }
                controller?.onStop(it)
            }
        }
    }

    private fun initializeApplication() {
        println("Loading configuration.")
        val config = ConfigurationModel(javaClass.classLoader.getResourceAsStream("config.json"))
        println("Done.")

        config?.let { config ->
            println("Initializing model.")
            val model = SimulationModel(config)
            model.reloadConfiguration()

            println("Done.")


            println("Initializing controller.")
            controller = SimulationController(model)

            println("Done.")

            println("Adding observer to model")
            model.addObserver(this)

            println("Done.")
            println("Initialization of application successful.")
        }
    }

    private fun togglePlayingState() {
        isPlaying = !isPlaying

        if(isPlaying) {
            playButton.styleClass.remove("play")
            playButton.styleClass.add("pause")
        } else {
            playButton.styleClass.add("play")
            playButton.styleClass.remove("pause")
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BufferedImage) {
            mapView.mapImage = arg
        }
    }
}