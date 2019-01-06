package at.fhooe.mc.wifipositioning.interfaces;

import at.fhooe.mc.wifipositioning.model.simulation.recording.ScannedAccessPoint;

import java.util.List;

public interface PlaybackCallbackInterface {
    public void nearestAccessPoint(List<ScannedAccessPoint> scannedAccessPoints);

    public void allAccessPoints(List<ScannedAccessPoint> scannedAccessPointList);

    public void wayPointCount(int[] wayPointCount);
}
