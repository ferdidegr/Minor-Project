package Game;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import Utils.Graphics;
import Utils.Models;
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
	private boolean colX,colZ,colY;
	protected double distanceToPlayer;
	private boolean followplayer =true;
	private int Count = 0;
	
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
	
	/**
	 * Dit is de loop waarin beslissingen gemaakt worden en beweging uitgevoerd wordt.
	 * @param deltaTime
	 */
	public void update(int deltaTime){
		
		toPlayer();
		
		checkCount();
		
		if(followplayer){
			dirToPlayer();
		} else {
			randomWalk();
		}
		
		avoidWalls();
		
		dir.normalize2D();
		
		collision();
		
		//System.out.println(Count);

		updateV(deltaTime);
		
		System.out.println(lineOfSight(playerloc));
		
	}
	
	/** 
	 * Controleert de count en past deze aan. Wanneer er collision is: count verhogen. 
	 * Wanneer count groter dan een threshold value is, wordt geswitcht van followplayer (wel/niet) modus.
	 * 
	 */
	public void checkCount(){
		if(colX | colZ){
			Count++;
		}
		if(Count > 200){
			followplayer = !followplayer;
			Count = 0;
		}
	}
	
	/**
	 * Voert een random walk uit. Wanneer er sprake is van collision wordt de richting
	 * veranderd, anders loopt monster door.
	 */
	public void randomWalk(){
		if(colX | colZ){
			dir.rotate(Math.random()* 2 * Math.PI);
			Count++;
		} else { Count++; }
	}
	
	/**
	 * Controleert of het monster vast zit. (wordt nu niet gebruikt, in plaats daarvan checkcount)
	 * @return
	 */
	public boolean isStuck(){
		if(colX | colZ){
			Count++;
		} else { Count = 0 ;}
		return Count > 200;
	}
	
	/**
	 * Bepaalt de vector die naar de player wijst (toPlayer)
	 */
	public void toPlayer(){
		Vector vec = new Vector(locationX, locationY, locationZ);
		toPlayer = playerloc.clone();
		vec.scale(-1);
		toPlayer.add(vec);
		distanceToPlayer = toPlayer.length2D();
		toPlayer.normalize2D();
	}
	
	/**
	 * Laat de monster richting player lopen.
	 */
	public void dirToPlayer(){
		toPlayer.scale(0.6);
		dir.add(toPlayer);
		
	}
	
	/**
	 * Wanneer er collision is loopt het monster om de muur heen, in richting van de player.
	 */
	public void avoidWalls(){
		if(colX){	
			dir.add(0.0 , 0.0,  Math.signum(toPlayer.getZ()));
		}
		if(colZ){
			dir.add(Math.signum(toPlayer.getX()), 0.0, 0.0);
		}
		
	}
	
	/**
	 * Determines whether a location (Vector) b is in the line of sight of a monster.
	 * Not working yet!
	 */
	public boolean lineOfSight(Vector b){
		//Calculate the necessary vectors
		Vector a = new Vector(locationX, locationY, locationZ);
		Vector direc = a.difference(b);
		
		Double length = direc.length() * 10;
		int distance = length.intValue();
		direc.normalize();
		direc.scale(0.1);
		
		Vector loc = a;
		for(int i = 0; i < distance; i++){
			loc.add(direc);
			loc.scale(1/SQUARE_SIZE);
			Double xd=loc.getX();
			Double zd=loc.getZ();
			int x = xd.intValue();
			int z = zd.intValue();
			System.out.println(x + ", " + z);
			//if(Mazerunner.maze[x][z]>0 && Mazerunner.maze[x][z] <11){
			//	return false;
			//}
			if(loc.distance(b) < 0.5){
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Controleert of er sprake is van collision en past zonodig de beweging van het monster aan.
	 */
	public void collision(){
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

		glCallList(Models.monster);

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

	@Override
	public void change() {
		// TODO Auto-generated method stub
		
	}
	
}
