package at.fhooe.mc.wifipositioning.model.slidingwindow

interface Metric<Value, Result> {
    fun add(value: Value)
    fun compute(): Result
}