/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hpc;

import image.ImageHelper;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Darren
 */
public class HPC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        StopWatch watch = new StopWatch();
        StringBuilder builder = new StringBuilder();
        watch.start();
        for (int i = 0; i <= 271; i++) {
            builder.delete(0, builder.length());
            String numberStr = Integer.toString(i);

            for (int j = 0; j < 6 - numberStr.length(); j++) {
                builder.append(0);
            }
            builder.append(numberStr).append(".jpg");

            System.out.println(builder);
            BufferedImage gray = ImageHelper.openAsGrayscale(new File("testing/" + builder.toString()));

            int ints[] = null;
//            try {
//                ImageIO.write(gray, "jpg", new File("out" + i + ".jpg"));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            
//            int w = gray.getWidth();
//            int h = gray.getHeight();
//            
//            ints = gray.getRaster().getPixels(0, 0, w, h, ints);
//            int min = 255;
//            int max = 0;
//
//            int total = 0;
//            for (int pixel : ints) {
//                if (pixel < min) {
//                    min = pixel;
//                } else if (pixel > max) {
//                    max = pixel;
//                }
//                total += pixel;
//            }
//            float average = total / ints.length;

            System.out.println(ImageHelper.getTotal(gray));
//            System.out.println("MIN: " + min + ", MAX: " + max + ", AVERAGE: " + average + " for image " + builder.toString());

        }
        watch.stop();
        System.out.println(watch.getElapsedTime() + "milliseconds");
    }

    public HPC() {
    }
}
