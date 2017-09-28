import java.awt.event.*;
import javax.swing.*;

class MenuItemListener implements ActionListener {
    ParticleSimulatorMenubar menubar;
    
    public MenuItemListener(ParticleSimulatorMenubar menubar){
        this.menubar = menubar;
    }
    
    public void actionPerformed(ActionEvent e){
        JMenuItem src = (JMenuItem) e.getSource();
        
        if(src == menubar.itemNew){
            menubar.frame.jnDialog.setVisible(true);
        }
        
        else if(src == menubar.itemTextualModeParticle){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.jpDialog.setVisible(true, ParticleDialog.INSERT);
        }
        
        else if(src == menubar.itemTextualModeAttractor){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.jaDialog.setVisible(true, AttractorDialog.INSERT);
        }
        
        else if(src == menubar.itemFill){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.jfDialog.setVisible(true, FillDialog.ALL);
        }
        
        else if(src == menubar.itemPlayPause){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.sp.playPauseSimulation();
        }
        
        else if(src == menubar.itemOneStep){
            menubar.frame.nextStep();
        }
        
        else if(src == menubar.itemGraphSingleParticle){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Particle.SINGLE);
            } catch(SimulationPanelWrongTypeException e1){};
            
        }
        
        else if(src == menubar.itemGraphDoubleParticle){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Particle.DOUBLE);
            } catch(SimulationPanelWrongTypeException e2){};
            
        }
        
        else if(src == menubar.itemGraphBreakerParticle){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Particle.BREAKER);
            } catch(SimulationPanelWrongTypeException e3){};
            
        }
        
        else if(src == menubar.itemGraphAllAttractor){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Attractor.ALL);
            } catch(SimulationPanelWrongTypeException e4){};
            
        }
        
        else if(src == menubar.itemGraphSingleAttractor){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Attractor.SINGLE);
            } catch(SimulationPanelWrongTypeException e5){};
            
        }
        
        else if(src == menubar.itemGraphDoubleAttractor){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Attractor.DOUBLE);
            } catch(SimulationPanelWrongTypeException e6){};
            
        }
        
        else if(src == menubar.itemGraphBreakerAttractor){
            if(checkEnvironment() == false){
                return;
            }
            try {
                menubar.frame.sp.enterInsertMode(Attractor.BREAKER);
            } catch(SimulationPanelWrongTypeException e7){};
            
        }
        
        else if(src == menubar.itemDelete){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.sp.removeSelected();
        }
        
        else if(src == menubar.itemEmptyEnvironment){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.sp.resetEnvironment();
            
        }
        if((src == menubar.itemQuit)) {
            menubar.frame.dispatchEvent(new WindowEvent(menubar.frame, WindowEvent.WINDOW_CLOSING));
        }
        
        else if(src == menubar.itemDimensions){
            if(checkEnvironment() == false){
                return;
            }
            menubar.frame.jdDialog.setVisible(true);
        }
                
        else if(src == menubar.itemAbout) {
            menubar.frame.aboutDialog.setVisible(true);
        }
        
        else if(src == menubar.itemProperties){
            if(checkEnvironment() == false){
                return;
            }
            Object selectedItem = menubar.frame.sp.getSelected();
            
            if(selectedItem instanceof Attractor){
                menubar.frame.jaDialog.setVisible(true, AttractorDialog.EDIT);
            } else if(selectedItem instanceof Particle){
                menubar.frame.jpDialog.setVisible(true, ParticleDialog.EDIT);
            }
            
            
            else {
                return;
            }
        }
    }
    
    boolean checkEnvironment(){
        if(menubar.frame.sp == null){
            JOptionPane.showMessageDialog(menubar.frame, "Missing environment!", "Missing environment", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
}
