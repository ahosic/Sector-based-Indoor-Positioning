package at.fhooe.mc.wifipositioning

import at.fhooe.mc.wifipositioning.debug.DebugController
import at.fhooe.mc.wifipositioning.debug.Debugging
import at.fhooe.mc.wifipositioning.ui.SimulationApplication
import javafx.application.Application

class App {
    companion object {
        var isDebugMode: Boolean = false
            private set

        @JvmField val debugger: Debugging = DebugController()

        @JvmStatic fun main(args: Array<String>) {
            App.isDebugMode = args.any { it.contains("-debug") }

            Application.launch(SimulationApplication::class.java, *args)
        }
    }
}
