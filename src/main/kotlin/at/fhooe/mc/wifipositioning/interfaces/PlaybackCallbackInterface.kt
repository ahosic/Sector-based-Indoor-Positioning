package at.fhooe.mc.wifipositioning.interfaces

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

/**
 * A callback interface used by a player to simulate a detection of new access points.
 */
interface PlaybackCallbackInterface {
    /**
     * Fired, when new access points are detected.
     */
    fun allAccessPoints(scannedAccessPointList: List<ScannedAccessPoint>)

    /**
     * Sets the waypoint count of the callback object.
     */
    fun wayPointCount(wayPointCount: IntArray)
}
