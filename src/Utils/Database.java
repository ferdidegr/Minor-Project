package Utils;

import java.sql.*;

public class Database {
	static Connection conn;
	static Statement stat;

	public static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:db/scores.db");
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(String sql){
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet query(String query){
		try {
			return stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		connect();
//		update("INSERT INTO highscores (name, score) values ('Ferdi', 10000)");
		ResultSet rs=query("SELECT * FROM highscores;");
		try {
			while (rs.next()){
				System.out.println(rs.getString("name"));
				System.out.println(rs.getInt("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
