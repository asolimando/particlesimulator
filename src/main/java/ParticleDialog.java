import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.PropertyChangeListener;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;

class ParticleDialog extends JDialog {
  
  public static final int INSERT = 0;
  public static final int EDIT = INSERT + 1;
  
  ParticleSimulator jpf;
  
  JLabel degreesSymbolLabel = new JLabel("degrees", JLabel.LEFT);
  JLabel xLabel = new JLabel("x:   ", JLabel.RIGHT);
  JLabel yLabel = new JLabel("y:   ", JLabel.RIGHT);
  JLabel angleLabel = new JLabel("angle:", JLabel.RIGHT);
  
  String [] particleStrings = {"Single", "Double", "Disruptive"};
  
  JButton insertButton = new JButton("Insert");
  JButton cancelButton = new JButton("Cancel");
  
  JSlider angleSlider = new JSlider(0, 359, 0);
  JComboBox particleCombo = new JComboBox(particleStrings);
  
  JTextField xField = new JTextField();
  JTextField yField = new JTextField();
  JTextField angleField = new JTextField("0",3);
  
  double x = 0, y = 0;
  int angle = -1, type = Particle.SINGLE;
  int mode = ParticleDialog.INSERT;
  
  public ParticleDialog(ParticleSimulator frame){
    super(frame, "Particle's properties", true);
    
    jpf = frame;
    (degreesSymbolLabel.getFont()).deriveFont(Font.ITALIC, 16);
    
    angleSlider.setValue(0);
    
    setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2,
            (Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);		// centra la finestra
    
    JRootPane rootPane = this.getRootPane();
    rootPane.setDefaultButton(insertButton);
    
    angleSlider.setSnapToTicks(true);
    
    /* aggiungo i vari listener ai diversi componenti */
    
    ParticleDialogButtonListener pdButtonListener = new ParticleDialogButtonListener(this);
    insertButton.addActionListener(pdButtonListener);
    cancelButton.addActionListener(pdButtonListener);
    
    particleCombo.addItemListener(new ParticleDialogComboBoxListener(this));
    
    angleField.addCaretListener(new ParticleDialogFieldListener(this));
    
    angleSlider.addChangeListener(new ParticleDialogSliderListener(this));
    /* fine aggiunta listener */
    
    JPanel particleComboPanel = new JPanel(new GridLayout(1,1));
    particleComboPanel.setBorder(BorderFactory.createTitledBorder(" Kind "));
    particleComboPanel.add(particleCombo);
    
    JPanel dirAnglePanel = new JPanel(new FlowLayout());
    dirAnglePanel.setBorder(BorderFactory.createTitledBorder(" Direction (angle) "));
 //   dirAnglePanel.add(angleLabel);
    dirAnglePanel.add(angleSlider);
    dirAnglePanel.add(angleField);
    dirAnglePanel.add(degreesSymbolLabel);
    
    JPanel coordsInputPanel = new JPanel(new GridLayout(1,4));
    coordsInputPanel.setBorder(BorderFactory.createTitledBorder(" Coordinates "));
    coordsInputPanel.add(xLabel);
    coordsInputPanel.add(xField);
    coordsInputPanel.add(yLabel);
    coordsInputPanel.add(yField);
    
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(insertButton);
    buttonPanel.add(cancelButton);
    
    
    Container mainPanel = getContentPane();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(particleComboPanel);
    mainPanel.add(dirAnglePanel);
    mainPanel.add(coordsInputPanel);
    mainPanel.add(buttonPanel);
    
    this.setResizable(false);
    this.pack();
  }
  
  void setVisible(boolean val, int x, int y){
    xField.setText("" + x);
    yField.setText("" + y);
    this.x = x;
    this.y = y;
    this.setVisible(val);
  }
  
  void setVisible(boolean val, int mode){
    if(mode == ParticleDialog.INSERT){
      this.setVisible(val);
    }
    else {
      this.mode = ParticleDialog.EDIT;
      
      insertButton.setText("Apply");
      
      Particle src = (Particle) jpf.sp.getSelected();
      
      particleCombo.setEnabled(false);
      
      type = src.getType();
      if(type == Particle.SINGLE){
        particleCombo.setSelectedItem(particleStrings[0]);
      }
      else if(type == Particle.DOUBLE){
        particleCombo.setSelectedItem(particleStrings[1]);
      }
      else {
        particleCombo.setSelectedItem(particleStrings[2]);
      }
      
      PairOfDouble direction = src.getDirection();
      angleSlider.setValue((int) Math.toDegrees(jpf.sp.vectorToAngle(direction)));
      angleField.setText("" + angleSlider.getValue());
      angle = angleSlider.getValue();
      
      PairOfDouble center = src.getCenter();
      xField.setText("" + (int) Math.floor(center.x));
      yField.setText("" + (int) Math.floor(center.y));
      x = (int) Math.floor(center.x);
      y = (int) Math.floor(center.y);
  
      this.setVisible(val);
    }
  }
  
}
