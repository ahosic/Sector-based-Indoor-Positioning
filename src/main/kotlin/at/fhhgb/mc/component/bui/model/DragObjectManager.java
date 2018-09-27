package at.fhhgb.mc.component.bui.model;

import at.fhhgb.mc.component.sim.domain.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class DragObjectManager {
    private final int ellipseWidth = 10;
    private final int ellipseHeight = 10;
    private List<IDragableEllipse> ellipseList;
    private List<DragableCornerPoint> dragableCornerPoints;
    private List<DragableAccessPoint> dragableAccessPoints;
    private List<DragableWayPoint> dragableWaypoints;

    private IDragableEllipse currentlyDragedEllipse;
    private boolean currentlyDragging = false;
    private double startX;
    private double startY;

    public DragObjectManager() {
        ellipseList = new ArrayList<IDragableEllipse>();
        dragableCornerPoints = new ArrayList<DragableCornerPoint>();
        dragableAccessPoints = new ArrayList<DragableAccessPoint>();
        dragableWaypoints = new ArrayList<DragableWayPoint>();
    }

    public void addDragableCornerPoint(int x, int y) {
        DragableCornerPoint cornerPoint = new DragableCornerPoint(x, y, ellipseWidth, ellipseHeight);
        ellipseList.add(cornerPoint);
        dragableCornerPoints.add(cornerPoint);
        currentlyDragedEllipse = cornerPoint;
    }

    public void addDragableAccessPoint(int x, int y, int floor) {
        DragableAccessPoint accessPoint = new DragableAccessPoint(x, y, ellipseWidth, ellipseHeight, floor);
        ellipseList.add(accessPoint);
        dragableAccessPoints.add(accessPoint);
        currentlyDragedEllipse = accessPoint;
    }

    public void addDragableWayPoint(int x, int y, int floor) {
        DragableWayPoint waypoint = new DragableWayPoint(x, y, ellipseWidth, ellipseHeight, dragableWaypoints.size() + 1, "", floor);
        ellipseList.add(waypoint);
        dragableWaypoints.add(waypoint);
        currentlyDragedEllipse = waypoint;
    }

    public boolean checkSelectedEllipse(int x, int y, Matrix matrix) {
        for (IDragableEllipse ellipse : ellipseList) {
            Rectangle rect = matrix.multiply(ellipse.getEllipse2D().getBounds());
            rect.setBounds(rect.x, rect.y, ellipseWidth, ellipseHeight);
            System.out.println(rect + "  " + x + "  " + y);
            if (rect.contains(x, y)) {
                currentlyDragging = true;
                currentlyDragedEllipse = ellipse;
                startX = ellipse.getEllipse2D().getX();
                startY = ellipse.getEllipse2D().getY();
                return true;
            }
        }
        return false;
    }

    public void changeDraggedEllipsePosition(int deltaX, int deltaY, Matrix matrix) {
        if (currentlyDragging) {
            //Point p = matrix.multiply(new Point((int)Math.round(deltaX), (int)Math.round(deltaY)));
            Rectangle rect = matrix.multiply(currentlyDragedEllipse.getEllipse2D().getBounds());

            currentlyDragedEllipse.getEllipse2D().setFrame(deltaX - ellipseWidth / 2, deltaY - ellipseHeight / 2, ellipseWidth, ellipseHeight);
            currentlyDragedEllipse.setPositionToEllipse();
        }
    }

    public List<DragableCornerPoint> getDragableCornerPoints() {
        return dragableCornerPoints;
    }

    public List<DragableAccessPoint> getDragableAccessPoints() {
        return dragableAccessPoints;
    }

    public List<DragableWayPoint> getDragableWaypoints() {
        return dragableWaypoints;
    }

    public IDragableEllipse getDraggedEllipse() {
        return currentlyDragedEllipse;
    }

    public boolean isAnObjectDragged() {
        return currentlyDragging;
    }

    public void dragStopped() {
        currentlyDragging = false;
    }

}
