package gui;

import data.GoodOutputs;
import data.ReadWriteFile;
import gui.components.DrawingPanel;
import neural.Train;
import neural.TrainingSet;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGui extends JFrame {

    private final int RESOLUTION = 30;

    private Train networkTrainer;

    private JPanel mainPanel;
    private DrawingPanel drawingPanel;

    private JButton clearButton;
    private JButton trainButton;
    private JButton transformButton;
    private JButton helpButton;
    private JButton testButton;
    private JButton trainNetworkButton;
    private JComboBox<String> trainAsCombo;
    private JTextField outputTextField;
    private ArrayList<ArrayList<Integer>> testingSets;
    private JFrame tempFrame;
    private JTextField tempTextField;

    public static void main(String[] args) {
        new MainGui();
    }

    public MainGui() {
        super("Natural Network");

        networkTrainer = new Train();

        setMainPanel();
        setLeftSide();
        setCenterArea();

        setOnClicks();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setResizable(false);
        testingSets = new ArrayList();
        loadTestingSets();
                    /*tempFrame = new JFrame();
            tempTextField = new JTextField("kkk");
            tempFrame.add(tempTextField);
            tempFrame.pack();
            tempFrame.setVisible(true);*/
    }

    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridLayout(1, 3));
        setContentPane(mainPanel);
    }

    private void setLeftSide() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(800, 440));

        drawingPanel = new DrawingPanel(450, 450, RESOLUTION);
        panel.add(drawingPanel);

        mainPanel.add(panel);
    }

    private void setCenterArea() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        // centerPanel.setPreferredSize(new Dimension(100, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        trainNetworkButton = new JButton("Train network");
        //  centerPanel.setSize(new Dimension(100,40));
        centerPanel.add(trainNetworkButton, gbc);

        testButton = new JButton("Test");
        centerPanel.add(testButton, gbc);

        centerPanel.add(Box.createVerticalStrut(50));

        helpButton = new JButton("HELP");
        centerPanel.add(helpButton, gbc);

        centerPanel.add(Box.createVerticalStrut(50));

        transformButton = new JButton(">>");
        centerPanel.add(transformButton, gbc);
        centerPanel.add(Box.createVerticalStrut(50));

        clearButton = new JButton("Clear");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(clearButton, gbc);

        centerPanel.add(Box.createVerticalStrut(50));

        centerPanel.add(new JLabel("Train as:", SwingConstants.CENTER), gbc);

        trainAsCombo = new JComboBox<>(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        trainAsCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainAsCombo.setMaximumSize(new Dimension((int) trainAsCombo.getPreferredSize().getWidth(), 30));
        centerPanel.add(trainAsCombo, gbc);

        trainButton = new JButton("Train");
        centerPanel.add(trainButton, gbc);

        centerPanel.add(Box.createVerticalStrut(50));
        outputTextField = new JTextField();
        outputTextField.setPreferredSize(new Dimension(200, 20));
        centerPanel.add(outputTextField);

        mainPanel.add(centerPanel);
    }

    private void readFile(String filename) throws FileNotFoundException, IOException {

        try (InputStream in = new FileInputStream(new File(filename));
                Reader reader = new InputStreamReader(in, Charset.defaultCharset());
                Reader buffer = new BufferedReader(reader)) {
            for (int j = 0; j < 10; j++) {
                int r = 0;
                ArrayList<Integer> pixels = new ArrayList<>();
                for (int i = 0; i < 900; i++) {
                    if ((r = reader.read()) != -1) {
                        r -= 48;
                        pixels.add(r);
                    }
                }
                testingSets.add(pixels);
            }
        }
    }

    private void loadTestingSets() {
        for (int j = 0; j < 26; j++) {
            char letterValue = (char) (j + 65);
            String path = "src\\resources\\" + letterValue + "test.txt";
            try {
                readFile(path);
            } catch (IOException ex) {
                Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setOnClicks() {
        clearButton.addActionListener(e -> {
            drawingPanel.clear();
            outputTextField.setText("");
                    });

        trainButton.addActionListener(e -> {
            String letter = (String) trainAsCombo.getSelectedItem();
            networkTrainer.addTrainingSet(new TrainingSet(drawingPanel.getPixels(), GoodOutputs.getInstance().getGoodOutput(letter)));
            ReadWriteFile.saveToFile(drawingPanel.getPixels(), letter);
        });

        testButton.addActionListener(e -> {
            double result = 0.0, amountOfTries = 0.0, amountOfSucceses = 0.0;
            /*JFrame tempFrame = new JFrame();
            JTextField tempTextField = new JTextField("Network has " + result + " success ratio.\t"
                    + "Best score so far was: "+ amountOfSucceses + " out of " + amountOfTries);
            tempFrame.add(tempTextField);
            tempFrame.pack();
            tempFrame.setVisible(true);*/
            do {
                amountOfTries = 0;
                amountOfSucceses = 0;
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 10; k++) {
                        int ind = j * 10 + k;
                        networkTrainer.setInputs(testingSets.get(ind));
                        ArrayList<Double> outputs = networkTrainer.getOutputs();
                        int index = 0;
                        for (int i = 0; i < outputs.size(); i++) {
                            if (outputs.get(i) > outputs.get(index)) {
                                index = i;
                            }
                        }
                        amountOfTries++;
                        if (index == j) {
                            amountOfSucceses++;
                        }
                    }
                }
                double tempres = result;
                result = amountOfSucceses / amountOfTries;
                if (result > tempres){
                    System.out.println("Network has " + result + " success ratio.\n"
                    + "Best score so far was: "+ amountOfSucceses + " out of " + amountOfTries);
                }
                if (result < 0.7) {
                    networkTrainer.train();
                } else {
                    break;
                }
            } while (result < 0.7);
        });

        transformButton.addActionListener(e
                -> {
            networkTrainer.setInputs(drawingPanel.getPixels());

            ArrayList<Double> outputs = networkTrainer.getOutputs();
            int index = 0;
            for (int i = 0; i < outputs.size(); i++) {
                if (outputs.get(i) > outputs.get(index)) {
                    index = i;
                }
            }

            //updateTextArea();
            trainAsCombo.setSelectedIndex(index);
            outputTextField.setText("Written letter is: " + trainAsCombo.getItemAt(index));
        }
        );

        helpButton.addActionListener(e
                -> {
            drawingPanel.getPixels();
            StringBuilder sb = new StringBuilder();
            sb.append("Train network after you start the program.\n");
            sb.append("\n");
            sb.append("Use left/right mouse button to draw/erase\n");
            sb.append("\n");
            sb.append("Click \">>\" to see result\n");
            sb.append("\n");
            sb.append("Click \"Train\" to train specific letter\n");
            JOptionPane.showMessageDialog(this, sb.toString(), "Help", JOptionPane.PLAIN_MESSAGE);
        }
        );

        trainNetworkButton.addActionListener(e
                -> {
            networkTrainer.train();//number);
        }
        );
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Double> outputs = networkTrainer.getOutputs();
        for (int i = 0; i < outputs.size(); i++) {
            int letterValue = i + 65;
            sb.append((char) letterValue);
            double value = outputs.get(i);
            if (value < 0.01) {
                value = 0;
            }
            if (value > 0.99) {
                value = 1;
            }

            value *= 1000;
            int x = (int) (value);
            value = x / 1000.0;

            sb.append("\t " + value);
            sb.append("\n");
        }
        outputTextField.setText(sb.toString());
    }

}
