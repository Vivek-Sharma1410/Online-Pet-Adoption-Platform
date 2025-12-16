package app.controller;

import app.db.AdoptionDAO;

public class AdoptionController {

    public boolean handleAdoption(int petId, String adopterEmail) {

        // Server-side validation
        if (petId <= 0 || adopterEmail == null || adopterEmail.isEmpty()) {
            return false;
        }

        AdoptionDAO dao = new AdoptionDAO();
        return dao.processAdoption(petId, adopterEmail);
    }
}
