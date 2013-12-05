package Game;


import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;

import Menu.GameState;
import Menu.Menu;
import Utils.*;


public class Mazerunner {
	/*
	 * Local Variables
	 */
	public static boolean soundon = false;
	
	private int screenWidth = 1280, screenHeight = 720;		// Deprecated
	public Player player;									// The player object.
	private UserInput input;								// The user input object that controls the player.
	public static int[][] maze; 								// The maze.
	
	
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used to calculate elapsed time.
	private Wall wall;										// Wall Class, used to put one wall in for test TODO remove
	
	private FloatBuffer lightPosition;		

	protected ArrayList<Pickup> pickuplijst;						// A list of pickup items
	protected ArrayList<Hatch> hatch;					// A list of objects that will be displayed on screen. (immediate mode)
	private ArrayList<VisibleObject> visibleObjects;				// A list of objects that will be displayed on screen. (DLlist mode)
	protected static ArrayList<Monster> monsterlijst;				// Lijst met alle monsters
	protected static ArrayList<levelObject> objlijst;				// List of all collidable objects
	protected static int[][] objectindex;							// reference to the arraylist entry
	protected static int SQUARE_SIZE=1;								// Size of a unit block
	
	protected static boolean isdood;
	private MiniMap minimap;								// The minimap object.
	private String level = "levels/test8.maze";
	private int objectDisplayList = glGenLists(1);
	
