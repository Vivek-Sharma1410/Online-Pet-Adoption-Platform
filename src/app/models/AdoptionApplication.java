package app.models;

public class AdoptionApplication {

    private int id;
    private String adopterEmail;
    private int petId;
    private String status;

    public AdoptionApplication(String adopterEmail, int petId) {
        this.adopterEmail = adopterEmail;
        this.petId = petId;
        this.status = "Pending";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAdopterEmail() { return adopterEmail; }
    public int getPetId() { return petId; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application #" + id + " | Pet ID: " + petId + " | Adopter: " + adopterEmail + " | Status: " + status;
    }
}
