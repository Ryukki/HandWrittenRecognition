package gui;

import data.GoodOutputs;
import data.ReadWriteFile;
import gui.components.DrawingPanel;
import neural.Train;
import neural.TrainingSet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainGui extends JFrame {

    private final int RESOLUTION = 150;

    private Train networkTrainer;

    private JPanel mainPanel;
    private DrawingPanel drawingPanel;

    private JButton clearButton;
    private JButton trainButton;
    private JButton transformButton;
    private JButton helpButton;
    private JButton trainNetworkButton;
    private JTextField trainingSetsAmount;
    private JComboBox<String> trainAsCombo;
    private JTextField outputTextField;

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
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridLayout(1,3));
        setContentPane(mainPanel);
    }

    private void setLeftSide() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(800, 440));
        
        drawingPanel = new DrawingPanel(600, 450, RESOLUTION);
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
       
        trainNetworkButton = new JButton("Train X times:");
        trainingSetsAmount = new JFormattedTextField("5000");
        trainingSetsAmount.setMaximumSize(new Dimension(100, 30));
        trainingSetsAmount.setPreferredSize(new Dimension(100, 30));
      //  centerPanel.setSize(new Dimension(100,40));
        centerPanel.add(trainNetworkButton, gbc);
        //centerPanel.add(trainingSetsAmount, gbc);

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

    private void setOnClicks() {
        clearButton.addActionListener(e -> drawingPanel.clear());

        trainButton.addActionListener(e -> {
            String letter = (String) trainAsCombo.getSelectedItem();
            networkTrainer.addTrainingSet(new TrainingSet(drawingPanel.getPixels(), GoodOutputs.getInstance().getGoodOutput(letter)));
            ReadWriteFile.saveToFile(drawingPanel.getPixels(), letter);
        });

        transformButton.addActionListener(e -> {
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
        });

        helpButton.addActionListener(e -> {
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
        });

        trainNetworkButton.addActionListener(e -> {
            networkTrainer.train();//number);
        });
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
