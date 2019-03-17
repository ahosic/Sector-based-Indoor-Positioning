package at.fhooe.mc.wifipositioning.ui

import at.fhooe.mc.wifipositioning.debug.ApplicationState
import at.fhooe.mc.wifipositioning.debug.DebugLogEntry
import at.fhooe.mc.wifipositioning.debug.DebugReporting
import at.fhooe.mc.wifipositioning.debug.Debugging
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextArea
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.stage.Stage
import java.lang.StringBuilder

class DebugApplication(private val debugger: Debugging) : Application(), DebugReporting {
    private var stage: Stage? = null
    private val stylePath: String

    private val statePane = JFXTextArea()
    private val scanningInputPane = JFXTextArea()
    private val outputPane = JFXTextArea()
    private val resumeButton = JFXButton("Resume".toUpperCase())

    init {
        stylePath = javaClass.classLoader.getResource("debugStyle.css").toExternalForm()
        debugger.addDebugReportObserver(this)
    }

    protected fun finalize() {
        debugger.removeDebugReportObserver(this)
    }

    override fun start(stage: Stage) {
        this.stage = stage
        val layout = createLayout()

        stage.scene = Scene(layout)
        stage.title = "Debug"
        stage.scene.stylesheets.add(stylePath)
        stage.show()
    }

    private fun createLayout(): Pane {
        val root = BorderPane()
        root.padding = Insets(8.0)
        root.styleClass.add("mainLayout")

        val toolBoxPane = HBox()
        toolBoxPane.spacing = 8.0
        toolBoxPane.padding = Insets(8.0)

        val mainPane = HBox()
        mainPane.spacing = 8.0

        appendResumeButton(toolBoxPane)

        appendStatePane(mainPane)
        appendScanningPane(mainPane)
        appendOutputPane(mainPane)

        root.top = toolBoxPane
        root.center = mainPane

        return root
    }

    private fun appendResumeButton(pane: HBox) {
        resumeButton.styleClass.add("resumeButton")
        resumeButton.setOnAction {
            Platform.runLater {
                debugger.resume()
            }
        }

        pane.children.add(resumeButton)
    }

    private fun appendStatePane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Application State")

        statePane.styleClass.add("pane")
        statePane.isEditable = false

        box.top = label
        box.center = statePane

        pane.children.add(box)
    }

    private fun appendScanningPane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Scanned Access Points")

        scanningInputPane.styleClass.add("pane")
        scanningInputPane.isEditable = false

        box.top = label
        box.center = scanningInputPane

        pane.children.add(box)
    }

    private fun appendOutputPane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Positioning Output")

        outputPane.styleClass.add("pane")
        outputPane.isEditable = false

        val output = StringBuilder()
        for(entry in debugger.allLogEntries) {
            output.appendln(entry)
        }

        outputPane.text = output.toString()

        box.top = label
        box.center = outputPane

        pane.children.add(box)
    }

    override fun stateChanged(debugger: Debugging, state: ApplicationState) {
        // Application State
        val output = StringBuilder()
        output.appendln("Sector ID: ${state.currentSector?.id}")
        output.appendln("Description: ${state.currentSector?.description}")
        output.appendln("Waypoint: ${state.wayPointNumber}")
        output.appendln("Interpolation step: ${state.interpolationStep}")

        statePane.text = output.toString()

        // Scanning

        val scannedOutput = StringBuilder()
        for(scanned in state.scannedAccessPoints){
            scannedOutput.appendln(scanned)
        }

        scanningInputPane.text = scannedOutput.toString()
    }

    override fun logged(debugger: Debugging, entry: DebugLogEntry) {
        val output = StringBuilder(outputPane.text)
        output.appendln(entry)

        outputPane.text = output.toString()
    }
}