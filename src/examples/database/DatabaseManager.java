package examples.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseManager {
    private static final String url = "jdbc:mysql://localhost:3306/java_example"; // Note the correct JDBC URL format
    private static final String user = "root";
    private static final String pass = "";
    protected Connection con;

    // Constructor to open connection
    public DatabaseManager() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Destructor (finalize) to close connection
    @Override
    protected void finalize() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add other abstract methods for update, select, etc.
        protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
