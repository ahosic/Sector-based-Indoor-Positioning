package at.fhooe.mc.wifipositioning

import at.fhooe.mc.wifipositioning.debug.DebugController
import at.fhooe.mc.wifipositioning.debug.Debugging
import at.fhooe.mc.wifipositioning.ui.SimulationApplication
import javafx.application.Application

/**
 * The main application.
 *
 * @property isDebugMode indicates whether the app was started into debug mode or not.
 * @property debugger the application's debugger.
 */
class App {
    companion object {
        var isDebugMode: Boolean = false
            private set

        @JvmField val debugger: Debugging = DebugController()

        /**
         * Launches tha main application. Pass -debug as argument to start into debug mode.
         */
        @JvmStatic fun main(args: Array<String>) {
            App.isDebugMode = args.any { it.contains("-debug") }

            Application.launch(SimulationApplication::class.java, *args)
        }
    }
}
