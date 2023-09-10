package com.simplilearn.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.jdbc.bean.User;

public class _02_StmtSelect {

	public static void main(String[] args) {
		
		String dbUrl = "jdbc:mysql://localhost:3306/mydb";
		String dbUsername = "root";
		String dbPassword = "rootroot";

		
		List<User> users = new ArrayList<>();
		
		/*
		 * Create the Select SQL Statement
		 */
		String sql = "SELECT * FROM USER";
		                   
		System.out.println("Query: " + sql);
		
		/*
		 * Get data from the DB table;
		 */
		try(Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql)) {

			/*
			 * Note that the Connection, Statement and the ResultSet are closed
			 * automatically by the try() with resources.
			 */
			
			ResultSetMetaData meta = rs.getMetaData();
			
			System.out.println("ResultSetMetaData, columns: " + meta.getColumnCount());
			System.out.println("ResultSetMetaData, first column name: " + meta.getColumnName(1));
			System.out.println("ResultSetMetaData, first column type: " + meta.getColumnTypeName(1));
			
			
			while (rs.next()) {
				
				int idUser = rs.getInt("ID_USER");
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				Date birth = rs.getDate("BIRTH");
				String status = rs.getString("STATUS");
				
				/*
				 * Create and populate a User object.
				 */
				User user = new User();
				user.setIdUser(idUser);
				user.setUsername(username);
				user.setPassword(password);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setBirth(birth);
				user.setStatus(status);
				
				/*
				 * Add the user to the users list
				 */
				users.add(user);
			}
			
			System.out.println("Number of rows retrieved: " + users.size());
		} catch (SQLException ex) {
			
			System.err.println("Error while accessing the DB! , " + ex.getSQLState() + ", " + ex.getMessage());
		} 	

		users.forEach(System.out::println);
	}

}
