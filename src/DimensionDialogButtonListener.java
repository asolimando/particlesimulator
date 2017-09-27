import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class DimensionDialogButtonListener implements ActionListener {
  
  DimensionDialog dDialog;
  
  public DimensionDialogButtonListener(DimensionDialog dDialog){
    this.dDialog = dDialog;
  }
  
  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    
     if(button == dDialog.applyButton){
      
      if(dDialog.frame.sp == null){
        resetAll();
        JOptionPane.showMessageDialog(dDialog, "Non e' presente alcun ambiente!", "Ambiente mancante", JOptionPane.ERROR_MESSAGE);
        return;
      }
      
      try {
        dDialog.width = Double.valueOf(dDialog.widthField.getText()).doubleValue();
      } catch(NumberFormatException exc){
        JOptionPane.showMessageDialog(dDialog, "Larghezza non valida!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      } catch(IllegalStateException exc2){
        JOptionPane.showMessageDialog(dDialog, "Larghezza non valida!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      }
      if(dDialog.width <= 0){
        JOptionPane.showMessageDialog(dDialog, "Larghezza non valida!\nDev'essere maggiore di zero!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      }
       
      try {
        dDialog.height = Double.valueOf(dDialog.heightField.getText()).doubleValue();
      } catch(NumberFormatException exc){
        JOptionPane.showMessageDialog(dDialog, "Altezza non valida!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      } catch(IllegalStateException exc2){
        JOptionPane.showMessageDialog(dDialog, "Altezza non valida!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      }
      if(dDialog.height <= 0){
        JOptionPane.showMessageDialog(dDialog, "Altezza non valida!\nDev'essere maggiore di zero!", "Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      }
      
                        // richiama metodo di pannello per ridimensionarlo*/
      try {
        dDialog.frame.sp.resizeEnvironment((int)(dDialog.width), (int)(dDialog.height), false);
        dDialog.frame.setDimensionText();
      }
      catch(SimulationPanelOutOfBoundsException e1) {
        int val = JOptionPane.showConfirmDialog(dDialog, "Alcune particelle rimarrebbero fuori dall'ambiente ridimensionato, procedere ugualmente?","Errore ridimensionamento",JOptionPane.YES_NO_OPTION);
        if(val == JOptionPane.YES_OPTION) {
          try {
            dDialog.frame.sp.resizeEnvironment((int)(dDialog.width), (int)(dDialog.height), true);
            
          }
          catch(SimulationPanelOutOfBoundsException e3) {
          }
          catch(SimulationPanelInvalidParameterException e4) {
          }
          dDialog.frame.setDimensionText();
        }
        else {
            return;
        }
      }
      catch(SimulationPanelInvalidParameterException e2) {
        JOptionPane.showMessageDialog(dDialog, "Dimensioni del nuovo ambiente non valide!","Dati non validi",JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
    
    resetAll();
  }
  
  void resetAll(){
    
    Rectangle size; 
    if(dDialog.frame.sp != null){
      size = dDialog.frame.sp.getEnvironmentRectangle();
      dDialog.widthField.setText(String.valueOf((int) size.getMaxX()));
      dDialog.heightField.setText(String.valueOf((int) size.getMaxY()));
    }
    else {
      dDialog.widthField.setText("");
      dDialog.heightField.setText("");
    }
    
    dDialog.width = 0;
    dDialog.height = 0;
    
    dDialog.setVisible(false);
  }
}
