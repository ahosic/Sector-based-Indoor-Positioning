package at.fhooe.mc.wifipositioning.model.positioning;

import java.util.ArrayList;
import java.util.List;


/**
 * An abstract implementation of a Sliding Window.
 *
 * @param <T> the type of object that should be put into the sliding window.
 */
public abstract class BaseSlidingWindow<T> {

    protected List<List<T>> arrayLists = new ArrayList<List<T>>();
    protected int windowSize;

    public BaseSlidingWindow(int windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Adds a list of elements to the sliding window.
     *
     * @param elements a list of elements
     */
    public void addElement(List<T> elements) {
        if (arrayLists.size() > windowSize) {
            arrayLists.remove(0);
        }

        arrayLists.add(elements);
    }
}
