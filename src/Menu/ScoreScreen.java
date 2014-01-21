package Menu;

import static org.lwjgl.opengl.GL11.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import Utils.InputField;
import Utils.Timer;

/**\
 * 
 * Static class to display the score after succeeding in the level
 * It is a standalone, it is not linked to the menu. The menu class does not handle the events
 * 
 * @author ZL
 *
 */

public class ScoreScreen {
	private static int fontSize;
	
	/**
	 * startpoint, GO stands for GameOver
	 * @param score the score achieved in game
	 * @param timeLeft the time left
	 * @param health the health you have when ending the game
	 */
	public static void displayScoreatGO(int score, int timeLeft, double health){
		boolean loop = true;
		boolean ishighscore = false;
		int highscore = 0;
		
		/*
		 * Get previous highscore from database
		 */
		String currentlevel = Menu.levelList.get(Menu.currentlevel);
		String levelname;
		if(currentlevel.startsWith("custom")){
			levelname = "custom/"+currentlevel.split("\\.maze")[0].split("/")[1];
		}else{
			levelname = currentlevel.split("\\.maze")[0].split("/")[1];
		}
		
		initview();
		highscore = readDatabase(levelname);
		
		fontSize = (int) (50* Display.getWidth()/1024f);
		InputField ipf = new InputField(8, fontSize, 10, 10, Menu.mainfont);
		ipf.centerX();
		ipf.setY(Display.getHeight()*2/3);
		Timer t1 = new Timer().start();
		/*
		 * Display success or failure text
		 */
		
		while(t1.getTime()<2000 && loop){
			glClear(GL_COLOR_BUFFER_BIT);
			if(score == -200){
				showFailure();
			}else{
				showSuccess();
			}
			// Only get out when ENTER is released, to prevent jumping through the highscore name input screen
			while(Keyboard.next()){
				if(!Keyboard.getEventKeyState()){
					if(Keyboard.getEventKey()==Keyboard.KEY_RETURN 
					|| Keyboard.getEventKey()==Keyboard.KEY_ESCAPE )loop=false;
				}
			}
			
			Display.update();
			Display.sync(60);
		}
		/*
		 * Catch all remaining keyboard events and do nothing with it
		 */
		while(Keyboard.next()){Keyboard.getEventKey();};
		/*
		 * If it is a success then display score computations
		 */		
		if(score!=-200){
			score = computescore(health, timeLeft, score);
			if(highscore<score || score==0){ishighscore = true;}
		}	
		
		/*
		 * If highscore then allow for input of your name
		 */
		loop = true;
		
		if(ishighscore){			
			
			// Defining prompt text
			int fontsize = (int) (50 *Display.getWidth()/1024f);
			String prompt = "ENTER NAME:";
			double width = Menu.mainfont.getWidth(fontsize, prompt);
			double height = Menu.mainfont.getHeight(fontsize);
			
			// Defining highscore text
			int fonts1 = (int) (100 * Display.getHeight()/768f);
			String hstext = "NEW HIGHSCORE!";
			double hwidth = Menu.mainfont.getWidth(fonts1, hstext);
			double hheight = Menu.mainfont.getHeight(fonts1);
			double hsint = Menu.mainfont.getWidth(fonts1/4f, ""+score);
			
			while(loop){
				glClear(GL_COLOR_BUFFER_BIT);			
				
				// poll for the inputfield				
					ipf.poll();					

				// End the loop when the ENTER key s pressed
				while(Keyboard.next()){
					if(Keyboard.getEventKey()==Keyboard.KEY_RETURN && ipf.getString().length()>0)loop=false;
				}
				
				Menu.mainfont.draw((float) ((Display.getWidth()-hwidth)/2), (float) (Display.getHeight()/4), fonts1, hstext, Color.red);
				Menu.mainfont.draw((float) ((Display.getWidth()-hsint)/2), (float) (Display.getHeight()/4+hheight), (int) (fonts1/3f), ""+score);
				Menu.mainfont.draw((float) ((Display.getWidth()-width)/2), (float) (ipf.getY()-1.5*height), fontsize, prompt);
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
		/*
		 * No highscore and no failure
		 */
		else if(score!= -200){
			t1.start();
			int fonts1 = (int) (100 * Display.getHeight()/768f);
			int fonts2 = (int) (50* Display.getHeight()/768f);
			String yourscore = "Your score: "+score;			
			double yswidth = Menu.mainfont.getWidth(fonts2, yourscore);	
			double height = Menu.mainfont.getHeight(fonts1);
			String Highscore = "HIGHSCORE: "+highscore;			
			double hswidth = Menu.mainfont.getWidth(fonts1, Highscore);
			
			while(t1.getTime()<4000){
				glClear(GL_COLOR_BUFFER_BIT);				
				
				Menu.mainfont.draw((float) ((Display.getWidth()-hswidth)/2), (float) Display.getHeight()/3, fonts1, Highscore,Color.red);
				Menu.mainfont.draw((float) ((Display.getWidth()-yswidth)/2), (float) (height+Display.getHeight()/3), fonts2, yourscore);
				
				
				Display.update();
				Display.sync(60);
			}
		}
//		System.out.println(ipf.getString());
		Highscores HS = (Highscores)Menu.menus.get(GameState.HIGHSCORE);
		HS.reset();
	}	
	
	/**
	 * Formulas to compute score, also displays the score computation
	 * @param healthfraction
	 * @param timeLeft
	 * @param score
	 * @return
	 */
	private static int computescore(double health, int timeLeft, int score) {
		Timer tempt = new Timer();
		int healthbonus = (int) (2 * health);
		int timebonus 	= (int) (timeLeft/100f);
		int diffmodifier = 1;
		// Difficulty
		switch(Menu.getDifficulty()){
			case EASY: diffmodifier = 1; break;
			case MEDIUM: diffmodifier = 2; break;
			case HARD:	diffmodifier = 4; break;
			case INSANE: diffmodifier = 8; break;
		}
		
		
		int fontsize = (int) (50 * Display.getHeight()/768f);
		double healthwidth = Menu.mainfont.getWidth(fontsize, healthbonus+"");
		double timewidth = Menu.mainfont.getWidth(fontsize, timebonus+"");
		double height = Menu.mainfont.getHeight(fontsize);
		double lvlscorewidth = Menu.mainfont.getWidth(fontsize, score+"");
		double intermscorew =  Menu.mainfont.getWidth(fontsize, (score+healthbonus+timebonus)+"");
		double diffwidth =  Menu.mainfont.getWidth(fontsize, "X"+diffmodifier);
		int score2 = (int) (score + healthbonus + timebonus)*diffmodifier;
		double finalscorew = Menu.mainfont.getWidth(fontsize, score2+"");
		boolean skip = false;
		int maxlooptime = 8000;
		tempt.start();
		while(tempt.getTime() < maxlooptime ){
			glClear(GL_COLOR_BUFFER_BIT);
			Menu.mainfont.draw(Display.getWidth()*(1/6f), (float) height/2, fontsize, "level score:");
			Menu.mainfont.draw((float) (Display.getWidth()*5/6-lvlscorewidth), (float) height/2, fontsize, score+"");
			
			if(tempt.getTime()>1000 || skip){
				Menu.mainfont.draw(Display.getWidth()*(1/6f), (float) height*2, fontsize, "health bonus:");
				Menu.mainfont.draw((float) (Display.getWidth()*5/6-healthwidth), (float) height*2, fontsize, healthbonus+"");
			}
			if(tempt.getTime()>2000 || skip){
				Menu.mainfont.draw(Display.getWidth()*(1/6f), (float) height * 3.5f, fontsize, "time bonus:");	
				Menu.mainfont.draw((float) (Display.getWidth()*5/6-timewidth), (float) height *3.5f, fontsize, timebonus+"");
			}
			if(tempt.getTime()>3000 || skip){
				glLineWidth(2);
				glBegin(GL_LINES);
				glVertex2d(Display.getWidth()*(1/6f), (float) height *3.5f+height);
				glVertex2d(Display.getWidth()*(5/6f), (float) height *3.5f+height);
				glEnd();
			
				Menu.mainfont.draw((float) (Display.getWidth()*5/6-intermscorew), (float) height *5f, fontsize, (score+healthbonus+timebonus)+"");
			}
			if(tempt.getTime()>4000 || skip){
				Menu.mainfont.draw(Display.getWidth()*(1/6f), (float) height * 6.5f, fontsize, "difficulty:");	
				Menu.mainfont.draw((float) (Display.getWidth()*5/6-diffwidth), (float) height *6.5f, fontsize, "X"+diffmodifier);
			}			
			
			if(tempt.getTime()>5000 || skip){
				glLineWidth(2);
				glBegin(GL_LINES);
				glVertex2d(Display.getWidth()*(1/6f), (float) height *6.5f+height);
				glVertex2d(Display.getWidth()*(5/6f), (float) height *6.5f+height);
				glEnd();
			
				Menu.mainfont.draw((float) (Display.getWidth()*5/6-finalscorew), (float) height *8f, fontsize, score2+"");
			}
			if(Mouse.next() && Mouse.getEventButtonState() || Keyboard.next() && Keyboard.getEventKeyState()){
				skip = true;
				maxlooptime = 3000;
			}
			Display.update();
			Display.sync(60);
		}
		
		
		return score2;
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
//			System.out.println(query);
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
		float width = (float) Menu.mainfont.getWidth(fontSize, "Failure!");
		float height = (float) Menu.mainfont.getHeight(fontSize);
		Menu.mainfont.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Failure!");
	}
	/**
	 * Print the text success on the screen
	 */
	private static void showSuccess(){
		float width = (float) Menu.mainfont.getWidth(fontSize, "Success!");
		float height = (float) Menu.mainfont.getHeight(fontSize);
		Menu.mainfont.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, fontSize, "Success!");
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
