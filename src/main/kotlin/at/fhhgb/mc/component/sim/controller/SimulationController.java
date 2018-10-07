package at.fhhgb.mc.component.sim.controller;

import at.fhhgb.mc.component.sim.model.SimulationModel;
import at.fhhgb.mc.component.sim.model.initialisations.InitSimulatorData;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;
import at.fhhgb.mc.component.sim.model.recorder.network.DataSnapshot;
import at.fhhgb.mc.component.sim.model.simulator.Building;
import at.fhhgb.mc.component.sim.model.simulator.Floor;
import at.fhhgb.mc.component.sim.positioning.AveragePositioning;
import at.fhhgb.mc.component.sim.positioning.FilteredPositioning;
import at.fhhgb.mc.component.sim.positioning.StrongestAccessPointPositioning;
import at.fhhgb.mc.component.sim.sectorizing.GridSectors;
import at.fhhgb.mc.component.sim.sectorizing.QuadTreeSectors;
import at.fhhgb.mc.component.sim.sectorizing.VoronoiSectors;
import at.fhhgb.mc.component.utility.Player;
import at.fhhgb.mc.interfaces.PlayBackEnum;
import at.fhhgb.mc.interfaces.PlaybackCallbackInterface;
import at.fhhgb.mc.interfaces.ViewControllerInterface;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Iterator;
import java.util.List;

/**
 * The Controler of the MVC pattern which is used for this application.
 * All Listeners are implemented in this class.
 */
public class SimulationController implements WindowListener, ActionListener, MouseListener, MouseMotionListener, ComponentListener, ItemListener, PlaybackCallbackInterface, ChangeListener {

    ObjectMapper mapper = new ObjectMapper();
    Building building;
    ViewControllerInterface viewControllerInterface;
    Player player;
	private SimulationModel m_model;
	private int deltaX = 0;
	private int deltaY = 0;
	private boolean scaleerror = false;
	private boolean isRect = false;
	private int startposX = 0;
	private int startposY = 0;
	private ImageReader reader;

	/**
	 * The constructor which is initialized with the mode.
	 */
	public SimulationController(SimulationModel simulationModel) {
		this.m_model = simulationModel;
		ImageIO.scanForPlugins();
	}

