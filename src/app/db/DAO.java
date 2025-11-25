package app.db;

import app.models.*;
import java.sql.*;
import java.util.*;

public class DAO {
    private final DBHelper db = DBHelper.getInstance();

    // USERS
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT id, name, email, password, role, phone FROM users WHERE email = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUser(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    public Optional<User> findUserById(int id) {
        String sql = "SELECT id, name, email, password, role, phone FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapUser(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    public int createUser(User u) {
        String sql = "INSERT INTO users (name,email,password,role,phone) VALUES (?,?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    // PETS
    public List<Pet> findAvailablePets(String filter) {
        String sql = "SELECT p.id, p.shelter_id, p.name, p.species, p.breed, p.age, p.description, p.status, p.photo_path, p.created_at, u.name as shelter_name "
                   + "FROM pets p JOIN users u ON p.shelter_id = u.id WHERE p.status = 'AVAILABLE'";
        if (filter != null && !filter.trim().isEmpty()) {
            sql += " AND (p.name LIKE ? OR p.species LIKE ? OR p.breed LIKE ?)";
        }
        List<Pet> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (filter != null && !filter.trim().isEmpty()) {
                String f = "%" + filter + "%";
                ps.setString(1, f);
                ps.setString(2, f);
                ps.setString(3, f);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pet p = mapPet(rs);
                p.setShelterName(rs.getString("shelter_name"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int createPet(Pet pet) {
        String sql = "INSERT INTO pets (shelter_id,name,species,breed,age,description,status,photo_path) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pet.getShelterId());
            ps.setString(2, pet.getName());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getBreed());
            ps.setInt(5, pet.getAge());
            ps.setString(6, pet.getDescription());
            ps.setString(7, pet.getStatus());
            ps.setString(8, pet.getPhotoPath());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Pet> findPetsByShelter(int shelterId) {
        String sql = "SELECT p.*, u.name as shelter_name FROM pets p JOIN users u ON p.shelter_id = u.id WHERE p.shelter_id = ?";
        List<Pet> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shelterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pet p = mapPet(rs);
                p.setShelterName(rs.getString("shelter_name"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // APPLICATIONS
    public int createApplication(AdoptionApplication app) {
        String sql = "INSERT INTO applications (pet_id, adopter_id, message, status) VALUES (?,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, app.getPetId());
            ps.setInt(2, app.getAdopterId());
            ps.setString(3, app.getMessage());
            ps.setString(4, app.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<AdoptionApplication> findApplicationsByPet(int petId) {
        String sql = "SELECT a.*, u.name as adopter_name, u.email as adopter_email FROM applications a JOIN users u ON a.adopter_id = u.id WHERE a.pet_id = ?";
        List<AdoptionApplication> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AdoptionApplication a = new AdoptionApplication();
                a.setId(rs.getInt("id"));
                a.setPetId(rs.getInt("pet_id"));
                a.setAdopterId(rs.getInt("adopter_id"));
                a.setMessage(rs.getString("message"));
                a.setStatus(rs.getString("status"));
                a.setCreatedAt(rs.getString("created_at"));
                a.setAdopterName(rs.getString("adopter_name"));
                a.setAdopterEmail(rs.getString("adopter_email"));
                list.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<AdoptionApplication> findApplicationsByAdopter(int adopterId) {
        String sql = "SELECT a.*, p.name as pet_name FROM applications a JOIN pets p ON a.pet_id = p.id WHERE a.adopter_id = ?";
        List<AdoptionApplication> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adopterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AdoptionApplication a = new AdoptionApplication();
                a.setId(rs.getInt("id"));
                a.setPetId(rs.getInt("pet_id"));
                a.setAdopterId(rs.getInt("adopter_id"));
                a.setMessage(rs.getString("message"));
                a.setStatus(rs.getString("status"));
                a.setCreatedAt(rs.getString("created_at"));
                a.setPetName(rs.getString("pet_name"));
                list.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateApplicationStatus(int applicationId, String status) {
        String sql = "UPDATE applications SET status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, applicationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updatePetStatus(int petId, String status) {
        String sql = "UPDATE pets SET status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, petId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // helpers
    private User mapUser(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        if ("ADMIN".equalsIgnoreCase(role)) {
            return new Admin(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("phone"));
        } else if ("SHELTER".equalsIgnoreCase(role)) {
            return new Shelter(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("phone"));
        } else {
            return new Adopter(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("phone"));
        }
    }

    private Pet mapPet(ResultSet rs) throws SQLException {
        Pet p = new Pet();
        p.setId(rs.getInt("id"));
        p.setShelterId(rs.getInt("shelter_id"));
        p.setName(rs.getString("name"));
        p.setSpecies(rs.getString("species"));
        p.setBreed(rs.getString("breed"));
        p.setAge(rs.getInt("age"));
        p.setDescription(rs.getString("description"));
        p.setStatus(rs.getString("status"));
        p.setPhotoPath(rs.getString("photo_path"));
        p.setCreatedAt(rs.getString("created_at"));
        return p;
    }
}

