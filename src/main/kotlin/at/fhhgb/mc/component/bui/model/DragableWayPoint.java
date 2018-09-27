package at.fhhgb.mc.component.bui.model;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.features.Waypoint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.geom.Ellipse2D;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class DragableWayPoint extends Waypoint implements IDragableEllipse {

    @JsonIgnore
    private Ellipse2D ellipse2D;
    @JsonIgnore
    private int width;
    @JsonIgnore
    private int height;

    public DragableWayPoint(int x, int y, int width, int height, int id, String description, int floor) {
        super(id, description, floor);
        ellipse2D = new Ellipse2D.Double(x, y, width, height);
        setPositionToEllipse();
        this.width = width;
        this.height = height;
    }

    @Override
    public Ellipse2D getEllipse2D() {
        return ellipse2D;
    }

    @Override
    public void setPositionToEllipse() {
        super.setPosition(new Position((int) ellipse2D.getCenterX(), (int) ellipse2D.getCenterY()));
    }
}
