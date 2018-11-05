package at.fhhgb.mc.component.sim.view

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import java.awt.image.BufferedImage

class MapView : AnchorPane() {
    private val imageView = ImageView()
    var mapImage: BufferedImage? = null
        set(value) {
            field = value
            imageView.image = SwingFXUtils.toFXImage(value, null)
        }

    init {
        AnchorPane.setTopAnchor(imageView, 0.0)
        AnchorPane.setBottomAnchor(imageView, 0.0)
        AnchorPane.setLeftAnchor(imageView, 0.0)
        AnchorPane.setRightAnchor(imageView, 0.0)

        children.add(imageView)
    }
}