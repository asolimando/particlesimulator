import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import javax.swing.*;

public class SimulationPanel extends JPanel implements ActionListener {
    
    public static final int FILL_RECTANGLE = 0;
    public static final int FILL_ENVIRONMENT = FILL_RECTANGLE + 1;
    
    private static final int MODE_PLAY = FILL_ENVIRONMENT + 1;
    private static final int MODE_EDIT = MODE_PLAY + 1;
    private static final int MODE_INSERT = MODE_EDIT + 1;
    private static final int MODE_MODIFY = MODE_INSERT + 1;
    
    private static final int PARTICLE_POSITION = MODE_MODIFY + 1;
    private static final int PARTICLE_VECTOR = PARTICLE_POSITION + 1;
    private static final int ATTRACTOR_POSITION = PARTICLE_VECTOR + 1;
    private static final int ATTRACTOR_INTENSITY = ATTRACTOR_POSITION + 1;
    
    public static final int MODIFY_POSITION = ATTRACTOR_INTENSITY + 1;
    public static final int MODIFY_DIRECTION_INTENSITY = MODIFY_POSITION + 1;
    
    
    private static final int SELECTED_NONE = -1;
    private static final int SELECTED_ATTR_FLOAT = SELECTED_NONE - 1;
    private static final int SELECTED_ATTR_WALL = SELECTED_ATTR_FLOAT - 1;
    private static final int SELECTED_RECTANGLE = SELECTED_ATTR_WALL - 1;
    
    
    public static final int SPEED_TICKS = 20;
    
    private static final int THICKNESS = 20;
    private static final int RADIUS_FLOAT_MIN = 15;
    private static final int RADIUS_FLOAT_MAX = 115;
    private static final int RADIUS_WALL_MIN = 30;
    private static final int RADIUS_WALL_MAX = 180;
    private static final int DIRECTION_LENGTH = 50;
    private static final int DIRECTION_SHIFT = 5;
    private static final int NET_WIDTH = 10;
    
