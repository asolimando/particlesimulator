import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AttractorDialogComboBoxListener implements ItemListener {
  
  AttractorDialog aDialog;
  
  public AttractorDialogComboBoxListener(AttractorDialog aDialog){
    this.aDialog = aDialog;
  }

  public void itemStateChanged(ItemEvent e){
    Object source = e.getItemSelectable();
    if(e.getStateChange() == ItemEvent.SELECTED)
      if(aDialog.wallCombo.getSelectedItem() == aDialog.wallStrings[0]){
        aDialog.wall = Environment.BOTTOM;
      }
      else if(aDialog.wallCombo.getSelectedItem() == aDialog.wallStrings[1]){
        aDialog.wall = Environment.TOP;
      }
      else if(aDialog.wallCombo.getSelectedItem() == aDialog.wallStrings[2]){
        aDialog.wall = Environment.LEFT;
      }
	    else {
				aDialog.wall = Environment.RIGHT;
		  }
  	}
	}
