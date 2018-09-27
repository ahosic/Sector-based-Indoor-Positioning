package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public abstract class BasePositioning implements IPositioning {

    private List<AccessPoint> accessPointList;

    public BasePositioning(List<AccessPoint> accessPointList) {
        this.accessPointList = accessPointList;
    }

    @Override
    public abstract Position calculatePosition(List<AccessPoint> accessPointList);

    public List<AccessPoint> getAccessPointList() {
        return accessPointList;
    }
}
