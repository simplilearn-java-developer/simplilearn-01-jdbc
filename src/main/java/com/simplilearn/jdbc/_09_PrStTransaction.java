package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.simplilearn.jdbc.bean.User;

public class _09_PrStTransaction {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "rootroot";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        User user1 = null;
        User user2 = null;

        try {
            user1 = new User("awright","123","Andrew","Wright",formatter.parse("2023-09-16"),"A");
            user2 = new User("rking","123","Rebecca","King",formatter.parse("2023-09-15"),"A");
        }
        catch (ParseException ex) {
            System.err.println("Error while reading input: " + ex.getMessage());
            System.exit(-1);
        }

        System.out.println("User 1: " + user1);
        System.out.println("User 2: " + user2);

        /*
         * Create the Insert SQL Statement
         */
        String sql = "INSERT INTO USER (USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, BIRTH, STATUS) "
                   + "VALUES(?,?,?,?,?,?)";

        System.out.println("Query: " + sql);

        /*
         * Insert the data into the DB table using a transaction;
         */
        try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){

            try(PreparedStatement prSt = con.prepareStatement(sql)) {

                /*
                 * Create a transaction.
                 * Note:
                 *   In a Transaction the data is reflected in the DB when do commit().
                 *   The changes can be discarded (in case of error) using rollback();
                 *
                 *   In the transaction, we can insert, update, delete, in one or multiple
                 *   tables. In this example, we have two inserts to keep it simple.
                 */
                con.setAutoCommit(false);

                /*
                 * Insert First User
                 */
                prSt.setString(1, user1.getUsername());
                prSt.setString(2, user1.getPassword());
                prSt.setString(3, user1.getFirstName());
                prSt.setString(4, user1.getLastName());
                prSt.setDate(5, new Date(user1.getBirth().getTime()));
                prSt.setString(6, user1.getStatus());
                /*
                 *  We are in a transaction so we can commit or rollback later.
                 */
                prSt.executeUpdate();

                /*
                 * Insert Second User
                 */
                prSt.setString(1, user2.getUsername());
                prSt.setString(2, user2.getPassword());
                prSt.setString(3, user2.getFirstName());
                prSt.setString(4, user2.getLastName());
                prSt.setDate(5, new Date(user2.getBirth().getTime()));
                prSt.setString(6, user2.getStatus());
                /*
                 *  We are in a transaction so we can commit or rollback later.
                 */
                prSt.executeUpdate();

                /*
                 * We force an ArithmeticException to see that the transaction
                 * is doing rollback;
                 */
                //int a = 5/0;
                /*
                 * If all the operations within the transaction complete successfully
                 * we can do commit();
                 */
                con.commit();

            } catch (Exception ex) {

                con.rollback();

                System.err.println("Error while inserting! , " + ex.getMessage());
            } finally {
                /*
                 * We close the transaction.
                 * This is important if you plan to use the same connection later.
                 */
                con.setAutoCommit(true);
            }

        }catch (SQLException ex) {

            System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
        }

    }

}
