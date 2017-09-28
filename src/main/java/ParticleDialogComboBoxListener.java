import java.awt.event.*;

class ParticleDialogComboBoxListener implements ItemListener {
  
  ParticleDialog pDialog;
  
  public ParticleDialogComboBoxListener(ParticleDialog pDialog){
    this.pDialog = pDialog;
  }
  
  public void itemStateChanged(ItemEvent e){
    Object source = e.getItemSelectable();
    if(e.getStateChange() == ItemEvent.SELECTED)
      if(pDialog.particleCombo.getSelectedItem() == pDialog.particleStrings[0]){
        pDialog.type = Particle.SINGLE;
      }
      else if(pDialog.particleCombo.getSelectedItem() == pDialog.particleStrings[1]){
        pDialog.type = Particle.DOUBLE;
      }
      else{
        pDialog.type = Particle.BREAKER;
      }
  }
}
