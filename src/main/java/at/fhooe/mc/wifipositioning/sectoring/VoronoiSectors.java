package at.fhooe.mc.wifipositioning.sectoring;

import at.fhooe.mc.wifipositioning.model.building.Floor;
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager;
import at.fhooe.mc.wifipositioning.model.graphics.Matrix;
import at.fhooe.mc.wifipositioning.model.building.Position;
import at.fhooe.mc.wifipositioning.sectoring.voronoi.GrahamScan;
import at.fhooe.mc.wifipositioning.sectoring.voronoi.GraphEdge;
import at.fhooe.mc.wifipositioning.sectoring.voronoi.Voronoi;
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VoronoiSectors implements Sectoring {

    private List<Position> positions = new ArrayList<>();
    private List<Position> inTransition = new ArrayList<>();

    public VoronoiSectors() {}

    @Override
    public void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix) {
        Floor floor = floorManager.getFloor();
        double[] xvalues = new double[floor.getAccessPoints().size()];
        double[] yvalues = new double[xvalues.length];

        for (int i = 0; i < floorManager.getFloor().getAccessPoints().size(); i++) {
            Position pixelPosition = floorManager.calculatePixelPositionFromMeter(floor.getAccessPoints().get(i).getPosition().getX(), floor.getAccessPoints().get(i).getPosition().getY());
            xvalues[i] = pixelPosition.getX();
            yvalues[i] = pixelPosition.getY();
        }

        Voronoi voronoi = new Voronoi(1);
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
            }

            List<Point> convexHull = GrahamScan.getConvexHull(xValuesVoronoi, yValuesVoronoi);

            for (Point p : convexHull) {
                polygon.addPoint((int) p.getX(), (int) p.getY());
            }
        }
        for (Polygon polygon : polygons) {
            DrawingContext.INSTANCE.drawVoronoi(polygon, g, m_tMatrix, checkIfContainsPerson(polygon), checkIfInTransition(polygon), positions.size());
        }

        Rectangle bounds = new Rectangle(floorManager.getOffsetLeftInPixel(), floorManager.getOffsetTopInPixel(), floorManager.getOffsetRightInPixel() - floorManager.getOffsetLeftInPixel(), floorManager.getOffsetBottomInPixel() - floorManager.getOffsetTopInPixel());

        DrawingContext.INSTANCE.drawCells(bounds, g, m_tMatrix, false);

    }

    private boolean checkIfContainsPerson(Polygon polygon) {
        if (positions == null) return false;

        return positions.stream().anyMatch(pos -> polygon.contains(new Point(pos.getX(), pos.getY())));
    }

    private boolean checkIfInTransition(Polygon polygon) {
        if(inTransition == null) return false;

        return inTransition.stream().anyMatch(pos -> polygon.contains(new Point(pos.getX(), pos.getY())));
    }

    @Override
    public void addPositionsOfEstimatedSectors(List<Position> positions, List<Position> inTransition) {
        this.positions = positions;
        this.inTransition = inTransition;
    }
}
