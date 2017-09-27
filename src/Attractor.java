
public class Attractor {
    
    public static final int SINGLE = 3;
    public static final int DOUBLE = 4;
    public static final int BREAKER = 5;
    public static final int ALL = 6;
    
    public static final int FLOATING = 7;
    public static final int WALL_UP = 8;
    public static final int WALL_DOWN = 9;
    public static final int WALL_LEFT = 10;
    public static final int WALL_RIGHT = 11;
    
    private int attracts;
    private int placement;
    private int intensity;
    private double radius;
    private PairOfDouble position;

    public Attractor(int attracts, int intensity,  int placement) throws SimulationPanelInvalidParameterException, SimulationPanelWrongTypeException {
        if((attracts != SINGLE) && (attracts != DOUBLE) && (attracts != BREAKER) && (attracts != ALL) && (placement != FLOATING) && (placement != WALL_UP) && (placement != WALL_DOWN) && (placement != WALL_LEFT) && (placement != WALL_RIGHT))
            throw new SimulationPanelWrongTypeException();
        
        if((intensity < 1) || (intensity > 100))
            throw new SimulationPanelInvalidParameterException();
                
        this.attracts = attracts;
        this.intensity = intensity;
        this.placement = placement;
        position = null;
                
    }
    
    public Attractor(int attracts, int intensity, PairOfDouble position) throws SimulationPanelInvalidParameterException, SimulationPanelWrongTypeException {
        if((attracts != SINGLE) && (attracts != DOUBLE) && (attracts != BREAKER) && (attracts != ALL))
            throw new SimulationPanelWrongTypeException();
        
        if((intensity < 0) || (intensity > 100))
            throw new SimulationPanelInvalidParameterException();
        
        if(position == null)
            throw new NullPointerException();

        this.attracts = attracts;
        this.intensity = intensity;
        this.placement = FLOATING;
        this.position = position;
                
    }

    public int getAttracts() {
        return attracts;
    }

    public PairOfDouble getPosition() {
        return position;
    }
    
    public int getPlacement() {
        return this.placement;
    }
    
    public int getIntensity() {
        return intensity;
    }
    
    
}
