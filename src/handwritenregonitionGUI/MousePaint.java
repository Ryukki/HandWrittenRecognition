/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwritenregonitionGUI;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Marcel
 */
public class MousePaint extends JPanel implements MouseMotionListener
    {
     private int x1, y1, x2, y2;
     private Border redBorder;
     public MousePaint()
         {
         
         addMouseMotionListener(this);
         setBounds(50,50,400,250);
         redBorder=BorderFactory.createLineBorder(Color.red,3,true);
         setBackground(Color.red);
         setBorder(redBorder);
         setVisible(true);
     }
     public static void main(String[] argv)
         {
         new MousePaint();
     }
     @Override
     public void update(Graphics g)
         {
         paint(g);
     }
     @Override
     public void paint(Graphics g)
         {
         g.setColor(Color.black);
         g.drawLine(x1, y1, x2, y2);
        
     }
      @Override
     public void mouseDragged(MouseEvent me)
         {
         me.consume();
         int x = me.getX();
         int y = me.getY();
         if ( x1 == 0 )
             {
             x1 = x;
         }
         if ( y1 == 0 )
             {
             y1 = y;
         }
         x2 = x;
         y2 = y;
         repaint();
         x1 = x2;
         y1 = y2;
     }
      @Override
     public void mouseMoved(MouseEvent me)
     { }

    
}
