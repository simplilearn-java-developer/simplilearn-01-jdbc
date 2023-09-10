package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.simplilearn.jdbc.bean.User;

public class _01_StmtInsert {

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
		 * Create the Insert SQL Statement
		 */
		String sql = "INSERT INTO USER (USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, BIRTH, STATUS) VALUES ";
		sql = sql + "('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getFirstName() + "','" +
		                   user.getLastName() + "','" + formatter.format(user.getBirth()) + "','" + user.getStatus() + "')";
		                   
		System.out.println("Query: " + sql);
		
		/*
		 * Insert the data into the DB table;
		 */
		try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement st = con.createStatement()) {
		
			int rows = st.executeUpdate(sql);
			
			System.out.println("Number of rows affected: " + rows);
		} catch (SQLException ex) {
			
			System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
		} 	

//		try {
//			con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//
//			System.out.println("Connection Successful");
//		} catch (SQLException ex) {
//
//			System.err.println("Error while connecting! , " + ex.getSQLState() + ", " + ex.getMessage());
//		} finally {
//			try {
//				if (con != null && !con.isClosed()) {
//					con.close();
//				}
//			} catch (SQLException ex) {
//				System.err.println("Error while closing connection! , " + ex.getSQLState() + ", " + ex.getMessage());
//			}
//		}
	}

}
