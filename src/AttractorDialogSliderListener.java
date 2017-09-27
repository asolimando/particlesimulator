import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AttractorDialogSliderListener implements ChangeListener {
  
  AttractorDialog aDialog;
  
  public AttractorDialogSliderListener(AttractorDialog aDialog){
    this.aDialog = aDialog;
  }
  
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if(aDialog.strengthField.getText() != aDialog.strengthSlider.getValue()+""){
      try {
        aDialog.strengthField.setText((aDialog.strengthSlider.getValue())+"");
      }
      catch(IllegalStateException exc){}
    }
    aDialog.strength = aDialog.strengthSlider.getValue();    
  }
}
