package baithiLuongTuanPhi.dao;

import baithiLuongTuanPhi.model.Contact;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/AddressBookDB";
    private static final String USER = "root";
    private static final String PASSWORD = "phi";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addContact(Contact contact) {
        String sql = "INSERT INTO Contacts (name, company, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getCompany());
            pstmt.setString(3, contact.getEmail());
            pstmt.setString(4, contact.getPhone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Contact findContactByName(String name) {
        String sql = "SELECT * FROM Contacts WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Contact(rs.getString("name"), rs.getString("company"), rs.getString("email"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Contact> getAllContacts() {
        String sql = "SELECT * FROM Contacts";
        ArrayList<Contact> contacts = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contacts.add(new Contact(rs.getString("name"), rs.getString("company"), rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacts;
    }

    public void updateContact(String oldName, Contact newContact) {
        String sql = "UPDATE Contacts SET name = ?, company = ?, email = ?, phone = ? WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newContact.getName());
            pstmt.setString(2, newContact.getCompany());
            pstmt.setString(3, newContact.getEmail());
            pstmt.setString(4, newContact.getPhone());
            pstmt.setString(5, oldName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteContact(String name) {
        String sql = "DELETE FROM Contacts WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

