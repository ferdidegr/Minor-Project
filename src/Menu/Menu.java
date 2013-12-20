package Menu;

import Game.*;
import Utils.Chooser;
import Utils.Database;
import Utils.Sound;
import Utils.Text;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Where everything starts
 * @author ZL
 *
 */

public class Menu {
	
	private static GameState gamestate;
	public static Difficulty difficulty;
	public static Mazerunner game;
	protected static HashMap<GameState, ButtonList> menus = new HashMap<>();	
	protected static int top, bottom, scrollspeed;
	private static int screenx;
	private static int screeny;
	private static double height_width_ratio = 1/8f;			// Height/Width 
	static int currentlevel = 0;
	private static boolean closerequested = false;
	public static Text bebas;
	public static boolean ingame = false;
	static Database score ;
	static ArrayList<String> levelList;
	/**
	 * ************************************
	 * Main loop
	 * @throws LWJGLException
	 * ************************************
	 */
	public static void start() throws LWJGLException{
		gamestate = GameState.MAIN;
		difficulty = Difficulty.EASY;
		levelList = Utils.Utils.loadLevelList();
		score =  new Database("db/scores.db");
		Chooser keuze = new Chooser(true);
		while(keuze.getDisplay()==null){
			try {Thread.sleep(500);	} catch (InterruptedException e) {	}
		}
		
		Display.create();
		Display.setResizable(false);		
		screenx = Display.getWidth();
		screeny = Display.getHeight();
		bebas = new Text("BEBAS.TTF");	
		Sound.init();
		new Textures();
		initButtons();
		run();
		
	}
	
	public static void resetScroll(){
		top=Display.getHeight();
		bottom=top-screeny;
	}
	
	/**
	 * if the window is reshaped, change accordingly
	 */
	public static void reshape(){
		screenx = Display.getWidth();
		screeny = Display.getHeight();
		bottom = top  - screeny;		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());	
		for(ButtonList bl: menus.values()){
			bl.reinit();
		}
	}
	
	public static void run() {
		bottom = 0;
		top = Display.getHeight();
		scrollspeed = Display.getHeight()/15;
		if(!gamestate.equals(GameState.PAUSE)){
			glLoadIdentity();
			initGL();
		}
		
		// Main loop, while the X button is not clicked
				while(!( closerequested = Display.isCloseRequested()) && !gamestate.equals(GameState.GAME) && !gamestate.equals(GameState.EXIT)
						&& !gamestate.equals(GameState.TOMAIN)){
			
					
					// If the window is resized, might not be implemented
					if(Display.getWidth()!=screenx || Display.getHeight()!=screeny){
						reshape();							
					}
					
					mousepoll();
					// Discard all keyboard events
					while(Keyboard.next() && ingame){ 
						if(Keyboard.getEventKeyState() && Keyboard.getEventKey()==Keyboard.KEY_ESCAPE)gamestate=GameState.GAME;
					}
					display();					
					Display.update();
					Display.sync(60);
				}
		if(Menu.getState() == GameState.EXIT || closerequested){
			cleanup();
		}
			
	}
	private static void cleanup() {
		Sound.exit();
		Display.destroy();
	}

	/**
	 * ************************************
	 * poll Mouse input using events
	 * ************************************
	 */
	public static void mousepoll(){
		
		int wheeldx = Mouse.getDWheel();
		if(wheeldx>0){top-=scrollspeed;bottom-=scrollspeed;initview();}
		if(wheeldx<0){top+=scrollspeed;bottom+=scrollspeed;initview();}
		MenuButton.setMouse(Mouse.getX(), top - Mouse.getY());
	
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
		int ID = -1;
		ID = menus.get(gamestate).checkButtons(top);		
		menus.get(gamestate).actionButtons(ID);	
		
	}
	
	/**
	 * ************************************
	 * define buttons
	 * Hier worden alle buttons geinitialiseerd met de init() functie
	 * ************************************
	 */
	public static void initButtons(){
		
		int buttonwidth = Display.getWidth()/3*768/Display.getHeight();		
		int buttonheight = (int) (buttonwidth*height_width_ratio);
		MenuButton.setDimensions(buttonwidth, buttonheight);
		
		menus.put(GameState.MAIN, new MainMenu());
		menus.put(GameState.SETTINGS, new Settings());
		menus.put(GameState.GAMEOVER, new GameOver());
		menus.put(GameState.PAUSE, new PauseMenu());
		menus.put(GameState.PSETTINGS, new PSettings());
		menus.put(GameState.SELECTLVL, new MazechooserMenu());
		menus.put(GameState.DIFFICULTY, new DiffMenu());
		menus.put(GameState.HIGHSCORE, new Highscores());
		
		for(ButtonList menu: menus.values()){
			menu.init(buttonwidth, buttonheight);
		}
	}
	/**
	 * ************************************
	 * Display all objects	
	 * Afhankelijk van huidige gamestate wordt de display van de juiste ButtonList aangeroepen
	 * ************************************
	 */
	public static void display(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);		
			if(!ingame){					
				drawBackground();
			}else{
				drawGameAsBackground();

			}

			ButtonList BL = menus.get(gamestate);
			if(BL != null){
				BL.display();
			}
	}
	/**
	 * ************************************
	 * Draw background, background texture can be mapped
	 * ************************************
	 */
	public static void drawBackground(){
		glColor4f(1.0f, 1.0f, 1.0f, (gamestate==GameState.PAUSE?0.0f:1.0f));						// When using textures, set this to white
		
		
		glMatrixMode(GL_PROJECTION);						// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(),  Display.getHeight(),0, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
		
		glEnable(GL_TEXTURE_2D);
		Textures.menubackground.bind();
		double smallnumber = 0;
		glBegin(GL_QUADS);
		glTexCoord2d(0+smallnumber, 0+smallnumber);		glVertex2i(0, 0);
		glTexCoord2d(0+smallnumber, 1-smallnumber);		glVertex2i(0, Display.getHeight());
		glTexCoord2d(1-smallnumber, 1-smallnumber);		glVertex2i(Display.getWidth(), Display.getHeight());
		glTexCoord2d(1-smallnumber, 0+smallnumber);		glVertex2i(Display.getWidth(), 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
		initview();
	}
	/**
	 * ************************************
	 * Draw the ingame situation as background
	 * ************************************
	 */
	public static void drawGameAsBackground(){
		glPushAttrib(GL_ENABLE_BIT);
		game.changetoWorld();
		game.display();
		glPopAttrib();
		game.changetoHUD();
		initview();
		glDisable(GL_CULL_FACE);
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		glClear(GL_DEPTH_BUFFER_BIT);
		glEnable(GL_BLEND);
		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
		glRectd(0, Menu.bottom, Display.getWidth(), Display.getHeight()+Menu.bottom);		
		glDisable(GL_BLEND);
	}
	/**
	 * ************************************
	 * Initialize openGL
	 * ************************************
	 */
	public static void initGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Now we set up our viewpoint.
		initview();

	}
	/**
	 * ************************************
	 * Initialize your view window
	 * ************************************
	 */
	public static  void initview(){
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(), top, bottom, 1, -1);
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
	 * GET/SET GAMESTATE
	 * @param state
	 * ************************************
	 */
	public static GameState getState() {return gamestate;}
	public static void setState(GameState state) {gamestate = state;resetScroll();}
	
	public static Difficulty getDifficulty() {return difficulty;}
	public static void setDifficulty(Difficulty dif) {difficulty = dif;}
	
	public static int getScreeny() {return screeny;}
	public static int getScreenx() {return screenx;}
}
