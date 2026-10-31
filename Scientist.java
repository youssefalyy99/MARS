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

    // replaces the old Research.execute(): the colony calls this when the mission starts,
    // so the mission never has to check for a Scientist with instanceof (spec 6.1)
    @Override
    public void onMissionStart(Mission m) {
        if (m != null && m.getType() == MissionType.RESEARCH) {
            System.out.println(getName() + " has started the research mission '"
                    + m.getName() + "'.");
        }
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
