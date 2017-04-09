/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwritenregonitionGUI;
/**
 *
 * @author Marcel
 */
public class Frame {
    
    private JFrameMainWidnow mainWindow;
    private  MousePaint mouse;
    
    public Frame(){
        mainWindow= new JFrameMainWidnow();
        mouse=new MousePaint();
        mouse.setBackground(new java.awt.Color(153, 0, 51));
        mouse.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        mainWindow.add(mouse);
      
        
    }
    
     public static void main(String[] args) {
        
        Frame frame=new Frame();
      
     }
     
     
     
     
}
