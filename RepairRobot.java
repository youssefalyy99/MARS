package lab5;

public class RepairRobot extends Robot {

    private static final int DRAIN_RATE = 3;

    private final int sparePartsPerRepair;
    private final int batteryPerRepair;

    public RepairRobot(String name, int id, String currentLocation) {
        super(name, id, currentLocation, DRAIN_RATE);
        this.sparePartsPerRepair = 2;
        this.batteryPerRepair = 5;
    }

    public int getSparePartsPerRepair() {
        return sparePartsPerRepair;
    }

    public int getBatteryPerRepair() {
        return batteryPerRepair;
    }

    // The colony consumes the spare parts BEFORE calling this (all-or-nothing, spec 5.1).
    public boolean performRepair(Facility f) {
        if (f == null || !isUsable() || !hasEnoughBattery(batteryPerRepair)) {
            return false;   // nothing consumed
        }
        drainBattery(batteryPerRepair);
        f.setStatuesOPERATIONAL();   // match your Facility class
        System.out.println(getName() + " repaired " + f.getName() + ".");
        return true;
    }

    @Override
    public boolean canPerform(Mission m) {
        if (m == null || !isUsable()) {
            return false;
        }
        if (m.getType() != MissionType.REPAIR) {
            return false;
        }
        return hasEnoughBattery(m.getMinBattery());
    }

    @Override
    public boolean canHandle(Emergency e) {
        // oxygen leak per spec 9.1; add fire / rover breakdown if your team decides so
        return e != null && e.getType() == EmergencyType.OXYGEN_LEAK;
    }

    @Override
    public boolean canOperateIn(Environment env) {
        return true;   // works inside facilities
    }

    @Override
    public String getStatusSummary() {
        return super.getStatusSummary()
                + " | Repair cost: " + sparePartsPerRepair + " parts, "
                + batteryPerRepair + " battery";
    }
}
