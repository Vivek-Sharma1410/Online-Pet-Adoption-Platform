package app.ui;

import javax.swing.*;
import java.awt.*;
import app.models.*;
import app.memorydb.MemoryDatabase;

public class AdopterDashboard extends JFrame {

    private Adopter adopter;

    public AdopterDashboard(Adopter adopter) {
        this.adopter = adopter;

        setTitle("Adopter Dashboard");
        setSize(450, 300);
        setLayout(new GridLayout(3, 1));
        setLocationRelativeTo(null);

        add(new JLabel("Welcome, " + adopter.getName(), SwingConstants.CENTER));

        JButton browsePets = new JButton("Browse Pets");
        JButton myApplications = new JButton("My Applications");

        add(browsePets);
        add(myApplications);

        browsePets.addActionListener(e -> browse());

        myApplications.addActionListener(e -> showApps());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void browse() {
        StringBuilder sb = new StringBuilder("Available Pets:\n\n");

        MemoryDatabase.pets.values().forEach(p -> {
            if (p.getAdoptionStatus().equals("Available")) {
                sb.append(p.toString()).append("\n");
            }
        });

        JOptionPane.showMessageDialog(this, sb.toString());
        new ApplicationDialog(this, adopter).setVisible(true);
    }

    private void showApps() {
        StringBuilder sb = new StringBuilder("Your Applications:\n\n");

        MemoryDatabase.applications.values().forEach(app -> {
            if (app.getAdopterEmail().equals(adopter.getEmail())) {
                sb.append(app.toString()).append("\n");
            }
        });

        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
