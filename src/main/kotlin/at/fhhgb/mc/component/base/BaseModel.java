package at.fhhgb.mc.component.base;

import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.GeoDoublePoint;
import at.fhhgb.mc.component.sim.model.simulator.Floor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Observable;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public abstract class BaseModel extends Observable {

    protected Matrix m_tMatrix;
    protected FloorManager floorManager;
    protected int panelWidth = 300;
    protected int panelHeight = 300;
    private Rectangle m_zoomRect;


    public BaseModel() {
        m_tMatrix = new Matrix();
    }

    /**
     * Sets the panel size in case the window is resized.
     *
     * @param width  - the new width of the panel
     * @param height - the new height of the panel
     */
    public void setPanelSize(int width, int height) {
        panelWidth = width - 1;
        panelHeight = height - 1;
    }

    public abstract void generateFloorMap(Floor floor, int[] dimensions);

    /**
     * Gives a translation matrix which displays all objects inside a given rectangle.
     *
     * @param _mapBounds - the rectangle which should be entirely drawn on the screen
     */
    public void zoomRect(Rectangle _mapBounds) {

        Rectangle win = new Rectangle(0, 0, panelWidth, panelHeight);//
        Rectangle world = m_tMatrix.invers().multiply(_mapBounds);
        m_tMatrix = Matrix.zoomToFit(world, win);

        callObserver(createBufferedImage());
    }

    /**
     * Zooms the entire world or the zooming rectangle to the screen size.
     *
     * @param wholeWorld - determines if the world or only a zooming rectangle are scaled
     */
    public void zoomToFit(boolean wholeWorld) {
        if (floorManager != null && floorManager.getFloor().getFloorImage() != null) {
            Rectangle world = null;

            if (wholeWorld) {
                world = new Rectangle(floorManager.getFloor().getFloorImage().getRaster().getBounds());
                //world = new java.awt.Rectangle(getMapBounds(m_rooms));
            } else {
                world = new Rectangle(m_zoomRect);
            }

            Rectangle window = new Rectangle(0, 0, panelWidth, panelHeight);

            m_tMatrix = new Matrix();

            m_tMatrix = Matrix.zoomToFit(world, window).multiply(m_tMatrix);

            int i = 0;

            callObserver(createBufferedImage());
        }
    }

    /**
     * Rotates the screen for a given angle.
     *
     * @param angle - the angle in degrees
     */
    public void rotate(int angle) {

        Rectangle world = new Rectangle(floorManager.getFloor().getFloorImage().getWidth(), floorManager.getFloor().getFloorImage().getHeight());
        Point middleWorld = new Point((int) world.getCenterX(), (int) world.getCenterY());

        middleWorld.x *= -1;
        middleWorld.y *= -1;

        Matrix m1 = Matrix.translate(middleWorld);
        Matrix m2 = Matrix.rotate(angle);
        middleWorld.x *= -1;
        middleWorld.y *= -1;
        Matrix m3 = Matrix.translate(middleWorld);


        m_tMatrix = m1.multiply(m3.multiply(m2.multiply(m_tMatrix)));

        callObserver(createBufferedImage());
    }


    protected BufferedImage applyMatrixToBufferedImage(BufferedImage originalImage) {
        ColorModel colorModel = ColorModel.getRGBdefault();
        WritableRaster raster = colorModel.createCompatibleWritableRaster(panelWidth, panelHeight);
        Raster originalRaster = originalImage.getRaster();
        for (int i = 0; i < originalRaster.getWidth(); i++) {
            for (int j = 0; j < originalRaster.getHeight(); j++) {
                int[] data = originalRaster.getPixel(i, j, (int[]) null);
                Point p = m_tMatrix.multiply(new Point(i, j));
                if (p.getX() < raster.getWidth() && p.getY() < raster.getHeight() && p.getX() >= 0 && p.getY() >= 0) {
                    raster.setPixel((int) p.getX(), (int) p.getY(), data);
                }
            }
        }
        //int[] pixels = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), (int[]) null);

        BufferedImage image = new BufferedImage(colorModel, raster, true, null);
        return image;
    }

    /**
     * Scrolls the screen horizontally for a given value.
     *
     * @param _delta - determines the horizontal scrolled distance
     */
    public void scrollHorizontal(int _delta) {
        m_tMatrix = Matrix.translate(_delta, 0).multiply(m_tMatrix);

        callObserver(createBufferedImage());
    }

    /**
     * Scrolls the screen vertically for a given value.
     *
     * @param _delta - determines the vertical scrolled distance
     */
    public void scrollVertical(int _delta) {
        m_tMatrix = Matrix.translate(0, _delta).multiply(m_tMatrix);
        int i = 0;

        callObserver(createBufferedImage());
    }

    /**
     * Zooms the screen for a given factor
     *
     * @param _factor - determines the value of the zoom
     */
    public void zoom(double _factor) {
        m_tMatrix = Matrix.zoomPoint(m_tMatrix, new Point((int) (panelWidth / 2), (int) (panelHeight / 2)), _factor);

        callObserver(createBufferedImage());
    }

    /**
     * Zooms the screen at a specific point for a given factor
     *
     * @param _pt     - determines the position of the zoom
     * @param _factor - determines the value of the zoom
     */
    public void zoom(Point _pt, double _factor) {
        m_tMatrix = Matrix.zoomPoint(m_tMatrix, _pt, _factor).multiply(m_tMatrix);

        callObserver(createBufferedImage());
    }

    /**
     * Calls the observer if the buffered image changed.
     *
     * @param _data - the current buffered image
     */
    public void callObserver(BufferedImage _data) {
        setChanged();

        //notifies the Observer of the changed objects
        notifyObservers(_data);
    }

    /**
     * Calls the observer if the a zoom rectangle is drawn.
     *
     * @param _rect - the current zoom rectangle.
     */
    public void callObserver(Rectangle _rect) {
        setChanged();
        //notifies the Observer of the changed objects
        notifyObservers(_rect);
    }

    public abstract BufferedImage createBufferedImage();

    /**
     * Manually Scales the screen to a specific ratio.
     *
     * @param _manualScale - the value to which the screen is zoomed
     */
    public void manualMapScale(int _manualScale) {
        int scale = calculateScale();
        double ratio = (double) scale / (double) _manualScale;
        zoom(ratio);
    }

    public FloorManager getFloorManager() {
        return floorManager;
    }

    /**
     * Calculates the visible scale of the screen-
     *
     * @return - the actual scale
     */
    public int calculateScale() {
        GeoDoublePoint vector = new GeoDoublePoint(0, 1.0);
        GeoDoublePoint vector_transformed = m_tMatrix.multiply(vector);
        double dpcm = Toolkit.getDefaultToolkit().getScreenResolution() / 2.54;
        double scale = 1 / vector_transformed.length() * dpcm;
        return (int) scale;
    }

    public Matrix getM_tMatrix() {
        return m_tMatrix;
    }
}
