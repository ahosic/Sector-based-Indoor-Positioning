package at.fhooe.mc.wifipositioning.ui

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import java.awt.image.BufferedImage

class MapView : BorderPane() {
    private val imageView = ImageView()
    var mapImage: BufferedImage? = null
        set(value) {
            field = value
            imageView.image = SwingFXUtils.toFXImage(value, null)
        }

    init {
        imageView.fitHeightProperty().bind(widthProperty())
        imageView.fitHeightProperty().bind(heightProperty())
        imageView.isPreserveRatio = true

        center = imageView
    }
}