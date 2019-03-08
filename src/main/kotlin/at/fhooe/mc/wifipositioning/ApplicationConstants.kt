package at.fhooe.mc.wifipositioning

import at.fhooe.mc.wifipositioning.debug.DebugController
import at.fhooe.mc.wifipositioning.debug.Debugging

class App {
    companion object {
        @JvmField val debugger: Debugging = DebugController()
    }
}