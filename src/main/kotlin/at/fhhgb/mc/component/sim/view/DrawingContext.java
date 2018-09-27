package at.fhhgb.mc.component.sim.view;

import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.Person;
import at.fhhgb.mc.component.sim.model.Room;
import at.fhhgb.mc.component.sim.model.initialisations.WaypointPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Vector;


/**
 * This class provides a method for the right drawing of different GeoObject types.
 * 
 * @author Michael Nigl
 *
 */
public class DrawingContext {

	/**
	 * This method draws the GeoObjects in the different Colors.
	 *
	 * @param _room - the GeoObject which is drawn
	 * @param _g    - the Graphic Context where the Object is drawn
	 * @param _m    - the actual TranslationMatrix
	 */
	public static void drawObject(Room _room, Graphics _g, Matrix _m) {
		Vector<Polygon> polygons = _room.getPolygon();
		int i = 0;
		while (i < polygons.size()) {
			Polygon poly = null;
			if (_room.getType() != 500) {
				poly = _m.multiply(polygons.get(i));
			}
			if (_room.getType() == 666) {
				_g.setColor(new Color(255, 255, 255));
				_g.fillPolygon(poly);
				_g.setColor(new Color(0, 0, 0));
				_g.drawPolygon(poly);


			} else if (_room.getType() == 667) {
				_g.setColor(new Color(255, 0, 0));
				_g.drawPolyline(poly.xpoints, poly.ypoints, poly.npoints);
			} else if (_room.getType() == 668) {
				_g.setColor(new Color(0, 0, 255));
				_g.fillPolygon(poly);
				_g.setColor(new Color(0, 0, 0));
				_g.drawPolygon(poly);
			} else if (_room.getType() == 931) {
				_g.setColor(new Color(255, 0, 0));
				_g.fillPolygon(poly);
				_g.setColor(new Color(0, 0, 0));
				_g.drawPolygon(poly);
			} else if (_room.getType() == 932) {
				_g.setColor(new Color(255, 140, 0));
				_g.fillPolygon(poly);
				_g.setColor(new Color(0, 0, 0));
				_g.drawPolygon(poly);

			} else if (_room.getType() == 1101) {
				_g.setColor(new Color(190, 190, 190));
				_g.fillPolygon(poly);

			} else if (_room.getType() == 1) {

				_g.setColor(new Color(115, 178, 115));
				_g.fillPolygon(poly);
				_g.setColor(new Color(76, 115, 0));
				_g.drawPolygon(poly);


			} else if (_room.getType() == 2) {

				_g.setColor(new Color(211, 255, 190));
				_g.fillPolygon(poly);
				_g.setColor(new Color(211, 255, 190));
				_g.drawPolygon(poly);

			} else if (_room.getType() == 3) {

				_g.setColor(new Color(41, 80, 255));
				_g.fillPolygon(poly);
				_g.setColor(new Color(20, 56, 255));
				_g.drawPolygon(poly);

			} else if (_room.getType() == 4) {

				_g.setColor(new Color(255, 190, 190));
				_g.fillPolygon(poly);
				_g.setColor(new Color(255, 127, 127));
				_g.drawPolygon(poly);

			} else if (_room.getType() == 500) {
				//_g.drawImage(((POIObject)_room).getImage(),_room.getPolygon().firstElement().xpoints[0],_room.getPolygon().firstElement().ypoints[0],null);

			} else {

				_g.setColor(new Color(255, 255, 255));
				_g.fillPolygon(poly);
				_g.setColor(new Color(255, 0, 0));
				_g.drawPolygon(poly);

			}

			if (_room.getSelected()) {
				_g.setColor(new Color(100, 100, 100));
				_g.drawPolygon(poly);
			}
			i++;
		}

	}

    public static void drawVoronoi(Polygon polygon, Graphics _g, Matrix _m, boolean containsPerson) {
		Graphics2D g2 = (Graphics2D) _g;
		Polygon poly = _m.multiply(polygon);
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(new Color(0, 0, 0));
		g2.drawPolygon(poly);
		if (containsPerson) {
			g2.setColor(new Color(255, 255, 200, 80));
			g2.fillPolygon(poly);
		}
		g2.setStroke(oldStroke);
	}

    public static void drawAccessPoint(int x, int y, Graphics _g, Matrix _m) {
        Point p = new Point(x, y);
        Point p2 = _m.multiply(p);
        _g.setColor(new Color(0, 0, 255));
        _g.fillOval((int) p2.getX() - 5, (int) p2.getY() - 5, 10, 10);
    }


