package at.fhooe.mc.wifipositioning.debug

import at.fhooe.mc.wifipositioning.App

class DebugController : Debugging {
    private var paused = false

    override var state: ApplicationState? = null
        set(value) {
            field = value
            paused = true

            value?.let {
                notify {
                    it.onDebugPause(this, value)
                }
            }
        }

    private var entries: MutableList<DebugLogEntry> = mutableListOf()

    private var debuggables: MutableList<Debuggable> = mutableListOf()

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

    override fun log(entry: DebugLogEntry) {
        entries.add(entry)
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
}