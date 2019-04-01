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
    private val skipToNextWaypointButton = JFXButton("Skip to next Waypoint".toUpperCase())

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
        appendSkipToNextWaypointButton(toolBoxPane)

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

    private fun appendSkipToNextWaypointButton(pane: HBox) {
        skipToNextWaypointButton.styleClass.add("resumeButton")
        skipToNextWaypointButton.setOnAction {
            Platform.runLater {
                debugger.skipToNextWayPoint()
            }
        }

        pane.children.add(skipToNextWaypointButton)
    }

    private fun appendStatePane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Application State")

        statePane.styleClass.add("pane")
        statePane.isEditable = false
        statePane.prefWidth = 200.0

        box.top = label
        box.center = statePane

        pane.children.add(box)
    }

    private fun appendScanningPane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Scanned Access Points")

        scanningInputPane.styleClass.add("pane")
        scanningInputPane.isEditable = false
        scanningInputPane.prefWidth = 250.0

        box.top = label
        box.center = scanningInputPane

        pane.children.add(box)
    }

    private fun appendOutputPane(pane: HBox) {
        val box = BorderPane()
        val label = Label("Positioning Output")

        outputPane.styleClass.add("pane")
        outputPane.isEditable = false
        outputPane.prefWidth = 500.0

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
        Platform.runLater {
            synchronized(this) {
                // Application State
                val output = StringBuilder()
                output.appendln(state.currentEstimation)
                output.appendln("Waypoint: ${state.wayPointNumber}")
                output.appendln("Interpolation step: ${state.interpolationStep}")

                statePane.text = output.toString()

                // Scanning

                val scannedOutput = StringBuilder()
                for (scanned in state.scannedAccessPoints.sortedByDescending { it.signalLevel }) {
                    scannedOutput.appendln(scanned)
                }

                scanningInputPane.text = scannedOutput.toString()
                outputPane.text = ""
            }
        }
    }

    override fun logged(debugger: Debugging, entry: DebugLogEntry) {
        Platform.runLater {
            synchronized(this) {
                val output = StringBuilder(outputPane.text)
                output.appendln(entry)

                outputPane.text = output.toString()
            }
        }
    }
}