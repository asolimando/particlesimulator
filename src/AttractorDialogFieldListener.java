import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AttractorDialogFieldListener implements CaretListener {
  
  AttractorDialog aDialog;
  
  public AttractorDialogFieldListener(AttractorDialog aDialog){
    this.aDialog = aDialog;
  }

  public void caretUpdate(CaretEvent e){
    JTextField text = (JTextField) e.getSource();
    
    if(text == aDialog.strengthField){
      try {
        aDialog.strengthSlider.setValue(Integer.valueOf(aDialog.strengthField.getText()).intValue());
      }
      catch(NumberFormatException exc){}
      catch(IllegalStateException exc2){}

      aDialog.strength = aDialog.strengthSlider.getValue();
    }
  }
}
