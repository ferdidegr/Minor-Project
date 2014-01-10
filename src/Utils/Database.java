package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class Database {
	Connection conn;
	Statement stat;
	/**
	 * Connect to a database file
	 * @param databasepath the path to the database file
	 */
	public Database (String databasepath) {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+databasepath);
			stat = conn.createStatement();
			setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Setup
	 */
	public void setup(){
		createTable("highscores", "level STRING", "name STRING","score INT");
	}
	/**
	 * Reset scores
	 */
	public void reset() {
		update("DROP TABLE IF EXISTS highscores;");
		createTable("highscores", "level STRING", "name STRING","score INT");
	}
	/**
	 * insert an update query (INSERT, DELETE, UPDATE)
	 * @param sql
	 */
	public void update(String sql){
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create a table
	 * @param TableName		name of the table
	 * @param PrimaryKey	primary key of the table
	 * @param attributes	a list of strings with their type e.g. STRING or INT
	 */
	public void createTable(String TableName, String PrimaryKey, String... attributes){
		String query = "CREATE TABLE IF NOT EXISTS "+TableName+" ("+PrimaryKey+" PRIMARY KEY";
		for(int i = 0 ; i < attributes.length; i++){
			query+= ", "+attributes[i];
		}
		query += ")";
		update(query);
	}
	/**
	 * Drop a table
	 * @param TableName
	 */
	public void dropTable(String TableName){
		update("DROP TABLE "+TableName+";");
	}
	/**
	 * Query the database, it must be a READ since it must return a result
	 * @param query
	 * @return
	 */
	public ResultSet query(String query){
		try {
			return stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void destroy() throws SQLException{
		conn.close();
		stat.close();
	}
	/**
	 * MAin not needed
	 * @param args
	 */
	public static void main(String[] args){
		Database score = new Database("db/sc.db");
		try {
			String test = "blablabla";
			
//			reset();
			
			score.update("INSERT INTO highscores (level, name, score) values ('"+test+"', 'Ferdi', 10000);");
	

//				stat.executeUpdate("DELETE FROM highscores WHERE level='blablabla'");
//				dropTable("scores");
//				createTable("student", "id", "name","address");
			ResultSet rs = score.query("SELECT * FROM highscores;");
			
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
			score.destroy();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
