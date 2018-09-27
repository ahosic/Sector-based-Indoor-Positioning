package at.fhhgb.mc.component.sim.model.initialisations;

import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.Position;

import java.awt.*;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class WaypointPosition {
    final int wayPointId;
    final Position wayPointPosition;

    public WaypointPosition(int wayPointId, Position wayPointPosition) {
        this.wayPointId = wayPointId;
        this.wayPointPosition = wayPointPosition;
    }

    public int getWayPointId() {
        return wayPointId;
    }

    public Position getWayPointPosition() {
        return wayPointPosition;
    }

    public void paint(Graphics _g, Matrix _matrix){
        _g.setColor(new Color(100, 255, 100));
        _g.fillOval(wayPointPosition.getX()-5,wayPointPosition.getY()-5,10,10);
    }
}
