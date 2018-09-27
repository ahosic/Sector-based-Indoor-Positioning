package at.fhhgb.mc.component.sim.domain;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class PixelPosition {
    private final int xPosition;
    private final int yPosition;

    public PixelPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
