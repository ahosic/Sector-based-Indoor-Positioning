package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.controller.SettingsController
import at.fhooe.mc.wifipositioning.model.configuration.Configuration
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



class SettingsApplication(val configModel: ConfigurationModel) : Application() {

    private val controller: SettingsController
    private var stage: Stage? = null

    private var floorPlanPathField = JFXTextField()
    private var walkRecordingPathField = JFXTextField()
    private var positioningCombobox = JFXComboBox<Label>()

    private val applyButton = JFXButton("Save".toUpperCase())
    private val cancelButton = JFXButton("Cancel".toUpperCase())
    private val stylePath: String

    init {
        controller = SettingsController(configModel)
        stylePath = javaClass.classLoader.getResource("settingsStyle.css").toExternalForm()
    }

    override fun start(stage: Stage) {
        this.stage = stage
        val layout = createLayout()

        stage.scene = Scene(layout)
        stage.title = "Settings"
        stage.scene.stylesheets.add(stylePath)
        stage.show()
    }

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
        initWalkRecordingSettings(settingsLayout)
        initPositoiningSelection(settingsLayout)

        val buttonPane = HBox()
        buttonPane.alignment = Pos.CENTER_RIGHT
        buttonPane.spacing = 16.0

        initButtons()
        buttonPane.children.addAll(cancelButton, applyButton)

        root.children.addAll(settingsLayout, buttonPane)

        return root
    }

    private fun initFloorPlanSettings(layout: GridPane) {
        val floorLabel = Label("Floor plan file: ")
        layout.add(floorLabel, 0, 0)

        floorPlanPathField.text = configModel.configuration.floorsPath
        layout.add(floorPlanPathField, 1,0)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select floor plan")
            floorPlanPathField.text = path ?: floorPlanPathField.text
        }


        layout.add(selectPathButton, 3, 0)
    }

    private fun initWalkRecordingSettings(layout: GridPane) {
        val floorLabel = Label("Walk recording file: ")
        layout.add(floorLabel, 0, 1)

        walkRecordingPathField.text = configModel.configuration.walkRecordingPath
        layout.add(walkRecordingPathField, 1,1)

        val selectPathButton = JFXButton("Select".toUpperCase())
        selectPathButton.styleClass.add("pathButton")
        selectPathButton.setOnAction {
            val path = getFilePathFromFileChooser("Select walk recording")
            walkRecordingPathField.text = path ?: walkRecordingPathField.text
        }


        layout.add(selectPathButton, 3, 1)
    }

    private fun initPositoiningSelection(layout: GridPane) {
        val floorLabel = Label("Positioning Method: ")
        layout.add(floorLabel, 0, 2)

        positioningCombobox.items.add(Label(PositioningType.STRONGEST_AP_POSITIONING.name))
        positioningCombobox.items.add(Label(PositioningType.AVERAGE_POSITIONING.name))
        positioningCombobox.items.add(Label(PositioningType.FILTERED_POSITIONING.name))

        val idx = positioningCombobox.items.indexOfFirst { label -> label.text.equals(configModel.configuration.positioningType.name) }
        positioningCombobox.selectionModel.select(idx)

        layout.add(positioningCombobox, 1, 2)
    }

    private fun initButtons() {
        applyButton.styleClass.add("applyButton")
        applyButton.setOnAction {

            val positioningType = PositioningType.valueOf(positioningCombobox.value.text)
            val configuration = Configuration(floorPlanPathField.text, walkRecordingPathField.text, positioningType)
            controller.applyConfiguration(configuration)

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