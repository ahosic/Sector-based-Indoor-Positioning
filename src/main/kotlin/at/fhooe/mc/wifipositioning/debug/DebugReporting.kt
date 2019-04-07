package at.fhooe.mc.wifipositioning.debug

/**
 * A common interface for debugging views to update their state on debugger state changes.
 */
interface DebugReporting {
    /**
     * Event fired, when the application state changes.
     */
    fun stateChanged(debugger: Debugging, state: ApplicationState)

    /**
     * Event fired, when a new log entry was added.
     */
    fun logged(debugger: Debugging, entry: DebugLogEntry)
}