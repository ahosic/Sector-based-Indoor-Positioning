package at.fhhgb.mc.component.sim.model;

/**
 * A point representation which calculates its position with polar coordinates.
 *
 * @author Michael Nigl
 */
public class GeoDoublePoint {

    private double m_x = 0.0;
    private double m_y = 0.0;


    /**
     * The constructor which initializes the coordinates of the point.
     *
     * @param _x - x coordinate
     * @param _y - y coordinate
     */
    public GeoDoublePoint(double _x, double _y) {
        m_x = _x;
        m_y = _y;
    }

    /**
     * Gives the x coordinate of the point.
     *
     * @return - value if the x coordinate.
     */
    public double getX() {
        return m_x;
    }

    /**
     * Gives the y coordinate of the point.
     *
     * @return - value if the y coordinate.
     */
    public double getY() {
        return m_y;
    }

    /**
     * Calculates the distance from the center of the coordinate system to the point.
     * (Polar coordinate)
     *
     * @return - the distance of the point
     */
    public double length() {
        return Math.sqrt(Math.pow(m_x, 2) + Math.pow(m_y, 2));
    }

    /**
     * Prints a string representation of this point with its coordinates.
     *
     * @return the string of this point
     */
    public String toString() {
        return "Point Coordinates: x: " + m_x + " y: " + m_y;
    }

}
