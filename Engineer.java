package lab5;

import java.util.ArrayList;

public class Engineer extends CrewMember {

    public Engineer(ArrayList<String> Skills, int FatigueLevel, int HealthStatus, String Name, int id, boolean availability, String currentLocation) {
        super(Skills, FatigueLevel, HealthStatus, Name, id, availability, currentLocation);
    }

    @Override
    public boolean canHandle(Emergency e) {
        return e != null && e.getType() == EmergencyType.OXYGEN_LEAK;
    }

    @Override
    protected String getRole() {
        return "Engineer";
    }

    @Override
    public void update() {
        super.update();
    }
}
