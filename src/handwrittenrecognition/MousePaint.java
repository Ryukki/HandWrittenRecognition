/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittenrecognition;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Marcel
 */
public class MousePaint extends JPanel implements MouseMotionListener {

    private int x1, y1, x2, y2;

    public MousePaint() {
        addMouseMotionListener(this);
        setBounds(0, 0, 140, 60);
        setVisible(true);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        me.consume();
        int x = me.getX();
        int y = me.getY();
        if (x1 == 0) {
            x1 = x;
        }
        if (y1 == 0) {
            y1 = y;
        }
        x2 = x;
        y2 = y;
        repaint();
        x1 = x2;
        y1 = y2;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

}