    private static final Color ARED = new Color(Color.RED.getRed(),Color.RED.getGreen(), Color.RED.getBlue(), 128);
    private static final Color AGREEN = new Color(Color.GREEN.getRed(),Color.GREEN.getGreen(), Color.GREEN.getBlue(), 128);
    private static final Color ABLUE = new Color(Color.BLUE.getRed(),Color.BLUE.getGreen(), Color.BLUE.getBlue(), 128);
    private static final Color ABLACK = new Color(Color.BLACK.getRed(),Color.BLACK.getGreen(), Color.BLACK.getBlue(), 128);
    private static final Color AGRAY = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(), Color.GRAY.getBlue(), 128);
    private static final Color AYELLOW = new Color(Color.YELLOW.getRed(),Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), 150);
    private static final Color rectColor = new Color(0.0f, 0.0f, 1.0f, 0.3f);
    private static final Cursor CurDefault = new Cursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor CurHand = new Cursor(Cursor.HAND_CURSOR);
    
    
    private static Image ImgAttractorSingle;
    private static Image ImgAttractorDouble;
    private static Image ImgAttractorBreaker;
    private static Image ImgAttractorAll;
    private static Image ImgSelAttractor;
    private static int attrWidth;
    private static int attrHeight;
    
    
    private SimulationPanelGUI spgui;
    private Environment env;
    private int currentMode;
    private Timer timer;
    private int animationSpeed;
    private double scalingFactor;
    private double angle;
    private int intensity;
    private int radius;
    private int blinking;
    private boolean isIn;
    private double envWidth;
    private double envHeight;
    private double envX;
    private double envY;
    
    private PairOfDouble auxPoint;
    private PairOfDouble beginPoint;
    private PairOfDouble cursor;
    private PairOfDouble insertPoint;
    private PairOfDouble vect;
    private PairOfDouble positionPoint;
    private int insertType;
    private int insertState;
    private Particle modifiedParticle;
    private Attractor modifiedAttractor;
    private int selectedItem;
    private Rectangle selectionRectangle;
    private Attractor attr;
    private Stroke lineStroke;
    private int attractorPlacement;
    private Attractor attractor;
    private ParticleSimulatorPopupMenu popup;
    private boolean showDirection;
    private boolean showPosition;
    private boolean showIntensity;
    
    public static final void angleToVector(double angle, PairOfDouble p) {
        p.x = Math.sin(angle);
        p.y = -Math.cos(angle);
    }
    
    public static final double vectorToAngle(PairOfDouble vect) {
        double angle = Math.atan2(vect.x, -vect.y);
        if (angle < 0)
            angle += 2*Math.PI;
        return angle;
    }
    
    public static final Image loadImage(Component c, String s) {
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        URL imgURL = SimulationPanel.class.getResource(s);
        
        Image img;
        if(imgURL == null) {
            img = tk.getImage(s);
        } else {
            img = tk.getImage(imgURL);
        }
        
        MediaTracker mediaTracker = new MediaTracker(c);
        mediaTracker.addImage(img, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return img;
    }
    
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        
        image = new ImageIcon(image).getImage();
        boolean hasAlpha = hasAlpha(image);
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException ex) {
            ex.printStackTrace();
        }
        
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return bimage;
    }
    
    private static boolean hasAlpha(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }
    
    
    public SimulationPanel(int width, int height, int initialSpeed, SimulationPanelGUI gui) throws SimulationPanelInvalidParameterException {
        if(initialSpeed < 0)
            throw new SimulationPanelInvalidParameterException();
        
        setLayout(null);
        this.spgui = gui;
        envWidth = width;
        envHeight = height;
        envX = 0;
        envY = 0;
        //resizePanel((int)envWidth, (int)envHeight);
        env = new Environment(envX, envY, envWidth, envHeight);
        timer = new Timer(0, this);
        setAnimationSpeed(initialSpeed);
        
        this.popup = spgui.getPopupMenu();
        popup.setInvoker(this);
        isIn = false;
        showDirection = false;
        showIntensity = false;
        showPosition = false;
        
        ImgAttractorSingle = loadImage(this, "images/attr_single.png");
        ImgAttractorDouble = loadImage(this, "images/attr_double.png");
        ImgAttractorBreaker = loadImage(this, "images/attr_breaker.png");
        ImgAttractorAll = loadImage(this, "images/attr_all.png");
        
        
        ImgSelAttractor = toBufferedImage(ImgAttractorSingle);
        int i = ((BufferedImage)ImgSelAttractor).getWidth(), j;
        for (i = 0; i < ((BufferedImage)ImgSelAttractor).getWidth(); i++) {
            for (j = 0; j < ((BufferedImage)ImgSelAttractor).getHeight(); j++) {
                if((((BufferedImage)ImgSelAttractor).getRGB(i, j) & 0xFFFFFF) != 0)
                    ((BufferedImage)ImgSelAttractor).setRGB(i, j, AYELLOW.getRGB());
            }
        }
        
        attrWidth = ImgAttractorSingle.getWidth(null);
        attrHeight = ImgAttractorSingle.getHeight(null);
        
        selectionRectangle = new Rectangle();
        beginPoint = null;
        cursor = new PairOfDouble(0.0d, 0.0d);
        auxPoint = new PairOfDouble(0.0d, 0.0d);
        insertPoint = new PairOfDouble(0.0d, 0.0d);
        vect = new PairOfDouble(0.0d, 0.0d);
        positionPoint = new PairOfDouble(0.0d, 0.0d);
        
        SimulationPanelMouseListener listener = new SimulationPanelMouseListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        setMode(MODE_EDIT);
        lineStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);
        
        addComponentListener(new ComponentAdapter() {
            
            public void componentResized(ComponentEvent e) {
                scalingFactor = Math.min(getWidth()/envWidth, getHeight()/envHeight);
                repaint();
            }
        });
        
        resizePanel((int)envWidth, (int)envHeight);
        spgui.notifyEvent(SimulationPanelGUI.ENVIRONMENT_CREATED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    private void resizePanel(int width, int height) throws SimulationPanelInvalidParameterException {
        if((width <= 0) || (height <= 0))
            throw new SimulationPanelInvalidParameterException();
        
        //scalingFactor = ((double)width)/((double)envWidth);
        Dimension d = new Dimension(width, height);
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
        setSize(d);
        
    }
    
    public void resizeEnvironment(int width, int height, boolean force) throws SimulationPanelOutOfBoundsException, SimulationPanelInvalidParameterException {
        
        if ((width<=0) || (height <=0))
            throw new SimulationPanelInvalidParameterException();
        
        if((width == envWidth) && (height == envHeight))
            return;
        
        if((!env.setBoundary(0, 0, width, height))){
            if(force) {
                Rectangle r = new Rectangle((int)envX, height, (int)envWidth, (int)(envHeight - height));
                clearRectangle(r);
                r.setRect(width, envY, envWidth - width, envHeight);
                clearRectangle(r);
                env.setBoundary(0, 0, width, height);
            } else {
                throw new SimulationPanelOutOfBoundsException();
            }
            
        }
        selectedItem = SELECTED_NONE;
        envWidth = width;
        envHeight = height;
        scalingFactor = Math.min(getWidth()/envWidth, getHeight()/envHeight);
        spgui.notifyEvent(SimulationPanelGUI.DIMENSIONS_CHANGED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        if(currentMode == MODE_PLAY)
            advance();
        else if((currentMode == MODE_INSERT) || (currentMode == MODE_MODIFY)){
            blinking--;
            repaint();
            if(blinking == 0) {
                timer.stop();
            }
        }
    }
    
    public Rectangle getEnvironmentRectangle() {
        return new Rectangle((int)envX, (int)envY, (int)envWidth, (int)envHeight);
    }
    
    public Object getSelected(){
        switch(selectedItem) {
            case SELECTED_RECTANGLE:
                return selectionRectangle;
            case SELECTED_ATTR_FLOAT:
            case SELECTED_ATTR_WALL:
                return attractor;
            case SELECTED_NONE:
                return null;
            default:
                return env.getParticle(selectedItem);
        }
        
    }
    
    public void cancelOperation() {
        switch(currentMode) {
            case MODE_MODIFY:
                switch(insertState) {
                    case PARTICLE_POSITION:
                    case PARTICLE_VECTOR:
                        env.addParticle(modifiedParticle);
                        break;
                    case ATTRACTOR_POSITION:
                    case ATTRACTOR_INTENSITY:
                        attractor = modifiedAttractor;
                        break;
                }
            case MODE_INSERT:
                setMode(MODE_EDIT);
                repaint();
                spgui.notifyEvent(SimulationPanelGUI.OPERATION_CANCELED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                break;
        }
    }
    
    public void enterInsertMode(int type) throws SimulationPanelWrongTypeException {
        
        if((type != Particle.SINGLE)&&(type != Particle.DOUBLE)&&(type != Particle.BREAKER)&&(type != Attractor.SINGLE)&&(type != Attractor.DOUBLE)&&(type != Attractor.BREAKER)&&(type != Attractor.ALL))
            throw new SimulationPanelWrongTypeException();
        
        setMode(MODE_INSERT);
        insertType = type;
        selectionRectangle.setSize(0, 0);
        
        if ( (type == Attractor.SINGLE) || (type == Attractor.DOUBLE) || (type == Attractor.BREAKER) || (type == Attractor.ALL) ) {
            insertState = ATTRACTOR_POSITION;
            attractorPlacement = Attractor.FLOATING;
            attractor = null;
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_POSITIONING, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        } else {
            insertState = PARTICLE_POSITION;
            spgui.notifyEvent(SimulationPanelGUI.PARTICLE_POSITIONING, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        }
        
        repaint();
    }
    
    public void enterModifyMode(Particle particle, int particleModification) throws SimulationPanelWrongTypeException {
        if(particle == null)
            throw new NullPointerException();
        
        if((particleModification != PARTICLE_POSITION) && (particleModification != PARTICLE_VECTOR))
            throw new SimulationPanelWrongTypeException();
        
        setMode(MODE_MODIFY);
        modifiedParticle = particle;
        env.removeParticle(particle);
        insertState = particleModification;
        insertType = particle.getType();
        if(particleModification == PARTICLE_VECTOR) {
            insertPoint.x = particle.getCenter().x;
            insertPoint.y = particle.getCenter().y;
            spgui.notifyEvent(SimulationPanelGUI.PARTICLE_DIRECTING, (int)Math.toDegrees(angle), SimulationPanelGUI.DUMMY_PARAMETER);
        } else {
            spgui.notifyEvent(SimulationPanelGUI.PARTICLE_POSITIONING, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        }
        repaint();
    }
    
    public void enterModifyMode(int attractorModification) throws SimulationPanelWrongTypeException {
        
        if((attractorModification != ATTRACTOR_POSITION) && (attractorModification != ATTRACTOR_INTENSITY))
            throw new SimulationPanelWrongTypeException();
        
        setMode(MODE_MODIFY);
        modifiedAttractor = attractor;
        attractor = null;
        insertState = attractorModification;
        attractorPlacement = modifiedAttractor.getPlacement();
        if(attractorModification == ATTRACTOR_INTENSITY) {
            insertPoint.x = modifiedAttractor.getPosition().x;
            insertPoint.y = modifiedAttractor.getPosition().y;
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_STRENGTH, modifiedAttractor.getIntensity(), SimulationPanelGUI.DUMMY_PARAMETER);
        } else {
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_POSITIONING, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        }
        repaint();
        
    }
    
    
    public void modifySelected(int property) throws SimulationPanelWrongTypeException {
        
        if((property != MODIFY_POSITION) && (property != MODIFY_DIRECTION_INTENSITY))
            throw new SimulationPanelWrongTypeException();
        
        switch(selectedItem) {
            case SELECTED_ATTR_FLOAT:
            case SELECTED_ATTR_WALL:
                if(property == MODIFY_POSITION)
                    property = ATTRACTOR_POSITION;
                else
                    property = ATTRACTOR_INTENSITY;
                try {
                    enterModifyMode(property);
                } catch (SimulationPanelWrongTypeException ex) {
                    ex.printStackTrace();
                }
                break;
            case SELECTED_NONE:
            case SELECTED_RECTANGLE:
                break;
            default:
                if(property == MODIFY_POSITION)
                    property = PARTICLE_POSITION;
                else
                    property = PARTICLE_VECTOR;
                
                enterModifyMode(env.getParticle(selectedItem), property);
                
                break;
        }
    }
    
    
    
    public void insertAttractor(int attracts, int intensity, PairOfDouble position) throws SimulationPanelWrongTypeException, SimulationPanelOutOfBoundsException {
        
        if( (position.x >= envWidth) || (position.y >= envHeight) || (position.x < envX ) || (position.y < envY) )
            throw new SimulationPanelOutOfBoundsException();
        
        boolean modifying = (attractor != null);
        
        try {
            attractor = new Attractor(attracts, Math.max(1, Math.min(100, intensity)), position);
        } catch (SimulationPanelInvalidParameterException ex) {
            ex.printStackTrace();
        }
        
        if(modifying)
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        else
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_INSERTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        selectedItem = SELECTED_NONE;
        repaint();
    }
    
    
    public void insertAttractor(int attracts, int intensity, int placement) throws SimulationPanelWrongTypeException {
        
        boolean modifying = (attractor != null);
        
        try {
            attractor = new Attractor(attracts, Math.max(1, Math.min(100, intensity)),  placement);
            
        } catch (SimulationPanelInvalidParameterException ex) {
            ex.printStackTrace();
        }
        
        if(modifying)
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        else
            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_INSERTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        selectedItem = SELECTED_NONE;
        attractorPlacement = placement;
        repaint();
    }
    
    public void insertParticle(int type, PairOfDouble pos, PairOfDouble dir) throws SimulationPanelOutOfBoundsException, SimulationPanelWrongTypeException, SimulationPanelBadInsertionPointException {
        if( (type != Particle.SINGLE) && (type != Particle.DOUBLE) && (type != Particle.BREAKER) )
            throw new SimulationPanelWrongTypeException();
        
        insertParticle(new Particle(type, pos, dir));
    }
    
    
    public void insertParticle(Particle particle) throws SimulationPanelOutOfBoundsException, SimulationPanelBadInsertionPointException {
        if(particle == null)
            throw new NullPointerException();
        
        for(int i = env.getNumber() - 1; i >= 0; i--) {
            if(Particle.isHit(particle, env.getParticle(i)))
                throw new SimulationPanelBadInsertionPointException();
        }
        
        if(!env.addParticle(particle)) {
            throw new SimulationPanelOutOfBoundsException();
        }
        
        selectedItem = SELECTED_NONE;
        spgui.notifyEvent(SimulationPanelGUI.PARTICLE_INSERTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
        
    }
    
    
    public void modifyParticle(Particle p, int type, PairOfDouble pos, PairOfDouble dir) throws SimulationPanelOutOfBoundsException, SimulationPanelBadInsertionPointException, SimulationPanelWrongTypeException {
        if(p == null)
            throw new NullPointerException();
        
        if( (type != Particle.SINGLE) && (type != Particle.DOUBLE) && (type != Particle.BREAKER) )
            throw new SimulationPanelWrongTypeException();
        
        Particle tmp = new Particle(p);
        tmp.setType(type);
        tmp.setCenter(pos);
        tmp.setDirection(dir);
        
        env.removeParticle(p);
        
        if(!env.addParticle(tmp)) {
            env.addParticle(p);
            throw new SimulationPanelOutOfBoundsException();
        }
        env.removeParticle(tmp);
        
        for(int i = env.getNumber() - 1; i >= 0; i--) {
            if(Particle.isHit(tmp, env.getParticle(i))) {
                env.addParticle(p);
                throw new SimulationPanelBadInsertionPointException();
            }
        }
        
        p.setType(type);
        p.setCenter(pos);
        p.setDirection(dir);
        env.addParticle(p);
        selectedItem = SELECTED_NONE;
        spgui.notifyEvent(SimulationPanelGUI.PARTICLE_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    public void removeParticle(Particle p) {
        if(p == null)
            throw new NullPointerException();
        
        selectedItem = SELECTED_NONE;
        env.removeParticle(p);
        spgui.notifyEvent(SimulationPanelGUI.PARTICLE_DELETED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    public void removeAttractor() {
        selectedItem = SELECTED_NONE;
        attractor = null;
        spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_DELETED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    public void removeSelected() {
        
        switch(selectedItem) {
            case SELECTED_ATTR_FLOAT:
            case SELECTED_ATTR_WALL:
                removeAttractor();
                break;
            case SELECTED_RECTANGLE:
                clearRectangle(selectionRectangle);
                spgui.notifyEvent(SimulationPanelGUI.AREA_EMPTIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                break;
            case SELECTED_NONE:
                break;
            default:
                removeParticle(env.getParticle(selectedItem));
        }
    }
    
    
    public void fillSelectionRectangle(int area, int p1, int p2, int p3) throws SimulationPanelInvalidParameterException, SimulationPanelWrongTypeException, SimulationPanelOutOfBoundsException {
        
        if( (area!= FILL_RECTANGLE) && (area!= FILL_ENVIRONMENT))
            throw new SimulationPanelWrongTypeException();
        
        if(area == FILL_ENVIRONMENT) {
            if (!env.isEmptyArea(envX, envY, envWidth, envHeight))
                throw new SimulationPanelOutOfBoundsException();
            if(!env.fillArea(envX, envY, envWidth, envHeight, p1, p2, p3))
                throw new SimulationPanelInvalidParameterException();
        } else if(area == FILL_RECTANGLE) {
            if (!env.isEmptyArea(selectionRectangle.getMinX(), selectionRectangle.getMinY(), selectionRectangle.getMaxX(), selectionRectangle.getMaxY()))
                throw new SimulationPanelOutOfBoundsException();
            if(!env.fillArea(selectionRectangle.getMinX(), selectionRectangle.getMinY(), selectionRectangle.getMaxX(), selectionRectangle.getMaxY(), p1, p2, p3))
                throw new SimulationPanelInvalidParameterException();
        }
        
        spgui.notifyEvent(SimulationPanelGUI.AREA_FILLED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
        
    }
    
    private void clearRectangle(Rectangle rect) {
        if(rect == null)
            throw new NullPointerException();
        
        for(int i = env.getNumber() - 1; i >= 0 ; --i)
            if(env.getParticle(i).isPartInside(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY()))
                env.removeParticle(env.getParticle(i));
        
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.DARK_GRAY);
        
        int i, d = Math.max(getWidth(), getHeight());
        
        for(i = 0; i <= d; i+=NET_WIDTH) {
            g2d.drawLine(i, 0, d + i, d);
            g2d.drawLine(-i, 0, d - i, d);
        }
        
        d*=2;
        
        for(i = 0; i <= d; i+=NET_WIDTH) {
            g2d.drawLine(i, 0, 0, i);
        }
        
        
        Stroke oldStroke = g2d.getStroke();
        g2d.scale(scalingFactor, scalingFactor);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, (int)envWidth, (int)envHeight);
        
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if((attractor != null) && (attractor.getPlacement() != Attractor.FLOATING)) {
            drawAttractorWall(attractor.getPlacement(), attractor.getAttracts(), g2d, false);
        }
        
        for(i = env.getNumber() - 1; i >= 0 ; --i)
            drawParticle(env.getParticle(i), g2d, false);
        
        if((attractor != null) && (attractor.getPlacement() == Attractor.FLOATING)) {
            drawAttractorFloating(attractor.getAttracts(), attractor.getPosition(), g2d, false);
        }
        
        if((currentMode == MODE_INSERT) || (currentMode == MODE_EDIT) || (currentMode == MODE_MODIFY)) {
            if(showDirection) {
                Particle p;
                for(i = env.getNumber() - 1; i >= 0 ; --i) {
                    p = env.getParticle(i);
                    drawDirection(p.getCenter(), p.getDirection(), p.getRadius(), g2d, lineStroke, oldStroke);
                }
            }
            if(showPosition) {
                Particle p;
                PairOfDouble c;
                String s;
                int h, w;
                FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
                for(i = env.getNumber() - 1; i >= 0 ; --i) {
                    p = env.getParticle(i);
                    c = p.getCenter();
                    s = "(" + (int)c.x + ", " + (int)c.y + ")";
                    h = metrics.getHeight();
                    w = metrics.stringWidth(s);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.drawString(s, (int)(c.x - w/2), (int)(c.y + p.getRadius() + h));
                }
            }
            if(showIntensity && attractor != null) {
                if(attractor.getPlacement() == Attractor.FLOATING)
                    drawIntensity(attractor.getPosition(), (((attractor.getIntensity() - 1)*(RADIUS_FLOAT_MAX-RADIUS_FLOAT_MIN))/99) + RADIUS_FLOAT_MIN, g2d, lineStroke, oldStroke);
                else {
                    switch(attractor.getPlacement()) {
                        case Attractor.WALL_DOWN:
                            positionPoint.x = envWidth/2;
                            positionPoint.y = envHeight;
                            break;
                        case Attractor.WALL_LEFT:
                            positionPoint.x = envX;
                            positionPoint.y = envHeight/2;
                            break;
                        case Attractor.WALL_UP:
                            positionPoint.x = envWidth/2;
                            positionPoint.y = envY;
                            break;
                        case Attractor.WALL_RIGHT:
                            positionPoint.x = envWidth;
                            positionPoint.y = envHeight/2;
                            break;
                            
                    }
                    drawIntensity(positionPoint, (((attractor.getIntensity() - 1)*(RADIUS_WALL_MAX-RADIUS_WALL_MIN))/99) + RADIUS_WALL_MIN, g2d, lineStroke, oldStroke);
                }
            }
        }
        
        
        if((currentMode == MODE_INSERT) || (currentMode == MODE_MODIFY)) {
            
            switch(insertState) {
                case PARTICLE_POSITION:
                    if((blinking % 2 == 0) && isIn)
                        drawParticle(insertType, cursor, g2d, true);
                    break;
                case PARTICLE_VECTOR:
                    drawParticle(insertType, insertPoint, g2d, true);
                    drawDirection(insertPoint, vect, Particle.getRadius(insertType),  g2d, lineStroke, oldStroke);
                    
                    break;
                case ATTRACTOR_POSITION:
                    if(isIn) {
                        if(attractorPlacement == Attractor.FLOATING)
                            drawAttractorFloating(insertType, cursor, g2d, true);
                        else
                            drawAttractorWall(attractorPlacement, insertType, g2d, true);
                    }
                    break;
                case ATTRACTOR_INTENSITY:
                    
                    if(attractorPlacement == Attractor.FLOATING)
                        drawAttractorFloating(insertType, insertPoint, g2d, true);
                    else
                        drawAttractorWall(attractorPlacement, insertType, g2d, true);
                    
                    drawIntensity(insertPoint, radius, g2d, lineStroke, oldStroke);
                    break;
            }
        } else if(currentMode == MODE_EDIT) {
            
            switch(selectedItem) {
                case SELECTED_ATTR_FLOAT:
                    drawImg(ImgSelAttractor, attractor.getPosition(), g2d, true);
                    break;
                case SELECTED_ATTR_WALL:
                    drawAttractorWall(attractorPlacement, AYELLOW, AYELLOW, g2d);
                    break;
                case SELECTED_RECTANGLE:
                    int width = (int)Math.max(1, selectionRectangle.getWidth());
                    int height = (int)Math.max(1, selectionRectangle.getHeight());
                    g2d.setColor(rectColor);
                    g2d.fillRect((int)selectionRectangle.getMinX(), (int)selectionRectangle.getMinY(), width, height);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect((int)selectionRectangle.getMinX(), (int)selectionRectangle.getMinY(), width, height);
                    break;
                case SELECTED_NONE:
                    break;
                default:
                    Particle p = env.getParticle(selectedItem);
                    PairOfDouble c = p.getCenter();
                    double r = p.getRadius();
                    g2d.setColor(AYELLOW);
                    g2d.fillOval((int)(c.x - r), (int)(c.y - r), (int)r*2, (int)r*2);
                    g2d.drawOval((int)(c.x - r), (int)(c.y - r), (int)r*2, (int)r*2);
            }
        }
        
        
    }
    
    private void drawAttractorFloating(int type, PairOfDouble position, Graphics2D g2d, boolean transparent) {
        switch(type) {
            case Attractor.SINGLE:
                drawImg(ImgAttractorSingle, position, g2d, transparent);
                break;
            case Attractor.DOUBLE:
                drawImg(ImgAttractorDouble, position, g2d, transparent);
                break;
            case Attractor.BREAKER:
                drawImg(ImgAttractorBreaker, position, g2d, transparent);
                break;
            default:
                drawImg(ImgAttractorAll, position, g2d, transparent);
                break;
        }
    }
    
    private void drawIntensity(PairOfDouble pos, double radius, Graphics2D g, Stroke stroke, Stroke oldStroke) {
        g.setClip((int)envX, (int)envY, (int)envWidth, (int)envHeight);
        g.setStroke(stroke);
        g.setColor(Color.DARK_GRAY);
        g.drawOval((int)(pos.x - radius), (int)(pos.y - radius), (int)(radius * 2), (int)(radius * 2));
        g.setStroke(oldStroke);
        g.setClip((int)envX, (int)envY, getWidth(), getHeight());
    }
    
    private void drawDirection(PairOfDouble pos, PairOfDouble dir, double radius, Graphics2D g, Stroke stroke, Stroke oldStroke) {
        g.setColor(Color.DARK_GRAY);
        double angle = vectorToAngle(dir);
        double scaledDirX = dir.x*DIRECTION_LENGTH;
        double scaledDirY = dir.y*DIRECTION_LENGTH;
        g.drawLine((int)(dir.x*(radius + DIRECTION_SHIFT)+pos.x), (int)(dir.y*(radius + DIRECTION_SHIFT)+pos.y), (int)(scaledDirX+pos.x), (int)(scaledDirY+pos.y));
        g.setStroke(stroke);
        g.drawLine((int)(scaledDirX+pos.x+Math.cos(angle+Math.PI/4)*10), (int)(scaledDirY+pos.y+Math.sin(angle+Math.PI/4)*10), (int)(scaledDirX+pos.x), (int)(scaledDirY+pos.y));
        g.drawLine((int)(scaledDirX+pos.x+Math.cos(angle+3*Math.PI/4)*10), (int)(scaledDirY+pos.y+Math.sin(angle+3*Math.PI/4)*10), (int)(scaledDirX+pos.x), (int)(scaledDirY+pos.y));
        g.setStroke(oldStroke);
    }
    
    private void drawParticle(Particle p ,Graphics g, boolean transparent) {
        drawParticle(p.getType(), p.getRadius(), p.getCenter(), g, transparent);
    }
    
    private void drawParticle(int type, PairOfDouble position, Graphics g, boolean transparent) {
        drawParticle(type, Particle.getRadius(type), position, g, transparent);
        
    }
    
    private void drawParticle(int type, double radius, PairOfDouble position, Graphics g, boolean transparent) {
        switch(type) {
            case Particle.SINGLE:
                if (transparent)
                    g.setColor(ARED);
                else
                    g.setColor(Color.RED);
                break;
            case Particle.DOUBLE:
                if (transparent)
                    g.setColor(ABLUE);
                else
                    g.setColor(Color.BLUE);
                break;
            default:
                if (transparent)
                    g.setColor(AGREEN);
                else
                    g.setColor(Color.GREEN);
        }
        g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(radius * 2), (int)(radius * 2));
        if (transparent)
            g.setColor(ABLACK);
        else
            g.setColor(Color.BLACK);
        g.drawOval((int)(position.x - radius), (int)(position.y - radius), (int)(radius * 2), (int)(radius * 2));
    }
    
    private void drawImg(Image img, PairOfDouble position, Graphics2D g, boolean transparent) {
        int x = (int)(position.x - img.getWidth(null)/2);
        int y = (int)(position.y - img.getHeight(null)/2);
        if(transparent)
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
        
        g.drawImage(img, x, y, this);
        
        if(transparent)
            g.setComposite(AlphaComposite.Src);
    }
    
    private void drawAttractorWall(int placement, Color c, Color l, Graphics2D g) {
        g.setColor(c);
        switch(placement) {
            case Attractor.WALL_DOWN:
                g.fillRect((int)envX, (int)(envHeight-THICKNESS), (int)envWidth, THICKNESS);
                g.setColor(l);
                g.drawLine((int)envX, (int)(envHeight-THICKNESS), (int)envWidth, (int)(envHeight-THICKNESS));
                break;
            case Attractor.WALL_LEFT:
                g.fillRect((int)envX, (int)envY, THICKNESS, (int)envHeight);
                g.setColor(l);
                g.drawLine(THICKNESS, (int)envY, THICKNESS, (int)envHeight);
                break;
            case Attractor.WALL_UP:
                g.fillRect((int)envX, (int)envY, (int)envWidth, THICKNESS);
                g.setColor(l);
                g.drawLine((int)envX, THICKNESS, (int)envWidth, THICKNESS);
                break;
            default:
                g.fillRect((int)(envWidth-THICKNESS), (int)envY, THICKNESS, (int)envHeight);
                g.setColor(l);
                g.drawLine((int)(envWidth-THICKNESS), (int)envY, (int)(envWidth-THICKNESS), (int)envHeight);
                break;
        }
    }
    
    private void drawAttractorWall(int placement, int attracts, Graphics2D g, boolean transparent) {
        Color c, l;
        switch(attracts) {
            case Attractor.SINGLE:
                if(transparent) {
                    c = ARED;
                } else {
                    c = Color.RED;
                }
                break;
            case Attractor.DOUBLE:
                if(transparent) {
                    c = ABLUE;
                } else {
                    c = Color.BLUE;
                }
                break;
            case Attractor.BREAKER:
                if(transparent) {
                    c = AGREEN;
                } else {
                    c = Color.GREEN;
                }
                break;
            default:
                if(transparent) {
                    c = AGRAY;
                } else {
                    c = Color.GRAY;
                }
                break;
        }
        if(transparent)
            l = ABLACK;
        else
            l= Color.BLACK;
        drawAttractorWall(placement, c, l, g);
    }
    
    
    
    private void advance() {
        if(attractor != null) {
            
            int attracts = 0;
            boolean all = false;
            switch(attractor.getAttracts()) {
                case Attractor.SINGLE:
                    attracts = Particle.SINGLE;
                    break;
                case Attractor.DOUBLE:
                    attracts = Particle.DOUBLE;
                    break;
                case Attractor.BREAKER:
                    attracts = Particle.BREAKER;
                    break;
                default:
                    all = true;
                    break;
            }
            
            switch(attractor.getPlacement()) {
                case Attractor.WALL_DOWN:
                    if(all)
                        env.attract(Environment.TOP, attractor.getIntensity());
                    else
                        env.attract(Environment.TOP, attractor.getIntensity(), attracts);
                    break;
                case Attractor.WALL_LEFT:
                    if(all)
                        env.attract(Environment.LEFT, attractor.getIntensity());
                    else
                        env.attract(Environment.LEFT, attractor.getIntensity(), attracts);
                    break;
                case Attractor.WALL_UP:
                    if(all)
                        env.attract(Environment.BOTTOM, attractor.getIntensity());
                    else
                        env.attract(Environment.BOTTOM, attractor.getIntensity(), attracts);
                    break;
                case Attractor.WALL_RIGHT:
                    if(all)
                        env.attract(Environment.RIGHT, attractor.getIntensity());
                    else
                        env.attract(Environment.RIGHT, attractor.getIntensity(), attracts);
                    break;
                default:
                    if(all)
                        env.attract(attractor.getPosition(), attractor.getIntensity());
                    else
                        env.attract(attractor.getPosition(), attractor.getIntensity(), attracts);
                    break;
                    
            }
            attractor = null;
        }
        env.moveOneStep();
        repaint();
        
        
    }
    
    public void nextStep() {
        spgui.notifyEvent(SimulationPanelGUI.ONE_STEP, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        setMode(MODE_EDIT);
        advance();
    }
    
    public void resetEnvironment(){
        selectedItem = SELECTED_NONE;
        attractor = null;
        env.emptyArea(envX, envY, envWidth, envHeight);
        setMode(MODE_EDIT);
        spgui.notifyEvent(SimulationPanelGUI.ENVIRONMENT_RESETTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        repaint();
    }
    
    private void playSimulation() {
        spgui.notifyEvent(SimulationPanelGUI.SIMULATION_PLAY, animationSpeed, SimulationPanelGUI.DUMMY_PARAMETER);
        setMode(MODE_PLAY);
        timer.start();
    }
    
    private void pauseSimulation() {
        spgui.notifyEvent(SimulationPanelGUI.SIMULATION_PAUSE, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
        setMode(MODE_EDIT);
        timer.stop();
        repaint();
    }
    
    public void playPauseSimulation() {
        switch(currentMode) {
            case MODE_PLAY:
                pauseSimulation();
                break;
            case MODE_EDIT:
                playSimulation();
                break;
        }
    }
    
    public boolean isPlaying() {
        return currentMode == MODE_PLAY;
    }
    
    public void setAnimationSpeed(int value) {
        value = Math.max(1, Math.min(SPEED_TICKS, value));
        animationSpeed = value;
        if(currentMode == MODE_PLAY) {
            timer.setDelay((SPEED_TICKS - value) * 10);
            spgui.notifyEvent(SimulationPanelGUI.SIMULATION_PLAY, animationSpeed, SimulationPanelGUI.DUMMY_PARAMETER);
        }
    }
    
    
    public int getAnimationSpeed() {
        return animationSpeed;
    }
    
    
    void mouseClicked(int x, int y, int button) {
        double scaledx = scalex(x);
        double scaledy = scaley(y);
        if(button == MouseEvent.BUTTON1) {
            if((currentMode == MODE_INSERT) || (currentMode == MODE_MODIFY)) {
                
                switch(insertState) {
                    case PARTICLE_POSITION:
                        insertPoint.x = scaledx;
                        insertPoint.y = scaledy;
                        Particle tmp = new Particle(insertType, insertPoint.x, insertPoint.y, 0, 0);
                        int i;
                        for(i = env.getNumber() - 1; i >= 0; i--) {
                            if(Particle.isHit(tmp, env.getParticle(i)))
                                break;
                        }
                        
                        if((i < 0) && (tmp.isTotalInside(envX, envY, envWidth, envHeight))) {
                            if(currentMode == MODE_MODIFY) {
                                modifiedParticle.setCenter(insertPoint.x, insertPoint.y);
                                try {
                                    insertParticle(modifiedParticle);
                                    spgui.notifyEvent(SimulationPanelGUI.PARTICLE_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                                } catch (SimulationPanelOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                } catch (SimulationPanelBadInsertionPointException ex) {
                                    ex.printStackTrace();
                                }
                                setMode(MODE_EDIT);
                            } else {
                                insertState = PARTICLE_VECTOR;
                                updateCursorState();
                            }
                        } else {
                            blinking = 10;
                            timer.setDelay(100);
                            timer.start();
                        }
                        break;
                        
                    case PARTICLE_VECTOR:
                        PairOfDouble dir = new PairOfDouble(cursor.x - insertPoint.x, cursor.y - insertPoint.y);
                        if((dir.x == 0.0) && (dir.y == 0.0)) {
                            dir.y = -1.0;
                        }
                        
                        try {
                            insertParticle(insertType, new PairOfDouble(insertPoint), dir);
                            if(currentMode == MODE_MODIFY)
                                spgui.notifyEvent(SimulationPanelGUI.PARTICLE_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                        } catch (SimulationPanelOutOfBoundsException ex) {
                            ex.printStackTrace();
                        } catch (SimulationPanelWrongTypeException ex) {
                            ex.printStackTrace();
                        } catch (SimulationPanelBadInsertionPointException ex) {
                            ex.printStackTrace();
                        }
                        
                        setMode(MODE_EDIT);
                        break;
                        
                    case ATTRACTOR_POSITION:
                        switch (attractorPlacement) {
                            case Attractor.WALL_DOWN:
                                insertPoint.x = envWidth/2;
                                insertPoint.y = envHeight;
                                break;
                            case Attractor.WALL_LEFT:
                                insertPoint.x = envX;
                                insertPoint.y = envHeight/2;
                                break;
                            case Attractor.WALL_UP:
                                insertPoint.x = envWidth/2;
                                insertPoint.y = envY;
                                break;
                            case Attractor.WALL_RIGHT:
                                insertPoint.x = envWidth;
                                insertPoint.y = envHeight/2;
                                break;
                            default:
                                insertPoint.x = scaledx;
                                insertPoint.y = scaledy;
                                break;
                        }
                        
                        if(currentMode == MODE_MODIFY) {
                            
                            if(attractorPlacement == Attractor.FLOATING)
                                
                                try {
                                    insertAttractor(modifiedAttractor.getAttracts(), modifiedAttractor.getIntensity(), new PairOfDouble(insertPoint));
                                    
                                } catch (SimulationPanelOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                } catch (SimulationPanelWrongTypeException ex) {
                                    ex.printStackTrace();
                                } else
                                    
                                    try {
                                        insertAttractor(modifiedAttractor.getAttracts(), modifiedAttractor.getIntensity(), attractorPlacement);
                                    } catch (SimulationPanelWrongTypeException ex) {
                                        ex.printStackTrace();
                                    }
                            
                            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                            setMode(MODE_EDIT);
                        } else {
                            insertState = ATTRACTOR_INTENSITY;
                            updateCursorState();
                        }
                        break;
                    case ATTRACTOR_INTENSITY:
                        
                        if(attractorPlacement == Attractor.FLOATING)
                            
                            try {
                                insertAttractor(insertType, intensity,  new PairOfDouble(insertPoint));
                            } catch (SimulationPanelOutOfBoundsException ex) {
                                ex.printStackTrace();
                            } catch (SimulationPanelWrongTypeException ex) {
                                ex.printStackTrace();
                            } else
                                
                                try {
                                    insertAttractor(insertType, intensity, attractorPlacement);
                                } catch (SimulationPanelWrongTypeException ex) {
                                    ex.printStackTrace();
                                }
                        
                        if(currentMode == MODE_MODIFY)
                            spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_MODIFIED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                        
                        setMode(MODE_EDIT);
                        
                        break;
                }
            } else if(currentMode == MODE_EDIT) {
                select(scaledx, scaledy, button);
            }
        }
        if(isIn)
            popup.hide();
        repaint();
        if(isIn)
            triggerPopup(x, y, button, (int)scaledx, (int)scaledy);
    }
    
    void mouseDragged(int x, int y) {
        double scaledx = scalex(x);
        double scaledy = scaley(y);
        cursor.x = scaledx;
        cursor.y = scaledy;
        isIn = (scaledx < envWidth) && (scaledy < envHeight) && (scaledx >= envX) && (scaledy >= envY);
        if(currentMode == MODE_EDIT && beginPoint != null) {
            double minX = Math.min(beginPoint.x, Math.max(scaledx, envX));
            double minY = Math.min(beginPoint.y, Math.max(scaledy, envY));
            double maxX = Math.max(beginPoint.x, Math.min(scaledx, envWidth - 1));
            double maxY = Math.max(beginPoint.y, Math.min(scaledy, envHeight - 1));
            selectionRectangle.setRect(minX, minY, maxX - minX,  maxY - minY);
            spgui.notifyEvent(SimulationPanelGUI.AREA_SELECTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
            selectedItem = SELECTED_RECTANGLE;
        } else if ((currentMode == MODE_INSERT) || (currentMode == MODE_INSERT)) {
            updateCursorState();
        }
        if(isIn) spgui.notifyEvent(SimulationPanelGUI.COORDS_UPDATE, (int)scaledx, (int)scaledy);
        
        repaint();
    }
    
    void mousePressed(int x, int y, int button) {
        if(currentMode == MODE_EDIT) {
            double scaledx = scalex(x);
            double scaledy = scaley(y);
            if(isIn) {
                beginPoint = auxPoint;
                beginPoint.x = scaledx;
                beginPoint.y = scaledy;
            } else {
                beginPoint = null;
            }
            select(scaledx, scaledy, button);
            repaint();
        }
        
    }
    
    void mouseMoved(int x, int y) {
        double scaledx = scalex(x);
        double scaledy = scaley(y);
        cursor.x = scaledx;
        cursor.y = scaledy;
        updateCursorState();
        isIn = (scaledx < envWidth) && (scaledy < envHeight) && (scaledx >= envX) && (scaledy >= envY);
        if(isIn) {
            spgui.notifyEvent(SimulationPanelGUI.COORDS_UPDATE, (int)scaledx, (int)scaledy);
            if((currentMode == MODE_INSERT) || (currentMode == MODE_MODIFY)) {
                
                if(insertState == ATTRACTOR_POSITION) {
                    if (cursor.x < THICKNESS) {
                        attractorPlacement = Attractor.WALL_LEFT;
                    } else if (cursor.x > envWidth - THICKNESS) {
                        attractorPlacement = Attractor.WALL_RIGHT;
                    } else if (cursor.y < THICKNESS) {
                        attractorPlacement = Attractor.WALL_UP;
                    } else if (cursor.y > envHeight - THICKNESS) {
                        attractorPlacement = Attractor.WALL_DOWN;
                    } else {
                        attractorPlacement = Attractor.FLOATING;
                    }
                }
            }
        }
        
    }
    
    void mouseReleased(int x, int y, int button) {
        if((isIn) || (beginPoint != null))
            triggerPopup(x, y, button, (int)(x/scalingFactor), (int)(y/scalingFactor));
    }
    
    void mouseEntered() {
        //isIn = true;
    }
    
    void mouseExited() {
        isIn = false;
        repaint();
    }
    
    private double scalex(double x) {
        //return Math.max(envX, Math.min(envWidth - 1, x/scalingFactor));
        return x/scalingFactor;
    }
    
    private double scaley(double y) {
        //return Math.max(envY, Math.min(envHeight - 1, y/scalingFactor));
        return y/scalingFactor;
    }
    
    private void select(double scaledx, double scaledy, int button) {
        boolean found = false;
        if( (selectedItem != SELECTED_RECTANGLE) || (!selectionRectangle.contains(scaledx, scaledy)) || (button != MouseEvent.BUTTON3)) {
            selectedItem = SELECTED_NONE;
            selectionRectangle.setSize(0, 0);
        } else {
            found = true;
        }
        if((!found) && (attractor!=null)) {
            if(attractor.getPlacement() == Attractor.FLOATING ) {
                if (new Rectangle((int)(attractor.getPosition().x - attrWidth/2), (int)(attractor.getPosition().y - attrHeight/2), (int)attrWidth,(int)attrHeight).contains(scaledx, scaledy)) {
                    found = true;
                    selectedItem = SELECTED_ATTR_FLOAT;
                    spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_SELECTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                }
            }
        }
        if(!found) {
            int i;
            for(i = env.getNumber() - 1; i >= 0; i--) {
                if(env.getParticle(i).isPartInside(scaledx, scaledy, scaledx, scaledy)) {
                    found = true;
                    selectedItem = i;
                    spgui.notifyEvent(SimulationPanelGUI.PARTICLE_SELECTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
                    break;
                }
            }
        }
        if((!found) && (attractor!=null)) {
            if ( ((scaledx < THICKNESS) && (attractor.getPlacement() == Attractor.WALL_LEFT)) ||
                    ((scaledx > envWidth - THICKNESS) && (attractor.getPlacement() == Attractor.WALL_RIGHT)) ||
                    ((scaledy < THICKNESS) && (attractor.getPlacement() == Attractor.WALL_UP)) ||
                    ((scaledy > envHeight - THICKNESS) && (attractor.getPlacement() == Attractor.WALL_DOWN)) ) {
                found = true;
                selectedItem = SELECTED_ATTR_WALL;
                spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_SELECTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
            }
        }
        if(!found)
            spgui.notifyEvent(SimulationPanelGUI.OBJECT_DESELECTED, SimulationPanelGUI.DUMMY_PARAMETER, SimulationPanelGUI.DUMMY_PARAMETER);
    }
    
    private void triggerPopup(int x, int y, int button, int envX, int envY) {
        
        if((button == MouseEvent.BUTTON3) && (currentMode == MODE_EDIT)) {
            switch(selectedItem) {
                case SELECTED_RECTANGLE:
                    popup.show(ParticleSimulatorPopupMenu.RECTANGLE, x, y, envX, envY);
                    break;
                case SELECTED_ATTR_FLOAT:
                case SELECTED_ATTR_WALL:
                    popup.show(ParticleSimulatorPopupMenu.ATTRACTOR, x, y, envX, envY);
                    break;
                case SELECTED_NONE:
                    popup.show(ParticleSimulatorPopupMenu.ENVIRONMENT, x, y, envX, envY);
                    break;
                default:
                    if(selectedItem != SELECTED_RECTANGLE)
                        popup.show(ParticleSimulatorPopupMenu.PARTICLE, x, y, envX, envY);
                    
            }
        }
    }
    
    private void updateCursorState() {
        vect.x = cursor.x - insertPoint.x;
        vect.y = cursor.y - insertPoint.y;
        if((vect.x == 0.0) && (vect.y == 0.0))
            vect.y = -1.0;
        vect.normalize();
        //vect.scale(DIRECTION_LENGTH);
        angle = vectorToAngle(vect);
        
        
        if((currentMode == MODE_INSERT) || (currentMode == MODE_MODIFY)) {
            switch(insertState) {
                case ATTRACTOR_INTENSITY:
                    switch(attractorPlacement) {
                        case Attractor.FLOATING:
                            radius = Math.min((int)Math.max(Math.sqrt(Math.pow(insertPoint.x - cursor.x, 2) + Math.pow(insertPoint.y - cursor.y, 2) ),RADIUS_FLOAT_MIN), RADIUS_FLOAT_MAX);
                            intensity =  1 + (((radius - RADIUS_FLOAT_MIN) * 99) / (RADIUS_FLOAT_MAX - RADIUS_FLOAT_MIN));
                            break;
                        default:
                            radius = Math.min((int)Math.max(Math.sqrt(Math.pow(insertPoint.x - cursor.x, 2) + Math.pow(insertPoint.y - cursor.y, 2) ),RADIUS_WALL_MIN), RADIUS_WALL_MAX);
                            intensity =  1 + (((radius - RADIUS_WALL_MIN) * 99) / (RADIUS_WALL_MAX - RADIUS_WALL_MIN));
                            break;
                    }
                    spgui.notifyEvent(SimulationPanelGUI.ATTRACTOR_STRENGTH, intensity, SimulationPanelGUI.DUMMY_PARAMETER);
                    break;
                case PARTICLE_VECTOR:
                    spgui.notifyEvent(SimulationPanelGUI.PARTICLE_DIRECTING, (int)Math.toDegrees(angle), SimulationPanelGUI.DUMMY_PARAMETER);
                    break;
            }
        }
        repaint();
    }
    
    public double getMinX() {
        return envX;
    }
    
    public double getMinY() {
        return envY;
    }
    
    public double getMaxX() {
        return envWidth;
    }
    
    public double getMaxY() {
        return envHeight;
    }
    
    private void setMode(int mode) {
        currentMode = mode;
        
        switch(mode) {
            case MODE_MODIFY:
            case MODE_EDIT:
            case MODE_INSERT:
                selectedItem = SELECTED_NONE;
                blinking = 0;
                timer.stop();
                setCursor(CurHand);
                break;
            case MODE_PLAY:
                setCursor(CurDefault);
                setAnimationSpeed(animationSpeed);
        }
        
        
    }
    
    public void setShowDirection(boolean value) {
        showDirection = value;
        repaint();
    }
    
    public void setShowIntensity(boolean value) {
        showIntensity = value;
        repaint();
    }
    
    public void setShowPosition(boolean value) {
        showPosition = value;
        repaint();
    }
    
    public boolean getShowDirection() {
        return showDirection;
    }
    
    public boolean getShowIntensity() {
        return showIntensity;
    }
    
    public boolean getShowPosition() {
        return showPosition;
    }
    
}
