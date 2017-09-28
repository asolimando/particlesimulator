import java.awt.*;
import javax.swing.*;

class NewEnvironmentDialog extends JDialog {
    
    ParticleSimulator frame;
    
    JLabel widthLabel = new JLabel("Width:");
    JLabel heightLabel = new JLabel("Height:");
    
    JTextField widthField = new JTextField("");
    JTextField heightField = new JTextField("");
    
    JButton applyButton = new JButton("Apply");
    JButton cancelButton = new JButton("Cancel");
    
    double width = 0, height = 0;
    
    public NewEnvironmentDialog(ParticleSimulator frame) {
        super(frame, "Environment creation", true);
        
        this.frame = frame;
        
        // this.setPreferredSize(new Dimension(200,100)); // set the dims of the window
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(applyButton); // applybutton will receive by default the Return button pression
        
        /* start listener addition */
        NewEnvironmentDialogButtonListener neButtonListener = new NewEnvironmentDialogButtonListener(this);
        applyButton.addActionListener(neButtonListener);
        cancelButton.addActionListener(neButtonListener);
        /* end listener addtion */
        
        JPanel sizePanel = new JPanel(new GridLayout(2,2));
        sizePanel.setBorder(BorderFactory.createTitledBorder(" Dimensions "));
        sizePanel.add(widthLabel);
        sizePanel.add(widthField);
        sizePanel.add(heightLabel);
        sizePanel.add(heightField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);
        
        Container mainPanel = getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(sizePanel);
        mainPanel.add(buttonPanel);


        
        this.setResizable(false);
        this.pack();
        setLocationRelativeTo(frame);
    }
    
}

