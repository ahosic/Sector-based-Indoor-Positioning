package at.fhooe.mc.wifipositioning.utility

import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.DataSnapshot
import at.fhooe.mc.wifipositioning.interfaces.PlayBackEnum
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface

class Player(internal var dataSnapshots: List<DataSnapshot>, internal var callback: PlaybackCallbackInterface, internal val playBackEnum: PlayBackEnum) {
    private var playBackSpeed: Long = 500
    private var wayPointCount: IntArray? = null

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

    fun startPlayback(): Boolean {
        println(dataSnapshots.size)
        var i = 1
        for (dataSnapshot in dataSnapshots) {
            println("Snapshot $i")
            i++
            for (wLanData in dataSnapshot.getwLanDataList()) {
                if (wLanData.ssid == "fhhgb") {
                    callback.allAccessPoints(wLanData.accessPoints)
                }
            }
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
            }

        }
        return false
    }
}
