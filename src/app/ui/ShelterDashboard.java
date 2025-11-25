package app.ui;

import javax.swing.*;
import java.awt.*;
import app.models.*;
import app.memorydb.MemoryDatabase;

public class ShelterDashboard extends JFrame {

    private Shelter shelter;

    public ShelterDashboard(Shelter shelter) {
        this.shelter = shelter;

        setTitle("Shelter Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        add(new JLabel("Welcome, " + shelter.getName(), SwingConstants.CENTER));

        JButton addPetBtn = new JButton("Add Pet");
        JButton viewPetsBtn = new JButton("View Listed Pets");

        add(addPetBtn);
        add(viewPetsBtn);

        addPetBtn.addActionListener(e -> new PetFormDialog(this).setVisible(true));

        viewPetsBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Your Pets:\n\n");

            MemoryDatabase.pets.values().forEach(p -> sb.append(p.toString()).append("\n"));

            JOptionPane.showMessageDialog(this, sb.toString());
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
