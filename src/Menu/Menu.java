package Menu;
import Game.*;
import Utils.Chooser;
import Utils.Sound;
import Utils.Text;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.TextureImpl;

import static org.lwjgl.opengl.GL11.*;

public class Menu {
	
	private static GameState gamestate;
	public static Mazerunner game;
	private static MainMenu main = new MainMenu();
	private static Settings sets = new Settings();
	private static GameOver over = new GameOver();
	private static PauseMenu pauze = new PauseMenu();
	private static PSettings pset = new PSettings();
	private static MazechooserMenu mazemenu= new MazechooserMenu();
	private static int top, bottom, scrollspeed;
	private static int screenx;
	private static int screeny;
	private static double height_width_ratio = 1/8f;			// Height/Width 
	static int currentlevel = 0;
	
	/**
	 * ************************************
	 * Main loop
	 * @throws LWJGLException
	 * ************************************
	 */
	public static void start() throws LWJGLException{
		gamestate = GameState.MAIN;

		Chooser keuze = new Chooser(true);
		while(keuze.getDisplay()==null){
			try {Thread.sleep(500);	} catch (InterruptedException e) {	}
		}
		
		Display.create();
		Display.setResizable(true);
		new Utils.Text();
		screenx = Display.getWidth();
		screeny = Display.getHeight();
		Sound.init();
		new Textures();
		initButtons();
		run();
		
	}
	
	/**
	 * if the window is reshaped, change accordingly
	 */
	public static void reshape(){
		screenx = Display.getWidth();
		screeny = Display.getHeight();
		bottom = top  - screeny;
		glViewport(0, 0, Display.getWidth(), Display.getHeight());	
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
				while(!Display.isCloseRequested() && !gamestate.equals(GameState.GAME) && !gamestate.equals(GameState.EXIT)
						&& !gamestate.equals(GameState.TOMAIN)){
			
					
					// If the window is resized, might not be implemented
					if(Display.getWidth()!=screenx || Display.getHeight()!=screeny){
						reshape();	
						if(game!=null){
							game.reshape();
						}
					}
					
					mousepoll();
					// Discard all keyboard events
					while(Keyboard.next()){}
					display();					
					Display.update();
					Display.sync(60);
				}
		if(Menu.getState() == GameState.EXIT){
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
		switch(gamestate){
		
		case SETTINGS:
			ID = sets.checkButtons(top);
			Settings.actionButtons(ID);
			
			break;
			
		case MAIN:
			ID = main.checkButtons(top);
			MainMenu.actionButtons(ID);
			break;
			
		case GAMEOVER:
			ID = over.checkButtons(top);
			GameOver.actionButtons(ID);
			break;
			
		case PAUSE:
			ID = pauze.checkButtons(top);
			PauseMenu.actionButtons(ID);
			break;
			
		case PSETTINGS:
			ID = pset.checkButtons(top);
			PSettings.actionButtons(ID);
			break;
		
		case SELECTLVL:
			ID  =mazemenu.checkButtons(top);
			MazechooserMenu.actionButtons(ID);
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
		MenuButton.setDimensions(buttonwidth, buttonheight);
		
		// MainMenu
		main.init(buttonwidth, buttonheight);
		
		// Settings
		sets.init(buttonwidth, buttonheight);
		
		// Game Over
		over.init(buttonwidth, buttonheight);
		
		// Pauze
		pauze.init(buttonwidth, buttonheight);
		
		// Pauze ingame settings
		pset.init(buttonwidth, buttonheight);
		
		// Mazechooser
		mazemenu.init(buttonwidth, buttonheight);
		
		
	}
	/**
	 * ************************************
	 * Display all objects	
	 * Afhankelijk van huidige gamestate wordt de display van de juiste ButtonList aangeroepen
	 * ************************************
	 */
	public static void display(){
					
			if(!gamestate.equals(GameState.GAME)){	
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
				drawBackground();
			}
			

		
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
			drawGameAsBackground();					
			pauze.display();
			break;
			
		case PSETTINGS:
			drawGameAsBackground();
			pset.display();
			break;
		
		case SELECTLVL:
			mazemenu.display();
		
		default: break;
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
	public static void setState(GameState state) {gamestate = state;}
	
	public static int getScreeny() {return screeny;}
	public static int getScreenx() {return screenx;}
}
