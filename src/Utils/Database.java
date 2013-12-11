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

	public static void reset() {
		update("DROP TABLE IF EXISTS highscores;");
		update("CREATE TABLE highscores (level STRING, name STRING, score INT)");
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
		try {
			connect();
//			reset();
//			update("INSERT INTO highscores (level, name, score) values ('level 1', 'Ferdi', 10000);");
			ResultSet rs = query("SELECT * FROM highscores;");
			
			while (rs.next()){
				String lvl = rs.getString("level");
				String nm = rs.getString("name");
				int sc = rs.getInt("score");
				System.out.println("Level: " + lvl);
				System.out.println("Name: " + nm);
				System.out.println("Score: " + sc);
				System.out.println();
			}
			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
