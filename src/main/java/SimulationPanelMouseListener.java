import java.awt.event.*;

class SimulationPanelMouseListener extends MouseAdapter implements MouseMotionListener {
    
    private SimulationPanel panel;
    
    SimulationPanelMouseListener(SimulationPanel panel) {
        this.panel = panel;
    }
    
    public void mouseClicked(MouseEvent e) {
        panel.mouseClicked(e.getX(), e.getY(), e.getButton());
    }
    
    public void mousePressed(MouseEvent e) {
        panel.mousePressed(e.getX(), e.getY(), e.getButton());
    }
    
    
    public void mouseReleased(MouseEvent e) {
        panel.mouseReleased(e.getX(), e.getY(), e.getButton());
    }
    
    public void mouseEntered(MouseEvent e) {
        //panel.mouseEntered();
    }
    
    public void mouseExited(MouseEvent e) {
        panel.mouseExited();
    }
    
    
    public void mouseMoved(MouseEvent e) {
        panel.mouseMoved(e.getX(), e.getY());
        
    }
    
    public void mouseDragged(MouseEvent e) {
        panel.mouseDragged(e.getX(), e.getY());
    }
    
    
}