package app.ui;

import javax.swing.*;
import java.awt.*;
import app.models.*;
import app.memorydb.MemoryDatabase;

public class ApplicationDialog extends JDialog {

    private JTextField petIdField;
    private Adopter adopter;

    public ApplicationDialog(JFrame parent, Adopter adopter) {
        super(parent, "Apply for Adoption", true);
        this.adopter = adopter;

        setSize(300, 180);
        setLayout(new GridLayout(3, 1));

        petIdField = new JTextField();
        add(labeledPanel("Enter Pet ID:", petIdField));

        JButton applyBtn = new JButton("Apply");
        add(applyBtn);

        applyBtn.addActionListener(e -> apply());
    }

    private JPanel labeledPanel(String text, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void apply() {
        try {
            int petId = Integer.parseInt(petIdField.getText());

            if (!MemoryDatabase.pets.containsKey(petId)) {
                JOptionPane.showMessageDialog(this, "Invalid Pet ID");
                return;
            }

            AdoptionApplication app = new AdoptionApplication(adopter.getEmail(), petId);
            MemoryDatabase.addApplication(app);

            JOptionPane.showMessageDialog(this, "Application Submitted!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
