/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Darren
 */
public class ImageHelper {

    public static BufferedImage openAsGrayscale(File file) {

        BufferedImage colorImage;
        try {
            colorImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(file.getAbsoluteFile());
            e.printStackTrace();
            return null;
        }

        int w = colorImage.getWidth();
        int h = colorImage.getHeight();

        BufferedImage gray = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        Graphics gi = gray.getGraphics();
        gi.drawImage(colorImage, 0, 0, null);
        gi.dispose();

        return gray;
    }

    public static int getTotal(BufferedImage gray) {
        int ints[] = null;
        int w = gray.getWidth();
        int h = gray.getHeight();

        ints = gray.getRaster().getPixels(0, 0, w, h, ints);

        int total = 0;
        for (int pixel : ints) {
            total += pixel;
        }
        return total;
        
    }
}
