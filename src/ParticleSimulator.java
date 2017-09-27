import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

class ParticleSimulator extends JFrame implements SimulationPanelGUI {
    
    public static final String [] stringStatuses = {
            "No env existing.",
            "Simulation in progress, speed ",
            "Simulazione paused.",
            "Simulazione paused, step forward.",
            "Particle position.",
            "Particle's direction: ",
            "Particle inserted.",
            "Attractor's positioning.",
            "Attractor's intensity: ",
            "Attractor inserted.",
            "Particle selected.",
            "Attractor selected.",
            "Area selected.",
            "Area filled.",
            "Area cleared.",
            "Environment resetted.",
            "Particle deleted.",
            "Attractor deleted.",
            "Particle modified.",
            "Attractor modified.",
            "Environment dimentions modified.",
            "New environment created.",
            "Operation cancelled."};
    
    SimulationPanel sp;
    int actualStatus;
    JTextField dimensionText;
    Rectangle envDim;
    JTextField statusText = new JTextField();
    JTextField coordsText = new JTextField("(X,Y): ");
    ParticleDialog jpDialog = new ParticleDialog(this);
    AttractorDialog jaDialog = new AttractorDialog(this);
    FillDialog jfDialog = new FillDialog(this);
    DimensionDialog jdDialog = new DimensionDialog(this);
    NewEnvironmentDialog jnDialog = new NewEnvironmentDialog(this);
    AboutDialog aboutDialog = new AboutDialog(this);
    
    ParticleSimulatorMenubar particleSimulatorMenubar = new ParticleSimulatorMenubar(this);
    ParticleSimulatorToolbar particleSimulatorToolbar = new ParticleSimulatorToolbar(this);
    
    
    Container mainPanel;
    JPanel centerPanel = new JPanel();
    
    private ParticleSimulatorPopupMenu popup;
    
    public ParticleSimulator() {
        super("Particle simulator");
        setIconImage(SimulationPanel.loadImage(this, "images/icon.png"));
        
        // setStatus(NO_ENV, 0);
        setDimensionText();
        setStatusText("Create an environment.");
        mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int val = JOptionPane.showConfirmDialog(null, "All data will be lost,\ndo you want to exit?","Exiting", JOptionPane.YES_NO_OPTION);
                if(val == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        
        popup = new ParticleSimulatorPopupMenu(this);
        
        /* creo la toolbar */
        mainPanel.add(particleSimulatorToolbar, BorderLayout.NORTH);
        /* fine gestione toolbar*/
        
        //Dimension d = new Dimension(320, 600);
        centerPanel.setPreferredSize(new Dimension(320, 600));
        //centerPanel.setMinimumSize(d);
        //centerPanel.setSize(d);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        /* status bar init */
        statusText.setEditable(false);
        coordsText.setEditable(false);
        dimensionText.setEditable(false);
        GridBagConstraints constrStatus = new GridBagConstraints();
        GridBagConstraints constrCoords = new GridBagConstraints();
        GridBagConstraints constrDimension = new GridBagConstraints();
        coordsText.setPreferredSize(new Dimension(115,20));
        dimensionText.setPreferredSize(new Dimension(240,20));
        constrStatus.fill = GridBagConstraints.BOTH;
        constrCoords.fill = GridBagConstraints.VERTICAL;
        constrDimension.fill = GridBagConstraints.VERTICAL;
        
        constrStatus.weightx = 0.9;
        constrStatus.weighty = 0.1;
//	statusText.setBackground(new Color(189, 6, 6));
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.add(statusText, constrStatus);
        statusPanel.add(dimensionText, constrDimension);
        statusPanel.add(coordsText, constrCoords);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        /* end status bar init */

        setJMenuBar(particleSimulatorMenubar);

        //setResizable(false);
        pack();
        notifyEvent(INIT, DUMMY_PARAMETER, DUMMY_PARAMETER);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (screenDim.width - getWidth())/2, (screenDim.height - getHeight())/10);
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        ParticleSimulator pf = new ParticleSimulator();
    }
    
