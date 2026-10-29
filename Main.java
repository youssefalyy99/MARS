package lab5;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Resources resources = new Resources();

        // ==========================================
        // TEST 1: DisplayResources()
        // ==========================================
        System.out.println("\n========== TEST 1: DISPLAY RESOURCES ==========");
        resources.DisplayResources();


        // ==========================================
        // TEST 2: AddResources()
        // ==========================================
        System.out.println("\n========== TEST 2: ADD RESOURCES ==========");
        resources.AddResources("OXYGEN", 100);
        resources.AddResources("FOOD", 200);
        resources.AddResources("WATER", 150);
        resources.AddResources("FUEL", 80);
        resources.AddResources("POWER", 120);
        resources.AddResources("MEDICAL_SUPPLIES", 50);
        resources.AddResources("SPARE_PARTS", 40);
        resources.DisplayResources();

        // Test invalid resource
        resources.AddResources("WOOD", 20);

        // Test invalid amount (uncomment to test the exception)
        // resources.AddResources("FOOD", -10);


        // ==========================================
        // TEST 3: RemoveResources()
        // ==========================================
        System.out.println("\n========== TEST 3: REMOVE RESOURCES ==========");
        resources.RemoveResources("OXYGEN", 10);

        // Test insufficient resources
        resources.RemoveResources("FOOD", 500);

        // Test invalid resource
        resources.RemoveResources("WOOD", 10);


        // ==========================================
        // TEST 4: HasAtLeast()
        // ==========================================
        System.out.println("\n========== TEST 4: CHECK RESOURCE AVAILABILITY ==========");

        boolean result1 = resources.HasAtLeast("OXYGEN", 50);
        System.out.println("Oxygen check result: " + result1);

        boolean result2 = resources.HasAtLeast("FOOD", 500);
        System.out.println("Food check result: " + result2);

        boolean result3 = resources.HasAtLeast("WOOD", 10);
        System.out.println("Invalid resource check result: " + result3);


        // ==========================================
        // TEST 5: AddRequirement()
        // ==========================================
        System.out.println("\n========== TEST 5: ADD REQUIREMENTS ==========");

        // Request MORE oxygen than available (only 90 remain)
        resources.AddRequirement("OXYGEN", 1000);

        resources.AddRequirement("FOOD", 30);
        resources.AddRequirement("WATER", 40);
        resources.AddRequirement("FUEL", 10);
        resources.AddRequirement("POWER", 15);
        resources.AddRequirement("MEDICAL_SUPPLIES", 5);
        resources.AddRequirement("SPARE_PARTS", 8);

        // Test invalid resource
        resources.AddRequirement("WOOD", 10);


        // ==========================================
        // TEST 6: ViewRequirments()
        // ==========================================
        System.out.println("\n========== TEST 6: VIEW REQUIREMENTS ==========");
        resources.ViewRequirments();


        // ==========================================
        // TEST 7: GetRequirments()
        // ==========================================
        System.out.println("\n========== TEST 7: INSUFFICIENT RESOURCE TEST ==========");
        System.out.println("Expected: Transaction rejected because oxygen is insufficient.");
        System.out.println();

        resources.GetRequirments();


        // ==========================================
        // TEST 8: Verify Resources Were Not Deducted
        // ==========================================
        System.out.println("\n========== TEST 8: VERIFY RESOURCES ==========");
        resources.DisplayResources();

                // ==========================================
        // TEST 9: SUCCESSFUL TRANSACTION
        // ==========================================
        System.out.println("\n========== TEST 9: SUCCESSFUL TRANSACTION ==========");
        // Clear the failed transaction's requests
        resources.ClearRequirements();
        // Add requirements that are within available resources
        resources.AddRequirement("OXYGEN", 20);
        resources.AddRequirement("FOOD", 30);
        resources.AddRequirement("WATER", 40);
        resources.AddRequirement("FUEL", 10);
        resources.AddRequirement("POWER", 15);
        resources.AddRequirement("MEDICAL_SUPPLIES", 5);
        resources.AddRequirement("SPARE_PARTS", 8);
        // View the new requirements
        resources.ViewRequirments();
        // Process the transaction (enter Y when prompted)
        resources.GetRequirments();
        // Verify the resources after the successful transaction
        System.out.println("\n========== RESOURCES AFTER SUCCESSFUL TRANSACTION ==========");
        resources.DisplayResources();
        System.out.println("\n========== TESTING COMPLETE ==========");
        
         ArrayList<String> skills = new ArrayList<>();
        skills.add("Geology");

        Scientist s = new Scientist(skills, 10, 100, "Sara", 1, true, "Lab A");
        s.display();

        s.damage(80);
        System.out.println("After damage(80): health = " + s.getHealthStatus() + ", usable = " + s.isUsable());

        s.heal(500);
        System.out.println("After heal(500): health = " + s.getHealthStatus());

        s.increaseFatigue(90);
        System.out.println("After increaseFatigue(90): fatigue = " + s.getFatigueLevel() + ", usable = " + s.isUsable());

        s.setAvailability(false);
        System.out.println("Unavailable: ready = " + s.isReady());
    }
}