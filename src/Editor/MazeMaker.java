package Editor;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import Menu.Menu;
import Utils.Chooser;
import Utils.IO;
import Utils.Sound;
import static org.lwjgl.opengl.GL11.*;
/**
 * Program to make mazes for the game Ruins of scorps
 * 
 * @author ZL
 *
 */
public class MazeMaker {
	private int left;								// world coordinate of the left side of the screen
	private int right;								// world coordinate of the right side of the screen
	private int top;								// world coordinate of the top side of the screen
	private int bottom;								// world coordinate of the bottom side of the screen
	private int menubarwidth;	
	private ArrayList<Button> buttonlist = new ArrayList<Button>();
	private boolean mousedown = false, ctrldown = false, Lshiftdown = false, Laltdown = false;
	private MazeMap maze = null;
	private int ID = -1, leftID = 0, rightID = 0;								// ID when no button has been pressed
	private boolean exit = false;	
	private float tilesize;
	private Sound sound;
	private boolean standalone;
	/**
	 * ***********************************************
	 * Begin the program
	 * @throws LWJGLException
	 * @throws InterruptedException
	 * @throws IOException
	 * ***********************************************
	 */
	public void start(boolean standalone) throws LWJGLException, InterruptedException, IOException{
		Button.resetSelectors();
		this.standalone = standalone;
		/*
		 * Set mouse
		 */
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
			
		/*
		 * Initialize screen parameters
		 */
			right = Display.getWidth();
			top = Display.getHeight();
			left = 0;
			bottom = 0;
			menubarwidth = (Display.getWidth()<1024? Display.getWidth()/6:(int) (1024f/6f));
			tilesize = 0.2f * menubarwidth;
			MazeMap.setSize(tilesize);			
		/*
		 * Initialize openGL
		 */
			initGL();
			
		/*
		 * Initialize sound
		 */
			if(!standalone){
				sound=Menu.getSkitter();
			}
			
		/*
		 * Initialize Buttons
		 */
			initButtons();	

		
		/*
		 * Main loop
		 */
		while(!Display.isCloseRequested() && !exit){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			
			Mousepoll();
			keyboardpoll();
			display();
			
			Display.update();
			Display.sync(60);
		}
		
		Mouse.setGrabbed(true);
	}
	/**
	 * ***********************************************
	 * Draw all drawable items on the screen
	 * ***********************************************
	 */
	public void display(){			
		drawMaze();			
		drawMenu();
	}
	/**
	 * ***********************************************
	 * Draw the maze map
	 * ***********************************************
	 */
	public void drawMaze(){
		
		if(maze != null){
			maze.draw();
		}
	}
	/**
	 * ***********************************************
	 * Draw the menu items including buttons
	 * ***********************************************
	 */
	public void drawMenu(){
		/*
		 * Menu bar
		 */
		glColor3f(1, 1, 1);
		glEnable(GL_TEXTURE_2D);						// Enable Textures
		Textures.texmenubar.bind();						// Set this texture as active
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);			
		glBegin(GL_QUADS);								// Begin drawing the rectangle
			glTexCoord2f(0, 5);			glVertex2f(right-menubarwidth, bottom);		// Bottom left vertex
			glTexCoord2f(1, 5);			glVertex2f(right, bottom);					// Top right vertex
			glTexCoord2f(1, 0);			glVertex2f(right, top);						// Bottom Right vertex
			glTexCoord2f(0, 0); 		glVertex2f(right-menubarwidth, top);		// top Left vertex
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
	 * ********************************************************
	 * Check if a key on the keyboard is pressed
	 * ********************************************************
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
				if(Keyboard.getEventKey() == Keyboard.KEY_LCONTROL){ctrldown = true;}
				if(Keyboard.getEventKey() == Keyboard.KEY_LSHIFT){Lshiftdown = true;}
				if(Keyboard.getEventKey() == Keyboard.KEY_LMENU){Laltdown = true;}
			}else{
				/*
				 * Key Released events
				 */
				if(Keyboard.getEventKey() == Keyboard.KEY_LCONTROL){ctrldown = false;}
				if(Keyboard.getEventKey() == Keyboard.KEY_LSHIFT){Lshiftdown = false;}
				if(Keyboard.getEventKey() == Keyboard.KEY_LMENU){Laltdown = false;}
				if(Keyboard.getEventKey() == Keyboard.KEY_F1){infomenu();}
			}
		}
	}
	/**
	 * ***********************************************************************
	 * Checks if the mouse is clicked and where the mouse is at that instant
	 * @throws IOException 
	 * ***********************************************************************
	 * Reserved ID's
	 * 0 - No wall
	 * 1-10; Wall(corresponding height)
	 * 11 - Begin point
	 * 12 - End point
	 * 13 - Spike
	 * 14 - Monster (scorpion)
	 */
	public void Mousepoll() throws IOException{
		int x = Mouse.getX()+left;					// Transform to world coordinates
		int y = Mouse.getY()+bottom;				// Transform to world coordinates
		
		int wheeldx = Mouse.getDWheel();			// difference in wheel location compared to previous call
		if(wheeldx>0 && !ctrldown){Button.scrolldown();}			// if you scroll up, move menu buttons
		if(wheeldx<0 && !ctrldown){Button.scrollup();}			// if you scroll down, move menu buttons
		
		if(ctrldown && wheeldx >0){tilesize*=1.1;MazeMap.setSize(tilesize);}
		if(ctrldown && wheeldx <0){tilesize/=1.1;MazeMap.setSize(tilesize);}
		/*
		 * Unlock the mouse again when all mouse buttons are released
		 * Left, right and scroll respectively
		 */
			if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && !Mouse.isButtonDown(2)){mousedown = false;}
		/*
		 * Mouse drag (0) is left and (1) is right and (2) is wheel
		 */
		if(Mouse.isButtonDown(2) && mousedown){				
			int dx = Mouse.getDX();
			int dy = Mouse.getDY();			
			left-=dx;right-=dx;
			bottom-=dy;top-=dy;
			initGL();
		}else if(Mouse.isButtonDown(0) && mousedown){
			executeselectedbut(leftID, x, y);
			
		}else if(Mouse.isButtonDown(1) && mousedown){
			executeselectedbut(rightID,x,y);
		}
		/*
		 * Mouse click
		 */
		if(Mouse.isButtonDown(2))mousedown = true;
		if((Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && !mousedown){	// check if clicked and not hold down
			mousedown = true;
			for(Button knopje: buttonlist){			// check for button clicked
				if(knopje.isButton(x, y)){				
					ID = knopje.getID();
					if(ID<90){						// >90 are not object buttons
						if(Mouse.isButtonDown(0)){leftID=ID;Button.setLeftID(ID);}
						if(Mouse.isButtonDown(1)){rightID=ID;Button.setrightID(ID);}
					}
					
					if(!standalone){
						sound.playButton();
					}
					
					break;							// if found no need to check others
				}
			}		
		}
		// Check menu bar buttons Not object buttons
		switch(ID){
		    
			case 99: {saveMaze(); 	break;}
			case 98:{loadMaze();	break;}
			case 100: {newMaze();	break;}
			case 101: {resizeMaze(); break;}
			case 102: {shiftMaze(); break;}
			case 110:{exit = true; 	 break;}
		}
		ID = -1;
	}
	/**
	 * ********************************************
	 * Reset the maze to its initial position
	 * ********************************************
	 */
	public void resetView(){
		left=0; right = Display.getWidth(); top = Display.getHeight(); bottom = 0;
		tilesize = 0.2f*menubarwidth;
		MazeMap.setSize(tilesize);
		initGL();
	}
	/**
	 * ********************************************
	 * Show info about the maze
	 * ********************************************
	 */
	public void infomenu(){
		if(maze!=null){
		int[][] intmaze= maze.getMaze();
		int width = intmaze[0].length;
		int height = intmaze.length;
		int scorpcount = 0, spikecount = 0 , wallcount = 0 , liftcount = 0, hatchcount = 0, pitcount = 0;
		
		for(int j = 0 ; j < intmaze.length; ++j){
			for(int i = 0 ; i < intmaze[0].length; ++i){
				int a = intmaze[j][i];
				if(a>0 && a<10)wallcount++;
				if(a == 13) spikecount ++;
				if(a == 14) scorpcount ++;
				if(a == 15) pitcount ++;
				if(a == 16) hatchcount++;
				if(a == 17 || a ==18) liftcount++;
			}
		}
		
		JOptionPane.showMessageDialog(null, 
										"Width: "+width+
										"\nHeight: "+height+"\n"+
										"Walls: "+wallcount+"\n"+
										"Spikes: "+spikecount+"\n"+
										"Scorpions: "+scorpcount+"\n"+
										"Pits: "+pitcount+"\n"+
										"Hatches: "+hatchcount+"\n"+
										"Elevators: "+liftcount, "Maze info", JOptionPane.INFORMATION_MESSAGE);
		}else{
			Sys.alert("No existing maze available", "You do not have a maze open, please open or make a maze first");
		}
	}
	/**
	 * *************************************************
	 * Initialize buttons, declare all menu buttons here
	 * *************************************************
	 * Reserved ID's
	 * -1 - No button clicked
	 *  0 - No wall
	 * 1-10; Wall(corresponding height)
	 * 11 - Begin point
	 * 12 - End point
	 * 13 - Spikes
	 * 14 - Scorpion
	 * 15 - pit
	 * 16 - hatch
	 * 17 - movable wall
	 */
	public void initButtons(){
		final float LEFT = 0.05f;
		final float RIGHT = 0.55f;
		/*
		 * Set the menu coordinates in world coordinates
		 */
		Button.setStatics(right-menubarwidth, right, top, bottom);
		/*
		 * Add buttons to the arraylist, give each button an unique ID!
		 */
		buttonlist.add(new Button(LEFT, 0.1f,Textures.texopen, 98));		// 98 load button 
		buttonlist.add(new Button(RIGHT, 0.1f,Textures.texnewmaze, 100));	// 100 New maze
		buttonlist.add(new Button(LEFT, 1.2f,Textures.texsave, 99));		// 99 save button
		buttonlist.add(new Button(RIGHT, 1.2f,Textures.texquit, 110));		// 110 Exit button
		buttonlist.add(new Button(LEFT, 2.3f,Textures.texshift, 102)); 		// 102 Shift maze button
		buttonlist.add(new Button(RIGHT, 2.3f,Textures.texresize, 101));	// 101 Resize button
		
		buttonlist.add(new Button(LEFT, 4.0f,Textures.texempty, 0));		// 0
		buttonlist.add(new Button(RIGHT, 4.0f,Textures.texwall1, 1));		// 1 
		buttonlist.add(new Button(LEFT, 5.1f,Textures.texwall2, 2));		// 2 
		buttonlist.add(new Button(RIGHT, 5.1f,Textures.texwall3, 3));		// 3 
		buttonlist.add(new Button(LEFT, 6.2f,Textures.texwall4, 4));		// 4 
		buttonlist.add(new Button(RIGHT, 6.2f,Textures.texwall5, 5));		// 5 
		buttonlist.add(new Button(LEFT, 7.3f,Textures.texwall6, 6));		// 6 
		buttonlist.add(new Button(RIGHT, 7.3f,Textures.texwall7, 7));		// 7 
		
		buttonlist.add(new Button(LEFT, 9.0f,Textures.texstart,11));		// 11
		buttonlist.add(new Button(RIGHT, 9.0f,Textures.texend,12));			// 12
		buttonlist.add(new Button(LEFT, 10.1f,Textures.texspike, 13));		// 13
		buttonlist.add(new Button(RIGHT, 10.1f,Textures.hatch, 16));		// 16
		buttonlist.add(new Button(LEFT, 11.2f,Textures.movwallup, 17));		// 17
		buttonlist.add(new Button(RIGHT, 11.2f,Textures.movwalldown, 18));	// 18
		buttonlist.add(new Button(LEFT, 12.3f,Textures.butpit, 15));		// 15
		buttonlist.add(new Button(RIGHT, 12.3f,Textures.scorpion, 14));		// 14
	}
	/**
	 * ********************************************
	 * Initialize all openGL functions
	 * ********************************************
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
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);

	}
	/**
	 * ****************************************************************************************
	 * Check if the flags are set, if not the maze will not be saved  
	 * @param maze
	 * @return
	 * ****************************************************************************************
	 */
	public boolean checkFlags(int[][] maze){
		boolean red_exists=false, green_exists=false;
		for (int i=0;i<maze.length;i++){
			for (int j=0;j<maze[0].length;j++){
				if (maze[i][j]==11)
					green_exists=true;
				if (maze[i][j]==12)
					red_exists=true;
//				System.out.println(i+" "+j+", output: "+maze[i][j]);
				
			}
		}
		return (red_exists && green_exists);
	}
	/**
	 * ************************************************************************************************************************************
	 * execute selected button, 
	 * @param Nummer the int identifying the button pressed for left mouse button or right mouse button
	 * @param x
	 * @param y
	 * ************************************************************************************************************************************
	 */
	public void executeselectedbut(int Nummer, int x, int y){
		// If you are on the left side of the screen (Maze side)
		if(x>left && x<right-menubarwidth && y>bottom && y<top && maze!=null){
//			System.out.println(maze.getMazeX(x)+" "+maze.getMazeY(y));
			maze.setObject(Nummer, x, y);
		}
	}
	/**
	 * ********************************************
	 * Make a new maze
	 * ********************************************
	 */
	public void newMaze(){
		
		int mwidth = 0;
		int mheight = 0;
		boolean lcont = true;
		while(mwidth==0 && lcont){
			try{
									
				String temp = JOptionPane.showInputDialog("Enter the width:", "20");
				
				mwidth = Integer.parseInt(temp);
				
			}catch(Exception e){	
				{System.out.println("hier!");lcont=false; break;}
			}
		}
		while(mheight==0 && lcont){
			try{
				String temp = JOptionPane.showInputDialog("Enter the height:", "20");
				mheight = Integer.parseInt(temp);
				
			}catch(Exception e){	
				break;
			}
		}
		if(mwidth>0 && mheight>0)
		maze = new MazeMap(mwidth, mheight);
		if(mwidth*mheight > 2500 ){JOptionPane.showMessageDialog(null, "Your maze size is very large.\nSome pc's might experience performance problems.", 
																	"Warning: Large maze", JOptionPane.WARNING_MESSAGE, null);}
		resetView();
		
	}
	/**
	 * ****************************************************************************************
	 * Resize the maze, keep the data from the top left corner
	 * ****************************************************************************************
	 */
	private void resizeMaze(){
		if(maze!=null){
			int[][] tempmaze = maze.getMaze();
			newMaze();
			int newWidth = (int) (Math.min(tempmaze[0].length, maze.getMaze()[0].length));
			int newHeight = (int) (Math.min(tempmaze.length, maze.getMaze().length));
			
			for(int j = 0 ; j < newHeight; j++){
				for(int i = 0 ; i < newWidth; i++){
					maze.getMaze()[j][i] = tempmaze[j][i];
				}
			}			
		}else{
			Sys.alert("No existing maze available", "You do not have a maze open, please open or make a maze first");
		}
	}
	/**
	 * Shift the maze
	 */
	private void shiftMaze(){
		if(maze!=null){			
			int[][] shiftedmaze = new int[maze.getMaze().length][maze.getMaze()[0].length];
			// get Dimensions
			int mwidth = shiftedmaze[0].length;
			int mheight = shiftedmaze.length;
			
			// get shifts
			int Hshift = 0;
			int Vshift = 0;
			// Get input from user
			try{
				Hshift = Integer.parseInt(JOptionPane.showInputDialog("Shift ... in horizontal direction", "0"));
				Vshift = Integer.parseInt(JOptionPane.showInputDialog("Shift ... in vertical direction", "0"));
			}catch(Exception e){
				Hshift = 0; Vshift = 0;
			}
			// Shift the maze
			for(int j = (Vshift>0?Vshift:0) ; j < (Vshift>0?mheight:mheight+Vshift); ++j){
				for(int i = (Hshift>0?Hshift:0) ; i < (Hshift>0?mwidth:mwidth+Hshift); ++i){
					shiftedmaze[j][i] = maze.getMaze()[j-Vshift][i-Hshift];
				}
			}
			// Set the shifted maze
			maze.setMaze(shiftedmaze);

		}else{
			Sys.alert("No existing maze available", "You do not have a maze open, please open or make a maze first");
		}
	}
	
	/**
	 * ********************************************
	 * Save the maze
	 * @throws IOException
	 * ********************************************
	 */
	private void saveMaze() throws IOException{
		
			if (maze==null){
				Sys.alert("Warning", "No maze loaded!");
			}
			else{
				if (checkFlags(maze.getMaze())){
				IO.savechooser(maze,Lshiftdown&&Laltdown&&ctrldown);
				}				
				else{
					Sys.alert("Warning", "Start and/or ending flag not placed!");
				}			
			}
			ctrldown=false; Lshiftdown=false;Laltdown=false;

	}
	/**
	 * ********************************************
	 * Load in a maze file
	 * ********************************************
	 */
	private void loadMaze(){
		int[][] tempmaze = null;
		try {tempmaze = IO.loadchooser(Lshiftdown&&Laltdown&&ctrldown);		
		} catch (IOException e) {} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(tempmaze!=null){
			maze = new MazeMap(tempmaze[0].length, tempmaze.length);
			maze.setMaze(tempmaze);		
			
			resetView();
		}
		ctrldown=false; Lshiftdown=false;Laltdown=false;
	}

	/**
	 * ********************************************
	 * Main program starts here (standalone)
	 * @param args
	 * ********************************************
	 */
	public static void main(String[] args){
		/*
		 * Select resolution
		 */
		try{
		Chooser keuze = new Chooser(false);
		
		while(keuze.getDisplay() == null){
			Thread.sleep(500);
		}			
		
		MazeMaker maker = new MazeMaker();		
		Display.create();
		maker.start(true);		
		}catch(Exception e){e.printStackTrace();}
	}
}
