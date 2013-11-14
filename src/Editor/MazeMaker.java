package Editor;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;



public class MazeMaker {
	private int width;
	private int height;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int menubarwidth;
	private Texture texmenubar;
	private ArrayList<Button> buttonlist = new ArrayList<Button>();
	private boolean mousedown = false;
	private boolean keyleft = false, keyright = false, keyup = false, keydown = false;
	/**
	 * Begin the program
	 * @throws LWJGLException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void start() throws LWJGLException, InterruptedException, IOException{
		/*
		 * Select resolution
		 */
		Chooser keuze = new Chooser();
		while(keuze.getDisplay()==null){
			Thread.sleep(500);
		}
		/*
		 * Create Display
		 */
		Display.create();
		/*
		 * Initialize screen parameters
		 */
		width = Display.getWidth();
		height = Display.getHeight();
		left = 0;
		right = width;
		top = height;
		bottom = 0;
		menubarwidth = width/6;
		/*
		 * Initialize openGL
		 */
		initGL();
		/*
		 * Initialize Buttons
		 */
		initButtons();
		/*
		 * Load Textures
		 */
		texmenubar = IO.readtexture("res/Wooden_floor_original.jpg");
		/*
		 * Main loop
		 */
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			Mousepoll();
			keyboardpoll();
			display();			
			Display.update();Display.sync(60);
			
		}
	}
	/**
	 * Draw all drawable items on the screen
	 */
	public void display(){	
		drawMaze();		
		drawMenu();
	}
	/**
	 * Draw the maze map
	 */
	public void drawMaze(){
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glVertex2f(0, 0);
		glVertex2f(100, 0);
		glVertex2f(100, 100);
		glVertex2f(0f, 100f);
		glEnd();
	}
	/**
	 * Draw the menu items including buttons
	 */
	public void drawMenu(){
		
		/*
		 * Menu bar
		 */
		glEnable(GL_TEXTURE_2D);						// Enable Textures
		texmenubar.bind();								// Set this texture as active
		
		glBegin(GL_QUADS);								// Begin drawing the rectangle
		glVertex2f(right-menubarwidth, bottom);			// Bottom left vertex
		glTexCoord2f(0, 0);
		glVertex2f(right-menubarwidth, top);			// top Left vertex
		glTexCoord2f(0, 1);
		glVertex2f(right, top);							// Top right vertex
		glTexCoord2f(1, 0);
		glVertex2f(right, bottom);						// Bottom Right vertex
		glTexCoord2f(1, 1);
		glEnd();
		glDisable(GL_TEXTURE_2D);						// Disable textures
		/*
		 * Buttons
		 */
		Button.setStatics(right-menubarwidth, right, top, bottom);	// update menubar location
		for(Button knop:buttonlist){								// Loop through all buttons
			knop.update();											// Update button location
			knop.drawButton();										// Draw button
		}
	}
	/**
	 * Check if a key on the keyboard is pressed
	 */
	public void keyboardpoll(){
		
		/*
		 * Key events
		 */
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				/*
				 * Key Pressed
				 */
				if(Keyboard.getEventKey()==Keyboard.KEY_LEFT){keyleft=true;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_RIGHT)){keyright = true;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_UP)){keyup = true;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_DOWN)){keydown = true;}
			}else{
				/*
				 * Key Released events
				 */
				if(Keyboard.getEventKey()==Keyboard.KEY_LEFT){	keyleft=false;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_RIGHT)){keyright = false;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_UP)){keyup = false;}
				if(Keyboard.getEventKey()==(Keyboard.KEY_DOWN)){keydown = false;}
			}

		}
		if(keyleft){left+=10;	right+=10;	initGL();}
		if(keyright){left-=10;	right-=10;	initGL();}
		if(keyup){top-=10;	bottom-=10;	initGL();}
		if(keydown){top+=10;	bottom+=10;	initGL();}
		
	}
	/**
	 * Checks if the mouse is clicked and where the mouse is at that instant
	 */
	public void Mousepoll(){
		int x = Mouse.getX()+left;				// Transform to world coordinates
		int y = Mouse.getY()+bottom;			// Transform to world coordinates
		int ID = 0;								// ID when no button has been pressed
		if(Mouse.isButtonDown(0) && !mousedown){
			for(Button knopje: buttonlist){
				if(knopje.isButton(x, y)){				
					ID = knopje.getID();
					System.out.println(ID);
					mousedown = true;
				}
			}
		}
		switch(ID){
			case 1:{
				
				break;
			}
		}
	/*
	 * Unlock the mouse again when all mouse buttons are released
	 */
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)){mousedown = false;}
		
	}
	/**
	 * Initialize buttons
	 */
	public void initButtons(){
		/*
		 * Set the menu coordinates in world coordinates
		 */
		Button.setStatics(right-menubarwidth, right, top, bottom);
		/*
		 * Add buttons to the arraylist, give each button an unique ID!
		 */
		buttonlist.add(new Button(0.05f, 0.1f, 1));		// 1
		buttonlist.add(new Button(0.55f, 0.1f, 2));		// 2 
		buttonlist.add(new Button(0.05f, 1.2f, 3));		// 3
		buttonlist.add(new Button(0.55f, 1.2f, 4));		// 4
		buttonlist.add(new Button(0.05f, 2.3f, 5));		// 5 
		buttonlist.add(new Button(0.55f, 2.3f, 6));		// 6
	}
	/**
	 * Initialize all openGL functions
	 */
	public void initGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(left, right, bottom, top, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
		
	  
	}
	/**
	 * Main program starts here
	 * @param args
	 */
	public static void main(String[] args){
		MazeMaker maker = new MazeMaker();
		try {
			maker.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
