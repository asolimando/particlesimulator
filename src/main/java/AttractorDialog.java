import java.awt.*;
import javax.swing.*;

class AttractorDialog extends JDialog {
  ParticleSimulator jpf;
  
  public static final int INSERT = 0;
  public static final int EDIT = INSERT + 1;
 
  JLabel coordsLabel = new JLabel("Coordinates:");
  JLabel xLabel = new JLabel("x:   ", JLabel.RIGHT);
  JLabel yLabel = new JLabel("y:   ", JLabel.RIGHT);
  JLabel wallLabel = new JLabel("Wall:");
  
  JButton insertButton = new JButton("Insert");
  JButton cancelButton = new JButton("Cancel");
  
  JTextField xField = new JTextField();
  JTextField yField = new JTextField();
  
  String [] wallStrings = {"Upper wall","Lower wall","Left wall","Right wall"};
  JComboBox wallCombo = new JComboBox(wallStrings);
  
  JRadioButton pointRadio = new JRadioButton("Point", true);
  JRadioButton wallRadio = new JRadioButton("Wall");
  
  JRadioButton allRadio = new JRadioButton("Any type", true);
  JRadioButton singleRadio = new JRadioButton("Single");
  JRadioButton doubleRadio = new JRadioButton("Double");
  JRadioButton breakerRadio = new JRadioButton("  Disruptive");
  
  JSlider strengthSlider = new JSlider(SwingConstants.HORIZONTAL);
  
  JTextField strengthField = new JTextField((strengthSlider.getValue())+"", 3);
  
  double x = 0, y = 0;
  int strength = 50, type = -1, wall = Environment.BOTTOM, actualMode = AttractorDialog.INSERT;
  boolean isWall = false;
  
  public AttractorDialog(ParticleSimulator frame){
    super(frame, "Attractors' Property", true);
    jpf = frame;
    
    JRootPane rootPane = this.getRootPane();
    rootPane.setDefaultButton(insertButton);
    
    /* aggiungo i vari listener ai diversi componenti */
    strengthSlider.addChangeListener(new AttractorDialogSliderListener(this));
    
    wallCombo.addItemListener(new AttractorDialogComboBoxListener(this));
    
    AttractorDialogButtonListener adButtonListener = new AttractorDialogButtonListener(this);
    insertButton.addActionListener(adButtonListener);
    cancelButton.addActionListener(adButtonListener);
    
    AttractorDialogFieldListener adFieldListener = new AttractorDialogFieldListener(this);
    xField.addCaretListener(adFieldListener);
    yField.addCaretListener(adFieldListener);
    strengthField.addCaretListener(adFieldListener);
    
    AttractorDialogRadioButtonListener adCRadioButtonListener = new AttractorDialogRadioButtonListener(this);
    allRadio.addActionListener(adCRadioButtonListener);
    singleRadio.addActionListener(adCRadioButtonListener);
    doubleRadio.addActionListener(adCRadioButtonListener);
    breakerRadio.addActionListener(adCRadioButtonListener);
    pointRadio.addActionListener(adCRadioButtonListener);
    wallRadio.addActionListener(adCRadioButtonListener);
    /* fine assegnazione listener */
    
    ButtonGroup radiosGroup = new ButtonGroup();
    radiosGroup.add(allRadio);
    radiosGroup.add(singleRadio);
    radiosGroup.add(doubleRadio);
    radiosGroup.add(breakerRadio);
    
    ButtonGroup whereRadioGroup = new ButtonGroup();
    whereRadioGroup.add(pointRadio);
    whereRadioGroup.add(wallRadio);
    
    
    JPanel wallPointPanel = new JPanel(new GridLayout(1,2));
    wallPointPanel.add(pointRadio);
    wallPointPanel.add(wallRadio);
    
    JPanel strengthInputPanel = new JPanel(new FlowLayout());
    strengthInputPanel.setBorder(BorderFactory.createTitledBorder(" Intensita' "));
    strengthInputPanel.add(strengthSlider);
    strengthInputPanel.add(strengthField);
    
    JPanel coordsInputPanel = new JPanel(new GridLayout(1,4));
    coordsInputPanel.add(xLabel);
    coordsInputPanel.add(xField);
    coordsInputPanel.add(yLabel);
    coordsInputPanel.add(yField);
    
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(insertButton);
    buttonPanel.add(cancelButton);
    
    wallLabel.setEnabled(false);
    wallCombo.setEnabled(false);
    
    JPanel radioPanel = new JPanel(new GridLayout(4,1));
    radioPanel.setBorder(BorderFactory.createTitledBorder(" Kind "));
    radioPanel.add(allRadio);
    radioPanel.add(singleRadio);
    radioPanel.add(doubleRadio);
    radioPanel.add(breakerRadio);    
    
    JPanel wherePanel = new JPanel(new GridLayout(5,1));
    wherePanel.setBorder(BorderFactory.createTitledBorder(" Position "));
    wherePanel.add(wallPointPanel);
    wherePanel.add(wallLabel);
    wherePanel.add(wallCombo);
    wherePanel.add(coordsLabel);
    wherePanel.add(coordsInputPanel);
    
    Container mainPanel = getContentPane();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(radioPanel);
    mainPanel.add(strengthInputPanel);
    mainPanel.add(wherePanel);
    mainPanel.add(buttonPanel);

    this.setResizable(false);
    this.pack();
    setLocationRelativeTo(frame);
  }
  
