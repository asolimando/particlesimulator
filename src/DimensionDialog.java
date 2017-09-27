import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class DimensionDialog extends JDialog {
    
    ParticleSimulator frame;
    
    JLabel widthLabel = new JLabel("Larghezza:");
    JLabel heightLabel = new JLabel("Altezza:");
    
    JTextField widthField = new JTextField("");
    JTextField heightField = new JTextField("");
    
    JButton applyButton = new JButton("Applica");
    JButton cancelButton = new JButton("Annulla");
    
    double width = 0, height = 0;
    
    public DimensionDialog(ParticleSimulator frame){
        super(frame, "Ridimensionamento ambiente", true);
        
        this.frame = frame;
        
        // this.setPreferredSize(new Dimension(200,100)); // setta le dim della finestra
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(applyButton); // l'applybutton ricevera' di default la pressione dell'Invio
        
        /* aggiungo i listener */
        
        DimensionDialogButtonListener ddButtonListener = new DimensionDialogButtonListener(this);
        applyButton.addActionListener(ddButtonListener);
        cancelButton.addActionListener(ddButtonListener);
        /* fine aggiunta listener */
        
        Rectangle size;
        
        if(frame.sp != null){
            size = frame.sp.getEnvironmentRectangle();
            widthField.setText(String.valueOf((int) size.getMaxX()));
            heightField.setText(String.valueOf((int) size.getMaxY()));
        }
        
        JPanel sizePanel = new JPanel(new GridLayout(2,2));
        sizePanel.setBorder(BorderFactory.createTitledBorder(" Dimensioni "));
        
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