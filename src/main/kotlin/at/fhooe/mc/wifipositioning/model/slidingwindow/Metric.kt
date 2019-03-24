package at.fhooe.mc.wifipositioning.model.slidingwindow

interface Metric {
    fun add(value: Double)
    fun compute(): Double
}