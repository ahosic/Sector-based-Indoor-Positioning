package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.List;
import java.util.Optional;

public class AveragePositioning extends BasePositioning {

    private AccessPointSlidingWindow accessPointSlidingWindow;
    private int windowSize;

    public AveragePositioning(List<AccessPoint> accessPointList, int windowSize) {
        super(accessPointList);

        this.windowSize = windowSize;
        accessPointSlidingWindow = new AccessPointSlidingWindow(windowSize);
    }

    @Override
    public Position calculatePosition(List<AccessPoint> accessPointList) {
        accessPointSlidingWindow.addElement(accessPointList);

        String bssid = accessPointSlidingWindow.getBestAverageBSSID().toLowerCase();

        Optional<AccessPoint> accessPoint = getAccessPointList().stream()
                .filter(ap -> ap.getBSSID().toLowerCase().equals(bssid))
                .findFirst();

        if (!accessPoint.isPresent()) return null;

        return accessPoint.get().getPosition();
    }
}
