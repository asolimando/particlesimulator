import java.beans.PropertyChangeEvent;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class ParticleSimulatorToolbar extends JToolBar implements ActionListener, ChangeListener {
    
    ParticleSimulator frame;
    
    ImageIcon pauseIcon = new ImageIcon(SimulationPanel.loadImage(this, "images/pause.png"));
    ImageIcon playIcon = new ImageIcon(SimulationPanel.loadImage(this, "images/play.png"));
    
    JPanel sliderPanel = new JPanel(new BorderLayout());
    JButton newEnvButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/new.png")));
    JButton playToolButton = new JButton(playIcon);
    JButton nextStepToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/step.png")));
    JButton insertSingleToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/part_red.png")));
    JButton insertDoubleToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/part_blue.png")));
    JButton insertBreakerToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/part_green.png")));
    JButton emptyAllToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/reset.png")));
    JButton deleteToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/delete.png")));
    JButton insertAttrSingleToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/attr_red.png")));
    JButton insertAttrDoubleToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/attr_blue.png")));
    JButton insertAttrBreakerToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/attr_green.png")));
    JButton insertAttrAllToolButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/attr_black.png")));
    JButton cancelButton = new JButton(new ImageIcon(SimulationPanel.loadImage(this, "images/cancel.png")));
    //ImagePanel slowDownToolPanel = new ImagePanel(SimulationPanel.loadImage(this, "images/minus.png"));
    JSlider speedToolSlider = new JSlider(1, SimulationPanel.SPEED_TICKS , SimulationPanel.SPEED_TICKS/2);
    //ImagePanel speedUpToolPanel = new ImagePanel(SimulationPanel.loadImage(this, "images/plus.png"));
    JLabel slowDownToolIcon = new JLabel(new ImageIcon(SimulationPanel.loadImage(this, "images/minus.png")));
    JLabel speedUpToolIcon =  new JLabel(new ImageIcon(SimulationPanel.loadImage(this, "images/plus.png")));
    

    
    
    public ParticleSimulatorToolbar(ParticleSimulator frame) {
        super("Particle simulator");
        
        this.frame = frame;
        
        //this.setFloatable(false);
        
        /* start ToolTip */
        newEnvButton.setToolTipText("Create a new environment");
        playToolButton.setToolTipText("Start and pause the simulation");
        insertSingleToolButton.setToolTipText("Graphical insertion of a single particle");
        insertDoubleToolButton.setToolTipText("Graphical insertion of a double particle");
        insertBreakerToolButton.setToolTipText("Graphical insertion of a disruptive particle");
        nextStepToolButton.setToolTipText("Advance of a single step in the simulation");
        emptyAllToolButton.setToolTipText("Reset the environment");
        deleteToolButton.setToolTipText("Clear an area or deleted a selected element");
        insertAttrSingleToolButton.setToolTipText("Insertion of an attractor for a single particle");
        insertAttrDoubleToolButton.setToolTipText("Insertion of an attractor for a double particle");
        insertAttrBreakerToolButton.setToolTipText("Insertion of an attractor for a disruptive particle");
        insertAttrAllToolButton.setToolTipText("Insertion of an attractor for any particle");
        speedToolSlider.setToolTipText("Simulation speed setting");
        cancelButton.setToolTipText("Cancel the ongoing operation");
        /* end ToolTip */
        
        
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                JToolBar t = (JToolBar) evt.getSource();
                String propName = evt.getPropertyName();
                if ("orientation".equals(propName)) {
                    Integer oldValue = (Integer)evt.getOldValue();
                    Integer newValue = (Integer)evt.getNewValue();
                    
                    if (newValue.intValue() == JToolBar.HORIZONTAL) {
                        sliderPanel.removeAll();
                        speedToolSlider.setOrientation(SwingConstants.HORIZONTAL);
                        sliderPanel.add(slowDownToolIcon, BorderLayout.WEST);
                        sliderPanel.add(speedToolSlider, BorderLayout.CENTER);
                        sliderPanel.add(speedUpToolIcon, BorderLayout.EAST);
                        
                    } else {
                        sliderPanel.removeAll();
                        speedToolSlider.setOrientation(SwingConstants.VERTICAL);
                        sliderPanel.add(slowDownToolIcon, BorderLayout.SOUTH);
                        sliderPanel.add(speedToolSlider, BorderLayout.CENTER);
                        sliderPanel.add(speedUpToolIcon, BorderLayout.NORTH);
                    }
                }
            }
        });
        
        /* start toolbar init */
        
        add(newEnvButton);
        add(playToolButton);
        add(nextStepToolButton);
        addSeparator();
        add(insertSingleToolButton);
        add(insertDoubleToolButton);
        add(insertBreakerToolButton);
        addSeparator();
        add(insertAttrSingleToolButton);
        add(insertAttrDoubleToolButton);
        add(insertAttrBreakerToolButton);
        add(insertAttrAllToolButton);
        addSeparator();
        add(deleteToolButton);
        add(emptyAllToolButton);
        addSeparator();
        add(cancelButton);
        
        addSeparator();
        sliderPanel.add(slowDownToolIcon, BorderLayout.WEST);
        sliderPanel.add(speedToolSlider, BorderLayout.CENTER);
        sliderPanel.add(speedUpToolIcon, BorderLayout.EAST);
        add(sliderPanel);
        
        
        newEnvButton.addActionListener(this);
        playToolButton.addActionListener(this);
        insertSingleToolButton.addActionListener(this);
        insertDoubleToolButton.addActionListener(this);
        insertBreakerToolButton.addActionListener(this);
        nextStepToolButton.addActionListener(this);
        emptyAllToolButton.addActionListener(this);
        deleteToolButton.addActionListener(this);
        insertAttrSingleToolButton.addActionListener(this);
        insertAttrDoubleToolButton.addActionListener(this);
        insertAttrBreakerToolButton.addActionListener(this);
        insertAttrAllToolButton.addActionListener(this);
        cancelButton.addActionListener(this);
        speedToolSlider.addChangeListener(this);
        
        speedToolSlider.setValue(SimulationPanel.SPEED_TICKS/2);
        /* end toolbar init */
    }
    
    
    public void actionPerformed(ActionEvent e){
        JButton jbutt = (JButton) e.getSource();
        
        if(jbutt == newEnvButton){
            frame.jnDialog.setVisible(true);
            return;
        }
        
        if(checkEnvironment() == false){
            return;
        }
        
        if(jbutt == playToolButton){
            frame.sp.playPauseSimulation();
        }
        
        else if(jbutt == insertSingleToolButton){
            try {
                frame.sp.enterInsertMode(Particle.SINGLE);
            } catch(SimulationPanelWrongTypeException e1){};
        }
        
        else if(jbutt == insertDoubleToolButton){
            try {
                frame.sp.enterInsertMode(Particle.DOUBLE);
            } catch(SimulationPanelWrongTypeException e2){};
        }
        
        else if(jbutt == insertBreakerToolButton){
            try {
                frame.sp.enterInsertMode(Particle.BREAKER);
            } catch(SimulationPanelWrongTypeException e3){};
        }
        
        else if(jbutt == nextStepToolButton){
            frame.nextStep();
        }
        
        else if(jbutt == emptyAllToolButton){
            frame.sp.resetEnvironment();
        }
        
        else if(jbutt == deleteToolButton){
            frame.sp.removeSelected();
        }
        
        else if(jbutt == cancelButton){
            frame.sp.cancelOperation();
        }
        
        else if(jbutt == insertAttrSingleToolButton){
            try {
                frame.sp.enterInsertMode(Attractor.SINGLE);
            } catch(SimulationPanelWrongTypeException e4){};
        }
        
        else if(jbutt == insertAttrDoubleToolButton){
            try {
                frame.sp.enterInsertMode(Attractor.DOUBLE);
            } catch(SimulationPanelWrongTypeException e5){};
        }
        
        else if(jbutt == insertAttrBreakerToolButton){
            try {
                frame.sp.enterInsertMode(Attractor.BREAKER);
            } catch(SimulationPanelWrongTypeException e6){};
        }
        
        else if(jbutt == insertAttrAllToolButton){
            try {
                frame.sp.enterInsertMode(Attractor.ALL);
            } catch(SimulationPanelWrongTypeException e7){};
        }
        
    }
    
    boolean checkEnvironment(){
        if(frame.sp == null){
            JOptionPane.showMessageDialog(frame, "Missing environment!", "Missing environment", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if(frame.sp == null) return;
        
        frame.sp.setAnimationSpeed(source.getValue());
        /*
        if(frame.actualStatus == frame.SIMULATION_PLAY){
            frame.notifyEvent(frame.SIMULATION_PLAY, 0);
        }*/
    }
    
    void disableButtons(){
        newEnvButton.setEnabled(false);
        playToolButton.setEnabled(false);
        nextStepToolButton.setEnabled(false);
        insertSingleToolButton.setEnabled(false);
        insertDoubleToolButton.setEnabled(false);
        insertBreakerToolButton.setEnabled(false);
        emptyAllToolButton.setEnabled(false);
        deleteToolButton.setEnabled(false);
        insertAttrSingleToolButton.setEnabled(false);
        insertAttrDoubleToolButton.setEnabled(false);
        insertAttrBreakerToolButton.setEnabled(false);
        insertAttrAllToolButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }
}