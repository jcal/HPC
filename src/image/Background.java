/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 *
 * @author Darren
 */
public class Background {

    double mean[], deviation[], sigma[];
    int w, h, ceiling;

    public BufferedImage createCompatibleDestImage(BufferedImage src,
            ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }

        return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
    }

    public BufferedImage getSubtraction(BufferedImage image) {
        BufferedImage newImage = createCompatibleDestImage(image, null);
        
        int pixels[] = new int[ceiling];
        image.getRaster().getPixels(0, 0, w, h, pixels);

        int ones = 0, zeros =0;
        for (int i = 0; i < ceiling; i++) {
            double d = Math.abs(pixels[i] - mean[i]);
            pixels[i] = (d > (3 * deviation[i]) )? 255 : 0;
            if (pixels[i] == 0) {
                zeros++;
            } else {
                ones++;
            }
            
        }
        System.out.println("ZEROS: " + zeros + "  AND ONES: " + ones);

        WritableRaster raster = newImage.getRaster();
        raster.setPixels(0, 0, w, h, pixels);
        
        return newImage;
    }

    public void processBackground(File[] files) {

        int frames = files.length;
        BufferedImage[] images = new BufferedImage[frames];


        for (int i = 0; i < images.length; i++) {

            images[i] = ImageHelper.openAsGrayscale(files[i]);

        }

        w = images[0].getWidth();
        h = images[0].getHeight();
        ceiling = w * h;
        System.out.println("CEILING: " + ceiling + " AND FRAMES is + " + frames);
        int pixels[][] = new int[frames][ceiling];

        for (int i = 0; i < frames; i++) {
            images[i].getRaster().getPixels(0, 0, w, h, pixels[i]);
        }


        mean = new double[ceiling];
        deviation = new double[ceiling];
        for (int i = 0; i < ceiling; i++) {
            int total = 0;
            int totalsqr = 0;
            for (int j = 0; j < frames; j++) {
                total += pixels[j][i];
                totalsqr += Math.pow(pixels[j][i],2);
            }
            mean[i] = total / frames;
            deviation[i] = Math.max(Math.sqrt(totalsqr / frames - Math.pow(mean[i], 2)), 0.34);

        }

        System.out.println("DONE!");
    }
    
    
}
