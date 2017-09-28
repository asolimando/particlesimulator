import java.awt.event.*;
import javax.swing.*;

class ParticleDialogButtonListener implements ActionListener {

    private final static String INVALID_X = "X component is not valid!";
    private final static String INVALID_Y = "X component is not valid!";
    private final static String INVALID_DATA = "Invalid data";
    private final static String INVALID_DIRECTION = "Direction is not valid!";
    private final static String INSERTION_FAILED = "Insertion failed";
    private final static String PARTICLES_OVERLAP = "Two particles would overlap!";
    private final static String PARTICLE_OUTSIDE = "The particle would fall outside the environment!";

    ParticleDialog pDialog;
    
    public ParticleDialogButtonListener(ParticleDialog pDialog){
        this.pDialog = pDialog;
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if(button == pDialog.insertButton){
            
            if(pDialog.jpf.sp == null){
                resetAll();
                JOptionPane.showMessageDialog(pDialog, "Missing environment!", "Missing environment", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                pDialog.x = Double.valueOf(pDialog.xField.getText()).doubleValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(pDialog, INVALID_X, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(pDialog, INVALID_X, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(pDialog.x < 0){
                JOptionPane.showMessageDialog(pDialog, INVALID_X, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                pDialog.y = Double.valueOf(pDialog.yField.getText()).doubleValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(pDialog, INVALID_Y, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(pDialog, INVALID_Y, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(pDialog.y < 0){
                JOptionPane.showMessageDialog(pDialog, INVALID_Y, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                pDialog.angle = Integer.valueOf(pDialog.angleField.getText()).intValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(pDialog, INVALID_DIRECTION, INVALID_DATA, JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(pDialog, INVALID_DIRECTION, INVALID_DATA, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(pDialog.angle < 0 || pDialog.angle > 359){
                JOptionPane.showMessageDialog(pDialog, INVALID_DIRECTION, INVALID_DATA, JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            PairOfDouble dirVect = new PairOfDouble(0, 0);
            pDialog.jpf.sp.angleToVector(Math.toRadians(pDialog.angle), dirVect);
            
            if(pDialog.mode == ParticleDialog.INSERT) {
                
                // richiama metodo di pannello per inserire una particella
                try {
                    pDialog.jpf.sp.insertParticle(pDialog.type, new PairOfDouble(pDialog.x, pDialog.y), dirVect);
                } catch(SimulationPanelOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(pDialog, PARTICLE_OUTSIDE, INSERTION_FAILED, JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(SimulationPanelBadInsertionPointException ex){
                    JOptionPane.showMessageDialog(pDialog, PARTICLES_OVERLAP, INSERTION_FAILED, JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(SimulationPanelWrongTypeException ex){
                    ex.printStackTrace();
                }
                
                pDialog.jpf.sp.repaint();
            } else {
                // da inserire il metodo per modificare una particella già esistente
                Particle src = (Particle) pDialog.jpf.sp.getSelected();
                try {
                    pDialog.jpf.sp.modifyParticle(src, src.getType(), new PairOfDouble(pDialog.x, pDialog.y), dirVect);
                } catch (SimulationPanelWrongTypeException ex) {
                    ex.printStackTrace();
                } catch (SimulationPanelOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(pDialog, PARTICLE_OUTSIDE, INSERTION_FAILED, JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (SimulationPanelBadInsertionPointException ex) {
                    JOptionPane.showMessageDialog(pDialog, PARTICLES_OVERLAP, INSERTION_FAILED, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        resetAll();
    }
    
    void resetAll(){
        pDialog.insertButton.setText("Insert");
        pDialog.mode = ParticleDialog.INSERT;
        pDialog.particleCombo.setEnabled(true);
        pDialog.xField.setText("");
        pDialog.yField.setText("");
        pDialog.angleField.setText("0");
        pDialog.angleSlider.setValue(0);
        pDialog.particleCombo.setSelectedItem(pDialog.particleStrings[0]);
        
        pDialog.x = 0;
        pDialog.y = 0;
        pDialog.type = Particle.SINGLE;
        pDialog.angle = -1;
        pDialog.setVisible(false);
    }
}
