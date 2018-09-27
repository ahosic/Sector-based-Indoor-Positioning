package at.fhhgb.mc.component.sim.positioning;

/**
 * An interface for filtering data.
 *
 * @param <T> type of data that should be filtered
 */
public interface IFilterable<T> {
    /**
     * Filters input data by smoothing the output.
     *
     * @param input data that should be filtered
     * @return the smoothed data
     */
    T filter(T input);
}
