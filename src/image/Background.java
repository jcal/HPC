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
    BufferedImage newImage;

    public BufferedImage getSubtraction(BufferedImage image) {
        newImage = createCompatibleDestImage(image, null);

        int pixels[] = new int[ceiling];
        image.getRaster().getPixels(0, 0, w, h, pixels);
        int ones = 0, zeros = 0;
        for (int i = 0; i < ceiling; i++) {
            double d = Math.abs(pixels[i] - mean[i]);
            pixels[i] = (int) ((d > (3 * deviation[i])) ? 255 : 0);
            if (pixels[i] == 0) {
                zeros++;
            } else {
                ones++;
            }

        }


        try {
            for (int i = 0; i < ceiling; i++) {

                if (pixels[i] != 0) {
                    int count = 0;
                    if (i > w) {
                        count += (pixels[i - w] == 0) ? 0 : 1;
                        count += (pixels[i - w - 1] == 0) ? 0 : 1;
                        count += (pixels[i - w + 1] == 0) ? 0 : 1;
                        count += (pixels[i - 1] == 0) ? 0 : 1;
                        count += (pixels[i + 1] == 0) ? 0 : 1;
                        count += (pixels[i + w - 1] == 0) ? 0 : 1;
                        count += (pixels[i + w] == 0) ? 0 : 1;
                        count += (pixels[i + w + 1] == 0) ? 0 : 1;

                        if (count < 2) {
                            pixels[i] = 0;
                        }
                    }
                }

            }
        } catch (Exception e) {
        }


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
        int pixels[] = new int[ceiling];
        int totals[] = new int[ceiling];
        int totalsqr[] = new int[ceiling];

        for (int i = 0; i < frames; i++) {
            images[i].getData().getPixels(0, 0, w, h, pixels);
            for (int j = 0; j < ceiling; j++) {
                totals[j] += pixels[j];
                totalsqr[j] += Math.pow(pixels[j], 2);
            }
        }

        mean = new double[ceiling];
        deviation = new double[ceiling];
        for (int i = 0; i < ceiling; i++) {
            mean[i] = totals[i] / frames;
            deviation[i] = Math.max(Math.sqrt((totalsqr[i] / frames) - Math.pow(mean[i], 2)), 0.34);
        }

        System.out.println("DONE!");
    }
}
