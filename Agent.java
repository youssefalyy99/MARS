package lab5;

public abstract class Agent {

    private String Name;
    private final int id;
    private boolean availability;
    private String currentLocation;

    public Agent(String Name, int id, boolean availability, String currentLocation) {
        this.Name = Name;
        this.id = id;
        this.availability = availability;
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isAvailable() {
        return availability;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public abstract boolean canPerform(Mission m);

    public abstract boolean canHandle(Emergency e);

    public abstract boolean isUsable();

    public boolean isReady() {
        return availability && isUsable();
    }

    // humans work in any environment by default; robots override this
    public boolean canOperateIn(Environment env) {
        return true;
    }

    // called by the colony when a mission this agent belongs to starts;
    // by default nothing happens, subclasses react in their own way
    public void onMissionStart(Mission m) {
    }

    public abstract String getStatusSummary();

    public abstract void update();

    public void display() {
        System.out.println(getStatusSummary());
    }

}
