import javax.swing.*;
import javax.swing.event.*;

class ParticleDialogFieldListener implements CaretListener {
  
  ParticleDialog pDialog;
  
  public ParticleDialogFieldListener(ParticleDialog pDialog){
    this.pDialog = pDialog;
  }

  public void caretUpdate(CaretEvent e){
    //JTextField text = (JTextField) e.getSource();
    
    try {
      pDialog.angleSlider.setValue(Integer.valueOf(pDialog.angleField.getText()).intValue());
    }
    catch(NumberFormatException exc){}
    catch(IllegalStateException exc2){}
    
    pDialog.angle = pDialog.angleSlider.getValue();
     
  }
}
