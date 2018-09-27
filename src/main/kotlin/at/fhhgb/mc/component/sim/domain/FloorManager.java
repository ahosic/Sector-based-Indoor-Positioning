package at.fhhgb.mc.component.sim.domain;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.simulator.Floor;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class FloorManager {

    private Floor floor;
    private double widthMultiplikator;
    private double heightMultiplikator;

    public FloorManager(Floor floor) {
        this.floor = floor;
        calculateMultiplicators();
    }

    private void calculateMultiplicators() {
        widthMultiplikator = floor.getFloorImage().getWidth() / (floor.getFloorWidth() + floor.getOffsetRight() + floor.getOffsetLeft());
        heightMultiplikator = floor.getFloorImage().getHeight() / (floor.getFloorHeight() + floor.getOffsetTop() + floor.getOffsetBottom());
    }

    public PixelPosition calculatePixelPositionFromMeter(int x, int y) {
        int xPos = (int) ((x * widthMultiplikator) + (floor.getOffsetLeft() * widthMultiplikator));
        int yPos = (int) ((y * heightMultiplikator) + (floor.getOffsetTop() * heightMultiplikator));

        return new PixelPosition(xPos, yPos);
    }

    public Position calculateMetricPositionFromPixel(int x, int y) {
        double xPos = (x / widthMultiplikator) - (floor.getOffsetLeft() / widthMultiplikator);
        double yPos = (x / heightMultiplikator) - (floor.getOffsetTop() / heightMultiplikator);

        return new Position((int) Math.round(xPos), (int) Math.round(yPos));
    }

    public int getOffsetTopInPixel() {
        return (int) Math.round(floor.getOffsetTop() * getHeightMultiplikator());
    }

    public int getOffsetLeftInPixel() {
        return (int) Math.round((floor.getOffsetLeft() * getWidthMultiplikator()));
    }

    public int getOffsetBottomInPixel() {
        return (int) Math.round(((floor.getOffsetTop() + floor.getFloorHeight()) * getHeightMultiplikator()));
    }

    public int getOffsetRightInPixel() {
        return (int) Math.round(((floor.getOffsetLeft() + floor.getFloorWidth()) * getWidthMultiplikator()));
    }

    public void changeFloor(Floor floor) {
        this.floor = floor;
        calculateMultiplicators();
    }

    public double getWidthMultiplikator() {
        return widthMultiplikator;
    }

    public double getHeightMultiplikator() {
        return heightMultiplikator;
    }

    public Floor getFloor() {
        return floor;
    }
}
