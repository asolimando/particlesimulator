import javax.swing.*;
import javax.swing.event.*;

class ParticleDialogSliderListener implements ChangeListener {
  
  ParticleDialog pDialog;
  
  public ParticleDialogSliderListener(ParticleDialog pDialog){
    this.pDialog = pDialog;
  }

  public void stateChanged(ChangeEvent e){ 
    JSlider source = (JSlider)e.getSource();
    if(pDialog.angleField.getText() != pDialog.angleSlider.getValue()+""){
      try {
        pDialog.angleField.setText((pDialog.angleSlider.getValue())+"");
      }
      catch(IllegalStateException exc){}
    }
    pDialog.angle = pDialog.angleSlider.getValue();    
  }
}