package lab5;

import java.util.ArrayList;

public class Scientist extends CrewMember {

    public Scientist(ArrayList<String> Skills, int FatigueLevel, int HealthStatus, String Name, int id, boolean availability, String currentLocation) {
        super(Skills, FatigueLevel, HealthStatus, Name, id, availability, currentLocation);
    }

    @Override
    public boolean canHandle(Emergency e) {
        return false;
    }

    @Override
    protected String getRole() {
        return "Scientist";
    }

    @Override
    public void update() {
        super.update();
    }
}