package at.fhooe.mc.wifipositioning.model.metrics

/**
 * A common interface for Metric objects.
 */
interface Metric<Value, Result> {
    /**
     * Adds [value] to the metric.
     */
    fun add(value: Value)

    /**
     * Computes the result of the metric.
     *
     * @return the result of the metric.
     */
    fun compute(): Result
}