package at.fhooe.mc.wifipositioning

import at.fhooe.mc.wifipositioning.ui.SimulationApplication
import javafx.application.Application

fun main(args: Array<String>) {
//    val view = MainFrame()
//    val model = MainComponents()
//    val controller = MainController(view, model)
//    controller.start()

    Application.launch(SimulationApplication::class.java, *args)
}
