package at.fhhgb.mc.component.bui.controller;

import at.fhhgb.mc.component.bui.controller.input.AccessPointController;
import at.fhhgb.mc.component.bui.controller.input.BoundsController;
import at.fhhgb.mc.component.bui.controller.input.WayPointController;
import at.fhhgb.mc.component.bui.model.BUIModel;
import at.fhhgb.mc.component.bui.model.DragObjectManager;
import at.fhhgb.mc.component.bui.model.DragableAccessPoint;
import at.fhhgb.mc.component.bui.model.DragableCornerPoint;
import at.fhhgb.mc.component.bui.view.CreatonModi;
import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.ScreenToImagePosition;
import at.fhhgb.mc.component.sim.model.initialisations.InitSimulatorData;
import at.fhhgb.mc.component.sim.model.recorder.features.Route;
import at.fhhgb.mc.component.sim.model.recorder.features.Waypoint;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;
import at.fhhgb.mc.component.sim.model.simulator.Building;
import at.fhhgb.mc.component.sim.model.simulator.Floor;
import at.fhhgb.mc.interfaces.ComponentVCInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BUIController implements ActionListener, ChangeListener, ItemListener {

    Building building;
    ImageReader reader;
    private List<DragObjectManager> dragObjectManagerList;
    private DragObjectManager dragObjectManager;

    private BUIModel buiModel;
    private ComponentVCInterface viewControllerInterface;
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private int activeFloor = 0;

    private CreatonModi creatonModi = CreatonModi.NONE;

    public BUIController(BUIModel buiModel) {
        this.buiModel = buiModel;
        dragObjectManagerList = new ArrayList<DragObjectManager>();
    }

    public void setViewControllerInterface(ComponentVCInterface viewControllerInterface) {
        this.viewControllerInterface = viewControllerInterface;
        viewControllerInterface.setObervableModel(buiModel);
    }


    @Override
    public void stateChanged(ChangeEvent e) {


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((Component) e.getSource()).getName();
        if (name.equals("load")) {
            try {
                generateFloors();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (name.equals("bounds")) {
            ;
            dragObjectManager.addDragableCornerPoint(300, 300);
            dragObjectManager.addDragableCornerPoint(500, 500);
            buiModel.callObserver(buiModel.createBufferedImage());
        } else if (name.equals("boundingMode")) {
            creatonModi = CreatonModi.BOUNDING;
        } else if (name.equals("accessPointMode")) {
            creatonModi = CreatonModi.ACCESSPOINT;
        } else if (name.equals("wayPointMode")) {
            creatonModi = CreatonModi.WAYPOINT;
        } else if (name.equals("exportBuilding")) {
            exportBuilding();
        } else if (name.equals("exportRoute")) {
            exportRoute();
        }
    }

    private void exportRoute() {
        ObjectMapper mapper = new ObjectMapper();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        Route route = new Route(viewControllerInterface.getRouteName(), dragObjectManager.getDragableWaypoints(), "Testroute", 0);

        try {
            mapper.writeValue(new File(viewControllerInterface.exportBuilding() + "/" + viewControllerInterface.getRouteName()), route);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportBuilding() {
        ObjectMapper mapper = new ObjectMapper();

        JFileChooser chooser = new JFileChooser();
        //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        for (int i = 0; i < dragObjectManagerList.size(); i++) {
            List<DragableAccessPoint> dragableAccessPoints = dragObjectManagerList.get(i).getDragableAccessPoints();
            FloorManager floorManager = new FloorManager(building.getFloor(i));
            List<AccessPoint> exportAccessPoints = new ArrayList<>();
            ;
            for (DragableAccessPoint dragableAccessPoint : dragableAccessPoints) {
                AccessPoint ap = new AccessPoint(dragableAccessPoint);
                ap.setPosition(floorManager.calculateMetricPositionFromPixel(ap.getPosition().getX(), ap.getPosition().getY()));
                exportAccessPoints.add(ap);
            }
            building.getFloor(i).setAccessPointList(exportAccessPoints);
        }
        try {
            mapper.writeValue(new File(viewControllerInterface.exportBuilding() + ".json"), building);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateFloors() throws IOException {
        ImageInputStream is = ImageIO.createImageInputStream(viewControllerInterface.getFileFromFileChoose("tiff"));
        if (is == null || is.length() == 0) {
            // handle error
        }
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
        if (iterator == null || !iterator.hasNext()) {
            throw new IOException("Image file format not supported by ImageIO");
        }

        building = new Building("FHHGB - Building 2");


        //iterator = null;
        reader = iterator.next();
        reader.setInput(is);
        viewControllerInterface.createFLoorChoice(reader.getNumImages(true), 0);

        for (int i = 0; i < reader.getNumImages(true); i++) {
            double[] bounds = InitSimulatorData.getFloorBounds(i);
            building.addFloor(new Floor("FH2 Floor " + i, -1, -1, -1, -1, -1, -1, new ArrayList<AccessPoint>(), reader.read(i)));
            dragObjectManagerList.add(new DragObjectManager());
        }
        dragObjectManager = dragObjectManagerList.get(0);
        buiModel.setDragObjectManager(dragObjectManager);
        buiModel.generateFloorMap(building.getFloor(0), viewControllerInterface.getDimensions());
    }


    public MouseAdapter getMouseAdapter() {

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                screenX = e.getX();
                screenY = e.getY();
                System.out.println(e.getX() + " " + e.getY());
                boolean dragged = dragObjectManager.checkSelectedEllipse(e.getX(), e.getY(), buiModel.getM_tMatrix());

                if (!dragged) {
                    if (creatonModi == CreatonModi.ACCESSPOINT) {
                        Point p = ScreenToImagePosition.getPixelPosition(e.getPoint(), buiModel.getFloorManager().getFloor().getFloorImage(), viewControllerInterface.getDimensions());
                        dragObjectManager.addDragableAccessPoint(p.x, p.y, activeFloor);

                    } else if (creatonModi == CreatonModi.WAYPOINT) {
                        Point p = ScreenToImagePosition.getPixelPosition(e.getPoint(), buiModel.getFloorManager().getFloor().getFloorImage(), viewControllerInterface.getDimensions());
                        dragObjectManager.addDragableWayPoint(p.x, p.y, activeFloor);
                    }
                    changeActiveEllipse();
                    buiModel.callObserver(buiModel.createBufferedImage());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (dragObjectManager.isAnObjectDragged()) {
                    int deltaX = e.getX() - screenX;
                    int deltaY = e.getY() - screenY;
                    System.out.println("deltax " + deltaX + " deltay " + deltaY);

                    Point p2 = ScreenToImagePosition.getPixelPosition(e.getPoint(), buiModel.getFloorManager().getFloor().getFloorImage(), viewControllerInterface.getDimensions());

                    dragObjectManager.changeDraggedEllipsePosition(p2.x, p2.y, buiModel.getM_tMatrix());
                    buiModel.callObserver(buiModel.createBufferedImage());
                    changeActiveEllipse();

                }
                dragObjectManager.dragStopped();
            }
        };

        return mouseAdapter;
    }

    private void changeActiveEllipse() {
        if (dragObjectManager.getDraggedEllipse() instanceof AccessPoint) {
            AccessPointController.getInstance().changeActiveAccessPoint((AccessPoint) dragObjectManager.getDraggedEllipse());
            viewControllerInterface.changeInputPanel(AccessPointController.getInstance().getAccessPointPanel());
        } else if (dragObjectManager.getDraggedEllipse() instanceof Waypoint) {
            WayPointController.getInstance().changeActiveWayPoint((Waypoint) dragObjectManager.getDraggedEllipse());
            viewControllerInterface.changeInputPanel(WayPointController.getInstance().getWayPointPanel());
        } else {
            BoundsController.getInstance().changeFloorBounds(buiModel.getFloorManager().getFloor().getFloorBounds());
            viewControllerInterface.changeInputPanel(BoundsController.getInstance().getBoundsPanel());
            List<DragableCornerPoint> dragableCornerPointList = dragObjectManager.getDragableCornerPoints();
        }
    }

    public MouseMotionAdapter getMouseMotionAdapter() {
        MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

        };
        return mouseMotionAdapter;
    }

    public ComponentAdapter getComponentAdapter() {
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                buiModel.setPanelSize(e.getComponent().getWidth(), e.getComponent().getHeight());
                buiModel.zoomToFit(true);
            }
        };

        return componentAdapter;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String name = ((Component) e.getSource()).getName();
        if (name.equals("floors")) {
            activeFloor = ((Choice) e.getSource()).getSelectedIndex();
            dragObjectManager = dragObjectManagerList.get(activeFloor);
            buiModel.setDragObjectManager(dragObjectManager);
            buiModel.generateFloorMap(building.getFloor(activeFloor), viewControllerInterface.getDimensions());
        }
    }
}
