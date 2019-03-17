package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.App

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

    override fun isPaused(): Boolean {
        return if (App.isDebugMode) paused else false
    }

    override fun resume() {
        paused = false
        state?.let { state ->
            notify {
                it.onDebugResume(this, state)
            }
        }
    }

    override fun skipToNextWayPoint() {
        paused = false
        state?.let { state ->
            nextWaypointNumber = state.wayPointNumber + 1

            notify {
                it.onDebugResume(this, state)
            }
        }
    }

    override fun log(entry: DebugLogEntry) {
        entries.add(entry)

        report {
            it.logged(this, entry)
        }
    }

    // Debuggable Observers

    override fun addDebuggable(debuggable: Debuggable) {
        debuggables.add(debuggable)
    }

    override fun removeDebuggable(debuggable: Debuggable) {
        debuggables.remove(debuggable)
    }

    private fun notify(executionBlock: (Debuggable) -> Unit) {
        if (!App.isDebugMode) return

        debuggables.forEach {
            executionBlock(it)
        }
    }

    // Debug Reporting

    private fun report(executionBlock: (DebugReporting) -> Unit) {
        if (!App.isDebugMode) return

        debugObservers.forEach {
            executionBlock(it)
        }
    }

    override fun addDebugReportObserver(observer: DebugReporting) {
        debugObservers.add(observer)
    }

    override fun removeDebugReportObserver(observer: DebugReporting) {
        debugObservers.remove(observer)
    }
}