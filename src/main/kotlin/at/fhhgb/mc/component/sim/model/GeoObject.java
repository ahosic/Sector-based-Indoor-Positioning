package at.fhhgb.mc.component.sim.model;

import at.fhhgb.mc.component.sim.domain.Matrix;

import java.awt.*;
import java.util.Vector;

/**
 * This class is a GeoObject representation. The Geomtrie, the type and the name
 * are stored here.
 *
 * @author Michael Nigl
 */
public class GeoObject {

    Vector<Polygon> m_polygons = new Vector<Polygon>();
    private String m_id;
    private int m_type;
    private boolean selected = false;

    /**
     * Constructor which initializes this GeoObject with one polygon.
     *
     * @param _id   - id of the GeoObject
     * @param _type - type of the GeoObject
     * @param _poly - the Polygon representation of the GeoObject
     */
    public GeoObject(String _id, int _type, Polygon _poly) {
        m_id = _id;
        m_type = _type;
        m_polygons.add(new Polygon(_poly.xpoints, _poly.ypoints, _poly.npoints));
    }

    /**
     * Constructor which initializes this GeoObject with a vector of polygons.
     *
     * @param _id       - id of the GeoObject
     * @param _type     - type of the GeoObject
     * @param _polygons - vector of for the Polygon representation of the GeoObject
     */
    public GeoObject(String _id, int _type, Vector<Polygon> _polygons) {
        m_id = _id;
        m_type = _type;
        int i = 0;
        while (i < _polygons.size()) {
            m_polygons.add(_polygons.get(i));
            i++;
        }

    }

    /**
     * Copy constructor for the GeoObject
     *
     * @param _geo the GeoObject from where the values are copied.
     */
    public GeoObject(GeoObject _geo) {
        m_id = _geo.m_id;
        m_type = _geo.m_type;
        int i = 0;
        while (i < _geo.m_polygons.size()) {
            m_polygons.add(_geo.m_polygons.get(i));
            i++;
        }
    }

    /**
     * Gets if this GeoObject is selected.
     *
     * @return true if it is selected; false otherwise
     */
    public boolean getSelected() {
        return selected;
    }

    /**
     * Sets if this GeoObject is selected or not.
     *
     * @param _bool - the selection status
     */
    public void setSelected(boolean _bool) {
        selected = _bool;
    }

    /**
     * Gets the id of this GeoObject.
     *
     * @return - the id
     */
    public String getId() {
        return m_id;
    }

    /**
     * Gets the type of this GeoObject.
     *
     * @return - the type
     */
    public int getType() {
        return m_type;
    }

    /**
     * Gets the polygon(s) of this GeoObject.
     *
     * @return - the polygon(s)
     */
    public Vector<Polygon> getPolygon() {
        return m_polygons;
    }

    /**
     * Calculates the bounding rectangle of this GeoObject and returns it.
     *
     * @return - the bounding box
     */
    public Rectangle getBounds() {
        if (m_polygons != null) {
            if (m_polygons.size() > 0) {
                Rectangle rect = new Rectangle(m_polygons.get(0).getBounds());
                int i = 1;
                while (i < m_polygons.size()) {
                    rect = rect.union(m_polygons.get(i).getBounds());
                    i++;
                }
                return rect;
            }
        }
        return null;

    }

    /**
     * Paints a graphic representation of this GeoObject.
     *
     * @param _g      - the graphics context
     * @param _matrix - the transformation matrix for the position and size values.
     */
    public void paint(Graphics _g, Matrix _matrix) {
        int i = 0;
        while (i < m_polygons.size()) {
            Polygon poly = _matrix.multiply(m_polygons.get(i));
            if (selected == true) {
                _g.setColor(new Color(255, 0, 0));
            } else {
                _g.setColor(new Color(0, 0, 255));
            }
            _g.drawPolygon(poly);
            i++;
        }

    }

    /**
     * Prints a String representation of this GeoObject.
     *
     * @return the String of this GeoObject.
     */
    public String toString() {
        String output = "";
        int i = 0;
        while (i < m_polygons.size()) {

            output = output + "ID: " + m_id + "\n" + "Type: " + m_type + "\n" + m_polygons.get(i).toString() + "\n";
            i++;
        }
        return output;
    }

}