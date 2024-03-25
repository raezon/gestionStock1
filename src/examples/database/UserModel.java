package examples.database;

import javax.swing.*;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel extends DatabaseManager {

    public void registerUser(String firstName, String lastName, String email, String password) {
        if ("".equals(firstName)) {
            showErrorMessage("First Name is required");
        } else if ("".equals(lastName)) {
            //showErrorMessage("Last Name is required");
        } else if ("".equals(email)) {
            //showErrorMessage("Email Address is required");
        } else {
            try (Connection con = getConnection()) { // Using the getConnection method from DatabaseManager
                String query = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, email);
                    pst.setString(4, password);
                    pst.executeUpdate();
                }
               
            } catch (SQLException e) {
                showErrorMessage("Error: " + e.getMessage());
            }
        }
    }

    public boolean loginUser(String email, String password) {

    // Check if email and password are provided
    if ("".equals(password)) {
        showErrorMessage("Password is required");
      
    } else if ("".equals(email)) {
        showErrorMessage("Email Address is required");
      
    }

    // Authenticate user
    try {


        try (Connection con = getConnection()) {
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            try (PreparedStatement selectStatement = con.prepareStatement(query)) {
                selectStatement.setString(1, email);
                selectStatement.setString(2, password);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Authentication successful
                        System.out.println("User authenticated successfully.");
                        return true;
                        
                    } else {
                        // Authentication failed
                        showErrorMessage("Invalid email or password");
                        return false;
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showErrorMessage("Error: " + e.getMessage());
    }
        return false;
}

    private void showErrorMessage(String message) {
        // Print the error message to the console
        System.err.println("Error Message: " + message);

        // Display the error message dialog on the Event Dispatch Thread (EDT)
       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
