package at.fhhgb.mc.component.bui.model;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.awt.geom.Ellipse2D;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class DragableAccessPoint extends AccessPoint implements IDragableEllipse {

    private Ellipse2D ellipse2D;
    private int width;
    private int height;

    public DragableAccessPoint(int x, int y, int width, int height, int floor) {
        super("AccessPoint", new Position(x, y), floor);
        this.height = height;
        this.width = width;
        ellipse2D = new Ellipse2D.Double(x, y, width, height);
        setPositionToEllipse();
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
