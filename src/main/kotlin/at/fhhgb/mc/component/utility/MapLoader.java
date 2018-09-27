package at.fhhgb.mc.component.utility;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class MapLoader {
    private static JFileChooser fc;

    public static File openFileChooser(String extension, Component component) {
        fc = new JFileChooser();
        fc.setDialogTitle("Choose a recorded File");

        FileFilter filter = new FileNameExtensionFilter("TextFiles", extension);
        fc.setFileFilter(filter);
        int userSelection = fc.showOpenDialog(component);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fc.getSelectedFile();
            return fileToSave;
        }
        return null;
    }


    public static File choosStorageDirectory(Component component) {
        fc = new JFileChooser();
        fc.setDialogTitle("Choose a storage Directory");
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        int userSelection = fc.showSaveDialog(component);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fc.getSelectedFile();
            return fileToSave;
        }
        return null;
    }
}