	/*
	 *  *************************************************
	 *  * 					Main Loop					*
	 *  *************************************************
	 */
	
public void start() throws ClassNotFoundException, IOException{
	new Game.Textures();			// Initialize textures
	new Graphics();					// Initialize graphics
	new Models();
	// TODO remove
	Display.setResizable(true);
										
	initObj();
	initGL();
	initDisp();
	
	
	while(!Display.isCloseRequested() && player.locationY>-20 && !isdood){
		
		// If the window is resized, might not be implemented
		if(Display.getWidth()!=screenWidth || Display.getHeight()!=screenHeight) reshape();
		
		// Check for Input
		input.poll();
		
		// Check if pause menu is requested
		if(!Menu.getState().equals(GameState.GAME)){
			
			glPushMatrix();
			glPushAttrib(GL_ENABLE_BIT);
			cleanup();
			Menu.run();
			glPopAttrib();
			glPopMatrix();
			initGL();
			previousTime = Calendar.getInstance().getTimeInMillis();
			Menu.setState(GameState.GAME);
		}
		
		// Draw objects on screen
		display();
		
		// Location print player location
		if(input.view_coord==true)System.out.println(player.getGridX(SQUARE_SIZE)+" "+player.getGridZ(SQUARE_SIZE));
			
		Display.update();
		Display.sync(70);
		
	}
	Menu.setState(GameState.GAMEOVER);
	cleanup();
	
}

/*
 * **************************************************
 * *                 Load Maze                      *
 * **************************************************
 */
public void initMaze() throws ClassNotFoundException, IOException{
	maze = IO.readMaze(level);
	objectindex = new int[maze.length][maze[0].length];
	monsterlijst = new ArrayList<Monster>();
	hatch = new ArrayList<Hatch>();
	pickuplijst = new ArrayList<Pickup>();
	
	minimap=new MiniMap(maze);		//load the minimap
	StatusBars.init(100, 50);
	
	for(int j = 0; j < maze.length; j++){
		for(int i = 0; i<maze[0].length; i++){
						
			// Parsing the walls
			if(maze[j][i]>0 && maze[j][i]<11){
				levelObject lvlo = new Wall(i*SQUARE_SIZE+SQUARE_SIZE/2.0, 0, j*SQUARE_SIZE+SQUARE_SIZE/2.0, SQUARE_SIZE, maze[j][i],SQUARE_SIZE);
				visibleObjects.add(lvlo);
				objlijst.add(lvlo);
				objectindex[j][i]=objlijst.size()-1;
			}
			// Parsing the begin point
			else if(maze[j][i]==11){
				// Initialize the player.
				player = new Player( i * SQUARE_SIZE + SQUARE_SIZE / 2.0, 	// x-position
									 SQUARE_SIZE *40/ 2.0 ,					// y-position
									 j * SQUARE_SIZE + SQUARE_SIZE / 2.0, 	// z-position
									 0, 0 ,									// horizontal and vertical angle
									 0.25*SQUARE_SIZE,SQUARE_SIZE* 3/2.0);	// player width and player height			
			}
			// Parsing the scorpions
			else if(maze[j][i]==14){
				monsterlijst.add(new Monster(i * SQUARE_SIZE + SQUARE_SIZE / 2.0, 	// x-position
									 SQUARE_SIZE*0.7 ,							// y-position
									 j * SQUARE_SIZE + SQUARE_SIZE / 2.0, 			// z position
									 SQUARE_SIZE*0.7, SQUARE_SIZE*0.7, SQUARE_SIZE));
			}
			// parsing the hatch
			else if(maze[j][i]==16){
				Hatch h = new Hatch(i*SQUARE_SIZE, 0, j*SQUARE_SIZE);
				hatch.add(h);
				objlijst.add(h);
				objectindex[j][i]=objlijst.size()-1;
			}
			// Parsing the floor
			if((maze[j][i]>10|| maze[j][i]==0) && maze[j][i]!=15 && maze[j][i]!=16){
			levelObject lvlo = new Floor(i*SQUARE_SIZE, 0, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, 1);
			visibleObjects.add(lvlo);
			objlijst.add(lvlo);
			objectindex[j][i]=objlijst.size()-1;
			}

		}
	}
	// Parsing test pickups
	pickuplijst.add(new Pickup(SQUARE_SIZE*2,SQUARE_SIZE,SQUARE_SIZE*2,0,SQUARE_SIZE/4f));
	pickuplijst.add(new Pickup(SQUARE_SIZE*18,SQUARE_SIZE,SQUARE_SIZE*18,1,SQUARE_SIZE/4f));

}
/*
 *  *************************************************
 *  * 			Initialization methods				*
 *  *************************************************
 */
	public void initGL(){		
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		GLU.gluPerspective(60, (float)Display.getWidth()/Display.getHeight(), 0.001f, 1000);	// Set up the parameters for perspective viewing. 
		glMatrixMode(GL_MODELVIEW);
		
		// Enable back-face culling.
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		
		// Enable Z-buffering
		glEnable(GL_DEPTH_TEST);
		
		
		// Set and enable the lighting.
		
		 	lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(maze[0].length*SQUARE_SIZE).put(5.0f).put(maze.length*SQUARE_SIZE).put(1.0f).flip();	// High up in the sky!
	        FloatBuffer lightColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();		// White light!
	        glLight( GL_LIGHT0, GL_POSITION, lightPosition);	// Note that we're setting Light0.
	        glLight( GL_LIGHT0, GL_AMBIENT, lightColour);
	        
	        glEnable( GL_LIGHTING );
	        glEnable( GL_LIGHT0 );
	        
	     // Set the shading model.
	        glShadeModel( GL_SMOOTH );
	        
			glClearDepth(1.0f);			
			glDepthFunc(GL_LEQUAL);

	        
	}
	/**
	 * Cleanup after shut down
	 */
	public void cleanup(){
		glDisable(GL_LIGHTING);
		glDisable(GL_CULL_FACE);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHT0);
		glDisable(GL_BLEND);
		Mouse.setGrabbed(false);
	}  
	/**
	 * Initialize all objects
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	   public void initObj() throws ClassNotFoundException, IOException{  
		   
	     // We define an ArrayList of VisibleObjects to store all the objects that need to be
			// displayed by MazeRunner.
			visibleObjects = new ArrayList<VisibleObject>();
			objlijst = new ArrayList<levelObject>();
		 // Initialize Maze ( Loading in and setting the objects in the maze )
			initMaze();	     			
			
			input = new UserInput();
			player.setControl(input);
			
			/*
			 * adding test objects
			 */
			
			wall = new Wall(10, 10, 0, 5, 2,SQUARE_SIZE);

			objlijst.add(wall);

		
		
	}
	/**
	 * Display function, draw all visible objects
	 */
	public void display(){
				// Calculating time since last frame.
				Calendar now = Calendar.getInstance();		
				long currentTime = now.getTimeInMillis();
				int deltaTime = (int)(currentTime - previousTime);
				previousTime = currentTime;
				// TODO remove
				Display.setTitle("dt: "+ deltaTime);
	
				//Update any movement since last frame.
				Monster.setPlayerloc(new Vector(player.locationX, player.locationY, player.locationZ));
								
				updateMovement(deltaTime);	
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
				
				glLoadIdentity();
				
				glRotated(-player.getVerAngle()*(input.lookback?-1:1), 1, 0, 0);
				glRotated(-player.getHorAngle()+(input.lookback?180:0), 0, 1, 0);
				// Draw sky box, the skybox is always in the origin, so no need to translate
				glCallList(Models.skybox);
				
				glTranslated(-player.locationX, -player.locationY, -player.locationZ);					
				
		        
		        // Display all the visible objects of MazeRunner.
		        if(!input.debug){ 	glCallList(objectDisplayList); }
		        
		        // Display all movable visible objects (immediate mode)
		        for(VisibleObject vo:hatch){
		        	vo.display();
		        }
		        
		        //update light positions
		        glLight( GL_LIGHT0, GL_POSITION, lightPosition);	
		        
		        // Monsters		
//		        Material.setMtlScorp();
		        
		        for(Monster mo: monsterlijst){			        	
		        	mo.display();		        	
		        }
		        // Pickups
		        ArrayList<Pickup> rmpu = new ArrayList<Pickup>();
		        for(Pickup pu: pickuplijst){
		        	pu.display();
		        	if(pu.check(player)){
		        		rmpu.add(pu);
		        	}
		        }
		        for (Pickup pu: rmpu){
		        	if (pickuplijst.remove(pu)){
		        		pu.effect();
		        	}
		        }
		        
		        // HUD
		        glPushMatrix();
		        if(input.minimap){drawHUD();}
		        glPopMatrix();
		        player.draw();



	}
	
	/**
	 * Switches to 2D orthogonal view to project the HUD, after drawing the HUD, the Matrixmode is set back to 3D view
	 */
	private void drawHUD(){
		// Switch to 2D
		
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0.0, Display.getWidth(), Display.getHeight(), 0.0, 1.0, -1.0);
		
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		glLoadIdentity();
		glDisable(GL_CULL_FACE);
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		glClear(GL_DEPTH_BUFFER_BIT);

		minimap.draw(player,monsterlijst,SQUARE_SIZE);
		StatusBars.draw();

		// Making sure we can render 3d again
		glMatrixMode(GL_PROJECTION);
		glPopAttrib();
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}

	/**
	 * if the window is reshaped, change accordingly
	 */
	public void reshape(){
		screenWidth = Display.getWidth();
		screenHeight = Display.getHeight();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());	
	}
	/*
	 * **********************************************
	 * *				Methods						*
	 * **********************************************
	 */

		/**
		 * updateMovement(int) updates the position of all objects that need moving.
		 * This includes rudimentary collision checking and collision reaction.
		 */
		private void updateMovement(int deltaTime)
		{
			player.update(deltaTime);						// Updating velocity vector
			/*
			 * Movable objects
			 */
			for(Hatch lvlo: hatch){
				lvlo.update(deltaTime);				
			}
			
			/*
			 * Monsters
			 */
			for(Monster mon: monsterlijst){
				mon.update(deltaTime);				
			}
			
			
		}
		
		/**
		 * Setting up the displayLists
		 */
		public void initDisp(){
			/*
			 * Walls and ground
			 */			
			
			glNewList(objectDisplayList, GL_COMPILE);
			 
			
			 for(VisibleObject vo:visibleObjects){
		        	if(vo instanceof Wall){	Material.setMtlWall();}
		        	if(vo instanceof Floor){ Material.setMtlGround();}
		        	vo.display();
		        }
			 	Material.setMtlWall();
		        wall.display();		

												
				Material.setMtlsteel();
				glDisable(GL_TEXTURE_2D);
				glPushMatrix();

				glTranslatef(0.5f, 1f, 0.5f);
				Graphics.renderSpike(0.5f, 1);
				glPopMatrix();
				
				 
			 glEndList();			 
				   
		}
		
		public static boolean getSound(){
			return soundon;
		}
	}


