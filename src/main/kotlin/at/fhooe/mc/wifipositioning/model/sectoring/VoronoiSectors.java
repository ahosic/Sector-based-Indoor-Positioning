package at.fhooe.mc.wifipositioning.model.sectoring;

import at.fhooe.mc.wifipositioning.model.graphics.FloorManager;
import at.fhooe.mc.wifipositioning.model.graphics.Matrix;
import at.fhooe.mc.wifipositioning.model.simulation.Position;
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor;
import at.fhooe.mc.wifipositioning.model.sectoring.voronoi.GrahamScan;
import at.fhooe.mc.wifipositioning.model.sectoring.voronoi.GraphEdge;
import at.fhooe.mc.wifipositioning.model.sectoring.voronoi.Voronoi;
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class VoronoiSectors implements ISectoring {

    static double p = 3;
    static BufferedImage I;
    Position position;

    public VoronoiSectors() {
        Random rand = new Random();
    }

    @Override
    public void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix) {
        Floor floor = floorManager.getFloor();
        double[] xvalues = new double[floor.getAccessPointList().size()];
        double[] yvalues = new double[xvalues.length];

        for (int i = 0; i < floorManager.getFloor().getAccessPointList().size(); i++) {
            Position pixelPosition = floorManager.calculatePixelPositionFromMeter(floor.getAccessPoint(i).getPosition().getX(), floor.getAccessPoint(i).getPosition().getY());
            xvalues[i] = pixelPosition.getX();
            yvalues[i] = pixelPosition.getY();
        }

        Voronoi voronoi = new Voronoi(1);
        //List<GraphEdge> graphEdgeList = voronoi.generateVoronoi(xvalues, yvalues, -600, floorManager.getFloor().getFloorImage().getWidth() + 600, -600, floorManager.getFloor().getFloorImage().getHeight() + 600);
        List<GraphEdge> graphEdgeList = voronoi.generateVoronoi(xvalues, yvalues, -600, floorManager.getFloor().getFloorImage().getWidth() + 600, -600, floorManager.getFloor().getFloorImage().getHeight() + 600);




        Map<Integer, List<GraphEdge>> edgesByPolygon = new HashMap<>();
        for (GraphEdge edge : graphEdgeList) {
            List<GraphEdge> list = edgesByPolygon.get(edge.site1);
            if (list == null) {
                list = new ArrayList<>();
                edgesByPolygon.put(edge.site1, list);
            }
            list.add(edge);

            list = edgesByPolygon.get(edge.site2);
            if (list == null) {
                list = new ArrayList<>();
                edgesByPolygon.put(edge.site2, list);
            }
            list.add(edge);
        }

        List<Polygon> polygons = new ArrayList<Polygon>();
        int i = 0;
        for (List<GraphEdge> list : edgesByPolygon.values()) {
            System.out.println("Polygon " + i);
            i++;

            if (list.size() < 3) {
                for (GraphEdge graphEdge : list) {
                    DrawingContext.INSTANCE.drawLine((int) graphEdge.x1, (int) graphEdge.y1, (int) graphEdge.x2, (int) graphEdge.y2, g, m_tMatrix);
                }
                continue;
            }

            Polygon polygon = new Polygon();
            polygons.add(polygon);
            int[] xValuesVoronoi = new int[list.size() * 2];
            int[] yValuesVoronoi = new int[list.size() * 2];

            int j = 0;
            for (GraphEdge mGraphEdge : list) {
                xValuesVoronoi[j] = (int) Math.round(mGraphEdge.x1);
                yValuesVoronoi[j] = (int) Math.round(mGraphEdge.y1);
                j++;
                xValuesVoronoi[j] = (int) Math.round(mGraphEdge.x2);
                yValuesVoronoi[j] = (int) Math.round(mGraphEdge.y2);
                j++;

                System.out.println("Site 1" + mGraphEdge.site1 + " Site 2" + mGraphEdge.site2);

            }

            List<Point> convexHull = GrahamScan.getConvexHull(xValuesVoronoi, yValuesVoronoi);

            for (Point p : convexHull) {
                polygon.addPoint((int) p.getX(), (int) p.getY());
            }
        }
        for (Polygon polygon : polygons) {
            if (position != null && polygon.contains(new Point(position.getX(), position.getY()))) {
                DrawingContext.INSTANCE.drawVoronoi(polygon, g, m_tMatrix, true);
            } else {
                DrawingContext.INSTANCE.drawVoronoi(polygon, g, m_tMatrix, false);
            }
        }
        Rectangle bounds = new Rectangle(floorManager.getOffsetLeftInPixel(), floorManager.getOffsetTopInPixel(), floorManager.getOffsetRightInPixel() - floorManager.getOffsetLeftInPixel(), floorManager.getOffsetBottomInPixel() - floorManager.getOffsetTopInPixel());

        DrawingContext.INSTANCE.drawCells(bounds, g, m_tMatrix, false);

    }

    @Override
    public void addCurrentPosition(Position position) {
        this.position = position;
    }
    /*
    @Override
    public void createSectors(FloorConverter floorConverter, Graphics g, Matrix matrix) {
        int n = 0;

        I = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        int numberOfAccessPoints = floorConverter.getFloor().getAccessPointList().size();
        px = new int[numberOfAccessPoints];
        py = new int[numberOfAccessPoints];
        for (int i = 0; i < numberOfAccessPoints; i++) {
            px[i] = floorConverter.getFloor().getAccessPoint(i).getPosition().getX();
            py[i] = floorConverter.getFloor().getAccessPoint(i).getPosition().getY();

        }
        for (int x = 0; x < floorConverter.getFloor().getFloorWidth(); x++) {
            for (int y = 0; y < floorConverter.getFloor().getFloorHeight(); y++) {
                n = 0;
                for (byte i = 0; i < floorConverter.getFloor().getAccessPointList().size(); i++) {
                    if (distance(px[i], x, py[i], y) < distance(px[n], x, py[n], y)) {
                        n = i;
                    }
                }

                I.setRGB(x, y, rand.nextInt(16777215));

            }
        }

        Graphics2D g = I.createGraphics();
        g.setColor(Color.BLACK);
        for (int i = 0; i < cells; i++) {
            g.fill(new Ellipse2D.Double(px[i] - 2.5, py[i] - 2.5, 5, 5));
        }
    }*/

/*
    private double distance(int x1, int x2, int y1, int y2) {
        double d;
        d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); // Euclidian
        //  d = Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
        //  d = Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), (1 / p)); // Minkovski
        return d;
    }
*/
}
