package at.fhhgb.mc.component.sim.model.simulator;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class FloorBounds {
    private double offsetLeft = -1;
    private double offsetRight = -1;
    private double offsetTop = -1;
    private double offsetBottom = -1;
    private double floorWidth = -1;
    private double floorHeight = -1;

    public FloorBounds() {

    }

    public FloorBounds(double offsetLeft, double offsetRight, double offsetTop, double offsetBottom, double floorWidth, double floorHeight) {
        this.offsetLeft = offsetLeft;
        this.offsetRight = offsetRight;
        this.offsetTop = offsetTop;
        this.offsetBottom = offsetBottom;
        this.floorWidth = floorWidth;
        this.floorHeight = floorHeight;
    }


    public FloorBounds(double width, double height, Point upperLeft, Point lowerRight, BufferedImage image) {

        double meterWidth = (lowerRight.x - upperLeft.x) / width;
        double meterHeight = (lowerRight.y - upperLeft.y) / height;

        offsetLeft = upperLeft.x / meterWidth;
        offsetTop = upperLeft.y / meterHeight;
        offsetRight = (image.getWidth() - lowerRight.x) / meterWidth;
        offsetBottom = (image.getHeight() - lowerRight.y) / meterHeight;

        this.floorWidth = width;
        this.floorHeight = height;
    }

    public double getOffsetLeft() {
        return offsetLeft;
    }

    public void setOffsetLeft(double offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    public double getOffsetRight() {
        return offsetRight;
    }

    public void setOffsetRight(double offsetRight) {
        this.offsetRight = offsetRight;
    }

    public double getOffsetTop() {
        return offsetTop;
    }

    public void setOffsetTop(double offsetTop) {
        this.offsetTop = offsetTop;
    }

    public double getOffsetBottom() {
        return offsetBottom;
    }

    public void setOffsetBottom(double offsetBottom) {
        this.offsetBottom = offsetBottom;
    }

    public double getFloorWidth() {
        return floorWidth;
    }

    public void setFloorWidth(double floorWidth) {
        this.floorWidth = floorWidth;
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(double floorHeight) {
        this.floorHeight = floorHeight;
    }
}
