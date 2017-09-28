import java.awt.event.*;
import javax.swing.*;

class FillDialogButtonListener implements ActionListener {

    private static final String INVALID_PERC_SINGLE = "Invalid percentage for single particles!";
    private static final String INVALID_PERC_DOUBLE = "Invalid percentage for double particles!";
    private static final String INVALID_PERC_DISR = "Invalid percentage for disruptive particles!";

    private static final String INVALID_DATA = "Invalid data";

    FillDialog fDialog;
    
    public FillDialogButtonListener(FillDialog fDialog){
        this.fDialog = fDialog;
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(button == fDialog.fillButton){
            
            if(fDialog.jpf.sp == null){
                resetAll();
                JOptionPane.showMessageDialog(fDialog, "Missing environment!", "Missing environment", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                fDialog.singlePerc = Integer.valueOf(fDialog.singleField.getText()).intValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_SINGLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_SINGLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(fDialog.singlePerc > 100 || fDialog.singlePerc < 0 || fDialog.singlePerc != fDialog.singleSlider.getValue()){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_SINGLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                fDialog.doublePerc = Integer.valueOf(fDialog.doubleField.getText()).intValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DOUBLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DOUBLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(fDialog.doublePerc > 100 || fDialog.doublePerc < 0 || fDialog.doublePerc != fDialog.doubleSlider.getValue()){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DOUBLE, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                fDialog.breakerPerc = Integer.valueOf(fDialog.breakerField.getText()).intValue();
            } catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DISR, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            } catch(IllegalStateException exc2){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DISR, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(fDialog.breakerPerc > 100 || fDialog.breakerPerc < 0 || fDialog.breakerPerc != fDialog.breakerSlider.getValue()){
                JOptionPane.showMessageDialog(fDialog, INVALID_PERC_DISR, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if((fDialog.singlePerc + fDialog.doublePerc + fDialog.breakerPerc) != 100){
                JOptionPane.showMessageDialog(fDialog, "Sum of percentage should be 100, while now is " + (fDialog.singlePerc + fDialog.doublePerc + fDialog.breakerPerc) + "!", INVALID_DATA,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // richiama metodo di pannello per inserire un attrattore
            if(fDialog.allRadio.isSelected()){
                try {
                    fDialog.jpf.sp.fillSelectionRectangle(SimulationPanel.FILL_ENVIRONMENT, fDialog.singlePerc, fDialog.doublePerc, fDialog.breakerPerc);
                } catch(SimulationPanelInvalidParameterException e1){
                    JOptionPane.showMessageDialog(fDialog, "Check that the sum of percentage is 100!", "Filling failed", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(SimulationPanelWrongTypeException e2){
                } catch(SimulationPanelOutOfBoundsException e3){
                    JOptionPane.showMessageDialog(fDialog, "Filling target area should empty!", "Filling failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
            } else {
                // SimulationPanel method should be used!
                try {
                    fDialog.jpf.sp.fillSelectionRectangle(SimulationPanel.FILL_RECTANGLE, fDialog.singlePerc, fDialog.doublePerc, fDialog.breakerPerc);
                } catch(SimulationPanelInvalidParameterException e4) {
                    JOptionPane.showMessageDialog(fDialog, "Check that the sum of percentage is 100!", "Filling failed", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch(SimulationPanelWrongTypeException e5) {
                } catch(SimulationPanelOutOfBoundsException e6) {
                    JOptionPane.showMessageDialog(fDialog, "Filling target area should empty!", "Filling failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
        }
        resetAll();
    }
    
    void resetAll(){
        /*
        fDialog.singleField.setText("");
        fDialog.doubleField.setText("");
        fDialog.breakerField.setText("");*/
        
        //fDialog.reset = true; // disable the consistency property of some components to reset them
        /*setHandling(true);
        fDialog.singleField.setText("34");
        fDialog.singleSlider.setValue(34);
        fDialog.doubleField.setText("33");
        fDialog.doubleSlider.setValue(33);
        fDialog.breakerField.setText("33");
        fDialog.breakerSlider.setValue(33);
        setHandling(false);*/
        //fDialog.reset = false;
        
        fDialog.singlePerc = 0;
        fDialog.doublePerc = 0;
        fDialog.breakerPerc = 0;
        
        fDialog.allRadio.setSelected(true);
        fDialog.setVisible(false);
    }
}
