package at.fhooe.mc.wifipositioning.utility

import at.fhooe.mc.wifipositioning.model.simulation.recording.DataSnapshot
import at.fhooe.mc.wifipositioning.interfaces.PlayBackEnum
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface

class Player(internal var dataSnapshots: List<DataSnapshot>, internal var callback: PlaybackCallbackInterface, internal val playBackEnum: PlayBackEnum) {
    private var playBackSpeed: Long = 1
    private var wayPointCount: IntArray? = null
    var isRunning = false
    private set
    var pausePlayback = false
    var stopPlayback = false

    init {
        setNumberOfAPSteps()
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

    fun setPlayBackSpeed(playBackSpeed: Long) {
        this.playBackSpeed = playBackSpeed
    }

    fun startPlayback() {
        println(dataSnapshots.size)
        isRunning = true
        var i = 1
        for (dataSnapshot in dataSnapshots) {

            while (pausePlayback) {
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

            println("Snapshot $i")
            i++

            dataSnapshot.getwLanDataList()
                    .stream()
                    .filter { wd -> wd.ssid == "fhhgb" }
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
        return
    }
}
