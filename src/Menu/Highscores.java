package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Highscores extends ButtonList{
ArrayList<String> levelname = new ArrayList<String>();
ArrayList<String> playername = new ArrayList<String>();
ArrayList<String> score = new ArrayList<String>();
	@Override
	public void init(int x, int y) {		
		levelname.add("Level");
		playername.add("Name");
		score.add("Score");
		String query = "SELECT * FROM highscores;";
		ResultSet rs = Menu.score.query(query);
		
		try {
			while(rs.next()){
				levelname.add(rs.getString("level"));
				playername.add(rs.getString("name"));
				score.add(rs.getString("score"));
			}
		} catch (SQLException e) {	}
		
		lijst.add(new MenuButton(50, Textures.start, Textures.startover, 1, "Back"));
		
	}
	
	public void display(){
		super.display();
	}

	@Override
	public void actionButtons(int ID) {
		
		
	}
	
	

}

