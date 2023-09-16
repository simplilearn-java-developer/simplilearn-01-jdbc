package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.simplilearn.jdbc.bean.User;

public class _08_PrStInsertBatch {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "rootroot";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        List<User> users = new ArrayList<>();

        try {
            users.add(new User("cdavis","123","Charles","Davis",formatter.parse("2023-09-16"),"A"));
            users.add(new User("landerson","123","Lisa","Anderson",formatter.parse("2023-09-15"),"A"));
            users.add(new User("mtaylor","1234","Mark","Taylor",formatter.parse("2023-09-14"),"A"));
            users.add(new User("smoore","12345","Sandra","Moore",formatter.parse("2023-09-13"),"A"));
        }
        catch (ParseException ex) {
            System.err.println("Error while reading input: " + ex.getMessage());
            System.exit(-1);
        }

        users.forEach(System.out::println);

        /*
         * Create the Insert SQL Statement
         */
        String sql = "INSERT INTO USER (USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, BIRTH, STATUS) "
                   + "VALUES(?,?,?,?,?,?)";

        System.out.println("Query: " + sql);

        /*
         * Insert the data into the DB table in Batch;
         */
        try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            PreparedStatement prSt = con.prepareStatement(sql)) {

            for (User user: users) {

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

                /*
                 * Adds a set of parameters to this PreparedStatement object's batch of commands.
                 *
                 * We can execute prSt.executeUpdate() multiple times. One for each element on
                 * the users list, but that is inefficient. It's better to load this info in a
                 * buffer, then we create all the rows at once.
                 */
                prSt.addBatch();
            }


            int[] rows = prSt.executeBatch();// Create all the rows at once.

            System.out.println("Number of rows affected: " + Arrays.toString(rows));


        } catch (SQLException ex) {

            System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
        }
    }

}
