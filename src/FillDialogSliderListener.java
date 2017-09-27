import javax.swing.*;
import javax.swing.event.*;

class FillDialogSliderListener implements ChangeListener {
    
    FillDialog fDialog;
    private boolean handling;
    private boolean sw;
    
    public FillDialogSliderListener(FillDialog fDialog){
        handling = false;
        this.fDialog = fDialog;
    }
    
    public void setHandling(boolean value) {
        handling = value;
    }
    
    public boolean isHandling() {
        return handling;
    }
    
    private int add(JSlider slider, int value) {
        int tmp = value + slider.getValue();
        slider.setValue(Math.max(0, Math.min(100, tmp)));
        return slider.getValue() - (tmp - value); //quanti gliene ho tolti?
    }
    
    private void balance(JSlider slider1, JSlider slider2, int value) {
        int aux;
        aux = add(slider1, value);
        value = value - aux;
        aux = add(slider2, value);
        if (aux != 0)
            add(slider1, aux);
    }
    
    public void stateChanged(ChangeEvent e) {
        if (!handling) {
            JSlider source = (JSlider) e.getSource();
            //if(fDialog.reset == true) return; // non gestisce gli eventi in caso di reset della dialog
//    int totalSum = fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue();
            int sum = fDialog.singleSlider.getValue() + fDialog.doubleSlider.getValue() + fDialog.breakerSlider.getValue();
            int diff = 100 - sum;
            int half = diff / 2;
            int sign = diff<0?-1:1;
            
            
            
            if(source == fDialog.singleSlider)  {
                
                handling = true;
                /*balance(fDialog.doubleSlider, fDialog.breakerSlider, half);
                if( (diff % 2) != 0) {
                    if(fDialog.singleSlider.getValue()%2 == 0)
                        balance(fDialog.breakerSlider, fDialog.doubleSlider,  sign);
//                        add(fDialog.breakerSlider, sign);
                    else
                        balance(fDialog.doubleSlider, fDialog.breakerSlider, sign);
//                        add(fDialog.doubleSlider, sign);*/
                for(int i = 0; i < Math.abs(diff); i++) {
                    if(sw)
                        balance(fDialog.breakerSlider, fDialog.doubleSlider,  sign);
                    else
                        balance(fDialog.doubleSlider, fDialog.breakerSlider, sign);
                    sw = !sw;
                }
                
                
            } else if(source == fDialog.doubleSlider)  {
                handling = true;
                /*balance(fDialog.singleSlider, fDialog.breakerSlider, half);
                if( (diff % 2) != 0) {
                    if(fDialog.doubleSlider.getValue()%2 == 0)
                        balance(fDialog.breakerSlider, fDialog.singleSlider,  sign);
                    //add(fDialog.breakerSlider, sign);
                    else
                        balance(fDialog.singleSlider, fDialog.breakerSlider, sign);
//                    add(fDialog.singleSlider, sign);*/
                for(int i = 0; i < Math.abs(diff); i++) {
                    if(sw)
                        balance(fDialog.singleSlider, fDialog.breakerSlider,  sign);
                    else
                        balance(fDialog.breakerSlider, fDialog.singleSlider, sign);
                    sw = !sw;
                }
            }
            
            
            else {
                handling = true;
                /*balance(fDialog.singleSlider, fDialog.doubleSlider, half);
                if( (diff % 2) != 0) {
                    if(fDialog.breakerSlider.getValue()%2 == 0)
                        balance(fDialog.doubleSlider, fDialog.singleSlider,  sign);
//                    add(fDialog.doubleSlider, sign);
                    else
                        balance(fDialog.singleSlider, fDialog.doubleSlider, sign);
//                        add(fDialog.singleSlider, sign);*/
                for(int i = 0; i < Math.abs(diff); i++) {
                    if(sw)
                        balance(fDialog.singleSlider, fDialog.doubleSlider,  sign);
                    else
                        balance(fDialog.doubleSlider, fDialog.singleSlider, sign);
                    sw = !sw;
                }
            }
            
            
            if(!fDialog.fdFieldListener.isHandling()) {
                fDialog.singleField.setText(String.valueOf(fDialog.singleSlider.getValue()));
                fDialog.doubleField.setText(String.valueOf(fDialog.doubleSlider.getValue()));
                fDialog.breakerField.setText(String.valueOf(fDialog.breakerSlider.getValue()));
            }
            handling = false;
        }
    }
}
