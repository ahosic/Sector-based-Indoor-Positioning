package at.fhhgb.mc.component.bui.view;

import at.fhhgb.mc.component.base.BaseModel;
import at.fhhgb.mc.component.bui.controller.BUIController;
import at.fhhgb.mc.component.sim.view.DrawingPanel;
import at.fhhgb.mc.component.utility.MapLoader;
import at.fhhgb.mc.interfaces.ComponentVCInterface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class BUIView extends Panel implements Observer, ComponentVCInterface {

    DrawingPanel drawingPanel;
    private Button loadImage, createMapBounds, ztf, exportBuilding, exportWayPointRoute;
    private TextField routeName;
    private Panel panelLeft;
    private ModePanel modePanel;
    private Choice floorsChoice;

    public BUIView(BUIController c) {
        drawingPanel = new DrawingPanel();
        drawingPanel.addMouseListener(c.getMouseAdapter());
        drawingPanel.addMouseMotionListener(c.getMouseMotionAdapter());
        drawingPanel.addComponentListener(c.getComponentAdapter());


        Panel bottomPanel = new Panel();
        modePanel = new ModePanel(c);
        panelLeft = new Panel();
        loadImage = new Button("Load Map");
        createMapBounds = new Button("Map Bounds");
        exportBuilding = new Button("Export Building");
        exportWayPointRoute = new Button("Export Waypointroute");
        routeName = new TextField(15);



        loadImage.setName("load");
        createMapBounds.setName("bounds");
        exportBuilding.setName("exportBuilding");
        exportWayPointRoute.setName("exportRoute");


        loadImage.addActionListener(c);
        createMapBounds.addActionListener(c);
        exportBuilding.addActionListener(c);
        exportWayPointRoute.addActionListener(c);

        floorsChoice = new Choice();
        floorsChoice.setName("floors");
        floorsChoice.add("None");
        floorsChoice.addItemListener(c);



        bottomPanel.add(loadImage);
        bottomPanel.add(createMapBounds);
        bottomPanel.add(floorsChoice);
        bottomPanel.add(exportBuilding);
        bottomPanel.add(routeName);
        bottomPanel.add(exportWayPointRoute);

        bottomPanel.add(exportWayPointRoute);
        setLayout(new BorderLayout());
        add("South", bottomPanel);
        add("Center", drawingPanel);
        add("East", modePanel);
        add("West", panelLeft);

    }

    @Override
    public void update(Observable o, Object arg) {
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
    public File exportBuilding() {
        return MapLoader.choosStorageDirectory(this);
    }

    @Override
    public String getRouteName() {
        return routeName.getText();
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

    @Override
    public void changeInputPanel(Panel panel) {
        remove(panelLeft);
        panelLeft = panel;
        add("West", panelLeft);
        revalidate();
        repaint();
    }
}
