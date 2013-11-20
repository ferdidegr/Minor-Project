package Game;


import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
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
	private int[][] maze; 										// The maze.
	private ArrayList<VisibleObject> visibleObjects;		// A list of objects that will be displayed on screen.
	private ArrayList<levelObject> objlijst;
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used to calculate elapsed time.
	private double temp_X;
	private double temp_Z;
	private Wall wall;
	private Floor grond;
	private FloatBuffer lightPosition;
	private levelObject[][] levelobjecten;
	private int SQUARE_SIZE=1;
	
	/*
	 *  *************************************************
	 *  * 					Main Loop					*
	 *  *************************************************
	 */
	
public void start() throws ClassNotFoundException, IOException{

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
		if(input.view_coord==true)System.out.println(player.locationX/SQUARE_SIZE+" "+player.locationZ/SQUARE_SIZE);
			
		Display.update();
		Display.sync(70);
	}
	cleanup();
}

/*
 * **************************************************
 * *                 Load Maze                      *
 * **************************************************
 */
public void initMaze() throws ClassNotFoundException, IOException{
	maze = IO.readMaze("levels/test3.maze");
	levelobjecten = new levelObject[maze.length][maze[0].length];
	
	for(int j = 0; j < maze.length; j++){
		for(int i = 0; i<maze[0].length; i++){
			if(maze[j][i]==1){
				levelObject lvlo = new Wall(i*SQUARE_SIZE+SQUARE_SIZE/2, 0, j*SQUARE_SIZE+SQUARE_SIZE/2, SQUARE_SIZE, 4);
				visibleObjects.add(lvlo);
				objlijst.add(lvlo);
				levelobjecten[j][i]=lvlo;
			}
		}
	}

	
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
//			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glClearDepth(1.0f);			
			GL11.glDepthFunc(GL11.GL_LEQUAL);

	        
	}
	public void cleanup(){
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHT0);
		GL11.glDisable(GL11.GL_BLEND);
		Mouse.setGrabbed(false);
	}     
	   public void initObj() throws ClassNotFoundException, IOException{  
		   
	     // We define an ArrayList of VisibleObjects to store all the objects that need to be
			// displayed by MazeRunner.
			visibleObjects = new ArrayList<VisibleObject>();
			objlijst = new ArrayList<levelObject>();
			initMaze();
			
			// Add the maze that we will be using.
//			maze = new Maze();
//			visibleObjects.add( maze );
	     // Initialize the player.
			player = new Player( 6 * SQUARE_SIZE + SQUARE_SIZE / 2, 	// x-position
								 SQUARE_SIZE *15/ 2 ,							// y-position
								 5 * SQUARE_SIZE + SQUARE_SIZE / 2, 	// z-position
								 0, 0 );										// horizontal and vertical angle

			camera = new Camera( player.getLocationX(), player.getLocationY(), player.getLocationZ(), 
					             player.getHorAngle(), player.getVerAngle() );
			
			input = new UserInput();
			player.setControl(input);
			wall = new Wall(10, 10, 0, 5, 2);
			grond = new Floor(0, 0, 0, maze[0].length*SQUARE_SIZE, maze.length*SQUARE_SIZE,1);	
			objlijst.add(wall);
			
			objlijst.add(grond);
		
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
		        
		        for(VisibleObject vo:visibleObjects){
		        	if(vo instanceof Wall){GL11.glMaterial( GL11.GL_FRONT, GL11.GL_DIFFUSE, Graphics.wallColour);}
		        	vo.display();
		        }	
		        
		        GL11.glMaterial( GL11.GL_FRONT, GL11.GL_DIFFUSE, Graphics.wallColour);
		        wall.display();
				player.draw();
				GL11.glMaterial( GL11.GL_FRONT, GL11.GL_DIFFUSE, Graphics.floorColour);
				grond.display();
				// TODO remove
//				System.out.println(player.getGridX(SQUARE_SIZE)+" "+player.getGridZ(SQUARE_SIZE));
//				System.out.println(player.locationX+" "+player.locationY+" "+player.locationZ);
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
			int Xin = player.getGridX(SQUARE_SIZE);
			int Zin = player.getGridZ(SQUARE_SIZE);
			boolean colX = false;
			boolean colZ = false;
			boolean colY = false;
			
			for(levelObject lvlob:objlijst){
				if(lvlob.isCollision(temp_X+player.velocity.getX()+0.25*Math.signum(player.velocity.getX()), temp_Y, temp_Z)||
						lvlob.isCollision(temp_X+player.velocity.getX()+0.25*Math.signum(player.velocity.getX()), player.locationY-SQUARE_SIZE *3/ 2, temp_Z)){
					colX=true;
				}
				if(lvlob.isCollision(player.locationX, player.locationY-SQUARE_SIZE *3/ 2, temp_Z+0.25*Math.signum(player.velocity.getZ())+player.velocity.getZ())){
					colZ=true;
				}
				if(lvlob.isCollision(player.locationX,  player.locationY+player.velocity.getY()-SQUARE_SIZE *3/ 2 , player.locationZ)){
					colY=true;
				}				
			}
			//collision X			
		
//			if(wall.isCollision(temp_X+player.velocity.getX()+0.25*Math.signum(player.velocity.getX()), temp_Y, temp_Z)){
			if(colX){	
			}else{
				player.updateX();
			}
			// collsion Z
			//			
//			if(wall.isCollision(player.locationX, player.locationY, temp_Z+0.25*Math.signum(player.velocity.getZ())+player.velocity.getZ())){
			if(colZ){										
			}else{
				player.updateZ();
			}
			// Collision Y
//			if(grond.isCollision(player.locationX, player.locationY+player.velocity.getY()-SQUARE_SIZE *3/ 2 , player.locationZ)||
//					wall.isCollision(player.locationX,  player.locationY+player.velocity.getY()-SQUARE_SIZE *3/ 2 , player.locationZ)){
			if(colY){
				player.jump=false;
			}else{
				player.updateY();
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
