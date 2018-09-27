package at.fhhgb.mc.component.sim.sectorizing.quadtree.point;

import at.fhhgb.mc.component.sim.sectorizing.quadtree.AbstractNodeElement;

import java.awt.*;

@SuppressWarnings("serial")
public class PointNodeElement<T> extends AbstractNodeElement<T> {

    public PointNodeElement(Point coordinates, T element) {
        super(coordinates, element);
    }

}
