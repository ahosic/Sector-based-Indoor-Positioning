package at.fhhgb.mc.component.bui.view.input;

import at.fhhgb.mc.component.sim.model.simulator.FloorBounds;

import java.awt.*;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class BoundsPanel extends Panel {


    private Label widthLabel;
    private TextField widthTextField;
    private Label heightLabel;
    private TextField heightTextfield;
    private Button save;

    public BoundsPanel(ActionListener c) {

        widthLabel = new Label("Floor Width(m)");
        heightLabel = new Label("Floor Height(m)");

        widthTextField = new TextField(1);
        heightTextfield = new TextField(1);

        save = new Button("save");


        save.addActionListener(c);

        setLayout(new GridLayout(3, 3));
        add(widthLabel);
        add(widthTextField);
        add(heightLabel);
        add(heightTextfield);
        add(save, "span");
    }

    public void modifyDataElement(FloorBounds floorBounds) {
        floorBounds.setFloorWidth(Double.parseDouble(widthTextField.getText()));
        floorBounds.setFloorHeight(Double.parseDouble(heightTextfield.getText()));
    }

    public void setFloorBoundsData(FloorBounds floorBounds) {
        widthTextField.setText("" + floorBounds.getFloorWidth());
        heightTextfield.setText("" + floorBounds.getFloorHeight());
    }
}
