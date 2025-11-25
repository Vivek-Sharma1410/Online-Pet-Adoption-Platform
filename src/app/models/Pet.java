package app.models;

public class Pet {
    private int id;
    private String name;
    private String type;
    private String breed;
    private String description;
    private String adoptionStatus;

    public Pet(String name, String type, String breed, String description) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.description = description;
        this.adoptionStatus = "Available";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getBreed() { return breed; }
    public String getDescription() { return description; }
    public String getAdoptionStatus() { return adoptionStatus; }

    public void setAdoptionStatus(String status) {
        this.adoptionStatus = status;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + type + " - " + breed + ")";
    }
}
