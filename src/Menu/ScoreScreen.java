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
import Utils.Timer;

/**\
 * 
 * Static class to display the score after succeeding in the level
 * 
 * @author ZL
 *
 */

public class ScoreScreen {
	private static int fontSize;
	
	
	public static void displayScoreatGO(int score, int timeLeft, double healthfraction){
		boolean loop = true;
		boolean ishighscore = false;
		int highscore = 0;
		// TODO Remove
		System.out.println(score+" "+timeLeft+" "+healthfraction);
		
		String levelname = Menu.levelList.get(Menu.currentlevel).split("\\.maze")[0];
		initview();
		highscore = readDatabase(levelname);
		
		fontSize = (int) (50* Display.getWidth()/1024f);
		InputField ipf = new InputField(8, fontSize, 10, 10, Menu.bebas);
		ipf.centerX();
		Timer t1 = new Timer().start();
		/*
		 * Display success or failure text
		 */
		while(t1.getTime()<2000){
			glClear(GL_COLOR_BUFFER_BIT);
			if(score == -200){
				showFailure();
			}else{
				showSuccess();
			}
			Display.update();
			Display.sync(60);
		}
		/*
		 * If it is a success then display score computations
		 */
		score = (int) (score + 1000 * healthfraction);
		if(highscore<score || score==0){ishighscore = true;}
		/*
		 * If highscore then allow for input of your name
		 */
		if(ishighscore){
			while(loop){
				glClear(GL_COLOR_BUFFER_BIT);			
				
				// When you succeeded in the level
				if(score>=0){	
					ipf.poll();					
				}
				// When you ended up in complete failure
				else if (score== -200){break;}
				// End the loop when the ENTER key s pressed
				while(Keyboard.next()){
					if(Keyboard.getEventKey()==Keyboard.KEY_RETURN)loop=false;
				}
	
				// Draw the mouse pointer
				Menu.drawMousePointer();
				// Catch all presses on the X button, do nothing with it
				if(Display.isCloseRequested()){}
				// Update the display
				Display.update();			
	
				// Keep the frame rate at a steady 60fps
				Display.sync(60);			
			}
			// Do something with the input from the inputfield
			
			Menu.score.update("UPDATE highscores SET name = '"+ipf.getString()+"' , score = "+score+" WHERE level='"+levelname+"'");
		}
		System.out.println(ipf.getString());
		Highscores HS = (Highscores)Menu.menus.get(GameState.HIGHSCORE);
		HS.reset();
	}	
	/**
	 * get the highscore of the requested level
	 * @param levelname
	 * @return
	 */
	private static int readDatabase(String levelname){
		int highscore = 0;
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
				Menu.score.update("INSERT INTO highscores(level, name, score) values('"+levelname+"','',0)");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return highscore;
	}	
	
	/**
	 * 	Print the text Failure on the screen
	 */
	private static void showFailure(){
		float width = (float) Menu.bebas.getWidth(fontSize, "Failure!");
		float height = (float) Menu.bebas.getHeight(fontSize);
		Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Failure!");
	}
	/**
	 * Print the text success on the screen
	 */
	private static void showSuccess(){
		float width = (float) Menu.bebas.getWidth(fontSize, "Success!");
		float height = (float) Menu.bebas.getHeight(fontSize);
		Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Success!");
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
