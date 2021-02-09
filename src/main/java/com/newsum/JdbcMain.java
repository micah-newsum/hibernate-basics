package com.newsum;

import com.newsum.model.Contact;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class JdbcMain {
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO: Load the SQLite JDBC driver (JDBC class implements java.sql.Driver)
        Class.forName("org.sqlite.JDBC");

        // TODO: Create a DB connection
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:contactmgr.db")) {

            // TODO: Create a SQL statement
            Statement statement = connection.createStatement();

            // TODO: Create a DB table
            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts (id INTEGER PRIMARY KEY, firstname STRING, lastname STRING, email STRING, phone INT(10))");

            // TODO: Insert a couple contacts
            Contact c = new Contact("Micah","Newsum","micahnew90@yahoo.com",5622346959L);
            save(c,statement);

            c = new Contact("Aysha","Newsum","ajainoor@yahoo.com",5627081405L);
            save(c,statement);

            // TODO: Fetch all the records from the contacts table
            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");

            // TODO: Iterate over the ResultSet & display contact info
            while (rs.next())
            {
                int id;
                String firstname;
                String lastname;
                id = rs.getInt("id");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");

                System.out.printf("%s %s (%d)",firstname, lastname, id);
            }

        } catch (SQLException ex) {
            // Display connection or query errors
            System.err.printf("There was a database error: %s%n",ex.getMessage());
        }
    }

    public static void save(Contact contact, Statement statement) throws SQLException {
        String sql = "INSERT INTO contacts(firstname,lastname,email,phone) VALUES('%s','%s','%s','%d')";
        sql = String.format(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

        statement.executeUpdate(sql);
    }
}
