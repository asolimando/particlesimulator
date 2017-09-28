import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class FillDialogRadioButtonListener implements ActionListener {
    
    FillDialog fDialog;
    
    public FillDialogRadioButtonListener(FillDialog fDialog){
        this.fDialog = fDialog;
    }
    
    public void actionPerformed(ActionEvent e) {
        JRadioButton src = (JRadioButton) e.getSource();
        if(src == fDialog.areaRadio) {
            if(!(fDialog.jpf.sp.getSelected() instanceof Rectangle)) {
                fDialog.allRadio.setSelected(true);
                JOptionPane.showMessageDialog(fDialog, "Area not selected!", "Missing selection", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
