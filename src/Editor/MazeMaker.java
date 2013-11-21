package Editor;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;



public class MazeMaker {
	private int left;								// world coordinate of the left side of the screen
	private int right;								// world coordinate of the right side of the screen
	private int top;								// world coordinate of the top side of the screen
	private int bottom;								// world coordinate of the bottom side of the screen
	private int menubarwidth;	
	private ArrayList<Button> buttonlist = new ArrayList<Button>();
	private boolean mousedown = false, ctrldown = false;
	private MazeMap maze = null;
	private int ID = -1, leftID=0, rightID=0;								// ID when no button has been pressed
	private boolean exit = false;	
	private float tilesize;
	private int flaggreenx=-1,flaggreeny=-1,flagredx=-1,flagredy=-1;
	/**
	 * ***********************************************
	 * Begin the program
	 * @throws LWJGLException
	 * @throws InterruptedException
	 * @throws IOException
	 * ***********************************************
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
			tilesize = 0.2f*menubarwidth;
			MazeMap.setSize(tilesize);
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
		while(!Display.isCloseRequested() && !exit){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			Mousepoll();
			keyboardpoll();
			display();			
			Display.update();Display.sync(60);
			
		}
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
		if(maze!=null){
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
				if(Keyboard.getEventKey()==Keyboard.KEY_LCONTROL){ctrldown=true;}
			}else{
				/*
				 * Key Released events
				 */
				if(Keyboard.getEventKey()==Keyboard.KEY_LCONTROL){ctrldown=false;}
				
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
			// If you are on the left side of the screen (Maze side)
			if(x>left && x<right-menubarwidth && y>bottom && y<top && maze!=null){
				System.out.println(maze.getMazeX(x)+" "+maze.getMazeY(y));
				switch(leftID){
					case 0:{maze.setObject(0, x, y);break;} // Empty spot
					case 1:{maze.setObject(1, x, y);break;}	// Wall
					case 2:{maze.setObject(2, x, y);break;}	// Wall
					case 3:{maze.setObject(3, x, y);break;}	// Wall
					case 4:{maze.setObject(4, x, y);break;}	// Wall
					case 5:{maze.setObject(5, x, y);break;}	// Wall
					case 6:{maze.setObject(6, x, y);break;}	// Wall
					case 7:{maze.setObject(7, x, y);break;}	// Wall
					
					case 13:{maze.setObject(13, x, y);break;}	// Spikes
					case 11:{if (flaggreenx>0 && flaggreeny>0 && maze.getMaze()[maze.getMazeY(flaggreeny)][maze.getMazeX(flaggreenx)]==11){
								maze.setObject(0, flaggreenx, flaggreeny);
							}
							flaggreenx=x;
							flaggreeny=y;
							maze.setObject(11, x, y);
							break;} 						// Flag green
					case 12:{if (flagredx>0 && flagredy>0 && maze.getMaze()[maze.getMazeY(flagredy)][maze.getMazeX(flagredx)]==12){
								maze.setObject(0, flagredx, flagredy);
							}
							flagredx=x;
							flagredy=y;
							maze.setObject(12, x, y);
							break;} 						// Flag red
//					
				}
			}
		}else if(Mouse.isButtonDown(1) && mousedown){
			// If you are on the left side of the screen (Maze side)
			if(x>left && x<right-menubarwidth && y>bottom && y<top && maze!=null){
				System.out.println(maze.getMazeX(x)+" "+maze.getMazeY(y));
				switch(rightID){
				case 0:{maze.setObject(0, x, y);break;} // Empty spot
				case 1:{maze.setObject(1, x, y);break;}	// Wall
				case 2:{maze.setObject(2, x, y);break;}	// Wall
				case 3:{maze.setObject(3, x, y);break;}	// Wall
				case 4:{maze.setObject(4, x, y);break;}	// Wall
				case 5:{maze.setObject(5, x, y);break;}	// Wall
				case 6:{maze.setObject(6, x, y);break;}	// Wall
				case 7:{maze.setObject(7, x, y);break;}	// Wall
				
				case 13:{maze.setObject(13, x, y);break;}	// Spikes
				case 11:{if (flaggreenx>0 && flaggreeny>0 && maze.getMaze()[maze.getMazeY(flaggreeny)][maze.getMazeX(flaggreenx)]==11){
							maze.setObject(0, flaggreenx, flaggreeny);
						}
						flaggreenx=x;
						flaggreeny=y;
						maze.setObject(11, x, y);
						break;} 						// Flag green
				case 12:{if (flagredx>0 && flagredy>0 && maze.getMaze()[maze.getMazeY(flagredy)][maze.getMazeX(flagredx)]==12){
							maze.setObject(0, flagredx, flagredy);
						}
						flagredx=x;
						flagredy=y;
						maze.setObject(12, x, y);
						break;} 						// Flag red
//					
				}
			}
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
					System.out.println(ID);
					
					break;							// if found no need to check others
				}
			}		
		}
		// Check menu bar buttons Not object buttons
		switch(ID){
		    case 101:{exit = true; break;}
			case 99:{IO.savechooser(maze);ID=-1;break;}
			case 98:{
				int[][] tempmaze = null;
				try {tempmaze = IO.loadchooser();} catch (IOException e) {} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tempmaze==null){ID= -1;break;}
				maze = new MazeMap(tempmaze[0].length, tempmaze.length);
				maze.setMaze(tempmaze);		
				resetView();
				ID=-1;break;
				}
			case 100:{
				int mwidth = 0;
				int mheight = 0;
				boolean lcont = true;
				while(mwidth==0 && lcont){
					try{
											
						String temp = JOptionPane.showInputDialog("Enter the width as an integer:", "20");
						
						mwidth = Integer.parseInt(temp);
						
					}catch(Exception e){	
						{System.out.println("hier!");lcont=false; break;}
					}
				}
				while(mheight==0 && lcont){
					try{
						String temp = JOptionPane.showInputDialog("Enter the height as an integer:", "20");
						mheight = Integer.parseInt(temp);
						
					}catch(Exception e){	
						break;
					}
				}
				if(mwidth>0 && mheight>0)
				maze = new MazeMap(mwidth, mheight);
				resetView();
				ID=-1;
				break;
			}
		}
		
	}
	
	public void resetView(){
		left=0; right = Display.getWidth(); top = Display.getHeight(); bottom = 0;
		tilesize = 0.2f*menubarwidth;
		MazeMap.setSize(tilesize);
		initGL();
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
	 */
	public void initButtons(){
		/*
		 * Set the menu coordinates in world coordinates
		 */
		Button.setStatics(right-menubarwidth, right, top, bottom);
		/*
		 * Add buttons to the arraylist, give each button an unique ID!
		 */
		buttonlist.add(new Button(0.05f, 0.1f,Textures.texempty, 0));		// 0
		buttonlist.add(new Button(0.55f, 0.1f,Textures.texwall1, 1));		// 1 
		buttonlist.add(new Button(0.05f, 1.2f,Textures.texwall2, 2));		// 2 
		buttonlist.add(new Button(0.55f, 1.2f,Textures.texwall3, 3));		// 3 
		buttonlist.add(new Button(0.05f, 2.3f,Textures.texwall4, 4));		// 4 
		buttonlist.add(new Button(0.55f, 2.3f,Textures.texwall5, 5));		// 5 
		buttonlist.add(new Button(0.05f, 3.4f,Textures.texwall6, 6));		// 6 
		buttonlist.add(new Button(0.55f, 3.4f,Textures.texwall7, 7));		// 7 

		
		buttonlist.add(new Button(0.05f, 5.6f,Textures.texflaggreen,11));	// 11 flaggreen
		buttonlist.add(new Button(0.55f, 5.6f,Textures.texflagred,12));		// 12 flagred
		buttonlist.add(new Button(0.05f, 6.7f,Textures.texspike, 13));		// 13

		buttonlist.add(new Button(0.05f, 10.5f,Textures.texload, 98));		// 98 load button 
		buttonlist.add(new Button(0.55f, 10.5f,Textures.texsave, 99));		// 99 save button
		buttonlist.add(new Button(0.05f, 11.6f,Textures.texnewmaze, 100));	// 100 New maze
		buttonlist.add(new Button(0.55f, 11.6f,Textures.texempty,101)); 	// 101 Exit button

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
