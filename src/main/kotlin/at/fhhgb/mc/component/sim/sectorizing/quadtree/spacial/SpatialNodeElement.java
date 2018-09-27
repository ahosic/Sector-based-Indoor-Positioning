package at.fhhgb.mc.component.sim.sectorizing.quadtree.spacial;

import at.fhhgb.mc.component.sim.sectorizing.quadtree.AbstractNodeElement;

import java.awt.*;


@SuppressWarnings("serial")
public class SpatialNodeElement<T> extends AbstractNodeElement<T> {

    protected Dimension elementSize;

    public SpatialNodeElement(T element, Dimension elementSize) {
        super(element);
        this.elementSize = elementSize;
    }

    public SpatialNodeElement(Point coordinates, Dimension elementSize, T element) {
        super(coordinates, element);
        this.elementSize = elementSize;
    }

    public int getWidth() {
        return this.elementSize.width;
    }

    public int getHeight() {
        return this.elementSize.height;
    }

}
