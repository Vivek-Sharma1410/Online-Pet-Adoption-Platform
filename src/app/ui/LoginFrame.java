package app.ui;

import javax.swing.*;
import java.awt.*;
import app.memorydb.MemoryDatabase;
import app.models.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Pet Adoption - Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel title = new JLabel("LOGIN", SwingConstants.CENTER);
        add(title);

        emailField = new JTextField();
        add(labeledPanel("Email:", emailField));

        passwordField = new JPasswordField();
        add(labeledPanel("Password:", passwordField));

        JButton loginBtn = new JButton("Login");
        add(loginBtn);

        loginBtn.addActionListener(e -> login());
    }

    private JPanel labeledPanel(String text, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void login() {
        String email = emailField.getText();
        String pass = String.valueOf(passwordField.getPassword());

        User user = MemoryDatabase.users.get(email);

        if (user == null || !user.getPassword().equals(pass)) {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
            return;
        }

        setVisible(false);

        switch (user.getRole()) {
            case "admin":
                new AdminDashboard((Admin) user).setVisible(true);
                break;

            case "shelter":
                new ShelterDashboard((Shelter) user).setVisible(true);
                break;

            case "adopter":
                new AdopterDashboard((Adopter) user).setVisible(true);
                break;
        }
    }
}
