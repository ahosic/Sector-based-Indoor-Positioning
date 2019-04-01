package at.fhooe.mc.wifipositioning.model.recording

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.debug.ApplicationState
import at.fhooe.mc.wifipositioning.debug.DebugController
import at.fhooe.mc.wifipositioning.debug.DebugLogEntry
import at.fhooe.mc.wifipositioning.debug.Debuggable
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel

class Player(private var dataSnapshots: List<DataSnapshot>,
             private var callback: PlaybackCallbackInterface,
             private val config: ConfigurationModel) : Debuggable {

    private var playBackSpeed: Long = 1
    private var wayPointCount: IntArray? = null
    var isRunning = false
    private set
    var pausePlayback = false
    var stopPlayback = false
    var debugPause = false

    init {
        App.debugger.addDebuggable(this)
        setNumberOfAPSteps()
    }

    protected fun finalize() {
        App.debugger.removeDebuggable(this)
    }

    private fun setNumberOfAPSteps() {
        wayPointCount = IntArray(dataSnapshots[dataSnapshots.size - 1].wayPointID)
        wayPointCount?.let { wayPointCount ->
            for (dataSnapshot in dataSnapshots) {
                wayPointCount[dataSnapshot.wayPointID - 1] += 1
            }
            callback.wayPointCount(wayPointCount)
        }
    }

    fun startPlayback() {
        isRunning = true
        var i = 1
        for (dataSnapshot in dataSnapshots) {

            while (pausePlayback || debugPause) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {}
            }

            if (stopPlayback) {
                isRunning = false
                pausePlayback = false
                stopPlayback = false
                return
            }

            i++

            dataSnapshot.wifiDataList
                    .stream()
                    .filter { wd -> wd.ssid == config.building.ssid }
                    .forEach {
                        callback.allAccessPoints(it.scannedAccessPoints)
                    }

            try {
                Thread.sleep(playBackSpeed)
            } catch (e: InterruptedException) {
            }

        }

        isRunning = false
        pausePlayback = false
        stopPlayback = false
        return
    }

    // Debugging

    override fun onDebugPause(debugger: DebugController, state: ApplicationState) {
        debugPause = true
    }

    override fun onDebugResume(debugger: DebugController, state: ApplicationState) {
        debugPause = false
    }
}
