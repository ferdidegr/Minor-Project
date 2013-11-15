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
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int menubarwidth;	
	private ArrayList<Button> buttonlist = new ArrayList<Button>();
	private boolean mousedown = false;
	private boolean keyleft = false, keyright = false, keyup = false, keydown = false;
	private MazeMap maze = null;
	private int ID = 0;								// ID when no button has been pressed
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
		right = Display.getWidth();
		top = Display.getHeight();
		left = 0;
		bottom = 0;
		menubarwidth = (right-left)/6;
		MazeMap.setSize(0.2f*menubarwidth);
		/*
		 * Initialize openGL
		 */
		initGL();
		/*
		 * Initialize Buttons
		 */
		initButtons();
		
//		texnewmaze = IO.readtexture("res/newmaze.jpg");
		
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
		if(maze!=null){
			maze.draw();
		}
	}
	/**
	 * Draw the menu items including buttons
	 */
	public void drawMenu(){
		
		/*
		 * Menu bar
		 */
		glEnable(GL_TEXTURE_2D);						// Enable Textures
		Textures.texmenubar.bind();							// Set this texture as active
											
		glBegin(GL_QUADS);								// Begin drawing the rectangle
		glTexCoord2f(0, 1);
		glVertex2f(right-menubarwidth, bottom);			// Bottom left vertex
		glTexCoord2f(1, 1);	
		glVertex2f(right, bottom);							// Top right vertex
		glTexCoord2f(1, 0);	
		glVertex2f(right, top);						// Bottom Right vertex
		glTexCoord2f(0, 0);
		glVertex2f(right-menubarwidth, top);			// top Left vertex
		

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
				if(Keyboard.getEventKey()==(Keyboard.KEY_W)){Button.scrollup();}
				if(Keyboard.getEventKey()==Keyboard.KEY_S){Button.scrolldown();}
			}

		}
		if(keyleft){left+=10;	right+=10;	initGL();}
		if(keyright){left-=10;	right-=10;	initGL();}
		if(keyup){top-=10;	bottom-=10;	initGL();}
		if(keydown){top+=10;	bottom+=10;	initGL();}
		
	}
	/**
	 * Checks if the mouse is clicked and where the mouse is at that instant
	 * @throws IOException 
	 */
	public void Mousepoll() throws IOException{
		int x = Mouse.getX()+left;				// Transform to world coordinates
		int y = Mouse.getY()+bottom;			// Transform to world coordinates
		
		if(Mouse.isButtonDown(0) && !mousedown){
			for(Button knopje: buttonlist){
				if(knopje.isButton(x, y)){				
					ID = knopje.getID();
					System.out.println(ID);
					mousedown = true;
				}
			}
			if(x>left && x<right-menubarwidth && y>bottom && y<top && maze!=null){
				System.out.println(maze.getMazeX(x)+" "+maze.getMazeY(y));
				switch(ID){
					case 2:{maze.setObject(1, x, y);break;}
					case 3:{maze.setObject(0, x, y);break;}
					
				}
			}
		}
		switch(ID){
			case 99:{IO.savechooser(maze);ID=0;break;}
			case 98:{
				try {maze = IO.loadchooser();} catch (IOException e) {} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ID=0;break;}
			case 1:{
				int mwidth = 0;
				int mheight = 0;
				
				while(mwidth==0){
					try{
						String temp = JOptionPane.showInputDialog("Enter the width as an integer:", "20");
						mwidth = Integer.parseInt(temp);
					}catch(Exception e){		
					}
				}
				while(mheight==0){
					try{
						String temp = JOptionPane.showInputDialog("Enter the height as an integer:", "20");
						mheight = Integer.parseInt(temp);
					}catch(Exception e){		
					}
				}
				maze = new MazeMap(mwidth, mheight);
				ID=0;
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
		buttonlist.add(new Button(0.05f, 0.1f,Textures.texnewmaze, 1));		// 1
		buttonlist.add(new Button(0.55f, 0.1f,Textures.texwall, 2));		// 2 
		buttonlist.add(new Button(0.05f, 1.2f,Textures.texempty, 3));		// 3
		buttonlist.add(new Button(0.55f, 1.2f,Textures.texempty, 4));		// 4
		buttonlist.add(new Button(0.05f, 10.5f,Textures.texload, 98));		// 98 load button 
		buttonlist.add(new Button(0.55f, 10.5f,Textures.texsave, 99));		// 99 save button
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
