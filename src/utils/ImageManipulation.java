/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jakub
 */
public class ImageManipulation {
    
    public void process(){
        for (int i=0; i<26;i++){
            for(int j=0;j<55;j++){
            String inPath = "E:\\Desktop\\CharDB\\";
            String outPath = "E:\\Desktop\\CharDB\\";
            Integer folder= 11+i;
            Integer file = 1+j;
            String bonus = "";
            if (j<9)
                bonus = "0";
            inPath += "Sample0" + folder + "\\" + "img0" + folder + "-0" + bonus + file + ".png";
            char charNumber = (char)(65 + i);
            outPath += charNumber +"\\"+ charNumber + file + ".png";
                try {
                    resize(inPath, outPath);
                } catch (IOException ex) {
                    Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public void resize(String inputImagePath, String outputImagePath)
            throws IOException {
        int scaledWidth = 200, scaledHeight = 150;
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
}
