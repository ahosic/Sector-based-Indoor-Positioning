package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.List;

public interface IPositioning {
    public Position calculatePosition(List<AccessPoint> accessPointList);
}
