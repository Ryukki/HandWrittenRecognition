/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handwrittenrecognition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jakub
 */
public class GUI extends JPanel
                        implements ActionListener{
    protected JButton bTrain, bClearInputField, bRecognize, bAdd, bSave, bClose;
    protected JRadioButton rbPatterns, rbRecognizing; 
    protected JPanel pane1, pane2, pane3,rightPanel, leftPanel;
    private JSplitPane splitpane1;
    //JPanel panel;
    JFrame frame;
    private JFrameMainWindow mainWindow;
    private  MousePaint mouse;
    
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
                
        pane1 = new JPanel();
        pane1.add(rbPatterns);
        pane1.add(rbRecognizing);
        add(pane1);
        
        pane2 = new JPanel();
        pane2.setLayout(new GridLayout(2,5));
        pane2.add(bTrain);
        pane2.add(bRecognize);
        pane2.add(bClearInputField);
        
      
        pane2.add(bAdd);
        pane2.add(bSave);
        pane2.add(bClose);
        

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
                //if (frame.isDisplayable()) {
                    //frame.dispose();
                //}
                break;
            default:
                break;
        }
    }
    
    public void createAndShowGUI() {
//        mainWindow= new JFrameMainWindow();
        mouse=new MousePaint();
        mouse.setBackground(new java.awt.Color(153, 0, 51));
        mouse.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        mouse.setLocation(0, 60); // nie dziala
        mouse.setPreferredSize(new Dimension(2,100)); // czemu x nie dziala
        
        leftPanel=new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(mouse, BorderLayout.NORTH);
        
        rightPanel=new JPanel();
        rightPanel.add(pane1);
        rightPanel.add(pane2);
       // mainWindow.add(mouse);
       splitpane1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
       splitpane1.resetToPreferredSizes();
       splitpane1.setDividerLocation(200 + splitpane1.getInsets().left) ;
       splitpane1.setEnabled(false);
       splitpane1.setVisible(true);
       
       
        
        //Create and set up the window.
        frame = new JFrame("Hand written characters recognition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(splitpane1);
        //Create and set up the content pane.
//        GUI newContentPane = new GUI();
//        newContentPane.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(newContentPane);
        frame.add(splitpane1);
        //frame.add(mouse, BorderLayout.CENTER);
        //frame.getContentPane().add(newContentPane, BorderLayout.NORTH);
        
        //Display the window.
        //frame.pack();
        frame.setResizable(false);
        frame.setSize(600, 500);
        frame.setVisible(true);
        
        
    }
}
