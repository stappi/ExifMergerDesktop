/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stappi.exifmergerdesktop.utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author micha
 */
public final class ImageUtilities {

    private ImageUtilities() {
    }

    public static BufferedImage loadImage(
            File imageFile, int maxWidth, int maxHeight) throws IOException {
        
        BufferedImage originalImage = ImageIO.read(imageFile);
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // calculate the scaling to get the largest dimensions
        double widthScale = (double) maxWidth / originalWidth;
        double heightScale = (double) maxHeight / originalHeight;
        double scale = Math.min(widthScale, heightScale);

        // define new dimension
        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        // resize image
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        bufferedScaledImage.getGraphics().drawImage(scaledImage, 0, 0, null);

        return bufferedScaledImage;
    }

}
