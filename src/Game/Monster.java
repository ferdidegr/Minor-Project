package Game;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import Utils.Graphics;
import Utils.Vector;

public class Monster extends levelObject{

	private int SQUARE_SIZE;
	private double width;
	private double height;
	private double speed = 0.005;
	protected Vector velocity = new Vector(0, 0, 0);
	public static Vector playerloc = new Vector(0, 0, 0);
	private Vector toPlayer;
	private Vector dir= new Vector(1, 0, 0);
	private int counter =0;
	private double rand = 0;
	private boolean colX,colZ,colY;
	
	public Monster(double x, double y, double z, double width, double height, int SQUARE_SIZE) {
		super(x, y, z);
		this.width = width;
		this.height= height;
		this.SQUARE_SIZE  =SQUARE_SIZE;
		// TODO Auto-generated constructor stub
	}
	
	public double getHeight(){	return height;}
	public double getWidth(){ return width; }
	/**
	 * get X grid location
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridX(int SQUARE_SIZE){
		return (int) Math.floor(locationX/SQUARE_SIZE);
	}
	/**
	 * get Z grid location
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridZ(int SQUARE_SIZE){
		return (int) Math.floor(locationZ/SQUARE_SIZE);
	}
	
	public void update(int deltaTime){
		Vector vec = new Vector(locationX, locationY, locationZ);
		toPlayer = playerloc.clone();
		vec.scale(-0.8);
		toPlayer.add(vec);
		toPlayer.normalize2D();
		
		dir.add(toPlayer);
		dir.normalize2D();
		
		updateV(deltaTime);
		
		/*
		 * Collision detection
		 */
		// Reassign attribute names to make the equations shorter
		
		double px = getLocationX();				// Player X Location
		double py = locationY;					// Player Y location
		double pz = getLocationZ();				// Player Z location
		double ph	  = getHeight();			// Player Height
		double pw	  = getWidth()/2f;			// Player Width
		int Xin = getGridX(Mazerunner.SQUARE_SIZE);
		int Zin = getGridZ(Mazerunner.SQUARE_SIZE);
		colX = false;
		colZ = false;
		colY = false;
		
		int signX = (int) Math.signum(velocity.getX()); // Direction of the velocity in X
		int signZ = (int) Math.signum(velocity.getZ()); // Direction of the velocity in Z
		
		ArrayList<Integer> tempindex = new ArrayList<Integer>();
		
		// Get indices of the arraylist with collidable objects
		for(int i = -1 ; i<=1;i++){
			for(int j = -1; j<=1; j++){
				if((Xin+i)>=0 && (Xin+i)<Mazerunner.maze[0].length && (Zin+j)>=0 && (Zin+j)<Mazerunner.maze.length){
					if(Mazerunner.objectindex[Zin+j][Xin+i]>=0){							// < zero means there is nothing so no index
						tempindex.add(Mazerunner.objectindex[Zin+j][Xin+i]);
					}
				}
			}
		}
	
		
		//Add addition extra block
		tempindex.add(Mazerunner.objlijst.size()-2);
		//Add floor
		tempindex.add(Mazerunner.objlijst.size()-1);
		double maxX=100;
		//collision X	
		for(int i = 0; i< tempindex.size();i++){
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));				
			if(tempobj.isCollision(px+velocity.getX()+pw*signX, py-ph/2f, pz+pw)
			|| tempobj.isCollision(px+velocity.getX()+pw*signX, py-ph/2f, pz-pw)){
				colX=true;	
				dir.rotate(Math.PI/3);
				maxX=tempobj.getmaxDistX(locationX+pw*signX);
				break;
			}
		}
		for(Monster mo:Mazerunner.monsterlijst){
			if(mo!=this){
				if(mo.isCollision(px+velocity.getX()+pw*signX, py-ph/2f, pz+pw)
				|| mo.isCollision(px+velocity.getX()+pw*signX, py-ph/2f, pz-pw)){
					maxX=Math.min(maxX, mo.getmaxDistX(locationX+pw*signX));
					colX=true;
					dir.rotate(Math.PI/3);
				}
			}
		}
		
		if(colX){locationX+=maxX;}else{updateX();}		
		px = locationX;
		
		// collsion Z with wall		
		double maxZ =100;
		for(int i = 0; i< tempindex.size();i++){			
		
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
			if(tempobj.isCollision(px+pw, py-ph/2f, pz+pw*signZ+velocity.getZ())
			|| tempobj.isCollision(px-pw, py-ph/2f, pz+pw*signZ+velocity.getZ())){
				colZ=true;
				dir.rotate(Math.PI/3);
				maxZ=tempobj.getmaxDistZ(locationZ+pw*signZ);
				break;
			}
		}
		// with eachother
		for(Monster mo:Mazerunner.monsterlijst){
			if(mo!=this){
				if(mo.isCollision(px+pw, py-ph/2f, pz+pw*signZ+velocity.getZ())
				){
					maxZ=Math.min(maxZ, mo.getmaxDistZ(locationZ+pw*signZ));
					colZ=true;
					dir.rotate(Math.PI/3);
				}
			}
		}
		if(colZ){locationZ+=maxZ;}else{	updateZ();}
		pz= getLocationZ();
		
		// CollisionY
		for(int i = 0; i< tempindex.size();i++){
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
			if(tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz+pw)
			|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz+pw)
			|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz-pw)
			|| tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz-pw)){
				colY=true;
				locationY+=tempobj.getmaxDistY(locationY-ph/2f);
			}				
		}
		if(colY){}else{updateY();}
		py = getLocationY();		
		
	}
	
	public void updateV(int deltaTime){
		counter+=deltaTime;
		// Friction
		velocity.scale(0.1, 0.4, 0.1);
		// Random motion
		//if(counter>3000){
		//rand = Math.random();
		//counter=0;
		//}
		velocity.add(rand*speed*deltaTime,0,rand*speed*deltaTime);
		velocity.scale(0.0);
		// Movement to player
		velocity.add(dir.getX()*speed * deltaTime*0.5, -0.005*deltaTime, dir.getZ()*speed * deltaTime*0.5);
	}
	
	public void updateX(){	locationX += velocity.getX();	}
	
	public void updateY(){locationY += velocity.getY();}
	
	public void updateZ(){	locationZ += velocity.getZ(); }
	
	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		Graphics.renderCube(width, false, false, false, false);	

		glPopMatrix();
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		
		return x>=(locationX-width/2f) && x<=(locationX+width/2f) && z>=(locationZ-width/2f) && z<=(locationZ+width/2f) && y>=0 && y<height;
	}

	@Override
	public double getmaxDistX(double X) {
//		double right = locationX+width/2;
//		double left = locationX - width/2;
//		if(X>locationX) return right-X;
//		return left-X;
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
//		double back = locationZ+width/2;
//		double front = locationZ - width/2;
//		if(Z>locationZ) return back-Z;
//		return front-Z;
		return 0;
	}
	public String toString(){
		return locationX+" "+locationY+" "+locationZ;
	}
	
	public static void setPlayerloc(Vector playerlocatie){
		playerloc = playerlocatie;
	}
	
}
