import javax.swing.*;
import javax.swing.event.*;

class FillDialogSliderListenerOLD implements ChangeListener {
  
  FillDialog fDialog;
  boolean handling;
  
  public FillDialogSliderListenerOLD(FillDialog fDialog){
    this.fDialog = fDialog;
  }
  
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider) e.getSource();
    if(!handling) return; // non gestisce gli eventi in caso di reset della dialog
//    int totalSum = fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue();
    
    if(source == fDialog.singleSlider){

      if((fDialog.singleField.getText().equals(fDialog.singleSlider.getValue()+"")) == false){
        if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() > 100){
          if(((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100) % 2) == 0){
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
          }
          else {
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() - 
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2) - 1);
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
          }
        }
        else if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() < 100){
          if(((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()) % 2) == 0){
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
          else {
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2) + 1);
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
        }
        try {
          fDialog.singleField.setText((fDialog.singleSlider.getValue())+"");
        }
        catch(IllegalStateException exc){}
      }
      fDialog.singlePerc = fDialog.singleSlider.getValue();
        
//      if(fDialog.doubleSlider.getValue() == 0 && fDialog.breakerSlider.getValue() != 0 && (!source.getValueIsAdjusting())){
//        fDialog.doubleSlider.setValue(100-fDialog.singleSlider.getValue()-fDialog.breakerSlider.getValue());
//      }
//      else if(fDialog.doubleSlider.getValue() != 0 && fDialog.breakerSlider.getValue() == 0 && (!source.getValueIsAdjusting())){
//        fDialog.breakerSlider.setValue(100-fDialog.singleSlider.getValue()-fDialog.doubleSlider.getValue());
//      }

    }
    else if(source == fDialog.doubleSlider){
      if((fDialog.doubleField.getText().equals(fDialog.doubleSlider.getValue()+"")) == false){
        if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() > 100){ 
         if(((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100) % 2) == 0){
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
          }
          else {
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2) - 1);
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()-100)/2));
          }
        }
        else if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() < 100){
          if(((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()) % 2) == 0){
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
          else {
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2) + 1);
            fDialog.breakerSlider.setValue(fDialog.breakerSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
        }
        try {
          fDialog.doubleField.setText((fDialog.doubleSlider.getValue())+"");
        }
        catch(IllegalStateException exc){}
      }
      fDialog.doublePerc = fDialog.doubleSlider.getValue();
        
//      if(fDialog.singleSlider.getValue() == 0 && fDialog.breakerSlider.getValue() != 0 && (!source.getValueIsAdjusting())){
//        fDialog.singleSlider.setValue(100-fDialog.doubleSlider.getValue()-fDialog.breakerSlider.getValue());
//      }
//      else if(fDialog.singleSlider.getValue() != 0 && fDialog.breakerSlider.getValue() == 0 && (!source.getValueIsAdjusting())){
//        fDialog.breakerSlider.setValue(100-fDialog.doubleSlider.getValue()-fDialog.singleSlider.getValue());
//      }
      
    }
    else {
      if((fDialog.breakerField.getText().equals(fDialog.breakerSlider.getValue()+"")) == false){
        if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() > 100){
          if(((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100) % 2) == 0){
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() - 
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100)/2));
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100)/2));
          }
          else {
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() - 
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100)/2) - 1);
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() -
            ((fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() - 100)/2));
          }
        }
        else if(fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue() < 100){
          if(((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue()) % 2) == 0){
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
          else {
            fDialog.singleSlider.setValue(fDialog.singleSlider.getValue() + 
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2) + 1);
            fDialog.doubleSlider.setValue(fDialog.doubleSlider.getValue() +
            ((100 - fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue())/2));
          }
        }
        try {
          fDialog.breakerField.setText((fDialog.breakerSlider.getValue())+"");
        }
        catch(IllegalStateException exc){};
      }
      fDialog.breakerPerc = fDialog.breakerSlider.getValue();
        
//      if(fDialog.singleSlider.getValue() == 0 && fDialog.doubleSlider.getValue() != 0 && (!source.getValueIsAdjusting())){
//        fDialog.singleSlider.setValue(100-fDialog.doubleSlider.getValue()-fDialog.breakerSlider.getValue());
//      }
//      else if(fDialog.singleSlider.getValue() != 0 && fDialog.doubleSlider.getValue() == 0 && (!source.getValueIsAdjusting())){
//        fDialog.doubleSlider.setValue(100-fDialog.singleSlider.getValue()-fDialog.breakerSlider.getValue());
//      }
    }
  }
}
