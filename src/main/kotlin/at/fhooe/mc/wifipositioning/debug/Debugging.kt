package at.fhooe.mc.wifipositioning.debug

/**
 * A common interface for a debugger.
 *
 * @property state the current application state
 * @property allLogEntries contains all log entries during the lifecycle of the application
 */
interface Debugging {
    var state: ApplicationState?
    val allLogEntries: List<DebugLogEntry>

    /**
     * Gets the current paused state of the application.
     *
     * @return true if the application is paused, false if not.
     */
    fun isPaused(): Boolean

    /**
     * Resumes the application after the debugger has paused it and pauses again on the next interpolation step.
     */
    fun resume()

    /**
     * Resumes the application and pauses again on the next waypoint.
     */
    fun skipToNextWayPoint()

    /**
     * Logs an [entry].
     */
    fun log(entry: DebugLogEntry)

    /**
     * Adds a [debuggable] object.
     */
    fun addDebuggable(debuggable: Debuggable)

    /**
     * Removes a [debuggable] object.
     */
    fun removeDebuggable(debuggable: Debuggable)

    /**
     * Adds a debug observer.
     */
    fun addDebugReportObserver(observer: DebugReporting)

    /**
     * Removes a debug observer.
     */
    fun removeDebugReportObserver(observer: DebugReporting)
}