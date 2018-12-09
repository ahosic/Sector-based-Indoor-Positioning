package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.model.positioning.PositioningType

data class Configuration(val floorsPath: String, val walkRecordingPath: String, val positioningType: PositioningType)