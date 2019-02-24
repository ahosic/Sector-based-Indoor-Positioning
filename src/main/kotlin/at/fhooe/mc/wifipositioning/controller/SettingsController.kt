package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.configuration.Settings
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel

class SettingsController(val configModel: ConfigurationModel) {
    fun applySettings(settings: Settings) {
        configModel.saveConfiguration(settings)
    }
}