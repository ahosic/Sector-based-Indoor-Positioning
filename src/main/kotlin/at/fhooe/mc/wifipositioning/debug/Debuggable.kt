package at.fhooe.mc.wifipositioning.debug

/**
 * A common interface for object that can be debugged.
 */
interface Debuggable {

    /**
     * Event, when the debuggable needs to enter into paused state.
     */
    fun onDebugPause(debugger: DebugController, state: ApplicationState)

    /**
     * Event, when the debuggable should resume with its execution.
     */
    fun onDebugResume(debugger: DebugController, state: ApplicationState)
}