package at.fhhgb.mc.component.bui.model;

import at.fhhgb.mc.component.base.BaseModel;
import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.model.simulator.Floor;
import at.fhhgb.mc.component.sim.view.DrawingContext;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class BUIModel extends BaseModel {

    private DragObjectManager dragObjectManager;

    public BUIModel() {
    }

    @Override
    public void generateFloorMap(Floor floor, int[] dimensions) {
        setPanelSize(dimensions[0], dimensions[1]);
        if (floorManager == null) {
            floorManager = new FloorManager(floor);
        } else {
            floorManager.changeFloor(floor);
        }
        zoomToFit(true);
    }

    public void setDragObjectManager(DragObjectManager dragObjectManager) {
        this.dragObjectManager = dragObjectManager;
    }

    @Override
    public BufferedImage createBufferedImage() {
        BufferedImage images = applyMatrixToBufferedImage(floorManager.getFloor().getFloorImage());
        Graphics g = images.createGraphics();
        System.out.println("Image:  Width: " + floorManager.getFloor().getFloorImage().getWidth() + " Height: " + floorManager.getFloor().getFloorImage().getHeight());
        System.out.println("Panel:  Width: " + panelWidth + " Height: " + panelHeight);


        if (dragObjectManager.getDragableCornerPoints().size() > 0) {
            drawMapBounding(g);
        }
        return images;
    }


    private void drawMapBounding(Graphics g) {
        Ellipse2D upperLeft = dragObjectManager.getDragableCornerPoints().get(0).getEllipse2D();
        Ellipse2D lowerRight = dragObjectManager.getDragableCornerPoints().get(1).getEllipse2D();
        DrawingContext.drawCornerRectangle(upperLeft, lowerRight, g, m_tMatrix);

        for (DragableAccessPoint dragableAccessPoint : dragObjectManager.getDragableAccessPoints()) {
            DrawingContext.drawAccessPoint((int) dragableAccessPoint.getEllipse2D().getCenterX(), (int) dragableAccessPoint.getEllipse2D().getCenterY(), g, m_tMatrix);
        }

        for (DragableWayPoint dragableWayPoint : dragObjectManager.getDragableWaypoints()) {
            DrawingContext.drawWayPoint((int) dragableWayPoint.getEllipse2D().getCenterX(), (int) dragableWayPoint.getEllipse2D().getCenterY(), g, m_tMatrix);
        }

        if (dragObjectManager.getDragableWaypoints() != null && dragObjectManager.getDragableWaypoints().size() > 1) {
            DrawingContext.drawWayPoints(dragObjectManager.getDragableWaypoints(), g, m_tMatrix);
        }

    }
}
