import java.awt.event.*;
import javax.swing.*;

class MenuCheckBoxListener implements ActionListener {
    ParticleSimulatorMenubar menubar;
    
    public MenuCheckBoxListener(ParticleSimulatorMenubar menubar){
        this.menubar = menubar;
    }
    
    public void actionPerformed(ActionEvent e){
        JCheckBoxMenuItem src = (JCheckBoxMenuItem) e.getSource();
        if (menubar.frame.sp != null) {
            if(src == menubar.itemShowCoords){
                menubar.frame.sp.setShowPosition(menubar.itemShowCoords.isSelected());
            } else if(src == menubar.itemShowSpeedDirection) {
                menubar.frame.sp.setShowDirection(menubar.itemShowSpeedDirection.isSelected());
            } else if(src == menubar.itemShowStrength) {
                menubar.frame.sp.setShowIntensity(menubar.itemShowStrength.isSelected());
            }
        }
    }
}
