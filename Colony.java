package lab5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Colony {

    private final ArrayList<Agent> agents;
    private final ArrayList<Facility> facilities;
    private final ArrayList<Mission> missionHistory;

    private Environment environment;
    private Mission activeMission;

    public Colony() {
        this.agents = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.missionHistory = new ArrayList<>();
        this.environment = new Environment();
        this.activeMission = null;
    }

    public boolean addAgent(Agent agent) {
        if (agent == null) {
            return false;
        }
        for (Agent a : agents) {
            if (agent.getId() == a.getId()) {
                return false;
            }
        }
        agents.add(agent);
        return true;
    }

    public Agent findAgent(int id) {
        for (Agent a : agents) {
            if (id == a.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Agent> getAgents() {
        return Collections.unmodifiableList(agents);
    }

    public boolean addFacility(Facility facility) {
        if (facility == null) {
            return false;
        }
        for (Facility f : facilities) {
            if (facility.getId().equals(f.getId())) {
                return false;
            }
        }
        facilities.add(facility);
        return true;
    }

    public Facility findFacility(String id) {
        for (Facility f : facilities) {
            if (id != null && id.equals(f.getId())) {
                return f;
            }
        }
        return null;
    }

    public List<Facility> getFacilities() {
        return Collections.unmodifiableList(facilities);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null.");
        }
        this.environment = environment;
    }

    public Mission getActiveMission() {
        return activeMission;
    }

    public List<Mission> getMissionHistory() {
        return Collections.unmodifiableList(missionHistory);
    }

    private void setActiveMission(Mission activeMission) {
        this.activeMission = activeMission;
    }

    private boolean addMissionToHistory(Mission mission) {
        if (mission == null) {
            return false;
        }
        missionHistory.add(mission);
        return true;
    }

    public boolean submitMission(Mission mission, List<Agent> team) {
        if (mission == null) {
            return false;
        }

        if (mission.getState() != MissionStatus.PENDING) {
            return false;                        
        }

        addMissionToHistory(mission);                

        if (activeMission != null && activeMission.isActive()) {
            mission.failMission("Another mission is currently active.");
            return false;
        }

        if (!mission.canStartIn(environment)) {
            mission.failMission(mission.getStartBlockedReason());
            return false;
        }

        if (team != null) {
            for (Agent agent : team) {
                if (agent == null) {
                    mission.failMission("A required agent is missing.");
                    return false;
                }
                if (findAgent(agent.getId()) == null) {
                    mission.failMission("Agent with ID " + agent.getId() + " is not registered in this colony.");
                    return false;
                }
            }
        }

        if (mission.getResourceRequest() != null) {
            String shortage = checkResources(mission.getResourceRequest());
            if (shortage != null) {
                mission.failMission(shortage);
                return false;
            }
        }

        if (!mission.assignAgents(team)) {
            return false;
        }

        if (mission.getResourceRequest() != null) {
            consumeResources(mission.getResourceRequest());
        }

        activeMission = mission;
        return true;
    }

    private double amountIn(Facility f, String type) {
        switch (type) {
            case "OXYGEN":
                return f.R1.getOxygen();
            case "WATER":
                return f.R1.getWater();
            case "FOOD":
                return f.R1.getFood();
            case "FUEL":
                return f.R1.getFuel();
            case "POWER":
                return f.R1.getPower();
            case "MEDICAL_SUPPLIES":
                return f.R1.getMedicalSupplies();
            case "SPARE_PARTS":
                return f.R1.getSpareParts();
            default:
                return 0;
        }
    }

    private double getColonyTotal(String type) {
        double total = 0;
        for (Facility f : facilities) {
            if (f.isOperational()) {
                total += amountIn(f, type);
            }
        }
        return total;
    }

    private String checkResources(Resources request) {
        for (Map.Entry<String, Double> entry : request.getRequestRec().entrySet()) {
            double available = getColonyTotal(entry.getKey());
            if (available < entry.getValue()) {
                return "Insufficient " + entry.getKey() + ": required "
                        + entry.getValue() + ", available " + available + ".";
            }
        }
        return null;
    }

    private void consumeResources(Resources request) {
        if (request == null || request.getRequestRec() == null) {
            return;
        }
        for (Map.Entry<String, Double> entry : request.getRequestRec().entrySet()) {
            String type = entry.getKey();
            double needed = entry.getValue();

            for (Facility f : facilities) {
                if (needed <= 0) break;
                if (f.isOperational()) {
                    double available = amountIn(f, type);
                    double take = Math.min(available, needed);
                    
                    if (take > 0) {
                        f.R1.RemoveResources(type, take);
                        needed -= take;
                    }
                }
            }
        }
    }

    public void advanceTime() {
        for (Agent agent : agents) {
            agent.update();
        }

        if (activeMission != null && activeMission.isActive()) {
            if (activeMission.getState() == MissionStatus.ASSIGNED) {
                activeMission.startMission();
            }

            if (environment.isDustStorm()) {
                activeMission.failMission("Surface operations are not allowed during a dust storm");
            } else if (environment.isCommunicationDisrupted()) {
                activeMission.failMission("Communication disruption aborted the mission.");
            }

            if (activeMission.isActive()) {
                activeMission.advanceTime();

                if (activeMission.isFinished()) {
                    activeMission = null;
                }
            } else {
                activeMission = null;
            }
        }
    }

    public String getStatusReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COLONY STATUS REPORT ===\n");
        sb.append("Environment: ").append(environment.getDescription()).append("\n\n");

        sb.append("--- Colony-Wide Resource Totals ---\n");
        String[] resourceTypes = {"OXYGEN", "WATER", "FOOD", "FUEL", "POWER", "MEDICAL_SUPPLIES", "SPARE_PARTS"};
        for (String type : resourceTypes) {
            sb.append(type).append(": ").append(getColonyTotal(type)).append("\n");
        }
        sb.append("\n");

        sb.append("--- Agents ---\n");
        if (agents.isEmpty()) {
            sb.append("No agents registered.\n");
        } else {
            for (Agent agent : agents) {
                sb.append(agent.getStatusSummary()).append("\n");
            }
        }
        sb.append("\n");

        sb.append("--- Facilities ---\n");
        if (facilities.isEmpty()) {
            sb.append("No facilities registered.\n");
        } else {
            for (Facility facility : facilities) {
                sb.append("Facility ID: ").append(facility.getId())
                  .append(" | Status: ").append(facility.status).append("\n");
            }
        }
        sb.append("\n");

        sb.append("--- Active Mission ---\n");
        if (activeMission != null) {
            sb.append("Mission ID: ").append(activeMission.getId())
              .append(" | Name: ").append(activeMission.getName())
              .append(" | State: ").append(activeMission.getState())
              .append(" | Progress: ").append(activeMission.getProgress())
              .append("/").append(activeMission.getDuration()).append("\n");
        } else {
            sb.append("No active mission.\n");
        }
        sb.append("\n");

        sb.append("--- Mission History & Outcomes ---\n");
        if (missionHistory.isEmpty()) {
            sb.append("No missions in history.\n");
        } else {
            for (Mission mission : missionHistory) {
                sb.append("Mission ID: ").append(mission.getId())
                  .append(" | Name: ").append(mission.getName())
                  .append(" | State: ").append(mission.getState());
                if (mission.getFailureReason() != null) {
                    sb.append(" | Reason: ").append(mission.getFailureReason());
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}