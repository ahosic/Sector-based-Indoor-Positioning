package at.fhhgb.mc.component.sim.sectorizing;

import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.simulator.Floor;
import at.fhhgb.mc.component.sim.view.DrawingContext;

import java.awt.*;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class GridSectors implements ISectorizing {

    Position position;
    private int xGrid = 3;
    private int yGrid = 3;

    public GridSectors() {

    }

    @Override
    public void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix) {
        Floor floor = floorManager.getFloor();
        if (!(yGrid == 1 && xGrid == 1)) {
            int cellHeight = (int) ((floor.getFloorHeight() * floorManager.getHeightMultiplikator()) / yGrid);
            int cellWidth = (int) ((floor.getFloorWidth() * floorManager.getWidthMultiplikator()) / xGrid);
            for (int y = floorManager.getOffsetTopInPixel(); y < floorManager.getOffsetBottomInPixel() - cellHeight / 2; y += cellHeight) {
                for (int x = floorManager.getOffsetLeftInPixel(); x < floorManager.getOffsetRightInPixel() - cellWidth / 2; x += cellWidth) {
                    Rectangle rectangle = new Rectangle(x, y, cellWidth, cellHeight);
                    if (position != null && rectangle.contains(new Point(position.getX(), position.getY()))) {
                        DrawingContext.drawGrid(rectangle, g, m_tMatrix, true);
                    } else {
                        DrawingContext.drawGrid(rectangle, g, m_tMatrix, false);
                    }
                }
            }
        }
    }

    @Override
    public void addCurrentPosition(Position position) {
        this.position = position;
    }

}
