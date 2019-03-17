package at.fhooe.mc.wifipositioning.debug

interface Debugging {
    var state: ApplicationState?
    val allLogEntries: List<DebugLogEntry>

    fun isPaused(): Boolean
    fun resume()
    fun log(entry: DebugLogEntry)

    fun addDebuggable(debuggable: Debuggable)
    fun removeDebuggable(debuggable: Debuggable)

    fun addDebugReportObserver(observer: DebugReporting)
    fun removeDebugReportObserver(observer: DebugReporting)
}