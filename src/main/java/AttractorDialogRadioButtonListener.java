import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AttractorDialogRadioButtonListener implements ActionListener {
  
  AttractorDialog aDialog;
  
  public AttractorDialogRadioButtonListener(AttractorDialog aDialog){
    this.aDialog = aDialog;
  }
  
  public void actionPerformed(ActionEvent e) { 
    JRadioButton src = (JRadioButton) e.getSource();
    if(src == aDialog.allRadio){
      aDialog.type = -1;
    }
    else if(src == aDialog.singleRadio){
      aDialog.type = Particle.SINGLE;
    }
    else if(src == aDialog.doubleRadio){
      aDialog.type = Particle.DOUBLE;
    }
    else if(src == aDialog.breakerRadio){
      aDialog.type = Particle.BREAKER;
    }
		else if(src == aDialog.pointRadio){
			aDialog.wallLabel.setEnabled(false);
			aDialog.wallCombo.setEnabled(false);
			aDialog.coordsLabel.setEnabled(true);
			aDialog.xLabel.setEnabled(true);
			aDialog.yLabel.setEnabled(true);
			aDialog.coordsLabel.setEnabled(true);
			aDialog.xField.setEnabled(true);
			aDialog.yField.setEnabled(true);
			aDialog.isWall = false;
		}
		else {
			aDialog.wallLabel.setEnabled(true);
			aDialog.wallCombo.setEnabled(true);
			aDialog.coordsLabel.setEnabled(false);
			aDialog.xLabel.setEnabled(false);
			aDialog.yLabel.setEnabled(false);
			aDialog.coordsLabel.setEnabled(false);
			aDialog.xField.setEnabled(false);
			aDialog.yField.setEnabled(false);
			aDialog.isWall = true;
		}
  }
}
