package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.App

/**
 * A concrete implementation of [Debugging], which notifies debugging views on changes.
 *
 * @property paused indicates whether the debugger is in paused state (simulation does not proceed until paused == false)
 * @property state the current application state (pauses the debugger and application, when a new state is set)
 * @property allLogEntries contains all log entries during the lifecycle of the application
 * @property entries private list holding all log entries.
 * @property debuggables a list of objects that can be debugged
 * @property debugObservers a list of objects that observe the debugger on state changes (views of the debugger)
 * @property nextWaypointNumber the next waypoint number of the simulation
 */
class DebugController : Debugging {

    private var paused = false

    override var state: ApplicationState? = null
        set(value) {
            field = value

            if (nextWaypointNumber != null && nextWaypointNumber != value?.wayPointNumber) return

            nextWaypointNumber = null
            paused = true

            value?.let {
                report {
                    it.stateChanged(this, value)
                }

                notify {
                    it.onDebugPause(this, value)
                }
            }

        }

    override val allLogEntries: List<DebugLogEntry>
        get() = entries

    private var entries: MutableList<DebugLogEntry> = mutableListOf()

    private var debuggables: MutableList<Debuggable> = mutableListOf()
    private var debugObservers: MutableList<DebugReporting> = mutableListOf()

    private var nextWaypointNumber: Int? = null

    /**
     * Gets the current paused state of the application.
     *
     * @return true if the application is paused, false if not.
     */
    override fun isPaused(): Boolean {
        return if (App.isDebugMode) paused else false
    }

    /**
     * Resumes the application after the debugger has paused it and pauses again on the next interpolation step.
     */
    override fun resume() {
        paused = false
        state?.let { state ->
            notify {
                it.onDebugResume(this, state)
            }
        }
    }

    /**
     * Resumes the application and pauses again on the next waypoint.
     */
    override fun skipToNextWayPoint() {
        paused = false
        state?.let { state ->
            nextWaypointNumber = state.wayPointNumber + 1

            notify {
                it.onDebugResume(this, state)
            }
        }
    }

    /**
     * Logs an [entry].
     */
    override fun log(entry: DebugLogEntry) {
        entries.add(entry)

        report {
            it.logged(this, entry)
        }
    }

    // Debuggable Observers

    /**
     * Adds a [debuggable] object.
     */
    override fun addDebuggable(debuggable: Debuggable) {
        debuggables.add(debuggable)
    }

    /**
     * Removes a [debuggable] object.
     */
    override fun removeDebuggable(debuggable: Debuggable) {
        debuggables.remove(debuggable)
    }

    /**
     * Notifies all [debuggables] on debugger state changes.
     */
    private fun notify(executionBlock: (Debuggable) -> Unit) {
        if (!App.isDebugMode) return

        debuggables.forEach {
            executionBlock(it)
        }
    }

    // Debug Reporting

    /**
     * Notify views on debugger changes.
     */
    private fun report(executionBlock: (DebugReporting) -> Unit) {
        if (!App.isDebugMode) return

        debugObservers.forEach {
            executionBlock(it)
        }
    }

    /**
     * Adds a debug observer.
     */
    override fun addDebugReportObserver(observer: DebugReporting) {
        debugObservers.add(observer)
    }

    /**
     * Removes a debug observer.
     */
    override fun removeDebugReportObserver(observer: DebugReporting) {
        debugObservers.remove(observer)
    }
}