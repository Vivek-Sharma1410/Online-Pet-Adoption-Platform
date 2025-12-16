package app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdoptionDAO {

    // synchronized = prevents concurrent adoption of same pet
    public synchronized boolean processAdoption(int petId, String adopterEmail) {

        Connection conn = null;

        try {
            conn = DBHelper.getConnection();

            // START TRANSACTION
            conn.setAutoCommit(false);

            // 1️⃣ Check pet availability
            PreparedStatement checkPet = conn.prepareStatement(
                    "SELECT status FROM pets WHERE id = ?"
            );
            checkPet.setInt(1, petId);
            ResultSet rs = checkPet.executeQuery();

            if (!rs.next() || !"Available".equals(rs.getString("status"))) {
                conn.rollback();
                return false;
            }

            // 2️⃣ Insert adoption application
            PreparedStatement insertApp = conn.prepareStatement(
                    "INSERT INTO applications (pet_id, adopter_email, status) VALUES (?, ?, ?)"
            );
            insertApp.setInt(1, petId);
            insertApp.setString(2, adopterEmail);
            insertApp.setString(3, "Pending");
            insertApp.executeUpdate();

            // 3️⃣ Update pet status
            PreparedStatement updatePet = conn.prepareStatement(
                    "UPDATE pets SET status = ? WHERE id = ?"
            );
            updatePet.setString(1, "Adopted");
            updatePet.setInt(2, petId);
            updatePet.executeUpdate();

            // COMMIT (ALL SUCCESS)
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) { }
            return false;

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) { }
        }
    }
}
