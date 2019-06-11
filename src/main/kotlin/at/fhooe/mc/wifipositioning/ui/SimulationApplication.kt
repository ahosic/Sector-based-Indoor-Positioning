package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.controller.SimulationController
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.model.SimulationModel
import com.jfoenix.controls.JFXButton
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.util.*
import javafx.application.Platform


/**
 * The view of the simulation tool.
 *
 * @property controller the controller of the simulation
 * @property configModel the configuration model of the application
 * @property stage the stage of the JavaFX application
 * @property mapView the building plan view
 * @property playButton the button for toggling the playing state of the simulation
 * @property stopButton the button for stopping the simulation
 * @property settingsButton the button for opening the settings.
 * @property stylePath the path to the styling of the view.
 * @property isPlaying the current status of the simulation.
 */
class SimulationApplication : Application(), Observer {
    private var controller: SimulationController? = null
    private var configModel: ConfigurationModel? = null

    private var stage: Stage? = null
    private val mapView = MapView()
    private val playButton = JFXButton()
    private val stopButton = JFXButton()
    private val settingsButton = JFXButton()
    private val stylePath: String = javaClass.classLoader.getResource("mainStyle.css").toExternalForm()

    private var isPlaying = false

    init {
        initializeApplication()
    }

    /**
     * Starts the application's window and creates the layout.
     */
    override fun start(stage: Stage) {
        this.stage = stage
        val layout = createLayout()

        with(stage) {
            scene = Scene(layout)
            scene.stylesheets.add(stylePath)
            title = "Simulation"
            stage.onCloseRequest = controller

            show()

            if(App.isDebugMode) {
                val debugStage = Stage()
                debugStage.initModality(Modality.NONE)
                debugStage.initOwner(stage)

                val debugView = DebugApplication(App.debugger)
                debugView.start(debugStage)
            }
        }
    }

    /**
     * Creates the layout.
     */
    private fun createLayout(): Pane {
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

    /**
     * Initializes the map view of the building plan.
     */
    private fun initMapView() {
        mapView.styleClass.add("mapView")
        mapView.prefWidth = 1000.0
        mapView.prefHeight = 625.0
        controller?.dimensions = Dimension(mapView.prefWidth.toInt(), mapView.prefHeight.toInt())
    }

    /**
     * Initializes the buttons of the view.
     */
    private fun initButtons() {
        playButton.styleClass.addAll("playButton", "play")
        playButton.setOnAction {
                Platform.runLater {
                    togglePlayingState()
                    controller?.onPlay(it)
                }
        }

        settingsButton.styleClass.add("settingsButton")
        settingsButton.setOnAction {
            if (isPlaying) {
                Platform.runLater {
                    togglePlayingState()
                }
            }

            controller?.onStop(it)

            configModel?.let { configModel ->
                stage?.let { stage ->
                    val settingsStage = Stage()
                    settingsStage.initModality(Modality.WINDOW_MODAL)
                    settingsStage.initOwner(stage)

                    val settings = SettingsApplication(configModel)
                    settings.start(settingsStage)
                }
            }
        }

        stopButton.styleClass.add("stopButton")
        stopButton.setOnAction {
            Platform.runLater {
                if (isPlaying) {
                    togglePlayingState()
                }
                controller?.onStop(it)
            }

        }
    }

    /**
     * Initializes the application and loads all models and configurations.
     */
    private fun initializeApplication() {
        println("Loading settings.")
        configModel = ConfigurationModel(javaClass.classLoader.getResource("settings.json").toURI().path)
        println("Done.")

        configModel?.let { config ->
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

    /**
     * Toggles the playing state of the simulation.
     */
    private fun togglePlayingState() {
        isPlaying = !isPlaying

        if (isPlaying) {
            playButton.styleClass.remove("play")
            playButton.styleClass.add("pause")
        } else {
            playButton.styleClass.add("play")
            playButton.styleClass.remove("pause")
        }
    }

    /**
     * Updates the image of the map view.
     */
    override fun update(o: Observable?, arg: Any?) {
        if (arg is BufferedImage) {
            mapView.mapImage = arg
        }
    }
}