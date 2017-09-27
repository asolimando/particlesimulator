import java.awt.event.*;
import javax.swing.*;

class AttractorDialogButtonListener implements ActionListener {
    
    AttractorDialog aDialog;
    
    public AttractorDialogButtonListener(AttractorDialog aDialog){
        this.aDialog = aDialog;
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if(button == aDialog.insertButton){
            
            if(aDialog.jpf.sp == null){
                resetAll();
                JOptionPane.showMessageDialog(aDialog, "No envirnment available!", "Missing environment", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(aDialog.isWall == false){
                try {
                    aDialog.x = Double.valueOf(aDialog.xField.getText()).doubleValue();
                } catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(aDialog, "X coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(IllegalStateException exc2){
                    JOptionPane.showMessageDialog(aDialog, "X coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(aDialog.x < 0){
                    JOptionPane.showMessageDialog(aDialog, "X coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                try {
                    aDialog.y = Double.valueOf(aDialog.yField.getText()).doubleValue();
                } catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(aDialog, "Y coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(IllegalStateException exc2){
                    JOptionPane.showMessageDialog(aDialog, "Y coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(aDialog.y < 0){
                    JOptionPane.showMessageDialog(aDialog, "Y coordinate is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            try {
                aDialog.strength = Integer.valueOf(aDialog.strengthField.getText()).intValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(aDialog, "Intensity is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(aDialog, "Intensity is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(aDialog.strength > 100 || aDialog.strength < 1 || aDialog.strength != aDialog.strengthSlider.getValue()){
                JOptionPane.showMessageDialog(aDialog, "Intensity is not valid!", "Invalid data",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // call the method from the panel to insert an attractor
            if(aDialog.actualMode == AttractorDialog.INSERT){
                if(aDialog.type == -1){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException e1){
                            e1.printStackTrace();
                        } catch(SimulationPanelOutOfBoundsException e2){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Failed insertion",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc) {
                                exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc) {
                                exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                                exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc) {
                                exc.printStackTrace();
                            }
                        }
                    }
                } else if(aDialog.type == Particle.SINGLE){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException exc){ 
                        exc.printStackTrace();
                        } 
                        catch(SimulationPanelOutOfBoundsException e8){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                } else if(aDialog.type == Particle.DOUBLE){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException exc) {
                        exc.printStackTrace();
                        } catch(SimulationPanelOutOfBoundsException exc) {
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try{
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                } else {
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException e19){} catch(SimulationPanelOutOfBoundsException e20){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                // method for changing the values of an existing attractor
//        Attractor src = (Attractor) aDialog.jpf.sp.getSelected();
                
                if(aDialog.type == -1){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException exc){
                        exc.printStackTrace();
                        } catch(SimulationPanelOutOfBoundsException ex2){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.ALL, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                } else if(aDialog.type == Particle.SINGLE){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            } catch(SimulationPanelOutOfBoundsException ex8){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.SINGLE, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                } else if(aDialog.type == Particle.DOUBLE){
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException ex13){} catch(SimulationPanelOutOfBoundsException ex14){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.DOUBLE, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                } else {
                    if(aDialog.isWall == false){
                        PairOfDouble point = new PairOfDouble(aDialog.x, aDialog.y);
                        try {
                            aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, point);
                        } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            } catch(SimulationPanelOutOfBoundsException ex20){
                            JOptionPane.showMessageDialog(aDialog, "The attractor would be placed outside the environment!", "Insertion failed",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        if(aDialog.wall == Environment.BOTTOM){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_UP);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.LEFT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_LEFT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else if(aDialog.wall == Environment.RIGHT){
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_RIGHT);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        } else {
                            try {
                                aDialog.jpf.sp.insertAttractor(Attractor.BREAKER, aDialog.strength, Attractor.WALL_DOWN);
                            } catch(SimulationPanelWrongTypeException exc){
                            exc.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        resetAll();
    }
    
    void resetAll() {
        aDialog.insertButton.setText("Insert");
        aDialog.actualMode = AttractorDialog.INSERT;
        aDialog.xField.setText("");
        aDialog.yField.setText("");
        aDialog.strengthSlider.setValue(50);
        aDialog.strengthField.setText(aDialog.strengthSlider.getValue()+"");
        
        aDialog.allRadio.setSelected(true);
        aDialog.x = 0;
        aDialog.y = 0;
        aDialog.type = -1;
        aDialog.strength = 0;
        aDialog.wallCombo.setSelectedItem(aDialog.wallStrings[0]);
        
        aDialog.wall = Environment.BOTTOM;
        aDialog.pointRadio.setSelected(true);
        aDialog.wallLabel.setEnabled(false);
        aDialog.wallCombo.setEnabled(false);
        aDialog.coordsLabel.setEnabled(true);
        aDialog.xLabel.setEnabled(true);
        aDialog.yLabel.setEnabled(true);
        aDialog.coordsLabel.setEnabled(true);
        aDialog.xField.setEnabled(true);
        aDialog.yField.setEnabled(true);
        aDialog.isWall = false;
        
        aDialog.setVisible(false);
    }
}