    public ParticleSimulatorPopupMenu getPopupMenu() {
        return popup;
    }
    
    void setStatusText(String str){
        statusText.setText("Current status: " + str);
    }
    
    void nextStep(){
        sp.nextStep();
    }
    
    void disableAll(){
        particleSimulatorMenubar.disableMenus();
        particleSimulatorToolbar.disableButtons();
    }
    
    public void notifyEvent(int event, int argument1, int argument2){
        if(event == COORDS_UPDATE) {
            coordsText.setText("(X,Y): (" + argument1 + "," + argument2 + ")");
            return;
        }
        if(event == OBJECT_DESELECTED) {
            particleSimulatorToolbar.deleteToolButton.setEnabled(false);
            return;
        }
        if(event == AREA_EMPTIED) {
            setStatusText(stringStatuses[AREA_EMPTIED]);
            return;
        }

        disableAll();
        //setStatusText(stringStatuses[newStatus]);
        actualStatus = event;
        
        switch(event){
            case SIMULATION_PLAY:
                particleSimulatorMenubar.itemPlayPause.setText("Pause");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.pauseIcon);
                setStatusText(stringStatuses[event] + argument1 + ".");
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                break;
            case SIMULATION_PAUSE:
                particleSimulatorMenubar.itemPlayPause.setText("Play");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.playIcon);
                
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerParticle.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphAllAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerAttractor.setEnabled(true);
                particleSimulatorMenubar.itemFill.setEnabled(true);
                particleSimulatorMenubar.itemEmptyEnvironment.setEnabled(true);
                particleSimulatorMenubar.itemDimensions.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                particleSimulatorToolbar.insertSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.emptyAllToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrAllToolButton.setEnabled(true);
                break;
            case ONE_STEP:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ONE_STEP]);
                break;
            case PARTICLE_POSITIONING:
                //notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER);
                //disableAll();
                setStatusText(stringStatuses[PARTICLE_POSITIONING]);
                particleSimulatorMenubar.itemPlayPause.setText("Play");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.playIcon);
                particleSimulatorToolbar.cancelButton.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                break;
            case PARTICLE_DIRECTING:
                setStatusText(stringStatuses[PARTICLE_DIRECTING] + argument1 + " degrees.");
                particleSimulatorMenubar.itemPlayPause.setText("Play");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.playIcon);
                                particleSimulatorToolbar.cancelButton.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                break;
            case PARTICLE_INSERTED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[PARTICLE_INSERTED]);
                break;
            case ATTRACTOR_POSITIONING:
                setStatusText(stringStatuses[ATTRACTOR_POSITIONING]);
                particleSimulatorMenubar.itemPlayPause.setText("Play");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.playIcon);
                                particleSimulatorToolbar.cancelButton.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                break;
            case ATTRACTOR_STRENGTH:
                particleSimulatorMenubar.itemPlayPause.setText("Play");
                particleSimulatorToolbar.playToolButton.setIcon(particleSimulatorToolbar.playIcon);
                setStatusText(stringStatuses[ATTRACTOR_STRENGTH] + argument1 + ".");
                                particleSimulatorToolbar.cancelButton.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                break;
            case ATTRACTOR_INSERTED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ATTRACTOR_INSERTED]);
                break;
            case PARTICLE_SELECTED:
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerParticle.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphAllAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerAttractor.setEnabled(true);
                particleSimulatorMenubar.itemFill.setEnabled(true);
                particleSimulatorMenubar.itemDelete.setEnabled(true);
                particleSimulatorMenubar.itemEmptyEnvironment.setEnabled(true);
                particleSimulatorMenubar.itemDimensions.setEnabled(true);
                particleSimulatorMenubar.itemProperties.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                particleSimulatorToolbar.insertSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.emptyAllToolButton.setEnabled(true);
                particleSimulatorToolbar.deleteToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrAllToolButton.setEnabled(true);
                break;
            case ATTRACTOR_SELECTED:
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerParticle.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphAllAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerAttractor.setEnabled(true);
                particleSimulatorMenubar.itemFill.setEnabled(true);
                particleSimulatorMenubar.itemDelete.setEnabled(true);
                particleSimulatorMenubar.itemEmptyEnvironment.setEnabled(true);
                particleSimulatorMenubar.itemDimensions.setEnabled(true);
                particleSimulatorMenubar.itemProperties.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                particleSimulatorToolbar.insertSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.emptyAllToolButton.setEnabled(true);
                particleSimulatorToolbar.deleteToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrAllToolButton.setEnabled(true);
                break;
            case AREA_SELECTED:
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerParticle.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphAllAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerAttractor.setEnabled(true);
                particleSimulatorMenubar.itemFill.setEnabled(true);
                particleSimulatorMenubar.itemDelete.setEnabled(true);
                particleSimulatorMenubar.itemEmptyEnvironment.setEnabled(true);
                particleSimulatorMenubar.itemDimensions.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                particleSimulatorToolbar.insertSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.emptyAllToolButton.setEnabled(true);
                particleSimulatorToolbar.deleteToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrAllToolButton.setEnabled(true);
                break;
            case AREA_FILLED:
                setStatusText(stringStatuses[AREA_FILLED]);
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemPlayPause.setEnabled(true);
                particleSimulatorMenubar.itemOneStep.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleParticle.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerParticle.setEnabled(true);
                particleSimulatorMenubar.itemTextualModeAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphAllAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphSingleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphDoubleAttractor.setEnabled(true);
                particleSimulatorMenubar.itemGraphBreakerAttractor.setEnabled(true);
                //particleSimulatorMenubar.itemDelete.setEnabled(true);
                particleSimulatorMenubar.itemEmptyEnvironment.setEnabled(true);
                particleSimulatorMenubar.itemDimensions.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                
                
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                particleSimulatorToolbar.playToolButton.setEnabled(true);
                particleSimulatorToolbar.nextStepToolButton.setEnabled(true);
                particleSimulatorToolbar.insertSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.emptyAllToolButton.setEnabled(true);
                particleSimulatorToolbar.deleteToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrSingleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrDoubleToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrBreakerToolButton.setEnabled(true);
                particleSimulatorToolbar.insertAttrAllToolButton.setEnabled(true);
                break;
            case ENVIRONMENT_RESETTED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ENVIRONMENT_RESETTED]);
                break;
            case OPERATION_CANCELED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[OPERATION_CANCELED]);
                break;
            case PARTICLE_DELETED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[PARTICLE_DELETED]);
                break;
            case ATTRACTOR_DELETED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ATTRACTOR_DELETED]);
                break;
            case PARTICLE_MODIFIED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[PARTICLE_MODIFIED]);
                break;
            case ATTRACTOR_MODIFIED:
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ATTRACTOR_MODIFIED]);
                break;
            case DIMENSIONS_CHANGED:
                //int state = getExtendedState();
                //pack();
                //setExtendedState(state);
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[DIMENSIONS_CHANGED]);
                break;
            case ENVIRONMENT_CREATED:
                pack();
                notifyEvent(SIMULATION_PAUSE, DUMMY_PARAMETER, DUMMY_PARAMETER);
                setStatusText(stringStatuses[ENVIRONMENT_CREATED]);
                break;
            case INIT:
                particleSimulatorMenubar.itemNew.setEnabled(true);
                particleSimulatorMenubar.itemQuit.setEnabled(true);
                particleSimulatorMenubar.itemAbout.setEnabled(true);
                particleSimulatorToolbar.newEnvButton.setEnabled(true);
                break;
        }
    }
    
    
    
    void setDimensionText(){
        if(sp == null){
            dimensionText = new JTextField("Environment dimensions:");
        } else {
            envDim = sp.getEnvironmentRectangle();
            dimensionText.setText("Environment dimensions: " + envDim.getWidth() + " X " + envDim.getHeight());
        }
    }
}
