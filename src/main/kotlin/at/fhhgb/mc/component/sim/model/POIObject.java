package at.fhhgb.mc.component.sim.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class can be used to draw Points of Interests.
 * 
 * @author Michael Nigl
 *
 */
public class POIObject extends GeoObject {
	BufferedImage img;
	String imgPath = "poi.gif";
	
	/**
	 * Constructor which loads the image for the POI.
	 * 
	 * @param _geo - The GeoObject where the POI is stored
	 */
	public POIObject(GeoObject _geo) {
		super(_geo);

		 try {
             img = ImageIO.read(getClass().getResourceAsStream(imgPath));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
		
	}
	
	/**
	 * Returns the image of the POI.
	 * 
	 * @return - the image
	 */
	public BufferedImage getImage(){
		return img;
	}
}
