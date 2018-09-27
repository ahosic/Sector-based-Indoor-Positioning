package at.fhhgb.mc.component.bui.utility;

import at.fhhgb.mc.component.sim.domain.Matrix;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public abstract class DragableEllipse<T> {

    Ellipse2D ellipse2D;
    T data;
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private Color color;

    public DragableEllipse(int posX, int posY, int width, int height, Color color) {
        this.color = color;
        ellipse2D = new Ellipse2D.Double(posX, posY, width, height);

    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }

    public Color getColor() {
        return color;
    }

    public Ellipse2D getEllipse2D() {
        return ellipse2D;
    }

    public void drawComponent(Graphics _g, Matrix _m) {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
