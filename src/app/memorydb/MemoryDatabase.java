package app.memorydb;

import java.util.*;
import app.models.*;

public class MemoryDatabase {
    public static Map<String, User> users = new HashMap<>();
    public static Map<Integer, Pet> pets = new HashMap<>();
    public static Map<Integer, AdoptionApplication> applications = new HashMap<>();

    private static int petIdCounter = 1;
    private static int appIdCounter = 1;

    public static void initialize() {
        // Admin
        users.put("admin@platform.com", new Admin("Admin", "admin@platform.com", "admin123"));

        // Shelter
        users.put("shelter1@platform.com", new Shelter("Happy Paws Shelter", "shelter1@platform.com", "shelter123"));

        // Adopter
        users.put("adopter1@platform.com", new Adopter("John Doe", "adopter1@platform.com", "adopter123"));
    }

    public static int addPet(Pet pet) {
        pet.setId(petIdCounter);
        pets.put(petIdCounter, pet);
        return petIdCounter++;
    }

    public static int addApplication(AdoptionApplication app) {
        app.setId(appIdCounter);
        applications.put(appIdCounter, app);
        return appIdCounter++;
    }
}
