package Game;


import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import org.lwjgl.util.glu.GLU;


public class Mazerunner {
	/*
	 * Local Variables
	 */
	
	private int screenWidth = 1280, screenHeight = 720;		// Deprecated
	public Player player;									// The player object.
	private Camera camera;									// The camera object.
	private UserInput input;								// The user input object that controls the player.
	private int[][] maze; 									// The maze.
	private ArrayList<VisibleObject> visibleObjects;		// A list of objects that will be displayed on screen.
	private ArrayList<levelObject> objlijst;				// List of all collidable objects
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used to calculate elapsed time.
	private Wall wall;										// Wall Class, used to put one wall in for test TODO remove
	private Floor grond;									// Floor class used to put the floor in
	private FloatBuffer lightPosition;						
	private int[][] objectindex;							// reference to the arraylist entry
	private int SQUARE_SIZE=1;								// Size of a unit block
	private MiniMap minimap;								// The minimap object.
	private String level = "levels/test5.maze";
	
	/*
	 *  *************************************************
	 *  * 					Main Loop					*
	 *  *************************************************
	 */
	
public void start() throws ClassNotFoundException, IOException{
	// TODO remove
	Display.setResizable(true);
	
	initObj();
	init();
	
	
	while(!Display.isCloseRequested() && player.locationY>-50){
		
		// If the window is resized, might not be implemented
		if(Display.getWidth()!=screenWidth || Display.getHeight()!=screenHeight) reshape();
		
		// Check for Input
		input.poll();
		
		// Draw objects on screen
		display();
		
		// Location print player location
		if(input.view_coord==true)System.out.println(player.getGridX(SQUARE_SIZE)+" "+player.getGridZ(SQUARE_SIZE));
			
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
	maze = IO.readMaze(level);
	objectindex = new int[maze.length][maze[0].length];
	
	minimap=new MiniMap(maze);		//load the minimap
	
	for(int j = 0; j < maze.length; j++){
		for(int i = 0; i<maze[0].length; i++){
			if(maze[j][i]>0 && maze[j][i]<11){
				levelObject lvlo = new Wall(i*SQUARE_SIZE+SQUARE_SIZE/2.0, 0, j*SQUARE_SIZE+SQUARE_SIZE/2.0, SQUARE_SIZE, maze[j][i],SQUARE_SIZE);
				visibleObjects.add(lvlo);
				objlijst.add(lvlo);
				objectindex[j][i]=visibleObjects.size()-1;
			}else{
				objectindex[j][i]=-200;
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
		
		 	lightPosition = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(maze[0].length*SQUARE_SIZE).put(150.0f).put(maze.length*SQUARE_SIZE).put(1.0f).flip();	// High up in the sky!
	        FloatBuffer lightColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();		// White light!
	        glLight( GL_LIGHT0, GL_POSITION, lightPosition);	// Note that we're setting Light0.
	        glLight( GL_LIGHT0, GL_AMBIENT, lightColour);
	        glEnable( GL_LIGHTING );
	        glEnable( GL_LIGHT0 );
	        
	     // Set the shading model.
	        glShadeModel( GL_SMOOTH );
	        
			// Enable Textures
//			glEnable(GL_TEXTURE_2D);
			
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
			initMaze();
			
			// Add the maze that we will be using.
//			maze = new Maze();
//			visibleObjects.add( maze );
	     // Initialize the player.
			player = new Player( 6 * SQUARE_SIZE + SQUARE_SIZE / 2.0, 	// x-position
								 SQUARE_SIZE *30/ 2.0 ,							// y-position
								 5 * SQUARE_SIZE + SQUARE_SIZE / 2.0, 	// z-position
								 0, 0 ,0.25,SQUARE_SIZE* 3/2.0);										// horizontal and vertical angle

			camera = new Camera( player.getLocationX(), player.getLocationY(), player.getLocationZ(), 
					             player.getHorAngle(), player.getVerAngle() );
			
			input = new UserInput();
			player.setControl(input);
			wall = new Wall(10, 10, 0, 5, 2,SQUARE_SIZE);
			grond = new Floor(0, 0, 0, maze[0].length*SQUARE_SIZE, maze.length*SQUARE_SIZE,1,SQUARE_SIZE,Textures.ground);	
			objlijst.add(wall);
			
			objlijst.add(grond);
		
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
//				System.out.println(deltaTime);
				
				//Update any movement since last frame.
				updateMovement(deltaTime);
				updateCamera();				
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
				
				glLoadIdentity();
				
		        GLU.gluLookAt( (float)camera.getLocationX(), (float)camera.getLocationY(),(float) camera.getLocationZ(), 
		        		(float)camera.getVrpX(), (float)camera.getVrpY(), (float)camera.getVrpZ(),
		        		(float)camera.getVuvX(), (float)camera.getVuvY(), (float)camera.getVuvZ() );
		        drawSkybox();
		        //update light positions
		        glLight( GL_LIGHT0, GL_POSITION, lightPosition);	
		        
		        Textures.ingamewall.bind();
		        // Display all the visible objects of MazeRunner.
		        if(!input.debug){
		        for(VisibleObject vo:visibleObjects){
		        	if(vo instanceof Wall){glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.wallColour);}
		        	vo.display();
		        }}	
		        
		        glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.wallColour);
		        
		        wall.display();
		        
				
				glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.floorColour);
				grond.display();
				if(input.minimap){drawHUD();}
				player.draw();
//		        glLoadIdentity();
	}
	

	/**
	 * if the window is reshaped, change accordingly
	 */
	public void reshape(){
		screenWidth = Display.getWidth();
		screenHeight = Display.getHeight();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		// Now we set up our viewpoint.
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		GLU.gluPerspective(60, (float)Display.getWidth()/(float)Display.getHeight(), 0.001f, 1000);	// Set up the parameters for perspective viewing. 
		glMatrixMode(GL_MODELVIEW);
		
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
			double px = player.getLocationX();				// Player X Location
			double py = player.locationY;					// Player Y location
			double pz = player.getLocationZ();				// Player Z location
			double ph	  = player.getHeight();				// Player Height
			double pw	  = player.getWidth();				// Player Width
			player.update(deltaTime);						// Updating velocity vector
			int Xin = player.getGridX(SQUARE_SIZE);
			int Zin = player.getGridZ(SQUARE_SIZE);
			int signX = (int) Math.signum(player.velocity.getX()); // Direction of the velocity in X
			int signZ = (int) Math.signum(player.velocity.getZ()); // Direction of the velocity in Z
			boolean colX = false;
			boolean colZ = false;
			boolean colY = false;
			ArrayList<Integer> tempindex = new ArrayList<Integer>();
			
			// Get indices of the arraylist with collidable objects
			for(int i = -1 ; i<=1;i++){
				for(int j = -1; j<=1; j++){
					if((Xin+i)>=0 && (Xin+i)<maze[0].length && (Zin+j)>=0 && (Zin+j)<maze.length){
						if(objectindex[Zin+j][Xin+i]>=0){							// < zero means there is nothing so no index
							tempindex.add(objectindex[Zin+j][Xin+i]);
						}
					}
				}
			}
			//Add addition extra block
			tempindex.add(objlijst.size()-2);
			//Add floor
			tempindex.add(objlijst.size()-1);

			// Voor nu nog ff beunen
			double maxX = player.velocity.getX();
			//collision X	
			for(int i = 0; i< tempindex.size();i++){
				levelObject tempobj = objlijst.get((tempindex.get(i).intValue()));				
				if(tempobj.isCollision(px+player.velocity.getX()+pw*signX, py-ph, pz+pw)
				|| tempobj.isCollision(px+player.velocity.getX()+pw*signX, py-ph, pz-pw)){
					colX=true;
					player.locationX+=tempobj.getmaxDistX(player.locationX+pw*signX);
					break;
				}

			}			
			if(colX){}else{player.updateX();}
			px = player.locationX;
			// collsion Z						
			for(int i = 0; i< tempindex.size();i++){
				
			
				levelObject tempobj = objlijst.get((tempindex.get(i).intValue()));		
				if(tempobj.isCollision(px+pw, py-ph, pz+pw*signZ+player.velocity.getZ())
				|| tempobj.isCollision(px-pw, py-ph, pz+pw*signZ+player.velocity.getZ())){
					colZ=true;
					player.locationZ+=tempobj.getmaxDistZ(player.locationZ+pw*signZ);
					break;
				}
			}
			if(colZ){}else{	player.updateZ();}
			pz= player.getLocationZ();
			
			// CollisionY
			for(int i = 0; i< tempindex.size();i++){
				levelObject tempobj = objlijst.get((tempindex.get(i).intValue()));		
				if(tempobj.isCollision(px+pw,  py+player.velocity.getY()-ph , pz+pw)
				|| tempobj.isCollision(px-pw,  py+player.velocity.getY()-ph , pz+pw)
				|| tempobj.isCollision(px-pw,  py+player.velocity.getY()-ph , pz-pw)
				|| tempobj.isCollision(px+pw,  py+player.velocity.getY()-ph , pz-pw)){
					colY=true;
					player.locationY+=tempobj.getmaxDistY(player.locationY-ph);
				}				
			}
			if(colY){player.jump=false;}else{player.updateY();}
			
			
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
		/**
		 * Switches to 2D orthogonal view to project the HUD, after drawing the HUD, the Matrixmode is set back to 3D view
		 */
		private void drawHUD(){
			// Switch to 2D
			glMatrixMode(GL_PROJECTION);
			glPushMatrix();
			glLoadIdentity();
			glOrtho(0.0, Display.getWidth(), Display.getHeight(), 0.0, -1.0, 1.0);
			glMatrixMode(GL_MODELVIEW);

			glLoadIdentity();
			glDisable(GL_CULL_FACE);
			glDisable(GL_LIGHTING);

			glClear(GL_DEPTH_BUFFER_BIT);
			
			

			minimap.draw(player,SQUARE_SIZE);
			glEnable(GL_LIGHTING);

			// Making sure we can render 3d again
			glMatrixMode(GL_PROJECTION);
			glPopMatrix();
			glMatrixMode(GL_MODELVIEW);
		}
		
		public void drawSkybox(){

			camera.setLocationX( 0 );
			camera.setLocationY( 0 );  
			camera.setLocationZ( 0 );
			camera.setHorAngle( player.getHorAngle()+(input.lookback? 180:0) );
			camera.setVerAngle( player.getVerAngle() * (input.lookback? -1:1) );
			camera.calculateVRP();
		 // Store the current matrix
		    glPushMatrix();
		 
		    // Reset and transform the matrix.
		    glLoadIdentity();
		    GLU.gluLookAt(
		        0f,0f,0f,
		        (float) camera.getVrpX(), (float) camera.getVrpY(),(float) camera.getVrpZ(),
		        0f,1f,0f);
		 
		    // Enable/Disable features
		    glPushAttrib(GL_ENABLE_BIT);
		    glEnable(GL_TEXTURE_2D);
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		    
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		    
		    glDisable(GL_DEPTH_TEST);
		    glDisable(GL_LIGHTING);
		    glDisable(GL_BLEND);
		 
		    float smallnumber = 0.001f;
		    // Just in case we set all vertices to white.
		    glColor4f(1,1,1,1);
		 
		    // Render the front quad
		    Textures.front.bind();
		    glBegin(GL_QUADS);
		        glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );
		        glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
		        glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
		        glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );
		        
		        
		    glEnd();
		 
		    // Render the left quad
		    Textures.left.bind();
		    glBegin(GL_QUADS);
		    	glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
		    	glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
		    	glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );	
		    	glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );
		        
		       
		    glEnd();
		 
		    // Render the back quad
		    Textures.back.bind();
		    glBegin(GL_QUADS);
		    	
		        glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
		        glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
		        glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
		        glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );
		 
		    glEnd();
		 
		    // Render the right quad
		    Textures.right.bind();
		    glBegin(GL_QUADS);
		        glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );
		        glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
		        glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
		        glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
		        
		    glEnd();
		 
		    // Render the top quad
		    Textures.top.bind();
		    glBegin(GL_QUADS);
		        glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
		        glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
		        glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
		        glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
		        
		    glEnd();
		 
		    // Render the bottom quad
		    Textures.bottom.bind();
		    glBegin(GL_QUADS);
		    	glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );
		    	glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );
		        glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );  		       
		        glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
		        
		    glEnd();
		 
		    // Restore enable bits and matrix
		    glPopAttrib();
		    glPopMatrix();
		}
}
