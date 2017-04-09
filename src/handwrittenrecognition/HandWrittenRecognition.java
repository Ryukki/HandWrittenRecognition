/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittenrecognition;

import javax.swing.JFrame;

/**
 *
 * @author Jakub
 */
public class HandWrittenRecognition {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.createAndShowGUI(); 
            }
        });
    }
    
}
