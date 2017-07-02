package gui.components;

import gui.components.data.Section;
import java.awt.Graphics;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DrawingPanel extends CustomPanel implements MouseMotionListener, MouseListener {

    private ArrayList<Section> toDraw;
    private final int WIDTH = 30;
    private final int HEIGHT = 30;

    public DrawingPanel(int w, int h, int count) {
        super(w, h, count);

        addMouseMotionListener(this);
        addMouseListener(this);
        toDraw = new ArrayList<>();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        paintSections(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        paintSections(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g);

    }

    @Override
    public ArrayList<Integer> getPixels() {

        int centerX = 0;
        int centerY = 0;
        int circleRadius = (this.WIDTH / 2);
        ArrayList<Integer> pixels = new ArrayList<>();
        for (Section s : sections) {
            s.setActive(false); //reset matrix
            for (Section t : toDraw) {

                if (t.isActive() == true) {

                    centerX = t.getX() + circleRadius;
                    centerY = t.getY() + circleRadius;
                    double lenght = Math.sqrt(Math.pow(centerX - s.getX(), 2) + Math.pow(centerY - s.getY(), 2));
                    if (circleRadius >= lenght ) {

                        s.setActive(true);

                    } else if (circleRadius < lenght) {

                    }

                }

            }

        }
        for (Section s : sections) {
            if (s.isActive() == true) {
                pixels.add(1);
            } else {
                pixels.add(0);
            }
        }

        return pixels;
    }

    private void drawCircle(Graphics g) {

        for (Section s : toDraw) {
            if (s.isActive() == true) {
                g.fillOval(s.getX(), s.getY(), this.WIDTH, this.HEIGHT);
            }
        }
    }

    private void paintSections(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {

            toDraw.add(new Section(e.getX(), e.getY(), true));

        } else if (SwingUtilities.isRightMouseButton(e)) {
            for (Section s : toDraw) {
                if (e.getX() > s.getX() && e.getX() < s.getX() + this.WIDTH && e.getY() > s.getY() && e.getY() < s.getY() + this.HEIGHT) {
                    s.setActive(false);
                }

            }
        }

        repaint();
    }

    @Override
    public void clear() {
        for (Section s : toDraw) {
            s.setActive(false);
        }

        repaint();
    }
}
