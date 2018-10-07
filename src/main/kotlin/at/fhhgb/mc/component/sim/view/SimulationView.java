package at.fhhgb.mc.component.sim.view;

import at.fhhgb.mc.component.base.BaseModel;
import at.fhhgb.mc.component.sim.controller.SimulationController;
import at.fhhgb.mc.component.utility.MapLoader;
import at.fhhgb.mc.interfaces.ViewControllerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;


/**
 * The view of the MVC. The buttons and the Drawpanel are displayed here.
 */
public class SimulationView extends Panel implements Observer, ViewControllerInterface {

    JSlider slider;
    SimulationController simulationController;
    DrawingPanel drawingPanel;
    private Button draw, ztf, plus, minus, north, south, east, west, rotright, rotleft, store, loadFile, play;
    private TextField tf1;
    private Label l1;
    private Choice floorsChoice, sectorizationChoice, positioningChoice;
    //private Checkbox addNewElements;


    /**
     * The Constructor which initializes the GUI and its Listeners.
     */
    public SimulationView(SimulationController simulationController) {
        this.simulationController = simulationController;
        drawingPanel = new DrawingPanel();
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        Panel panel4 = new Panel();
        Panel zoom = new Panel();
        Panel move = new Panel();
        tf1 = new TextField("unknown", 5);
        drawingPanel.addMouseListener(simulationController);
        drawingPanel.addComponentListener(simulationController);
        drawingPanel.addMouseMotionListener(simulationController);
        l1 = new Label("MapScale  =  1    :");
        slider = new JSlider(JSlider.HORIZONTAL, 100, 1000, 500);

        floorsChoice = new Choice();
        floorsChoice.setName("floors");
        floorsChoice.add("None");
        floorsChoice.addItemListener(simulationController);

        sectorizationChoice = new Choice();
        sectorizationChoice.setName("xGrid");
        sectorizationChoice.add("None");
        sectorizationChoice.add("Grid");
        sectorizationChoice.add("Voronoi");
        sectorizationChoice.add("QuadTree");
        positioningChoice = new Choice();
        positioningChoice.setName("yGrid");
        positioningChoice.add("Filtered Algorithm");
        positioningChoice.add("Strongest AP");
        positioningChoice.add("Sliding Window");

        sectorizationChoice.addItemListener(simulationController);
        positioningChoice.addItemListener(simulationController);


        // layout for Button + drawpanel
        setLayout(new BorderLayout()); // North-east-south-west layout
        // layout for the Buttons
        zoom.setLayout(new BorderLayout());
        move.setLayout(new GridLayout(3, 3));
        panel4.setLayout(new BorderLayout());

        // line Button with ActionListener when its pressed
        draw = new Button("Draw");
        store = new Button("Store");
        ztf = new Button("Display");
        plus = new Button("+");
        minus = new Button("-");
        north = new Button("N");
        south = new Button("S");
        east = new Button("E");
        west = new Button("W");
        rotleft = new Button("<(");
        rotright = new Button(")>");
        loadFile = new Button("Load File");
        play = new Button("Play");

        draw.setName("Draw");
        store.setName("store");
        ztf.setName("Display");
        plus.setName("+");
        minus.setName("-");
        north.setName("N");
        south.setName("S");
        east.setName("E");
        west.setName("W");
        rotleft.setName("<(");
        rotright.setName(")>");
        tf1.setName("textfield");
        loadFile.setName("Load File");
        play.setName("Play");

        //m_nicolausB.addActionListener(_c);
        draw.addActionListener(simulationController);
        store.addActionListener(simulationController);
        ztf.addActionListener(simulationController);
        plus.addActionListener(simulationController);
        minus.addActionListener(simulationController);
        north.addActionListener(simulationController);
        south.addActionListener(simulationController);
        east.addActionListener(simulationController);
        west.addActionListener(simulationController);
        rotleft.addActionListener(simulationController);
        rotright.addActionListener(simulationController);
        tf1.addActionListener(simulationController);
        loadFile.addActionListener(simulationController);
        play.addActionListener(simulationController);
        slider.addChangeListener(simulationController);


        zoom.add("North", plus);
        zoom.add("South", minus);

        move.add(new Panel());
        move.add("North", north);
        move.add(new Panel());
        move.add("West", west);

        move.add(new Panel());

        move.add("East", east);
        move.add(new Panel());
        move.add("South", south);
        move.add(new Panel());


        panel2.add(l1);
        panel2.add(tf1);
        panel2.setBackground(new Color(200, 200, 200));
        //panel4.add("North",draw);
        //panel4.add("South",store);
        panel1.add(panel4);
        panel1.add(ztf);
        panel1.add(floorsChoice);
        panel1.add(sectorizationChoice);
        panel1.add(positioningChoice);
        panel1.add(zoom);
        panel1.add(move);
        //panel1.add(rotleft);
        //panel1.add(rotright);
        panel1.add(loadFile);
        panel1.add(play);
        panel1.add(panel2);
        panel1.add(slider);
        //panel1.add(panel3);
        add("South", panel1);
        add("Center", drawingPanel);
    }

    private void generateChoiceFilds() {

    }


    @Override
    public void update(Observable o, Object arg) {
        tf1.setText("" + simulationController.getMapScale());

        if (arg == null || arg instanceof Rectangle) {
            drawingPanel.getRectangle((Rectangle) arg);
        } else {
            drawingPanel.getBufferedImage((BufferedImage) arg);
        }

    }


    @Override
    public File getFileFromFileChoose(String extension) {
        return MapLoader.openFileChooser(extension, this);
    }


    @Override
    public void setObervableModel(BaseModel model) {
        model.addObserver(this);
    }

    @Override
    public void createFLoorChoice(int numberOfFloors, int startindex) {
        floorsChoice.removeAll();
        for (int i = 0; i < numberOfFloors; i++) {
            floorsChoice.add("Floor " + startindex + i);
        }
    }

    @Override
    public Panel getViewPanel() {
        return this;
    }

    @Override
    public int[] getDimensions() {
        return new int[]{drawingPanel.getWidth(), drawingPanel.getHeight()};
    }
}


