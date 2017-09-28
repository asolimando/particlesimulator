import javax.swing.*;
import javax.swing.event.*;

class FillDialogFieldListener implements CaretListener {
    
    FillDialog fDialog;
    private boolean handling;
    
    public FillDialogFieldListener(FillDialog fDialog){
        this.fDialog = fDialog;
    }
    
    public void setHandling(boolean value) {
        handling = value;
    }
    
    public boolean isHandling() {
        return handling;
    }
    
    public void caretUpdate(CaretEvent e){
        JTextField text = (JTextField) e.getSource();
        
        if((!handling) && (!fDialog.fdSliderListener.isHandling())) { // it does not handle events in case of reset of the dialog
            handling = true;
            int aux;
            
            try {
                aux = Integer.valueOf(text.getText());
            } catch(NumberFormatException exc) {
                aux = 0;
            }
            
            if(text == fDialog.singleField) {
                
                try {
                    fDialog.singleSlider.setValue(aux);
                    //fDialog.singleField.setText(String.valueOf(fDialog.singleSlider.getValue()));
                    fDialog.doubleField.setText(String.valueOf(fDialog.doubleSlider.getValue()));
                    fDialog.breakerField.setText(String.valueOf(fDialog.breakerSlider.getValue()));
                } catch(NumberFormatException exc){
                    exc.printStackTrace();
                } catch(IllegalStateException exc){
                    exc.printStackTrace();
                }
                
            } else if(text == fDialog.doubleField){
                try {
                    fDialog.doubleSlider.setValue(aux);
                    fDialog.singleField.setText(String.valueOf(fDialog.singleSlider.getValue()));
                    //fDialog.doubleField.setText(String.valueOf(fDialog.doubleSlider.getValue()));
                    fDialog.breakerField.setText(String.valueOf(fDialog.breakerSlider.getValue()));
                } catch(NumberFormatException exc){
                    exc.printStackTrace();
                } catch(IllegalStateException exc){
                    exc.printStackTrace();
                }
                
                
            } else {
                try {
                    fDialog.doubleSlider.setValue(aux);
                    fDialog.singleField.setText(String.valueOf(fDialog.singleSlider.getValue()));
                    fDialog.doubleField.setText(String.valueOf(fDialog.doubleSlider.getValue()));
                    //fDialog.breakerField.setText(String.valueOf(fDialog.breakerSlider.getValue()));
                } catch(NumberFormatException exc){
                    exc.printStackTrace();
                } catch(IllegalStateException exc){
                    exc.printStackTrace();
                }
                
            }
            
            handling = false;
        }
    }
}
