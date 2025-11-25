package app.ui;

import javax.swing.*;
import app.models.*;
import app.memorydb.MemoryDatabase;

public class AdminDashboard extends JFrame {

    public AdminDashboard(Admin admin) {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + admin.getName(), SwingConstants.CENTER);
        add(welcome);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
