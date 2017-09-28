import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ParticleSimulatorPopupMenu implements ActionListener {
    
    public static final int PARTICLE = 0;
    public static final int ATTRACTOR = PARTICLE + 1;
    public static final int RECTANGLE = ATTRACTOR + 1;
    public static final int ENVIRONMENT = RECTANGLE + 1;
    
    int envX, envY;
    
    private ParticleSimulator frame;
    
    private JPopupMenu popupMenuParticle;
    private JLabel labelParticle;
    private JMenuItem menuItemParticleGraphicalPositionEdit;
    private JMenuItem menuItemParticleGraphicalDirectionEdit;
    private JMenuItem menuItemParticleTextualEdit;
    private JMenuItem menuItemParticleDelete;
    
    private JPopupMenu popupMenuAttractor;
    private JLabel labelAttractor;
    private JMenuItem menuItemAttractorGraphicalPositionEdit;
    private JMenuItem menuItemAttractorGraphicalStrengthEdit;
    private JMenuItem menuItemAttractorTextualEdit;
    private JMenuItem menuItemAttractorDelete;
    
    private JPopupMenu popupMenuSelectionRectangle;
    private JLabel labelSelectionRectangle;
    private JMenuItem menuItemSelectionRectangleFill;
    private JMenuItem menuItemSelectionRectangleDelete;
    
    private JPopupMenu popupMenuEnvironment;
    private JLabel labelEnvironment = new JLabel("Environment");
    private JMenuItem menuItemEnvironmentTextualInsertParticle;
    private JMenu menuItemEnvironmentGraphicalInsertParticle;
    private JMenuItem menuItemEnvironmentGraphicalInsertSingleParticle;
    private JMenuItem menuItemEnvironmentGraphicalInsertDoubleParticle;
    private JMenuItem menuItemEnvironmentGraphicalInsertBreakerParticle;
    private JMenuItem menuItemEnvironmentTextualInsertAttractor;
    private JMenu menuItemEnvironmentGraphicalInsertAttractor;
    private JMenuItem menuItemEnvironmentGraphicalInsertSingleAttractor;
    private JMenuItem menuItemEnvironmentGraphicalInsertDoubleAttractor;
    private JMenuItem menuItemEnvironmentGraphicalInsertBreakerAttractor;
    private JMenuItem menuItemEnvironmentGraphicalInsertAllAttractor;
    
    private JMenuItem menuItemEnvironmentEdit;
    private JMenuItem menuItemEnvironmentFill;
    private JMenuItem menuItemEnvironmentDelete;
    
    private Component invoker;
    
    public ParticleSimulatorPopupMenu(ParticleSimulator frame){
        
        this.frame = frame;
        
        popupMenuParticle = new JPopupMenu();
        labelParticle = new JLabel(" Particle");
        labelParticle.setEnabled(false);
        menuItemParticleGraphicalPositionEdit = new JMenuItem("Modify graphically the position");
        menuItemParticleGraphicalDirectionEdit = new JMenuItem("Modify graphically the direction");
        menuItemParticleTextualEdit = new JMenuItem("Properties...");
        menuItemParticleDelete = new JMenuItem("Delete");
        
        popupMenuParticle.add(labelParticle);
        popupMenuParticle.addSeparator();
        popupMenuParticle.add(menuItemParticleGraphicalPositionEdit);
        popupMenuParticle.add(menuItemParticleGraphicalDirectionEdit);
        popupMenuParticle.add(menuItemParticleTextualEdit);
        popupMenuParticle.addSeparator();
        popupMenuParticle.add(menuItemParticleDelete);
        popupMenuParticle.pack();
        
        menuItemParticleGraphicalPositionEdit.addActionListener(this);
        menuItemParticleGraphicalDirectionEdit.addActionListener(this);
        menuItemParticleTextualEdit.addActionListener(this);
        menuItemParticleDelete.addActionListener(this);
        
        
        popupMenuAttractor = new JPopupMenu();
        labelAttractor = new JLabel(" Attractor");
        labelAttractor.setEnabled(false);
        menuItemAttractorGraphicalPositionEdit = new JMenuItem("Modify graphically the position");
        menuItemAttractorGraphicalStrengthEdit = new JMenuItem("Modify graphically the intensity");
        menuItemAttractorTextualEdit = new JMenuItem("Properties...");
        menuItemAttractorDelete = new JMenuItem("Delete");
        
        popupMenuAttractor.add(labelAttractor);
        popupMenuAttractor.addSeparator();
        popupMenuAttractor.add(menuItemAttractorGraphicalPositionEdit);
        popupMenuAttractor.add(menuItemAttractorGraphicalStrengthEdit);
        popupMenuAttractor.add(menuItemAttractorTextualEdit);
        popupMenuAttractor.addSeparator();
        popupMenuAttractor.add(menuItemAttractorDelete);
        popupMenuAttractor.pack();
        
        menuItemAttractorGraphicalPositionEdit.addActionListener(this);
        menuItemAttractorGraphicalStrengthEdit.addActionListener(this);
        menuItemAttractorTextualEdit.addActionListener(this);
        menuItemAttractorDelete.addActionListener(this);
        
        
        
        popupMenuSelectionRectangle = new JPopupMenu();
        labelSelectionRectangle = new JLabel(" Selection area");
        labelSelectionRectangle.setEnabled(false);
        menuItemSelectionRectangleFill = new JMenuItem("Fill...");
        menuItemSelectionRectangleDelete = new JMenuItem("Clear");
        
        popupMenuSelectionRectangle.add(labelSelectionRectangle);
        popupMenuSelectionRectangle.addSeparator();
        popupMenuSelectionRectangle.add(menuItemSelectionRectangleFill);
                popupMenuSelectionRectangle.addSeparator();
        popupMenuSelectionRectangle.add(menuItemSelectionRectangleDelete);
        popupMenuSelectionRectangle.pack();
        
        menuItemSelectionRectangleFill.addActionListener(this);
        menuItemSelectionRectangleDelete.addActionListener(this);
        
        
        popupMenuEnvironment = new JPopupMenu();
        labelEnvironment = new JLabel(" Environment");
        labelEnvironment.setEnabled(false);
        
        JMenu menuInsertParticle = new JMenu("Particle Insertion");
        JMenu menuInsertAttractor = new JMenu("Attractor Insertion");
        menuItemEnvironmentTextualInsertParticle = new JMenuItem("Insertion dialog...");
        menuItemEnvironmentGraphicalInsertParticle = new JMenu("Graphically");
        menuItemEnvironmentGraphicalInsertSingleParticle = new JMenuItem("Single Particle");
        menuItemEnvironmentGraphicalInsertDoubleParticle = new JMenuItem("Double Particle");
        menuItemEnvironmentGraphicalInsertBreakerParticle = new JMenuItem("Disruptive Particle");
        menuItemEnvironmentGraphicalInsertParticle.add(menuItemEnvironmentGraphicalInsertSingleParticle);
        menuItemEnvironmentGraphicalInsertParticle.add(menuItemEnvironmentGraphicalInsertDoubleParticle);
        menuItemEnvironmentGraphicalInsertParticle.add(menuItemEnvironmentGraphicalInsertBreakerParticle);
        menuItemEnvironmentTextualInsertAttractor = new JMenuItem("Insertion dialog...");
        menuItemEnvironmentGraphicalInsertAttractor = new JMenu("Graphically");
        menuItemEnvironmentGraphicalInsertSingleAttractor = new JMenuItem("Single Particle Attractor");
        menuItemEnvironmentGraphicalInsertDoubleAttractor = new JMenuItem("Double Particle Attractor");
        menuItemEnvironmentGraphicalInsertBreakerAttractor = new JMenuItem("Disruptive Particle Attractor");
        menuItemEnvironmentGraphicalInsertAllAttractor = new JMenuItem("Any particle Attractor");
        
        menuInsertParticle.add(menuItemEnvironmentTextualInsertParticle);
        menuInsertParticle.add(menuItemEnvironmentGraphicalInsertParticle);
        menuInsertAttractor.add(menuItemEnvironmentTextualInsertAttractor);
        menuInsertAttractor.add(menuItemEnvironmentGraphicalInsertAttractor);
        
        menuItemEnvironmentGraphicalInsertAttractor.add(menuItemEnvironmentGraphicalInsertSingleAttractor);
        menuItemEnvironmentGraphicalInsertAttractor.add(menuItemEnvironmentGraphicalInsertDoubleAttractor);
        menuItemEnvironmentGraphicalInsertAttractor.add(menuItemEnvironmentGraphicalInsertBreakerAttractor);
        menuItemEnvironmentGraphicalInsertAttractor.add(menuItemEnvironmentGraphicalInsertAllAttractor);
        menuItemEnvironmentEdit = new JMenuItem("Resize...");
        menuItemEnvironmentFill = new JMenuItem("Fill...");
        menuItemEnvironmentDelete = new JMenuItem("Reset");
        
        popupMenuEnvironment.add(labelEnvironment);
        popupMenuEnvironment.addSeparator();
        popupMenuEnvironment.add(menuInsertParticle);
        popupMenuEnvironment.add(menuInsertAttractor);
        popupMenuEnvironment.add(menuItemEnvironmentFill);
        popupMenuEnvironment.addSeparator();
        popupMenuEnvironment.add(menuItemEnvironmentEdit);        
        popupMenuEnvironment.add(menuItemEnvironmentDelete);
        popupMenuEnvironment.pack();
        
        menuItemEnvironmentTextualInsertParticle.addActionListener(this);
        menuItemEnvironmentGraphicalInsertSingleParticle.addActionListener(this);
        menuItemEnvironmentGraphicalInsertDoubleParticle.addActionListener(this);
        menuItemEnvironmentGraphicalInsertBreakerParticle.addActionListener(this);
        menuItemEnvironmentTextualInsertAttractor.addActionListener(this);
        menuItemEnvironmentGraphicalInsertSingleAttractor.addActionListener(this);
        menuItemEnvironmentGraphicalInsertDoubleAttractor.addActionListener(this);
        menuItemEnvironmentGraphicalInsertBreakerAttractor.addActionListener(this);
        menuItemEnvironmentGraphicalInsertAllAttractor.addActionListener(this);
        menuItemEnvironmentEdit.addActionListener(this);
        menuItemEnvironmentFill.addActionListener(this);
        menuItemEnvironmentDelete.addActionListener(this);
        
    }
    
    public void actionPerformed(ActionEvent e){
        JMenuItem menu = (JMenuItem) e.getSource();
        
        if(menu == menuItemParticleGraphicalPositionEdit){
            try {
                frame.sp.modifySelected(SimulationPanel.MODIFY_POSITION);
            } catch (SimulationPanelWrongTypeException e1) {}
        } else if(menu == menuItemParticleGraphicalDirectionEdit){
            try {
                frame.sp.modifySelected(SimulationPanel.MODIFY_DIRECTION_INTENSITY);
            } catch (SimulationPanelWrongTypeException e2) {}
        } else if(menu == menuItemParticleTextualEdit){
            frame.jpDialog.setVisible(true, ParticleDialog.EDIT);
        } else if(menu == menuItemParticleDelete){
            frame.sp.removeSelected();
        }
        
        else if(menu == menuItemAttractorGraphicalPositionEdit){
            try {
                frame.sp.modifySelected(SimulationPanel.MODIFY_POSITION);
            } catch (SimulationPanelWrongTypeException e3) {}
        } else if(menu == menuItemAttractorGraphicalStrengthEdit){
            try {
                frame.sp.modifySelected(SimulationPanel.MODIFY_DIRECTION_INTENSITY);
            } catch (SimulationPanelWrongTypeException e4) {}
        } else if(menu == menuItemAttractorTextualEdit){
            frame.jaDialog.setVisible(true, AttractorDialog.EDIT);
        } else if(menu == menuItemAttractorDelete){
            frame.sp.removeSelected();
        }
        
        else if(menu == menuItemSelectionRectangleFill){
            frame.jfDialog.setVisible(true, FillDialog.AREA);
        } else if(menu == menuItemSelectionRectangleDelete){
            frame.sp.removeSelected();
        }
        
        
        else if(menu == menuItemEnvironmentTextualInsertParticle){
            frame.jpDialog.setVisible(true, envX, envY);
        } else if(menu == menuItemEnvironmentGraphicalInsertSingleParticle){
            try {
                frame.sp.enterInsertMode(Particle.SINGLE);
            } catch(SimulationPanelWrongTypeException e1){};
        } else if(menu == menuItemEnvironmentGraphicalInsertDoubleParticle){
            try {
                frame.sp.enterInsertMode(Particle.DOUBLE);
            } catch(SimulationPanelWrongTypeException e2){};
        } else if(menu == menuItemEnvironmentGraphicalInsertBreakerParticle){
            try {
                frame.sp.enterInsertMode(Particle.BREAKER);
            } catch(SimulationPanelWrongTypeException e3){};
        } else if(menu == menuItemEnvironmentTextualInsertAttractor){
            frame.jaDialog.setVisible(true, envX, envY);
        } else if(menu == menuItemEnvironmentGraphicalInsertSingleAttractor){
            try {
                frame.sp.enterInsertMode(Attractor.SINGLE);
            } catch(SimulationPanelWrongTypeException e4){};
        } else if(menu == menuItemEnvironmentGraphicalInsertDoubleAttractor){
            try {
                frame.sp.enterInsertMode(Attractor.DOUBLE);
            } catch(SimulationPanelWrongTypeException e5){};
        } else if(menu == menuItemEnvironmentGraphicalInsertBreakerAttractor){
            try {
                frame.sp.enterInsertMode(Attractor.BREAKER);
            } catch(SimulationPanelWrongTypeException e6){};
        } else if(menu == menuItemEnvironmentGraphicalInsertAllAttractor){
            try {
                frame.sp.enterInsertMode(Attractor.ALL);
            } catch(SimulationPanelWrongTypeException e7){};
        } else if(menu == menuItemEnvironmentEdit){
            frame.jdDialog.setVisible(true);
        } else if(menu == menuItemEnvironmentFill){
            frame.jfDialog.setVisible(true);
        } else if(menu == menuItemEnvironmentDelete){
            frame.sp.resetEnvironment();
        }
        
    }
    
    public void show(int type, int x, int y, int envX, int envY) {
        this.envX = envX;
        this.envY = envY;
        
        switch (type) {
            case PARTICLE:
                popupMenuParticle.show(invoker, x, y);
                break;
            case ATTRACTOR:
                popupMenuAttractor.show(invoker, x, y);
                break;
            case RECTANGLE:
                popupMenuSelectionRectangle.show(invoker, x, y);
                break;
            case ENVIRONMENT:
                popupMenuEnvironment.show(invoker, x, y);
                break;
        }
    }
    
    public void hide(){
        popupMenuParticle.setVisible(false);
        popupMenuAttractor.setVisible(false);
        popupMenuSelectionRectangle.setVisible(false);
        popupMenuEnvironment.setVisible(false);
    }
    
    void setInvoker(Component c) {
        invoker = c;
    }
    
}