package app.models;

public class Adopter extends User {

    public Adopter(String name, String email, String password) {
        super(name, email, password, "adopter");
    }
}
