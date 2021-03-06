package gui.components;

import gui.components.data.Section;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomPanel extends JPanel {

    protected ArrayList<Section> sections;
    private int width;
    private int height;
    protected int count;
    private boolean generate = false;

    public CustomPanel(int w, int h, int count) {
        super();
        this.width = w;
        this.height = h;
        this.count = count;

        setPreferredSize(new Dimension(w, h));
        setBackground(Color.WHITE);
        sections = new ArrayList<>();
        generateSections();
    }

    private void generateSections() {

        for (int i = 0; i < width/15; i++) {
            for (int j = 0; j < height/15; j++) {
                sections.add(new Section(i * (width / 30), j * (height / 30), width / 30, height / 30));
                
            }
        }

        repaint();
       
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        generateSections(g);
        drawSections(g);

    }

    protected void generateSections(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        for (Section s : sections) {
            g.drawLine(0, s.getY(), width, s.getY());
            g.drawLine(s.getX(), 0, s.getX(), height);

        }

    }

    protected void drawSections(Graphics g) {
        g.setColor(Color.BLACK);
        for (Section s : sections) {
            if (s.isActive()) {
                //g.fillRect(s.getX(), s.getY(),40, 40);
                //g.fillOval(s.getX(), s.getY(), 40, 40);

            }
        }
    }

    public ArrayList<Integer> getPixels() {
        int count = 0;
        ArrayList<Integer> pixels = new ArrayList<>();
        for (Section s : sections) {

            if (s.isActive()) {
                pixels.add(1);
            } else {
                pixels.add(0);
            }
            ++count;
            if (count == 20) {
                count = 0;
            }

        }

        return pixels;
    }

    public void clear() {
        for (Section s : sections) {
            s.setActive(false);
        }

        repaint();
    }

    public void drawLetter(ArrayList<Integer> pixels) {
        for (int i = 0; i < pixels.size(); i++) {
            if (pixels.get(i) == 1) {
                sections.get(i).setActive(true);
            } else {
                sections.get(i).setActive(false);
            }
        }

        repaint();
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

}
