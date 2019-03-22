package at.fhooe.mc.wifipositioning.debug

interface DebugReporting {
    fun stateChanged(debugger: Debugging, state: ApplicationState)
    fun logged(debugger: Debugging, entry: DebugLogEntry)
}