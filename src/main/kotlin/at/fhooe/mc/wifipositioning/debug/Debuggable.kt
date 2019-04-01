package at.fhooe.mc.wifipositioning.debug

interface Debuggable {
    fun onDebugPause(debugger: DebugController, state: ApplicationState)
    fun onDebugResume(debugger: DebugController, state: ApplicationState)
}