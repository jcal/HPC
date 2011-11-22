/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.awt.Color;
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
//        
//        BufferedImage colorImage;
//        try {
//            colorImage = ImageIO.read(file);
//        } catch (IOException e) {
//            System.out.println(file.getAbsoluteFile());
//            e.printStackTrace();
//            return null;
//        }
//
//        int w = colorImage.getWidth();
//        int h = colorImage.getHeight();
//
//        BufferedImage rgb = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//        Graphics gi = rgb.getGraphics();
//        gi.drawImage(colorImage, 0, 0, null);
//        gi.dispose();
//        
//        BufferedImage gray = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
//        
//        int ints[] = new int[w*h];
//        
//        ints = colorImage.getRGB(0, 0, w, h, ints, 0, w);
////        rgb.getRaster().getPixels(0, 0, w, h, ints);
//        
//        for(int i = 0; i < ints.length; i++) {
//            Color color = new Color(ints[i]);
////            System.out.println("RED: " + color.getRed() + " GREEN: " + color.getGreen() + " BLUE: " + color.getBlue());
//            int grays = (int)(color.getRed() + color.getBlue() + color.getGreen())/3;
////            System.out.println(grays);
//            ints[i] = grays;
//        }
//        
//        gray.getRaster().setPixels(0, 0, w, h, ints);
//        

//        return gray;
    }

}
