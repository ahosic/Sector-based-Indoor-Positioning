package at.fhooe.mc.wifipositioning

import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.ui.SimulationApplication
import javafx.application.Application

fun main(args: Array<String>) {
    ConfigurationModel.debugMode = args.any { it.contains("-debug") }
    Application.launch(SimulationApplication::class.java, *args)
}
