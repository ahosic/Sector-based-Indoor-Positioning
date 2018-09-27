package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.Collections;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class StrongestAccessPointPositioning extends BasePositioning {


    public StrongestAccessPointPositioning(List<AccessPoint> accessPoints) {
        super(accessPoints);
    }


    @Override
    public Position calculatePosition(List<AccessPoint> accessPointList) {
        Collections.sort(accessPointList);
        Collections.reverse(accessPointList);
        for (AccessPoint accessPoint : accessPointList) {
            for (AccessPoint ap : getAccessPointList()) {
                if (ap.getBSSID().toLowerCase().equals(accessPoint.getBSSID().toLowerCase())) {
                    return new Position(ap.getPosition().getX(), ap.getPosition().getY());
                }
            }
        }
        return null;
    }
}
