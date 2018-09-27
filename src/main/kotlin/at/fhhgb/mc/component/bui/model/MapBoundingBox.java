package at.fhhgb.mc.component.bui.model;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class MapBoundingBox {

    private DragableCornerPoint upperLeftCorner;
    private DragableCornerPoint lowerRightCorner;

/*
    public MapBoundingBox(Point upperLeft, Point lowerRight){
        upperLeftCorner = new DragableCornerPoint(upperLeft);
        lowerRightCorner = new DragableCornerPoint(lowerRight);
    }


    public void drawMabBoundingBox(Graphics g, Matrix m){
        g.setColor(Color.red);
        upperLeftCorner.drawComponent(g,m);
        lowerRightCorner.drawComponent(g,m);
        g.drawLine(upperLeftCorner.getX(), upperLeftCorner.getY(), lowerRightCorner.getX(),upperLeftCorner.getY());
        g.drawLine(lowerRightCorner.getX(),upperLeftCorner.getY(), lowerRightCorner.getX(),lowerRightCorner.getY());
        g.drawLine(lowerRightCorner.getX(), lowerRightCorner.getY(), upperLeftCorner.getX(),lowerRightCorner.getY());
        g.drawLine(upperLeftCorner.getX(),lowerRightCorner.getY(), upperLeftCorner.getX(),upperLeftCorner.getY());
    }*/

}