  void setVisible(boolean val, int x, int y){
    wallLabel.setEnabled(false);
    wallCombo.setEnabled(false);
    coordsLabel.setEnabled(true);
    xLabel.setEnabled(true);
    yLabel.setEnabled(true);
    coordsLabel.setEnabled(true);
    xField.setEnabled(true);
    yField.setEnabled(true);
    isWall = false;
    pointRadio.setSelected(true);
    xField.setText("" + x);
    yField.setText("" + y);
    this.x = x;
    this.y = y;
    this.setVisible(val);
  }
  
  void setVisible(boolean val, int mode){
    if(mode == AttractorDialog.INSERT){
      this.setVisible(val);
    }
    else {
      actualMode = AttractorDialog.EDIT;
      // imposta i campi con i valori dell'attrattore da editare
      insertButton.setText("Apply");
      Attractor src = (Attractor) jpf.sp.getSelected();
      if(src.getAttracts() == Attractor.SINGLE){
        singleRadio.setSelected(true);
        type = Particle.SINGLE;
      }
      else if(src.getAttracts() == Attractor.DOUBLE){
        doubleRadio.setSelected(true);
        type = Particle.DOUBLE;
      }
      else if(src.getAttracts() == Attractor.BREAKER){
        breakerRadio.setSelected(true);
        type = Particle.BREAKER;
      }
      else {
        allRadio.setSelected(true);
        type = -1;
      }
      
       strengthSlider.setValue(src.getIntensity());
       strengthField.setText(""+src.getIntensity());
       strength = strengthSlider.getValue();
      
      if(src.getPlacement() != Attractor.FLOATING){
        
        wallLabel.setEnabled(true);
        wallCombo.setEnabled(true);
        coordsLabel.setEnabled(false);
        xLabel.setEnabled(false);
        yLabel.setEnabled(false);
        coordsLabel.setEnabled(false);
        xField.setEnabled(false);
        yField.setEnabled(false);
        
        isWall = true;
        wallRadio.setSelected(true);
        int oldType = src.getPlacement();
        if(oldType == Attractor.WALL_DOWN){
          wall = Environment.TOP;
          wallCombo.setSelectedItem(wallStrings[1]);
        }
        else if(oldType == Attractor.WALL_LEFT){
          wall = Environment.LEFT;
          wallCombo.setSelectedItem(wallStrings[2]);
        }
        else if(oldType == Attractor.WALL_RIGHT){
          wall = Environment.RIGHT;
          wallCombo.setSelectedItem(wallStrings[3]);
        }
        else {
          wall = Environment.BOTTOM;
          wallCombo.setSelectedItem(wallStrings[0]);
        }
      }
      else {
        wallLabel.setEnabled(false);
        wallCombo.setEnabled(false);
        coordsLabel.setEnabled(true);
        xLabel.setEnabled(true);
        yLabel.setEnabled(true);
        coordsLabel.setEnabled(true);
        xField.setEnabled(true);
        yField.setEnabled(true);
        isWall = false;
        pointRadio.setSelected(true);
        PairOfDouble pos = src.getPosition();
        xField.setText("" + (int) Math.floor(pos.x));
        yField.setText("" + (int) Math.floor(pos.y));
        x = (int) Math.floor(pos.x);
        y = (int) Math.floor(pos.y);
      }
      
      
      this.setVisible(val);
    }
  }
}
