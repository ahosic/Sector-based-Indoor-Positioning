package at.fhooe.mc.wifipositioning.interfaces;

import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.AccessPoint;

import java.util.List;

public interface PlaybackCallbackInterface {
    public void nearestAccessPoint(List<AccessPoint> accessPoints);

    public void allAccessPoints(List<AccessPoint> accessPointList);

    public void wayPointCount(int[] wayPointCount);
}
