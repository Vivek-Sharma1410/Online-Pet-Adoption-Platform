package app;

import app.ui.LoginFrame;
import app.memorydb.MemoryDatabase;

public class Main {
    public static void main(String[] args) {
        // Initialize in-memory database with sample data
        MemoryDatabase.initialize();

        // Launch Login UI
        new LoginFrame().setVisible(true);
    }
}

