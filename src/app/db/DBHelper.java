package app.db;

import java.sql.*;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:petadoption.db";
    private static DBHelper instance;

    private DBHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBHelper getInstance() {
        if (instance == null) instance = new DBHelper();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
        stmt.close();
        return conn;
    }

    public static void closeQuietly(AutoCloseable c) {
        if (c == null) return;
        try { c.close(); } catch (Exception ignored) {}
    }
}
