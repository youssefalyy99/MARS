package lab5;

public class Habitat extends Facility{
    private Boolean leaking;

    Habitat(String id, String name){
        super(id, name);
        this.leaking = false;
    }

    public void applyOxygenLeak(double amount){
        R1.LeakOxygen(amount);
        this.leaking = true;
        setStatuesUNAVAILABLE();
    }

    public double getOxygen(){
        return R1.getOxygenLevel();
    }

    public boolean isLeaking(){
        if(leaking == true){
            System.out.println("System Message : OXYGEN IS LEAKING ! ! !");
        }else{
            System.out.println("System Message : Oxygen is NOT Leaking . . .");
        }
        return leaking; }

    public void sealLeak(){ leaking = false; setStatuesOPERATIONAL();}
}
