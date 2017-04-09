/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittenrecognition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Jakub
 */
public class GUI extends JPanel
                        implements ActionListener{
    protected JButton bTrain, bClearInputField, bRecognize, bAdd, bSave, bClose;
    protected JRadioButton rbPatterns, rbRecognizing; 
    JFrame frame;
    
    public GUI(){
        //super(new BorderLayout());
        
        bTrain = new JButton("Train");
        bTrain.setActionCommand("Train");
        bTrain.setEnabled(false);
        
        bClearInputField = new JButton("Clear");
        bClearInputField.setActionCommand("Clear");
        bClearInputField.setEnabled(true);
        
        bRecognize = new JButton("Recognize");
        bRecognize.setActionCommand("Recognize");
        bRecognize.setEnabled(false);
        
        bAdd = new JButton("Add");
        bAdd.setActionCommand("Add");
        bAdd.setEnabled(true);
        
        bSave = new JButton("Save");
        bSave.setActionCommand("save");
        bSave.setEnabled(true);
        
        bClose = new JButton("Close");
        bClose.setActionCommand("Close");
        bClose.setEnabled(true);
        
        rbPatterns = new JRadioButton("Patterns");
        rbPatterns.setActionCommand("Patterns");
        rbPatterns.setSelected(true);
        
        rbRecognizing = new JRadioButton("Recognizing");
        rbRecognizing.setActionCommand("Recognizing");
        
        bTrain.addActionListener(this);
        bClearInputField.addActionListener(this);
        bRecognize.addActionListener(this);
        bAdd.addActionListener(this);
        bSave.addActionListener(this);
        bClose.addActionListener(this);
        
        rbPatterns.addActionListener(this);
        rbRecognizing.addActionListener(this);
        
        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(rbPatterns);
        rbGroup.add(rbRecognizing);                
                
        add(rbPatterns);
        add(rbRecognizing);
        
        add(bTrain);
        add(bRecognize);
        add(bClearInputField);
        add(bAdd);
        add(bSave);
        add(bClose);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "":
                break;
            case "Patterns":
                bTrain.setEnabled(false);
                bRecognize.setEnabled(false);
                bAdd.setEnabled(true);
                bSave.setEnabled(true);
                break;
            case "Recognizing":
                bTrain.setEnabled(true);
                bRecognize.setEnabled(true);
                bAdd.setEnabled(false);
                bSave.setEnabled(false);
                break;
            case "Close":
                if (frame.isDisplayable()) {
                    frame.dispose();
                }
                break;
        }
    }
    
    public void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("Hand written characters recognition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        GUI newContentPane = new GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        //frame.pack();
        frame.setSize(450, 400);
        frame.setVisible(true);
    }
}
