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
		lijst.add(new MenuButton(10, null, null, 100, "", MenuButton.Alignment.CENTER)); // Dummy
		lijst.add(new MenuButton(Fontheight*(levelname.size()+1), Textures.start, Textures.startover, 1, "Reset score", MenuButton.Alignment.CENTER));	
		lijst.add(new MenuButton(Fontheight*(levelname.size()+1), Textures.start, Textures.startover, 2, "Back", MenuButton.Alignment.RIGHT));		
	}
	
	private void getHighscores() {
		levelname.add("Level");
		playername.add("Name");
		score.add("Score");
		String query = "SELECT * FROM highscores ORDER BY level;";
		ResultSet rs = Menu.score.query(query);
		FontSize = (int) (30 *Display.getHeight()/768f);
		Fontheight = (int)( Menu.mainfont.getHeight(FontSize));
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
		lijst.get(1).setY(Fontheight*(levelname.size()+1));
		lijst.get(2).setY(Fontheight*(levelname.size()+1));
	}
	
	public void display(){	
		glEnable(GL_BLEND);
		
		toFixedScreen();		
		glColor4f(0, 0, 0,0.2f);
		glRectd(0, 0, Display.getWidth(),Display.getHeight());		
		toDynamicScreen();
		
		super.display();

		
		for(int i = 1 ; i < levelname.size();i++){Menu.mainfont.draw(Display.getWidth()*0.1f, i*Fontheight, FontSize, levelname.get(i));}
		for(int i = 1 ; i < playername.size();i++){Menu.mainfont.draw(Display.getWidth()*2/5, i*Fontheight, FontSize, playername.get(i));}
		for(int i = 1 ; i < levelname.size();i++){Menu.mainfont.draw(Display.getWidth()*4/5, i*Fontheight, FontSize, score.get(i));}
		

		toFixedScreen();		
		glColor4f(0, 0, 0,0.8f);
		glRectd(0, 0, Display.getWidth(),Fontheight);
		
		
		Menu.mainfont.draw(Display.getWidth()*0.1f, 0, FontSize, levelname.get(0));
		Menu.mainfont.draw(Display.getWidth()*2/5, 0, FontSize, playername.get(0));
		Menu.mainfont.draw(Display.getWidth()*4/5, 0, FontSize, score.get(0));		
		toDynamicScreen();
		
		glDisable(GL_BLEND);
	}

	@Override
	public void actionButtons(int ID) {
		switch(ID){
			case 1:
				String query = "DELETE FROM highscores;";
				Menu.score.update(query);
				reset();
				break;
			case 2:
				Menu.setState(GameState.MAIN);
				break;
		}
		
	}
	
	public void toFixedScreen(){
		glPushMatrix();
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void toDynamicScreen(){
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}

}

