package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class Highscores extends ButtonList{
ArrayList<String> levelname = new ArrayList<String>();
ArrayList<String> playername = new ArrayList<String>();
ArrayList<String> score = new ArrayList<String>();
int FontSize, Fontheight;

	@Override
	public void init(int x, int y) {			
		getHighscores();				
		lijst.add(new MenuButton(Fontheight*(levelname.size()+1), Textures.start, Textures.startover, 1, "Back"));		
	}
	
	private void getHighscores() {
		levelname.add("Level");
		playername.add("Name");
		score.add("Score");
		String query = "SELECT * FROM highscores;";
		ResultSet rs = Menu.score.query(query);
		FontSize = (int) (50 *Display.getHeight()/1024f);
		Fontheight = (int)( Menu.bebas.getHeight(FontSize));
		try {
			while(rs.next()){
				levelname.add(rs.getString("level"));
				playername.add(rs.getString("name"));
				score.add(rs.getString("score"));
			}
			rs.close();
		} catch (SQLException e) {	}	
		
	}
	
	public void reset(){
		levelname.clear();
		playername.clear();
		score.clear();
		getHighscores();
		lijst.get(0).setY(Fontheight*(levelname.size()+1));
	}
	
	public void display(){
		super.display();
		glColor3f(0, 0, 0);
		glRectd(0, 0, Display.getWidth(),Fontheight);
		for(int i = 0 ; i < levelname.size();i++){Menu.bebas.draw(Display.getWidth()*0.1f, i*Fontheight, FontSize, levelname.get(i));}
		for(int i = 0 ; i < playername.size();i++){Menu.bebas.draw(Display.getWidth()*2/5, i*Fontheight, FontSize, playername.get(i));}
		for(int i = 0 ; i < levelname.size();i++){Menu.bebas.draw(Display.getWidth()*4/5, i*Fontheight, FontSize, score.get(i));}
	}

	@Override
	public void actionButtons(int ID) {
		switch(ID){
			case 1:
				Menu.setState(GameState.MAIN);
				break;
		}
		
	}
	
	

}

