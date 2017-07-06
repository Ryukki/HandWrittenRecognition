/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jakub
 */
public class ImageToFile {
    
    public static void main(String[] args) {
        for (int i=0; i<26;i++){
            for(int j=0;j<50;j++){
            String inPath = "E:\\Desktop\\CharDB\\aaaaa\\";
            String outPathLearn = "E:\\Desktop\\CharDB\\res\\";
            String outPathTest = "E:\\Desktop\\CharDB\\res\\";
            Integer file = 1+j;
            char charNumber = (char)(65 + i);
            inPath += charNumber +"\\"+ charNumber + file + ".png";
            
            outPathTest += charNumber + "test.txt";
            outPathLearn += charNumber + "learn.txt";
                try {
                    if(j%2==0)
                        extractBytes(inPath, outPathLearn);
                    else
                        extractBytes(inPath, outPathTest);
                } catch (IOException ex) {
                    Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void extractBytes(String pngFile, String outputFile) throws IOException {
        File imgPath = new File(pngFile);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        Raster raster = bufferedImage.getData();
 
        int width = raster.getWidth();
        int height = raster.getHeight();
 
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
 
            ImageIO.write(bufferedImage, "png", bos);
 
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int temp = bufferedImage.getRGB(i, j);
                if (temp == -1){
                    //System.out.println(i + " " + j + " " + temp + "\n");
                    writer.write("0");
                } else{
                    //System.out.println(i + " " + j + " " + temp + "\n");
                    writer.write("1");
                }
                //writer.write(temp);
            }
            writer.flush();
        }
        writer.close();
    }
}
