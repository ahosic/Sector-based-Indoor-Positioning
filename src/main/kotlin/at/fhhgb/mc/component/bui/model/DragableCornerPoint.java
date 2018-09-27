package at.fhhgb.mc.component.bui.model;

import java.awt.geom.Ellipse2D;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class DragableCornerPoint implements IDragableEllipse {

    private Ellipse2D elLipse2D;

    public DragableCornerPoint(int x, int y, int width, int height) {
        elLipse2D = new Ellipse2D.Double(x, y, width, height);
    }

    @Override
    public Ellipse2D getEllipse2D() {
        return elLipse2D;
    }

    @Override
    public void setPositionToEllipse() {

    }
}
