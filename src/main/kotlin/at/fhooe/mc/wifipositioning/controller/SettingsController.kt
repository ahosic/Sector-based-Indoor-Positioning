package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.configuration.Configuration
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel

class SettingsController(val configModel: ConfigurationModel) {
    fun applyConfiguration(configuration: Configuration) {
        configModel.saveConfiguration(configuration)
    }
}