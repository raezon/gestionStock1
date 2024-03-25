package examples.database;

import javax.swing.*;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ProductModel extends DatabaseManager {

   public void registerProduct(String name, double price, String description) {
    if ("".equals(name)) {
        showErrorMessage("Product Name is required");
    } else {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO products (name, price, description) VALUES (?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, name);
                pst.setDouble(2, price);
                pst.setString(3, description);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            showErrorMessage("Error: " + e.getMessage());
        }
    }
  }
   public void loadProducts(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                model.addRow(new Object[]{id, name, description, price});
                
                 table.setModel(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error: " + e.getMessage());
        }
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
