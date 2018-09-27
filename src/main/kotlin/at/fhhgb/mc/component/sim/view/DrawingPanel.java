package at.fhhgb.mc.component.sim.view;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class contains a panel where all of the objects are drawn.
 * 
 * @author Michael Nigl
 */
public class DrawingPanel extends Panel{
	
	BufferedImage image;
	Rectangle m_zoomRect = null;
	Rectangle m_safeRect = null;

	private boolean isClicked = false;

	/**
	 * The Constructor which initializes the Panel
	 */
	public DrawingPanel() {
		super();
		this.setBackground(Color.gray);
	}

	/**
	 * Draws the buffered image on this panel
	 * @param _image - the image which is drawn
	 */
	public void getBufferedImage(BufferedImage _image){
		image = _image;
		repaint();
	}
	
	/**
	 * Draws the zoom rectangle on the DrawingPanel in XOR mode.
	 * 
	 * @param _rect - the rectangle which is drawn.
	 */
	public void getRectangle(Rectangle _rect){
		m_zoomRect = _rect;
		
		Graphics g = this.getGraphics();
		g.setXORMode(Color.RED);
		if(m_zoomRect!=null || m_safeRect != null){
			
			if(m_safeRect != null){
				g.drawRect(m_safeRect.x,m_safeRect.y,m_safeRect.width,m_safeRect.height);
				m_safeRect = null;
			}
			
			if(m_zoomRect != null){
				g.drawRect(m_zoomRect.x,m_zoomRect.y,m_zoomRect.width,m_zoomRect.height);
			}
			m_safeRect = m_zoomRect;
			m_zoomRect = null;
			
		}
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {

		if(image != null){
			g.drawImage(image,0,0,null);

		}		
	}

	

}
