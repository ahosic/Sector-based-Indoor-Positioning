package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.configuration.Settings
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel

/**
 * A controller for the settings/configuration view.
 *
 * @property configModel the configuration model for managing configurations.
 */
class SettingsController(val configModel: ConfigurationModel) {
    fun applySettings(settings: Settings) {
        configModel.saveConfiguration(settings)
    }
}