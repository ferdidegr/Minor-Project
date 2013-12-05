package Menu;
import Game.*;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Menu {
	
	private static GameState gamestate;
	public static Mazerunner game;
	private static MainMenu main = new MainMenu();
	private static Settings sets = new Settings();
	private static GameOver over = new GameOver();
	private static PauseMenu pauze = new PauseMenu();
	private static PSettings pset = new PSettings();
	private static int top, bottom, scrollspeed;
	private static int screenx = 1240;
	private static int screeny = 680;
	private static double height_width_ratio = 1/4f;			// Height/Width 
	/**
	 * ************************************
	 * Main loop
	 * @throws LWJGLException
	 * ************************************
	 */
	public static void start() throws LWJGLException{
		gamestate = GameState.MAIN;

		Display.setDisplayMode(new DisplayMode(screenx, screeny));
		Display.create();
		if (Mazerunner.getSound()==true){
		Sound.init();
		}
		new Textures();
		initButtons();
		run();
		
	}
	
	public static void run() {
		
		bottom = 0;
		top = Display.getHeight();
		scrollspeed = Display.getHeight()/15;
		glLoadIdentity();
		initGL();
		// Main loop, while the X button is not clicked
				while(!Display.isCloseRequested() && !Menu.getState().equals(GameState.GAME)){
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
					glClearColor(0.0f, 0.0f, 0.0f, 0.0f);			
					mousepoll();
					display();
					Display.update();
					Display.sync(60);
				}
	}
	/**
	 * ************************************
	 * poll Mouse input using events
	 * ************************************
	 */
	public static void mousepoll(){
		
		int wheeldx = Mouse.getDWheel();
		if(wheeldx>0){top+=scrollspeed;bottom+=scrollspeed;initview();}
		if(wheeldx<0){top-=scrollspeed;bottom-=scrollspeed;initview();}
		MenuButton.setMouse(Mouse.getX(), Mouse.getY()+bottom);
		while(Mouse.next()){
			/*
			 * Button pressed
			 */
			if(Mouse.getEventButtonState()){
				if(Mouse.getEventButton()==0){
					ButtonActions();
				}
			}
			
			/*
			 * Button released
			 */
			if(!Mouse.getEventButtonState()){
				
			}
		}
	}
	/**
	 * ************************************
	 * check buttons
	 * Deze methode roept de checkButtons aan voor het huidige menu
	 * !Define actions for buttons here!
	 * ************************************
	 */
	public static void ButtonActions(){
		int ID = 0;
		switch(gamestate){
		
		case SETTINGS:
			ID = sets.checkButtons(bottom);
			Settings.actionButtons(ID);
			
			break;
			
		case MAIN:
			ID = main.checkButtons(bottom);
			MainMenu.actionButtons(ID);
			break;
			
		case GAMEOVER:
			ID = over.checkButtons(bottom);
			GameOver.actionButtons(ID);
			break;
			
		case PAUSE:
			ID = pauze.checkButtons(bottom);
			PauseMenu.actionButtons(ID);
			break;
			
		case PSETTINGS:
			ID = pset.checkButtons(bottom);
			PSettings.actionButtons(ID);
			break;
			
		default: break;
			
		}
	}
	
	/**
	 * ************************************
	 * define buttons
	 * Hier worden alle buttons geinitialiseerd met de init() functie
	 * ************************************
	 */
	public static void initButtons(){
		
		int buttonwidth = Display.getWidth()/3;		
		int buttonheight = (int) (buttonwidth*height_width_ratio);
		MenuButton.setDimensions(226, 56);
		
		// MainMenu
		main.init(buttonwidth, buttonheight);
		
		// Settings
		sets.init(buttonwidth, buttonheight);
		
		// Game Over
		over.init(buttonwidth, buttonheight);
		
		// Pauze
		pauze.init(buttonwidth, buttonheight);
		
		// Pauze
		pset.init(buttonwidth, buttonheight);
		
		
	}
	/**
	 * ************************************
	 * Display all objects	
	 * Afhankelijk van huidige gamestate wordt de display van de juiste ButtonList aangeroepen
	 * ************************************
	 */
	public static void display(){
		drawBackground();
		
		switch(gamestate){
			
		case MAIN:
			main.display();
			break;
			
		case SETTINGS:
			sets.display();
			break;
			
		case GAMEOVER:
			over.display();
			break;
			
		case PAUSE:
			pauze.display();
			break;
			
		case PSETTINGS:
			pset.display();
			break;
			
		
		default: break;
		}
			
	}
	/**
	 * ************************************
	 * Draw background, background texture can be mapped
	 * ************************************
	 */
	public static void drawBackground(){
		glColor3f(1.0f, 1.0f, 1.0f);						// When using textures, set this to white
		
		
		glMatrixMode(GL_PROJECTION);						// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		glMatrixMode(GL_MODELVIEW);	
		
		glEnable(GL_TEXTURE_2D);
		Textures.menubackground.bind();
		glBegin(GL_QUADS);
		glTexCoord2d(0, 1);
		glVertex2i(0, 1);
		glTexCoord2d(0, 0);
		glVertex2i(0, Display.getHeight());
		glTexCoord2d(1, 0);
		glVertex2i(Display.getWidth(), Display.getHeight());
		glTexCoord2d(1, 1);
		glVertex2i(Display.getWidth(), 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
		initview();
	}
	/**
	 * ************************************
	 * Initialize openGL
	 * ************************************
	 */
	public static void initGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		initview();

	}
	/**
	 * ************************************
	 * Initialize your view window
	 * ************************************
	 */
	public static  void initview(){
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(), bottom, top, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
	}
	/**
	 * ************************************
	 * Start the menu
	 * @param args
	 * ************************************
	 */
	public static void main(String[] args){
		try {
			Menu.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ************************************
	 * SET GAMESTATE
	 * @param state
	 * ************************************
	 */
	
	public static GameState getState(){
		return gamestate;
	}
	
	/**
	 * ************************************
	 * SET GAMESTATE
	 * @param state
	 * ************************************
	 */
	public static void setState(GameState state){
		gamestate = state;
	}
	
	public static int getScreeny(){return screeny;}
	public static int getScreenx(){return screenx;}
}
