public interface SimulationPanelGUI {
    
    public static final int DUMMY_PARAMETER = -1;
    public static final int INIT = 0;
    public static final int SIMULATION_PLAY = INIT + 1;
    public static final int SIMULATION_PAUSE = SIMULATION_PLAY + 1;
    public static final int ONE_STEP = SIMULATION_PAUSE + 1;
    public static final int PARTICLE_POSITIONING = ONE_STEP + 1;
    public static final int PARTICLE_DIRECTING = PARTICLE_POSITIONING + 1;
    public static final int PARTICLE_INSERTED = PARTICLE_DIRECTING + 1;
    public static final int ATTRACTOR_POSITIONING = PARTICLE_INSERTED + 1;
    public static final int ATTRACTOR_STRENGTH = ATTRACTOR_POSITIONING + 1;
    public static final int ATTRACTOR_INSERTED = ATTRACTOR_STRENGTH + 1;
    public static final int PARTICLE_SELECTED = ATTRACTOR_INSERTED + 1;
    public static final int ATTRACTOR_SELECTED = PARTICLE_SELECTED + 1;
    public static final int AREA_SELECTED = ATTRACTOR_SELECTED + 1;
    public static final int AREA_FILLED = AREA_SELECTED + 1;
    public static final int AREA_EMPTIED = AREA_FILLED + 1;
    public static final int ENVIRONMENT_RESETTED = AREA_EMPTIED + 1;
    public static final int PARTICLE_DELETED = ENVIRONMENT_RESETTED + 1;
    public static final int ATTRACTOR_DELETED = PARTICLE_DELETED + 1;
    public static final int PARTICLE_MODIFIED = ATTRACTOR_DELETED + 1;
    public static final int ATTRACTOR_MODIFIED = PARTICLE_MODIFIED + 1;
    public static final int DIMENSIONS_CHANGED = ATTRACTOR_MODIFIED + 1;
    public static final int ENVIRONMENT_CREATED = DIMENSIONS_CHANGED + 1;
    public static final int OPERATION_CANCELED = ENVIRONMENT_CREATED + 1;
    public static final int COORDS_UPDATE = OPERATION_CANCELED + 1;
    public static final int OBJECT_DESELECTED = COORDS_UPDATE + 1;
    
    
    public ParticleSimulatorPopupMenu getPopupMenu();
    
    public void notifyEvent(int event, int argument1, int argument2);
    
}
