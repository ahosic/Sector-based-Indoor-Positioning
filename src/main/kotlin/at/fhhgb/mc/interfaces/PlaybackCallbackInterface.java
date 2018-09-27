package at.fhhgb.mc.interfaces;

import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.List;

public interface PlaybackCallbackInterface {
    public void nearestAccessPoint(List<AccessPoint> accessPoints);

    public void allAccessPoints(List<AccessPoint> accessPointList);

    public void wayPointCount(int[] wayPointCount);
}
