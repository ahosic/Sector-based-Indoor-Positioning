package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.controller.SettingsController
import at.fhooe.mc.wifipositioning.model.configuration.Settings
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.model.positioning.PositioningType
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXTextField
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Stage


/**
 * The view of the settings
 *
 * @property controller the settings controller
 * @property stage the stage of the JavaFX application
 * @property floorPlanPathField the path field for the building plan
 * @property buildingPathField the path field for the building file
 * @property routePathField the path field for the route
 * @property walkRecordingPathField the path field for the walk recording file
 * @property buildingGraphPathField the path field for the graph file
 * @property positioningCombobox the combobox for selecting the positioning method.
 * @property applyButton the button for saving the settings
 * @property cancelButton the button for cancelling the changes.
 * @property stylePath the path to the styling of the view.
 */
class SettingsApplication(val configModel: ConfigurationModel) : Application() {

    private val controller: SettingsController
    private var stage: Stage? = null

    private var floorPlanPathField = JFXTextField()
    private var buildingPathField = JFXTextField()
    private var routePathField = JFXTextField()
    private var walkRecordingPathField = JFXTextField()
    private var buildingGraphPathField = JFXTextField()
    private var positioningCombobox = JFXComboBox<Label>()

    private val applyButton = JFXButton("Save".toUpperCase())
    private val cancelButton = JFXButton("Cancel".toUpperCase())
    private val stylePath: String

    init {
        controller = SettingsController(configModel)
        stylePath = javaClass.classLoader.getResource("settingsStyle.css").toExternalForm()
    }

    /**
     * Starts the window and creates the layout.
     */
    override fun start(stage: Stage) {
        this.stage = stage
        val layout = createLayout()

        stage.scene = Scene(layout)
        stage.title = "Settings"
        stage.scene.stylesheets.add(stylePath)
        stage.show()
    }

    /**
     * Creates the layout of the view.
     */
    private fun createLayout(): Pane {
        val root = VBox()
        root.padding = Insets(8.0)
        root.styleClass.add("mainLayout")
        root.alignment = Pos.CENTER_RIGHT
        root.spacing = 16.0

        val settingsLayout = GridPane()
        settingsLayout.hgap = 8.0
        settingsLayout.vgap = 8.0

        initFloorPlanSettings(settingsLayout)
        initBuildingSettings(settingsLayout)
        initRouteSettings(settingsLayout)
        initWalkRecordingSettings(settingsLayout)
        initBuildingGraphSettings(settingsLayout)
        initPositioningSelection(settingsLayout)

        val buttonPane = HBox()
        buttonPane.alignment = Pos.CENTER_RIGHT
        buttonPane.spacing = 16.0

        initButtons()
        buttonPane.children.addAll(cancelButton, applyButton)

        root.children.addAll(settingsLayout, buttonPane)

        return root
    }

    /**
     * Appends the building plan UI controls to the [layout].
     */
    private fun initFloorPlanSettings(layout: GridPane) {
        val floorLabel = Label("Floor plan file: ")
        layout.add(floorLabel, 0, 0)

        floorPlanPathField.text = configModel.settings.floorsPath
        layout.add(floorPlanPathField, 1,0)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select floor plan")
            floorPlanPathField.text = path ?: floorPlanPathField.text
        }


        layout.add(selectPathButton, 3, 0)
    }

    /**
     * Appends the building file UI controls to the [layout].
     */
    private fun initBuildingSettings(layout: GridPane) {
        val buildingLabel = Label("Building file: ")
        layout.add(buildingLabel, 0, 1)

        buildingPathField.text = configModel.settings.buildingPath
        layout.add(buildingPathField, 1,1)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select building file")
            buildingPathField.text = path ?: floorPlanPathField.text
        }


        layout.add(selectPathButton, 3, 1)
    }

    /**
     * Appends the route file UI controls to the [layout].
     */
    private fun initRouteSettings(layout: GridPane) {
        val routeLabel = Label("Route file: ")
        layout.add(routeLabel, 0, 2)

        routePathField.text = configModel.settings.routePath
        layout.add(routePathField, 1,2)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select route file")
            routePathField.text = path ?: routePathField.text
        }


        layout.add(selectPathButton, 3, 2)
    }

    /**
     * Appends the walk recording UI controls to the [layout].
     */
    private fun initWalkRecordingSettings(layout: GridPane) {
        val floorLabel = Label("Walk recording file: ")
        layout.add(floorLabel, 0, 3)

        walkRecordingPathField.text = configModel.settings.walkRecordingPath
        layout.add(walkRecordingPathField, 1,3)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select walk recording")
            walkRecordingPathField.text = path ?: walkRecordingPathField.text
        }


        layout.add(selectPathButton, 3, 3)
    }

    /**
     * Appends the building graph UI controls to the [layout].
     */
    private fun initBuildingGraphSettings(layout: GridPane) {
        val graphLabel = Label("Graph file: ")
        layout.add(graphLabel, 0, 4)

        buildingGraphPathField.text = configModel.settings.buildingGraphPath
        layout.add(buildingGraphPathField, 1,4)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select building graph")
            buildingGraphPathField.text = path ?: walkRecordingPathField.text
        }


        layout.add(selectPathButton, 3, 4)
    }

    /**
     * Appends the positioning method UI controls to the [layout].
     */
    private fun initPositioningSelection(layout: GridPane) {
        val floorLabel = Label("Positioning Method: ")
        layout.add(floorLabel, 0, 5)

        positioningCombobox.items.add(Label(PositioningType.StrongestRSSI.name))
        positioningCombobox.items.add(Label(PositioningType.SlidingWindow.name))
        positioningCombobox.items.add(Label(PositioningType.Filtered.name))
        positioningCombobox.items.add(Label(PositioningType.Graphed.name))

        val idx = positioningCombobox.items.indexOfFirst { label -> label.text.equals(configModel.settings.positioningType.name) }
        positioningCombobox.selectionModel.select(idx)

        layout.add(positioningCombobox, 1, 5)
    }

    /**
     * Appends the buttons to the layout.
     */
    private fun initButtons() {
        applyButton.styleClass.add("applyButton")
        applyButton.setOnAction {

            val positioningType = PositioningType.valueOf(positioningCombobox.value.text)
            val configuration = Settings(floorPlanPathField.text, buildingPathField.text, walkRecordingPathField.text, routePathField.text, buildingGraphPathField.text, positioningType)
            controller.applySettings(configuration)

            stage?.let {
                it.close()
            }
        }

        cancelButton.styleClass.add("cancelButton")
        cancelButton.setOnAction {
            stage?.let {
                it.close()
            }
        }
    }

    /**
     * Gets a file path from a chosen file.
     *
     * @return the file path of a chosen file.
     */
    private fun getFilePathFromFileChooser(title: String): String? {
        stage?.let { stage ->
            val chooser = FileChooser()
            chooser.title = title

            val file = chooser.showOpenDialog(stage)
            file?.let {
                return file.absolutePath
            }
        }

        return null
    }
}