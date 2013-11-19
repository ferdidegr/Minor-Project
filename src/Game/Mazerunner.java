package Game;


import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class Mazerunner {
	/*
	 * Local Variables
	 */
	
	private int screenWidth = 1280, screenHeight = 720;
	public Player player;									// The player object.
	private Camera camera;									// The camera object.
	private UserInput input;								// The user input object that controls the player.
	private Maze maze; 										// The maze.
	private ArrayList<VisibleObject> visibleObjects;		// A list of objects that will be displayed on screen.
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used to calculate elapsed time.
	private double temp_X;
	private double temp_Z;
	private Wall wall;
	private FloatBuffer lightPosition;
	
	/*
	 *  *************************************************
	 *  * 					Main Loop					*
	 *  *************************************************
	 */
	
public void start(){

	init();
	initObj();
	
	while(!Display.isCloseRequested()){
		// If the window is resized
		if(Display.getWidth()!=screenWidth || Display.getHeight()!=screenHeight) reshape();
		
		// Check for Input
		input.poll();
		
		// Draw objects on screen
		display();
		
		// Location
		if(input.view_coord==true)System.out.println(player.locationX/maze.SQUARE_SIZE+" "+player.locationZ/maze.SQUARE_SIZE);
			
		Display.update();
		Display.sync(70);
	}
	cleanup();
}

/*
 *  *************************************************
 *  * 			Initialization methods				*
 *  *************************************************
 */
	public void init(){
		
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		GL11.glMatrixMode(GL11.GL_PROJECTION);					// We'll use orthogonal projection.
		GL11.glLoadIdentity();									// REset the current matrix.
		GLU.gluPerspective(60, (float)Display.getWidth()/Display.getHeight(), 0.001f, 1000);	// Set up the parameters for perspective viewing. 
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// Enable back-face culling.
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		// Enable Z-buffering
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
		// Set and enable the lighting.
		
		 	lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.0f).put(50.0f).put(0.0f).put(1.0f).flip();	// High up in the sky!
	        FloatBuffer lightColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();		// White light!
	        GL11.glLight( GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);	// Note that we're setting Light0.
	        GL11.glLight( GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightColour);
	        GL11.glEnable( GL11.GL_LIGHTING );
	        GL11.glEnable( GL11.GL_LIGHT0 );
	        
	     // Set the shading model.
	        GL11.glShadeModel( GL11.GL_SMOOTH );
	        
			// Enable Textures
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glClearDepth(1.0f);			
			GL11.glDepthFunc(GL11.GL_LEQUAL);

	        
	}
	public void cleanup(){
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHT0);
		GL11.glDisable(GL11.GL_BLEND);
	}     
	   public void initObj(){     
	     // We define an ArrayList of VisibleObjects to store all the objects that need to be
			// displayed by MazeRunner.
			visibleObjects = new ArrayList<VisibleObject>();
			// Add the maze that we will be using.
			maze = new Maze();
			visibleObjects.add( maze );
	     // Initialize the player.
			player = new Player( 6 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// x-position
								 maze.SQUARE_SIZE *3/ 2 ,							// y-position
								 5 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// z-position
								 90, 0 );										// horizontal and vertical angle

			camera = new Camera( player.getLocationX(), player.getLocationY(), player.getLocationZ(), 
					             player.getHorAngle(), player.getVerAngle() );
			
			input = new UserInput();
			player.setControl(input);
			wall = new Wall(10, 10, 0, 2, 2);
	}
	
	public void display(){
		// Calculating time since last frame.
				Calendar now = Calendar.getInstance();		
				long currentTime = now.getTimeInMillis();
				int deltaTime = (int)(currentTime - previousTime);
				previousTime = currentTime;
				
				
				//Update any movement since last frame.
				updateMovement(deltaTime);
				updateCamera();
				
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
				GL11.glLoadIdentity();

		        GLU.gluLookAt( (float)camera.getLocationX(), (float)camera.getLocationY(),(float) camera.getLocationZ(), 
		        		(float)camera.getVrpX(), (float)camera.getVrpY(), (float)camera.getVrpZ(),
		        		(float)camera.getVuvX(), (float)camera.getVuvY(), (float)camera.getVuvZ() );
		        
		        //update light positions
		        GL11.glLight( GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);	
		        

		        // Display all the visible objects of MazeRunner.
		        for( Iterator<VisibleObject> it = visibleObjects.iterator(); it.hasNext(); ) {
		        	it.next().display();
		        }
		        GL11.glMaterial( GL11.GL_FRONT, GL11.GL_DIFFUSE, Graphics.wallColour);
		        wall.draw();
		        GL11.glPushMatrix();
		        GL11.glTranslatef(2, 0, 0);
		        wall.draw();
		        GL11.glPopMatrix();
				System.out.println((wall.isCollision(player.locationX, player.locationY, player.locationZ))? "yes":"");

		   
//		        GL11.glLoadIdentity();
	}
	

	
	public void reshape(){
		screenWidth = Display.getWidth();
		screenHeight = Display.getHeight();
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		// Now we set up our viewpoint.
		GL11.glMatrixMode(GL11.GL_PROJECTION);					// We'll use orthogonal projection.
		GL11.glLoadIdentity();									// REset the current matrix.
		GLU.gluPerspective(60, (float)Display.getWidth()/(float)Display.getHeight(), 0.001f, 1000);	// Set up the parameters for perspective viewing. 
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
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
			temp_X = player.getLocationX();
			double temp_Y = player.locationY;
			temp_Z = player.getLocationZ();
			player.update(deltaTime);
			double dx = player.getSpeed()*deltaTime;
			
			//collision X

			player.updateX(deltaTime);
			if(maze.isWall(player.getLocationX(), player.getLocationZ()) 
					|| maze.isWall(player.getLocationX()+dx, player.getLocationZ()+dx)
					|| maze.isWall(player.getLocationX()+dx, player.getLocationZ()-dx)					
					|| maze.isWall(player.getLocationX()-dx, player.getLocationZ()+dx)
					|| wall.isCollision(temp_X, temp_Y, temp_Z)
					|| wall.isCollision(temp_X+dx, temp_Y, temp_Z+dx)
					|| wall.isCollision(temp_X+dx, temp_Y, temp_Z-dx)
					|| wall.isCollision(temp_X-dx, temp_Y, temp_Z+dx)
					|| wall.isCollision(temp_X-dx, temp_Y, temp_Z-dx)){
				player.setLocationX(temp_X);			
			}
			// collsion Z

			player.updateZ(deltaTime);
			if(maze.isWall(player.getLocationX(), player.getLocationZ()) 
					|| maze.isWall(player.getLocationX()+dx, player.getLocationZ()+dx)
					|| maze.isWall(player.getLocationX()+dx, player.getLocationZ()-dx)					
					|| maze.isWall(player.getLocationX()-dx, player.getLocationZ()+dx)
					|| wall.isCollision(temp_X, temp_Y, temp_Z)
					|| wall.isCollision(temp_X+dx, temp_Y, temp_Z+dx)
					|| wall.isCollision(temp_X+dx, temp_Y, temp_Z-dx)
					|| wall.isCollision(temp_X-dx, temp_Y, temp_Z+dx)
					|| wall.isCollision(temp_X-dx, temp_Y, temp_Z-dx)){
				player.setLocationZ(temp_Z);			
			}
			
		}

		/**
		 * updateCamera() updates the camera position and orientation.
		 * <p>
		 * This is done by copying the locations from the Player, since MazeRunner runs on a first person view.
		 */
		private void updateCamera() {
			camera.setLocationX( player.getLocationX() );
			camera.setLocationY( player.getLocationY() );  
			camera.setLocationZ( player.getLocationZ() );
			camera.setHorAngle( player.getHorAngle()+(input.lookback? 180:0) );
			camera.setVerAngle( player.getVerAngle() * (input.lookback? -1:1) );
			camera.calculateVRP();
		}
}
