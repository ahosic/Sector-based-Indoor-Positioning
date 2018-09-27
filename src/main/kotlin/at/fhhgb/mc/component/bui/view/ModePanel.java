package at.fhhgb.mc.component.bui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class ModePanel extends Panel {

    private Button wayPointMode, accessPointMode, boundingMode;


    public ModePanel(ActionListener c) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        boundingMode = new Button("Map Bounds Mode");
        wayPointMode = new Button("Way Point Mode");
        accessPointMode = new Button("Access Point Mode");

        boundingMode.setName("boundingMode");
        wayPointMode.setName("wayPointMode");
        accessPointMode.setName("accessPointMode");

        add(boundingMode);
        add(accessPointMode);
        add(wayPointMode);

        boundingMode.addActionListener(c);
        wayPointMode.addActionListener(c);
        accessPointMode.addActionListener(c);
    }

}
