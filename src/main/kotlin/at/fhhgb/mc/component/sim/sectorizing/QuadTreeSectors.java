package at.fhhgb.mc.component.sim.sectorizing;

import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.domain.PixelPosition;
import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;
import at.fhhgb.mc.component.sim.sectorizing.quadtree.AbstractNode;
import at.fhhgb.mc.component.sim.sectorizing.quadtree.AbstractNodeElement;
import at.fhhgb.mc.component.sim.sectorizing.quadtree.point.PointNode;
import at.fhhgb.mc.component.sim.sectorizing.quadtree.point.PointQuadTree;
import at.fhhgb.mc.component.sim.view.DrawingContext;

import java.awt.*;
import java.util.Map;
import java.util.Vector;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class QuadTreeSectors implements ISectorizing {


    protected PointQuadTree<String> tree;

    protected Vector<AbstractNodeElement<String>> selectedElements = new Vector<AbstractNodeElement<String>>();

    private Position position;

    private boolean matchingSector = false;


    @Override
    public void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix) {
        PointQuadTree<String> tree = new PointQuadTree<String>(new Point(floorManager.getOffsetLeftInPixel(), floorManager.getOffsetTopInPixel()),
                new Dimension(floorManager.getOffsetRightInPixel() - floorManager.getOffsetLeftInPixel(), floorManager.getOffsetBottomInPixel() - floorManager.getOffsetTopInPixel()));
        for (AccessPoint accessPoint : floorManager.getFloor().getAccessPointList()) {
            PixelPosition pixelPosition = floorManager.calculatePixelPositionFromMeter(accessPoint.getPosition().getX(), accessPoint.getPosition().getY());
            tree.insert(pixelPosition.getXPosition(), pixelPosition.getYPosition(), accessPoint.getBSSID());
        }
        PointNode<String> rootNode = tree.getRootNode();
        drawQuadTree(rootNode, g, m_tMatrix);
        matchingSector = false;
    }


    private void drawQuadTree(PointNode<String> node, Graphics g, Matrix matrix) {
        Dimension bounds = node.getBounds();
        Point startCoordinates = node.getStartCoordinates();

        // Draw subnodes
        Map<AbstractNode.Cell, PointNode<String>> subNodes = node.getSubNodes();
        for (PointNode<String> subNode : subNodes.values()) {
            drawQuadTree(subNode, g, matrix);
        }
        // Draw node bounds
        Rectangle rect = new Rectangle(startCoordinates.x, startCoordinates.y, bounds.width, bounds.height);
        if (position != null && !matchingSector && rect.contains(new Point(position.getX(), position.getY()))) {
            DrawingContext.drawCells(rect, g, matrix, true);
            matchingSector = true;
        } else {
            DrawingContext.drawCells(rect, g, matrix, false);
        }
    }

    @Override
    public void addCurrentPosition(Position position) {
        this.position = position;
    }

}
