package Menu;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Utils.InputField;

/**\
 * 
 * Static class to display the score after succeeding in the level
 * 
 * @author ZL
 *
 */

public class ScoreScreen {
	public static void displayScoreatGO(int score){
		boolean loop = true;
		boolean ishighscore = false;
		int highscore = 0;
		String levelname = Menu.levelList.get(Menu.currentlevel).split(".maze")[0];
		initview();
		try {
			String query = "SELECT score FROM highscores WHERE level='"+levelname+"';";
			System.out.println(query);
			ResultSet rs = Menu.score.query(query);
			int counter1 = 0;
			while(rs.next()){
				counter1++;
			highscore = (rs.getInt("score"));
			}
			rs.close();
			if(counter1==0){
				Menu.score.update("INSERT INTO highscores(level, name, score) values('"+levelname+"','aap',0)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int fontSize = (int) (50* Display.getWidth()/1024f);
		InputField ipf = new InputField(8, fontSize, 10, 10, Menu.bebas);
		ipf.centerX();
		while(loop){
			glClear(GL_COLOR_BUFFER_BIT);
			// When you succeeded in the level
			if(score>=0){
				float width = (float) Menu.bebas.getWidth(fontSize, "Success!");
				float height = (float) Menu.bebas.getHeight(fontSize);
				ipf.poll();
				if(highscore<score){ishighscore = true;}
				Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Success!");
			}
			// When you ended up in complete failure
			else if (score== -200){
				float width = (float) Menu.bebas.getWidth(fontSize, "Failure!");
				float height = (float) Menu.bebas.getHeight(fontSize);
				Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Failure!");
			}
			// End the loop when the ENTER key s pressed
			while(Keyboard.next()){
				if(Keyboard.getEventKey()==Keyboard.KEY_RETURN)loop=false;
			}
			// Catch all presses on the X button, do nothing with it
			if(Display.isCloseRequested()){}
			// Update the display
			Display.update();
			
			// Do something when the display is resized
			if(Display.wasResized()){
				Menu.reshape();
				fontSize = (int) (50* Display.getWidth()/1024f);
				ipf.resize(fontSize);
				ipf.centerX();
				initview();
			}
			// Keep the frame rate at a steady 60fps
			Display.sync(60);
			
		}
		// Do something with the input from the inputfield
		if(ishighscore)
		Menu.score.update("UPDATE highscores SET name = '"+ipf.getString()+"' , score = "+score+" WHERE level='"+levelname+"'");
		System.out.println(ipf.getString());
		Menu.HSMenu.reset();
	}
	
	/**
	 * Initialize openGL for a 2D view
	 */
	public static void initview(){
		glMatrixMode(GL_PROJECTION);			
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
	}
	
}
