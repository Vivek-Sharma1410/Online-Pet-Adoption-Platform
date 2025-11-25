package app.utils;

import javax.swing.JOptionPane;

public class Utils {

    // Show info message
    public static void info(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Show error message
    public static void error(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Validate empty input
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
