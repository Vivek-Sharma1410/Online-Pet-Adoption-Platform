package app.models;

public class Shelter extends User {

    public Shelter(String name, String email, String password) {
        super(name, email, password, "shelter");
    }
}
