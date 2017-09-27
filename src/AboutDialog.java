import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AboutDialog extends JDialog {
    
    JLabel tlLabel = new JLabel("App developed by:\n");
    JLabel flLabel = new JLabel("Fabrizio Larosa");
    JLabel asLabel = new JLabel("Alessandro Solimando");
    JLabel acLabel = new JLabel("Alessandro Costa");
    
    JButton closeButton = new JButton("Close");
    JLabel questionLabel = new JLabel(new ImageIcon(SimulationPanel.loadImage(this, "images/about.png")));
    
    public AboutDialog(ParticleSimulator frame){
        super(frame, "About", false);
        
        // this.setPreferredSize(new Dimension(200,100)); // setta le dim della finestra
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(closeButton);
        
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        Container mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        JPanel spacePanel = new JPanel();
        spacePanel.add(questionLabel);
        Dimension d = new Dimension(50,50);
        spacePanel.setSize(d);
        spacePanel.setMinimumSize(d);
        spacePanel.setMaximumSize(d);
        spacePanel.setPreferredSize(d);
        mainPanel.add(spacePanel, BorderLayout.WEST);
        JPanel namePanel = new JPanel(new GridLayout(5,1));
        namePanel.add(tlLabel);
        namePanel.add(new JLabel(""));
        namePanel.add(flLabel);
        namePanel.add(asLabel);
        namePanel.add(acLabel);
        mainPanel.add(namePanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setResizable(false);
        setSize(250, 150);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2,
        //        (Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
        setLocationRelativeTo(frame);
    }
}