    public static void drawCornerRectangle(Ellipse2D upperLeft, Ellipse2D lowerRight, Graphics _g, Matrix _m) {
        _g.setColor(new Color(255, 0, 0));

        Point p = _m.multiply(new Point((int) Math.round(upperLeft.getCenterX()), (int) Math.round(upperLeft.getCenterY())));
        Point p2 = _m.multiply(new Point((int) Math.round(lowerRight.getCenterX()), (int) Math.round(lowerRight.getCenterY())));
        _g.drawLine(p.x, p.y, p2.x, p.y);
        _g.drawLine(p2.x, p.y, p2.x, p2.y);
        _g.drawLine(p2.x, p2.y, p.x, p2.y);
        _g.drawLine(p.x, p2.y, p.x, p.y);

        _g.fillOval(p.x - 5, p.y - 5, (int) upperLeft.getWidth(), (int) upperLeft.getHeight());
        _g.fillOval(p2.x - 5, p2.y - 5, (int) lowerRight.getWidth(), (int) lowerRight.getHeight());
    }

	public static void drawPerson(Person person, Graphics _g, Matrix _m) {
		Point p = new Point(person.getX(),person.getY());
		Point p2 = _m.multiply(p);
		_g.setColor(new Color(255, 0, 0));
		_g.fillOval((int)p2.getX()-10,(int)p2.getY()-10,20,20);

	}

	public static void drawActualPosition(Person person, Graphics _g, Matrix _m) {
		Point p = new Point(person.getX(),person.getY());
		Point p2 = _m.multiply(p);
        _g.setColor(new Color(139, 69, 19));
        _g.fillOval((int)p2.getX()-10,(int)p2.getY()-10,20,20);

	}

    public static void drawWayPoint(int x, int y, Graphics _g, Matrix _m) {
        Point p = new Point(x, y);
        Point p2 = _m.multiply(p);
        _g.setColor(new Color(0, 102, 0));
        _g.fillOval((int) p2.getX() - 5, (int) p2.getY() - 5, 10, 10);
    }

    public static void drawWayPointPosition(WaypointPosition waypointPosition, Graphics _g, Matrix _m) {
        Point p = new Point(waypointPosition.getWayPointPosition().getX(),waypointPosition.getWayPointPosition().getY());
        Point p2 = _m.multiply(p);
        _g.setColor(new Color(0, 102, 0));
        _g.fillOval((int)p2.getX()-5,(int)p2.getY()-5,10,10);

    }


    public static void drawGrid(Rectangle rectangle, Graphics _g, Matrix _m, boolean containsPerson) {
        Rectangle rectangle2 = _m.multiply(rectangle);
		Graphics2D g2 = (Graphics2D) _g;
		g2.setColor(new Color(0, 0, 0));
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		g2.drawRect((int) rectangle2.getX(), (int) rectangle2.getY(), (int) rectangle2.getWidth(), (int) rectangle2.getHeight());
		if (containsPerson) {
			g2.setColor(new Color(255, 255, 200, 80));
			g2.fillRect((int) rectangle2.getX(), (int) rectangle2.getY(), (int) rectangle2.getWidth(), (int) rectangle2.getHeight());
		}
		g2.setStroke(oldStroke);
	}

	public static void drawLine(int x1, int y1, int x2, int y2, Graphics _g, Matrix _m) {

		Point p1 = _m.multiply(new Point(x1, y1));
		Point p2 = _m.multiply(new Point(x2, y2));

		_g.setColor(new Color(0, 0, 0));
		_g.drawLine(p1.x, p1.y, p2.x, p2.y);

	}


	public static void drawWayPointLine(ArrayList<WaypointPosition> waypointPositionList, Graphics _g, Matrix _m) {
		int[] xPoints = new int[waypointPositionList.size()];
		int[] yPoints = new int[waypointPositionList.size()];
		int nPoints = waypointPositionList.size();
		int i = 0;
		for(WaypointPosition waypointPosition : waypointPositionList){
			Point p = new Point(waypointPosition.getWayPointPosition().getX(),waypointPosition.getWayPointPosition().getY());
			Point p2 = _m.multiply(p);
			xPoints[i] = (int)p2.getX();
			yPoints[i] = (int)p2.getY();
			i++;
		}

		_g.setColor(new Color(0, 102, 0));
		_g.drawPolyline(xPoints, yPoints, nPoints);
	}

    public static void drawCells(Rectangle rectangle, Graphics g, Matrix _m, boolean containsPerson) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0));
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));

		Rectangle rect2 = _m.multiply(rectangle);
		g2.drawRect((int) rect2.getX(), (int) rect2.getY(), (int) rect2.getWidth(), (int) rect2.getHeight());

        if (containsPerson) {
			g2.setColor(new Color(255, 255, 200, 80));
			g2.fillRect((int) rect2.getX(), (int) rect2.getY(), (int) rect2.getWidth(), (int) rect2.getHeight());
		}
		g2.setStroke(oldStroke);
	}
}
