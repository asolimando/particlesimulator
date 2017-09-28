import java.awt.*;
import javax.swing.*;

class FillDialog extends JDialog {
    ParticleSimulator jpf;
    
    public static final int ALL = 0;
    public static final int AREA = ALL + 1;
    
    JLabel percSymbolLabel = new JLabel("%");
    JLabel percSymbolLabel2 = new JLabel("%");
    JLabel percSymbolLabel3 = new JLabel("%");
    
    JButton fillButton = new JButton("Fill");
    JButton cancelButton = new JButton("Cancel");
    
    JRadioButton areaRadio = new JRadioButton("Area");
    JRadioButton allRadio = new JRadioButton("Environment", true);
    ButtonGroup radiosGroup = new ButtonGroup();
    
    JSlider singleSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 34);
    JSlider doubleSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 33);
    JSlider breakerSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 33);
    
    JTextField singleField = new JTextField((singleSlider.getValue())+"", 3);
    JTextField doubleField = new JTextField((doubleSlider.getValue())+"", 3);
    JTextField breakerField = new JTextField((breakerSlider.getValue())+"", 3);
    
    FillDialogSliderListener fdSliderListener;
    FillDialogFieldListener fdFieldListener;
    
    int singlePerc = 0, doublePerc = 0, breakerPerc = 0;
    
    public FillDialog(ParticleSimulator frame) {
        super(frame, "Filling", true);
        
        jpf = frame;
        
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);		// centra la finestra
        
        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(fillButton);
        
        /* aggiungo i vari listener ai diversi componenti */
        fdSliderListener = new FillDialogSliderListener(this);
        fdSliderListener.setHandling(true);
        singleSlider.addChangeListener(fdSliderListener);
        doubleSlider.addChangeListener(fdSliderListener);
        breakerSlider.addChangeListener(fdSliderListener);
        
        FillDialogButtonListener fdButtonListener = new FillDialogButtonListener(this);
        fillButton.addActionListener(fdButtonListener);
        cancelButton.addActionListener(fdButtonListener);
        
        class RangeVerifier extends InputVerifier {
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                int aux;
                try {
                    aux = Integer.valueOf(tf.getText());
                } catch(NumberFormatException exc) {
                    aux = 0;
                }
                return ((aux>=0) && (aux<=100));
            }
        }
        
        fdFieldListener = new FillDialogFieldListener(this);
        RangeVerifier iv = new RangeVerifier();
        singleField.setInputVerifier(iv);
        doubleField.setInputVerifier(iv);
        breakerField.setInputVerifier(iv);
        singleField.addCaretListener(fdFieldListener);
        doubleField.addCaretListener(fdFieldListener);
        breakerField.addCaretListener(fdFieldListener);
        
        FillDialogRadioButtonListener fdCRadioButtonListener = new FillDialogRadioButtonListener(this);
        allRadio.addActionListener(fdCRadioButtonListener);
        areaRadio.addActionListener(fdCRadioButtonListener);
        /* fine aggiunta listener */
        
        singleSlider.setSnapToTicks(true);
        doubleSlider.setSnapToTicks(true);
        breakerSlider.setSnapToTicks(true);
        
        radiosGroup.add(areaRadio);
        radiosGroup.add(allRadio);
        JPanel radioPanel = new JPanel(new GridLayout(1,2));
        radioPanel.setBorder(BorderFactory.createTitledBorder(" Fill "));
        radioPanel.add(allRadio);
        radioPanel.add(areaRadio);
        
        JPanel singleInputPanel = new JPanel(new FlowLayout());
        singleInputPanel.setBorder(BorderFactory.createTitledBorder(" Single "));
        JPanel doubleInputPanel = new JPanel(new FlowLayout());
        doubleInputPanel.setBorder(BorderFactory.createTitledBorder(" Double "));
        JPanel breakerInputPanel = new JPanel(new FlowLayout());
        breakerInputPanel.setBorder(BorderFactory.createTitledBorder(" Disruptive "));
        
        singleInputPanel.add(singleSlider);
        singleInputPanel.add(singleField);
        singleInputPanel.add(percSymbolLabel);
        
        doubleInputPanel.add(doubleSlider);
        doubleInputPanel.add(doubleField);
        doubleInputPanel.add(percSymbolLabel2);
        
        breakerInputPanel.add(breakerSlider);
        breakerInputPanel.add(breakerField);
        breakerInputPanel.add(percSymbolLabel3);
        
        JPanel percPanel = new JPanel(new GridLayout(3,1));
        percPanel.setBorder(BorderFactory.createTitledBorder(" Particles % "));
        percPanel.add(singleInputPanel);
        percPanel.add(doubleInputPanel);
        percPanel.add(breakerInputPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(fillButton);
        buttonPanel.add(cancelButton);
        
        Container mainPanel = getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(radioPanel);
        mainPanel.add(percPanel);
        mainPanel.add(buttonPanel);
        
        this.setResizable(false);
        fdSliderListener.setHandling(false);
        this.pack();
    }
    

    void setVisible(boolean val, int type) {
        areaRadio.setSelected( (jpf.sp.getSelected() instanceof Rectangle) || (type == FillDialog.AREA) );
        fdSliderListener.setHandling(true);
        fdFieldListener.setHandling(true);
        singleSlider.setValue(34);
        doubleSlider.setValue(33);
        breakerSlider.setValue(33);
        singleField.setText(String.valueOf(singleSlider.getValue()));
        doubleField.setText(String.valueOf(doubleSlider.getValue()));
        breakerField.setText(String.valueOf(breakerSlider.getValue()));
        fdSliderListener.setHandling(false);
        fdFieldListener.setHandling(false);
        this.setVisible(val);
    }
    
}