	public void setViewControllerInterface(ViewControllerInterface viewControllerInterface) {
		this.viewControllerInterface = viewControllerInterface;
		viewControllerInterface.setObervableModel(m_model);
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//setVisible(false);
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String name =((Component)e.getSource()).getName();
		if(name.equals("Draw")){
			//m_model.loadData();
			try {
				generateFloors();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(name.equals("N")){
			m_model.scrollVertical(-20);
		}else if(name.equals("S")){
			m_model.scrollVertical(20);
		}else if(name.equals("W")){
			m_model.scrollHorizontal(-20);
		}else if(name.equals("E")){
			m_model.scrollHorizontal(20);
		}else if(name.equals("+")){
			m_model.zoom(1.3);
		}else if(name.equals("-")){
			m_model.zoom(1/1.3);
		}else if(name.equals("Display")){
			try {
				generateFloors();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			m_model.zoomToFit(true);
		}else if(name.equals("<(")){
			m_model.rotate(20);
		}else if(name.equals(")>")){
			m_model.rotate(-20);
		}else if(name.equals("store")){
			//m_model.saveImage();
		}else if(name.equals("textfield")){
			TextField t = (TextField)e.getSource();
			try{
				int scale = Integer.parseInt(t.getText());
				m_model.manualMapScale(scale);
			}catch(Exception ex){
				t.setText("Error");
			}
		}else if (name.equals("Load File")){
            try {
                //DataSnapshot dataSnapshot = mapper.readValue(viewControllerInterface.getFileFromFileChoose(), DataSnapshot.class);
				File simulationFile = new File("/Users/almin/Documents/projects/master/resources/final-nigl/FH2F4CCL_11202421.03.2016.txt");
//				List<DataSnapshot> dataSnapshots = mapper.readValue(viewControllerInterface.getFileFromFileChoose("txt"), new TypeReference<List<DataSnapshot>>(){});
				List<DataSnapshot> dataSnapshots = mapper.readValue(simulationFile, new TypeReference<List<DataSnapshot>>(){});

                player = new Player(dataSnapshots, this, PlayBackEnum.SIMPLE);
				if (m_model.getPositioning() == null) {
					m_model.setPositioning(new FilteredPositioning(building.getAllAccessPoints()));
				}

			} catch (IOException e1) {
                e1.printStackTrace();
            }
        }else if(name.equals("Play")) {
            if (player ==  null) return;
            new Thread() {
                public void run() {
                    player.startPlayback();
                }
            }.start();
        }
		
	}

	public void generateFloors() throws IOException {
	    File floorFile = new File("/Users/almin/Documents/projects/master/resources/final-nigl/all_floors.tiff");
//		ImageInputStream is = ImageIO.createImageInputStream(viewControllerInterface.getFileFromFileChoose("tiff"));
		ImageInputStream is = ImageIO.createImageInputStream(floorFile);
		if (is == null || is.length() == 0){
			// handle error
		}
		Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
		if (iterator == null || !iterator.hasNext()) {
			throw new IOException("Image file format not supported by ImageIO");
		}

		building = new Building("FHHGB - Building 2");


		//iterator = null;
		reader = (ImageReader) iterator.next();
		reader.setInput(is);
		viewControllerInterface.createFLoorChoice(reader.getNumImages(true), 0);

        for (int i = 0; i < reader.getNumImages(true); i++) {
            double[] bounds = InitSimulatorData.getFloorBounds(i);
			building.addFloor(new Floor("FH2 Floor", bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5], InitSimulatorData.getTargetFloorAccessPoints(i), reader.read(i)));
		}

        m_model.generateFloorMap(building.getFloor(4), viewControllerInterface.getDimensions());
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	    // m_model.createPoints(mouseX,mouseY);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			isRect = true;
		}
		
		 deltaX = e.getX();
	     deltaY = e.getY();
	     startposX = e.getX();
	     startposY = e.getY();
		
	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
	
		
		if(e.getButton() == MouseEvent.BUTTON1){
			isRect = false;
//			int compareX = e.getX()-deltaX;
//		    int compareY = e.getY()-deltaY;
			System.out.println(startposX);

			Rectangle zoomBounds = new Rectangle(new Point(startposX,startposY));
			zoomBounds.add(e.getPoint());//new Point(compareX,compareY));

			m_model.callObserver((Rectangle) null);
		    if(Math.abs(zoomBounds.width)>= 20 && Math.abs(zoomBounds.height) >= 20){
				m_model.zoomRect(zoomBounds);
		    }
		}
		
		if(e.getButton() == MouseEvent.BUTTON2){
			
		}

		if(e.getButton() == MouseEvent.BUTTON3){
	
			deltaX = e.getX()-deltaX;
		    deltaY = e.getY()-deltaY;
		    
		    if(deltaX != 0 && deltaY != 0){
				m_model.scrollHorizontal(deltaX);
				m_model.scrollVertical(deltaY);
		    }
		}
		
		deltaX = 0;
	    deltaY = 0;
		
	}
	
	/**
	 * Gives the actual scale for the map.
	 * 
	 * @return the scale value of the map.
	 */
	public String getMapScale(){
		if(scaleerror){
			scaleerror = false;
			return "error";
		}else{
			return ""+m_model.calculateScale();
		}
	}
	
	/**
	 * Calculates a rectangle from a given point to starting positions of the mouse drag.
	 * 
	 * @param _pt - the current point of the drag movement
	 */
	public void deltaRect(Point _pt){
		Rectangle rect = new Rectangle(new Point(startposX, startposY));
		rect.add(_pt);
		m_model.callObserver(rect);
		
	}
	
	/**
	 * Checks if there is a zoom rectangle on the screen.
	 * 
	 * @return true if there is a zoom rectangle; false otherwise
	 */
	public boolean isZoomRect(){
		return isRect;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		m_model.setPanelSize(e.getComponent().getWidth(), e.getComponent().getHeight());
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(isRect){
			deltaRect(arg0.getPoint());
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	
		
	}

    @Override
    public void nearestAccessPoint(List<AccessPoint> accessPoints) {
        m_model.calculateSimplePersonPosition(accessPoints);
    }

    @Override
    public void allAccessPoints(List<AccessPoint> accessPointList) {
		// m_model.calculateWeightedPersonPosition(accessPointList);
		m_model.addPlayerData(accessPointList);
	}

	@Override
	public void wayPointCount(int[] wayPointCount) {
		m_model.setWayPointCount(wayPointCount);
	}

	@Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() instanceof JSlider){
            JSlider slider = (JSlider)e.getSource();
            long value = slider.getValue();
            if(player != null){
                player.setPlayBackSpeed(value);
            }
        }
    }

	@Override
	public void itemStateChanged(ItemEvent e) {
		String name = ((Component) e.getSource()).getName();
		if (name.equals("floors")) {
			int index = ((Choice) e.getSource()).getSelectedIndex();
			m_model.generateFloorMap(building.getFloor(index), viewControllerInterface.getDimensions());
		} else if (name.equals("xGrid")) {
			String item = ((Choice) e.getSource()).getSelectedItem();
			if (item.equals("Grid")) {
				m_model.setSectorizing(new GridSectors());
            } else if (item.equals("Voronoi")) {
                m_model.setSectorizing(new VoronoiSectors());
            } else if (item.equals("QuadTree")) {
                m_model.setSectorizing(new QuadTreeSectors());
            } else {
                m_model.setSectorizing(null);
            }
        } else if (name.equals("yGrid")) {
			int index = ((Choice) e.getSource()).getSelectedIndex();
			String item = ((Choice) e.getSource()).getSelectedItem();
			if (item.equals("Strongest AP")) {
				System.out.println("Selected Method: Strongest AP");
				m_model.setPositioning(new StrongestAccessPointPositioning(building.getAllAccessPoints()));
			} else if (item.equals("Filtered Algorithm")) {
				System.out.println("Selected Method: Filtered Algorithm");
				m_model.setPositioning(new FilteredPositioning(building.getAllAccessPoints()));
			} else if (item.equals("Sliding Window")) {
				System.out.println("Selected Method: Sliding Window");
				m_model.setPositioning(new AveragePositioning(building.getAllAccessPoints(), 10));
			}
		}
	}
}
