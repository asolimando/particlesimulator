import javax.swing.*;

class ParticleSimulatorMenubar extends JMenuBar {
    
    ParticleSimulator frame;
    
    JMenu menuSession = new JMenu("Environment");
    JMenuItem itemNew = new JMenuItem("New...");
    JMenuItem itemPlayPause = new JMenuItem("Play");
    JMenuItem itemOneStep = new JMenuItem("Single step");
    JMenuItem itemQuit = new JMenuItem("Exit");
    
    JMenu menuModify = new JMenu("Modify");
    JMenu menuInsertParticle = new JMenu("Insert particle");
    JMenuItem itemTextualModeParticle = new JMenuItem("Insertion dialog...");
    JMenu menuGraphicalModeParticle = new JMenu("Graphically");
    JMenuItem itemGraphSingleParticle = new JMenuItem("Single particle");
    JMenuItem itemGraphDoubleParticle = new JMenuItem("Double particle");
    JMenuItem itemGraphBreakerParticle = new JMenuItem("Disruptive particle");
    JMenu menuInsertAttractor = new JMenu("Attractor insertion");
    JMenuItem itemTextualModeAttractor = new JMenuItem("Insertion dialog...");
    JMenu menuGraphicalModeAttractor = new JMenu("Graphically");
    JMenuItem itemGraphAllAttractor = new JMenuItem("Any kind");
    JMenuItem itemGraphSingleAttractor = new JMenuItem("Single particle");
    JMenuItem itemGraphDoubleAttractor = new JMenuItem("Double particle");
    JMenuItem itemGraphBreakerAttractor = new JMenuItem("Disruptive particle");
    JMenuItem itemFill = new JMenuItem("Fill...");
    JMenuItem itemDelete = new JMenuItem("Delete");
    JMenuItem itemEmptyEnvironment = new JMenuItem("Reset");
    JMenuItem itemDimensions = new JMenuItem("Resize...");
    JMenuItem itemProperties = new JMenuItem("Properties...");

    JMenu menuView = new JMenu("Visualize");
    JCheckBoxMenuItem itemShowCoords = new JCheckBoxMenuItem("Particles' coordinates");
    JCheckBoxMenuItem itemShowSpeedDirection = new JCheckBoxMenuItem("Speed vectors");
    JCheckBoxMenuItem itemShowStrength = new JCheckBoxMenuItem("Attractors' intensity");
    
    JMenu menuHelp = new JMenu("Help");
    JMenuItem itemAbout = new JMenuItem("About...");
    
    MenuCheckBoxListener menuCheckBoxListener = new MenuCheckBoxListener(this);
    
    
    public ParticleSimulatorMenubar(ParticleSimulator frame){
        
        this.frame = frame;
        
        /* start menubar init */
        add(menuSession);
        menuSession.add(itemNew);
        menuSession.add(itemDimensions);
        menuSession.add(itemEmptyEnvironment);
        menuSession.addSeparator();
        menuSession.add(itemPlayPause);
        menuSession.add(itemOneStep);
        menuSession.addSeparator();
        menuSession.add(itemQuit);
        
        add(menuModify);
        menuModify.add(menuInsertParticle);
        menuInsertParticle.add(itemTextualModeParticle);
        menuInsertParticle.add(menuGraphicalModeParticle);
        menuGraphicalModeParticle.add(itemGraphSingleParticle);
        menuGraphicalModeParticle.add(itemGraphDoubleParticle);
        menuGraphicalModeParticle.add(itemGraphBreakerParticle);
        
        menuModify.add(menuInsertAttractor);
        menuInsertAttractor.add(itemTextualModeAttractor);
        menuInsertAttractor.add(menuGraphicalModeAttractor);
        menuGraphicalModeAttractor.add(itemGraphAllAttractor);
        menuGraphicalModeAttractor.add(itemGraphSingleAttractor);
        menuGraphicalModeAttractor.add(itemGraphDoubleAttractor);
        menuGraphicalModeAttractor.add(itemGraphBreakerAttractor);
        menuModify.add(itemFill);
        menuModify.addSeparator();
        menuModify.add(itemDelete);
        
        menuModify.addSeparator();
        menuModify.add(itemProperties);
        
        add(menuView);
        menuView.add(itemShowCoords);
        itemShowCoords.addActionListener(menuCheckBoxListener);
        menuView.add(itemShowSpeedDirection);
        itemShowSpeedDirection.addActionListener(menuCheckBoxListener);
        menuView.add(itemShowStrength);
        itemShowStrength.addActionListener(menuCheckBoxListener);
        
        add(menuHelp);
        menuHelp.add(itemAbout);
        
        MenuItemListener menuItemListener = new MenuItemListener(this);
        itemQuit.addActionListener(menuItemListener);
        itemNew.addActionListener(menuItemListener);
        itemPlayPause.addActionListener(menuItemListener);
        itemOneStep.addActionListener(menuItemListener);
        itemFill.addActionListener(menuItemListener); // listener
        itemTextualModeAttractor.addActionListener(menuItemListener); // listener
        itemTextualModeParticle.addActionListener(menuItemListener); // listener
        itemGraphSingleParticle.addActionListener(menuItemListener);
        itemGraphDoubleParticle.addActionListener(menuItemListener);
        itemGraphBreakerParticle.addActionListener(menuItemListener);
        itemGraphAllAttractor.addActionListener(menuItemListener);
        itemGraphSingleAttractor.addActionListener(menuItemListener);
        itemGraphDoubleAttractor.addActionListener(menuItemListener);
        itemGraphBreakerAttractor.addActionListener(menuItemListener);
        itemDelete.addActionListener(menuItemListener);
        itemEmptyEnvironment.addActionListener(menuItemListener);
        itemDimensions.addActionListener(menuItemListener);
        itemProperties.addActionListener(menuItemListener);
        itemAbout.addActionListener(menuItemListener);
        
        
        /* end menubar init */
    }
    
    
    void disableMenus(){
        
        itemNew.setEnabled(false);
        itemOneStep.setEnabled(false);
        itemPlayPause.setEnabled(false);
        itemQuit.setEnabled(false);
        
        itemTextualModeParticle.setEnabled(false);
        itemGraphSingleParticle.setEnabled(false);
        itemGraphDoubleParticle.setEnabled(false);
        itemGraphBreakerParticle.setEnabled(false);
        itemTextualModeAttractor.setEnabled(false);
        itemGraphAllAttractor.setEnabled(false);
        itemGraphSingleAttractor.setEnabled(false);
        itemGraphDoubleAttractor.setEnabled(false);
        itemGraphBreakerAttractor.setEnabled(false);
        itemFill.setEnabled(false);
        itemDelete.setEnabled(false);
        itemEmptyEnvironment.setEnabled(false);
        itemDimensions.setEnabled(false);
        itemProperties.setEnabled(false);
        
        itemAbout.setEnabled(false);
    }
}