package at.fhhgb.mc.component.sim.positioning;

import java.util.ArrayList;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public abstract class BaseSlidingWindow<T> {

    protected List<List<T>> arrayLists = new ArrayList<List<T>>();
    protected int windowSize;

    public BaseSlidingWindow(int windowSize) {
        this.windowSize = windowSize;
    }

    public void addElement(List<T> elements) {
        if (arrayLists.size() > windowSize) {
            arrayLists.remove(0);
        }

        arrayLists.add(elements);
    }
}
