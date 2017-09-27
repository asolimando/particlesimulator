import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class NewEnvironmentDialogButtonListener implements ActionListener {

  private static final String INVALID_DATA = "Invalid data";
  private static final String INVALID_WIDTH = "Invalid width!";
  private static final String INVALID_HEIGHT = "Invalid height!";

  NewEnvironmentDialog nDialog;
  
  public NewEnvironmentDialogButtonListener(NewEnvironmentDialog nDialog){
    this.nDialog = nDialog;
  }
  
  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    
    if(button == nDialog.applyButton){
      
      try {
        nDialog.width = Double.valueOf(nDialog.widthField.getText()).doubleValue();
      } catch(NumberFormatException exc){
        JOptionPane.showMessageDialog(nDialog, INVALID_WIDTH, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      } catch(IllegalStateException exc2){
        JOptionPane.showMessageDialog(nDialog, INVALID_WIDTH, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      }
      if(nDialog.width <= 0){
        JOptionPane.showMessageDialog(nDialog, INVALID_WIDTH + "\nIt must be > 0!", INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      }
       
      try {
        nDialog.height = Double.valueOf(nDialog.heightField.getText()).doubleValue();
      } catch(NumberFormatException exc){
        JOptionPane.showMessageDialog(nDialog, INVALID_HEIGHT, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      } catch(IllegalStateException exc2){
        JOptionPane.showMessageDialog(nDialog, INVALID_HEIGHT, INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      }
      if(nDialog.height <= 0){
        JOptionPane.showMessageDialog(nDialog, INVALID_HEIGHT + "\nIt must be > 0!", INVALID_DATA,JOptionPane.ERROR_MESSAGE);
        return;
      }
      
      if(nDialog.frame.sp != null) {
        int val = JOptionPane.showConfirmDialog(null,"The old environment will be lost,\ndo you want to proceed?","Warning", JOptionPane.YES_NO_OPTION);
        if(val == JOptionPane.YES_OPTION){ 
          nDialog.frame.sp.resetEnvironment();
          try {
            nDialog.frame.sp.resizeEnvironment((int) nDialog.width, (int) nDialog.height, false);
          }
          catch(SimulationPanelOutOfBoundsException e1) {}
          catch(SimulationPanelInvalidParameterException e2) {}
          //nDialog.frame.pack();
          nDialog.frame.jdDialog = new DimensionDialog(nDialog.frame);
        }
      }
      else {
        try {
          nDialog.frame.sp = new SimulationPanel((int) nDialog.width,(int) nDialog.height, nDialog.frame.particleSimulatorToolbar.speedToolSlider.getValue(), nDialog.frame);
            nDialog.frame.sp.setShowDirection(nDialog.frame.particleSimulatorMenubar.itemShowSpeedDirection.isSelected());
            nDialog.frame.sp.setShowIntensity(nDialog.frame.particleSimulatorMenubar.itemShowStrength.isSelected());
            nDialog.frame.sp.setShowPosition(nDialog.frame.particleSimulatorMenubar.itemShowCoords.isSelected());
        }
        catch(SimulationPanelInvalidParameterException e3) {
          JOptionPane.showMessageDialog(nDialog, "Check the dimensions of the environment to be created!", "Environment creation failed", JOptionPane.ERROR_MESSAGE);
          return;
        }
        nDialog.frame.particleSimulatorMenubar.itemShowSpeedDirection.setState(nDialog.frame.sp.getShowDirection());
        nDialog.frame.particleSimulatorMenubar.itemShowStrength.setState(nDialog.frame.sp.getShowIntensity());
        nDialog.frame.particleSimulatorMenubar.itemShowCoords.setState(nDialog.frame.sp.getShowPosition());
        nDialog.frame.mainPanel.remove(nDialog.frame.centerPanel);
        nDialog.frame.mainPanel.validate();
        nDialog.frame.mainPanel.add(nDialog.frame.sp, BorderLayout.CENTER);
        nDialog.frame.pack();
        nDialog.frame.jdDialog = new DimensionDialog(nDialog.frame);
      }
      nDialog.frame.notifyEvent(SimulationPanelGUI.ENVIRONMENT_CREATED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
      nDialog.frame.setDimensionText();
    }
    
    nDialog.widthField.setText("");
    nDialog.heightField.setText("");

    nDialog.width = 0;
    nDialog.height = 0;
    
    nDialog.setVisible(false);
    nDialog.widthField.requestFocusInWindow();
  }
}
