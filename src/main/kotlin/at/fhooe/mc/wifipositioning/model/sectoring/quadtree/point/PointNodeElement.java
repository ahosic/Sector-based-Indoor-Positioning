package at.fhooe.mc.wifipositioning.model.sectoring.quadtree.point;

import at.fhooe.mc.wifipositioning.model.sectoring.quadtree.AbstractNodeElement;

import java.awt.*;

@SuppressWarnings("serial")
public class PointNodeElement<T> extends AbstractNodeElement<T> {

    public PointNodeElement(Point coordinates, T element) {
        super(coordinates, element);
    }

}
