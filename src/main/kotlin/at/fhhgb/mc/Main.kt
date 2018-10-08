package at.fhhgb.mc

import at.fhhgb.mc.controller.MainController
import at.fhhgb.mc.model.MainComponents
import at.fhhgb.mc.view.MainFrame

fun main(args: Array<String>) {
    val view = MainFrame()
    val model = MainComponents()
    val controller = MainController(view, model)
    controller.start()
}
