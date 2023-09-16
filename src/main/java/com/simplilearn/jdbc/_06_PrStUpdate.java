package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.simplilearn.jdbc.bean.User;

public class _06_PrStUpdate {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "rootroot";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        User user = new User();

        /*
         * Read the data from the User.
         */
        try(Scanner scan= new Scanner(System.in)){

            System.out.println("Enter the user ID: ");
            user.setIdUser(scan.nextInt());
            scan.nextLine();

            System.out.println("Enter the username: ");
            user.setUsername(scan.nextLine());

            System.out.println("Enter the password: ");
            user.setPassword(scan.nextLine());

            System.out.println("Enter the first name: ");
            user.setFirstName(scan.nextLine());

            System.out.println("Enter the last name: ");
            user.setLastName(scan.nextLine());

            System.out.println("Enter the date of birth (yyyy-MM-dd): ");
            user.setBirth(formatter.parse(scan.nextLine()));

            System.out.println("Enter the status: ");
            user.setStatus(scan.nextLine());

        } catch (ParseException ex) {
            System.err.println("Error while reading input: " + ex.getMessage());
            System.exit(-1);
        }

        System.out.println(user);

        /*
         * Create the Update SQL Statement
         */
        String sql = "UPDATE USER SET USERNAME = ?, PASSWORD = ?, FIRST_NAME = ?, LAST_NAME = ?, BIRTH = ?, STATUS = ? "
                   + "WHERE ID_USER = ?";

        System.out.println("Query: " + sql);

        /*
         * Update the data from the DB table;
         */
        try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement prSt = con.prepareStatement(sql)) {

            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getFirstName());
            prSt.setString(4, user.getLastName());
            /*
             * We need to convert from java.util.Date into java.sql.Date.
             *  - user.getBirth() returns a java.util.Date
             */
            prSt.setDate(5, new Date(user.getBirth().getTime()));
            prSt.setString(6, user.getStatus());
            prSt.setInt(7, user.getIdUser());

            int rows = prSt.executeUpdate();

            if (rows > 0) {
                System.out.println("Number of rows affected: " + rows);
            }
            else {
                System.out.println("User not found, user ID: " + user.getIdUser());
            }

        } catch (SQLException ex) {

            System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
        }
    }

}
