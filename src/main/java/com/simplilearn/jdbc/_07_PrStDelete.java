package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class _07_PrStDelete {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "rootroot";

        /*
         * Read the user ID from the User.
         */

        int input = 0;

        try(Scanner scan= new Scanner(System.in)){

            System.out.println("Enter the user ID: ");
            input = scan.nextInt();
        }

        /*
         * Create the Delete SQL Statement
         */
        String sql = "DELETE FROM USER WHERE ID_USER = ?";

        System.out.println("Query: " + sql);

        /*
         * Delete data from the DB table;
         */
        try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement prSt = con.prepareStatement(sql)) {

            prSt.setInt(1, input);

            int rows = prSt.executeUpdate();

            if (rows > 0) {
                System.out.println("Number of rows affected: " + rows);
            }
            else {
                System.out.println("User not found, user ID: " + input);
            }

        } catch (SQLException ex) {

            System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
        }
    }

}
