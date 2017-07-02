/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jakub
 */
public class ImageManipulation {
    
    public static void main(String[] args) {
        new ImageManipulation().process();
    }
    
    public void process(){
        for (int i=0; i<26;i++){
            for(int j=0;j<55;j++){
            String inPath = "E:\\Desktop\\CharDB\\aaa\\";
            String outPath = "E:\\Desktop\\CharDB\\aaaaa\\";
            Integer folder= 11+i;
            Integer file = 1+j;
            String bonus = "";
            if (j<9)
                bonus = "0";
            //inPath += "Sample0" + folder + "\\" + "img0" + folder + "-0" + bonus + file + ".png";
            char charNumber = (char)(65 + i);
            inPath += charNumber +"\\"+ charNumber + file + ".png";
            outPath += charNumber +"\\"+ charNumber + file + ".png";
                try {
                    resize(inPath, outPath);
                } catch (IOException ex) {
                    Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void cut(String inputImagePath, String outputImagePath)throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        // creates output image
        BufferedImage outputImage = inputImage.getSubimage(150, 0, 900, 900);
                /*new BufferedImage(900,
                900, inputImage.getType());
        Graphics2D temp = outputImage.createGraphics();
        temp.drawImage(inputImage, 0, 0, 900, 900, null);
        temp.dispose();*/
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @throws IOException
     */
    public void resize(String inputImagePath, String outputImagePath)
            throws IOException {
        int scaledWidth = 30, scaledHeight = 30;
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
