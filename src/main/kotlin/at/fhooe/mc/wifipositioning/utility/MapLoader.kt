package at.fhooe.mc.wifipositioning.utility

import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import java.awt.*
import java.io.File


object MapLoader {
    private var fc: JFileChooser? = null

    fun openFileChooser(extension: String, component: Component): File? {
        fc = JFileChooser()
        fc!!.dialogTitle = "Choose a recorded File"

        val filter = FileNameExtensionFilter("TextFiles", extension)
        fc!!.fileFilter = filter
        val userSelection = fc!!.showOpenDialog(component)

        return if (userSelection == JFileChooser.APPROVE_OPTION) {
            fc!!.selectedFile
        } else null
    }
}
