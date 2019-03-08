package at.fhooe.mc.wifipositioning.debug

interface Debugging {
    var state: ApplicationState?

    fun isPaused(): Boolean
    fun resume()
    fun log(entry: DebugLogEntry)

    fun addDebuggable(debuggable: Debuggable)
    fun removeDebuggable(debuggable: Debuggable)
}