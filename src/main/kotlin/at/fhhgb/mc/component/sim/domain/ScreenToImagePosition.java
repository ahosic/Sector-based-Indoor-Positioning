package at.fhhgb.mc.component.sim.domain;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class ScreenToImagePosition {

    private static boolean widthIsToBeScaled = false;


    public static Point getPixelPosition(Point clickPosition, BufferedImage image, int[] panelDimenstions) {
        double scaleDifference = calculateScaleDifference(image, panelDimenstions);
        double xmult = 0;
        double ymult = 0;
        Point p2;
        if (widthIsToBeScaled) {
            xmult = (double) image.getWidth() / ((double) panelDimenstions[0] - scaleDifference);
            ymult = (double) image.getHeight() / (double) panelDimenstions[1];
            p2 = new Point((int) Math.round((clickPosition.getX() * xmult) - scaleDifference / 2 * xmult), (int) Math.round(clickPosition.getY() * ymult));

        } else {
            xmult = (double) image.getWidth() / (double) panelDimenstions[0];
            ymult = (double) image.getHeight() / ((double) panelDimenstions[1] - scaleDifference);
            p2 = new Point((int) Math.round((clickPosition.getX() * xmult)), (int) Math.round((clickPosition.getY() * ymult) - scaleDifference / 2 * ymult));
        }
        return p2;
    }

    private static double calculateScaleDifference(BufferedImage image, int[] panelDimensions) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double panelWidth = panelDimensions[0];
        double panelHeight = panelDimensions[1];

        if (imageWidth / panelWidth < imageHeight / panelHeight) {
            widthIsToBeScaled = true;
        } else {
            widthIsToBeScaled = false;
        }

        /*double imageRelation = (double) imageHeight/(double) imageWidth;
        double panelRelation = (double) panelHeight/(double) panelWidth;

        return imageRelation - panelRel (double)panelHeight/(double)panelWidth */
        double scale = 0;
        if (widthIsToBeScaled) {
            scale = panelHeight / imageHeight * imageWidth;
            return (panelWidth - scale);
        } else {
            scale = panelWidth / imageWidth * imageHeight;
            return (panelHeight - scale);
        }

    }

}
