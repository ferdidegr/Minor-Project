package Game;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import Intelligence.*;
import Utils.Models;
import Utils.Vector;

public class Monster extends levelObject {

	private double width;
	private double height;
	private double speed = 0.005;
	protected Vector velocity = new Vector(0, 0, 0);
	public static Vector playerloc = new Vector(0, 0, 0);
	private Vector toPlayer;
	private Vector dir = new Vector(0, 0, -1);
	private boolean colX, colZ, colY;
	protected double distanceToPlayer;
	public boolean isDead = false;
	private Health health;
	private int immunitycounter;
	private int flickercounter;
	private int RouteCounter;
	private int monsterframe = 0;
	private ArrayList<Node> Route;
	private AStar pathfinding;
	public boolean active = false;
	private boolean isplaying;
	private double eps = 1E-5;
	private int ID;

	public Monster(double x, double y, double z, double width, double height, int SQUARE_SIZE, int ID) {
		super(x, y, z);
		this.width = width;
		this.height = height;
		this.health = new Health(30, false);
		pathfinding = new AStar();
		isplaying=false;
		Route = new ArrayList<Node>();
		this.ID=ID;
		RouteCounter = ID%10;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	/**
	 * get X grid location
	 * 
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridX(int SQUARE_SIZE) {
		return (int) Math.floor(locationX / SQUARE_SIZE);
	}

	/**
	 * get Z grid location
	 * 
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridZ(int SQUARE_SIZE) {
		return (int) Math.floor(locationZ / SQUARE_SIZE);
	}

	/**
	 * Dit is de loop waarin beslissingen gemaakt worden en beweging uitgevoerd
	 * wordt.
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {

		// Update immunity counter when you are hit, only when monster is hit
		if (immunitycounter > 0)
			immunitycounter += deltaTime;
		if (immunitycounter > 2000)
			immunitycounter = 0;

		// If monster is falling, it's dead.
		// Will be removed from the game next iteration
		if (locationY < -5) {
			isDead = true;
		} 
		if (playerSight()){
			active = true;
		}
		if(active) {
			walk(deltaTime);
		}
		if(isDead){
			Mazerunner.status.addScore(100);
			AStar.removeNode(new Vector(locationX, locationY, locationZ));
		}
	}
	
	/**
	 * Route wordt gevormd en monster past direction aan
	 * @param deltaTime
	 */
	public void walk(int deltaTime){
		RouteCounter++;
		if(RouteCounter>10 && findPath()){
			RouteCounter = 0;
		}
		
		checkRoute();
		
		avoidWalls();
		
		dir.normalize2D();
		updateV(deltaTime);			
		
		collision();
	}
	
	/**
	 * Checkt of de Route daadwerkelijk punten bevat en volgt de route. Zo niet, randomwalk.
	 */
	public void checkRoute(){
		if(Route.size()>2) {
			followRoute();
		} else {
			dirToPlayer();
		}
	}
	
	/**
	 * Lets the monster go to a given location in a straight path.
	 * @param loc
	 */
	public void goTo(Vector loc){
		Vector vec = new Vector(locationX, locationY, locationZ);
		vec.scale(-1);
		dir = loc.clone();
		dir.add(vec);
		dir.normalize2D();
	}
	
	/**
	 * Kijkt of het eind en beginpunt geset kunnen worden. Zo ja, zoekt een pad. Zo nee, geeft false.
	 * @return
	 */
	public boolean findPath(){
		if(pathfinding.setRoute(new Vector(locationX, 0, locationZ), playerloc)){
			Route = pathfinding.findRoute();
			return true;
		}
		return false;
	}
	
	public void followRoute(){
		
		//TODO: Opschonen
		if(Route.size()>1){
//			System.out.println("location: " + locationX+ ", " +  locationZ);
//			System.out.println("target: " + Route.get(0));
			Node firstpoint = Route.get(0);
			Double locX = locationX;
			Double locZ = locationZ;
			Node ownloc = new Node(locX.intValue(), locZ.intValue());
			if(ownloc.distance(firstpoint)<=1){
				Route.remove(0);
				followRoute();
			} else {
				Vector target = new Vector(firstpoint.getX()+0.5, locationY, firstpoint.getY()+0.5);
				goTo(target);
			}
		} else if(Route.size() == 1){
			Node firstpoint = Route.get(0);
			Vector target = new Vector(firstpoint.getX()+0.5, locationY, firstpoint.getY()+0.5);
			goTo(target);
		}
	}

	/**
	 * Bepaalt de vector die naar de player wijst (toPlayer)
	 */
	public void toPlayer() {
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
	public void dirToPlayer() {
		toPlayer();
		toPlayer.scale(0.6);
		dir.add(toPlayer);

	}

	/**
	 * Wanneer er collision is loopt het monster om de muur heen, in richting
	 * van de player.
	 */
	public void avoidWalls() {
		//toPlayer();
		if (colX) {
			//dir.add(0.0, 0.0, Math.signum(dir.getZ()));
			dir.scale(0.2, 1.0, 1.0);
		}
		if (colZ) {
			//dir.add(Math.signum(dir.getX()), 0.0, 0.0);
			dir.scale(1.0, 1.0, 0.2);
		}

	}

	public boolean lineOfSight(Vector b) {

		// Convert all location to integers
		Double tmp = locationX;
		int x0 = tmp.intValue();
		tmp = locationZ;
		int z0 = tmp.intValue();
		tmp = b.getX();
		int x1 = tmp.intValue();
		tmp = b.getZ();
		int z1 = tmp.intValue();

		int w = x1 - x0;
		int h = z1 - z0;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i < longest; i++) {
			int block;
			try {
				block = Mazerunner.maze[z0][x0];
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
			if (block > 0 && block < 11) {
				return false;
			}

			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x0 += dx1;
				z0 += dy1;
			} else {
				x0 += dx2;
				z0 += dy2;
			}
		}
		return true;
	}

	public boolean playerSight() {
		return lineOfSight(playerloc);
	}

	/**
	 * Controleert of er sprake is van collision en past zonodig de beweging van
	 * het monster aan.
	 */
	public void collision() {
		/*
		 * Collision detection
		 */
		// Reassign attribute names to make the equations shorter

		double px = getLocationX(); // Monster X Location
		double py = locationY; // Monster Y location
		double pz = getLocationZ(); // Monster Z location
		double ph = getHeight() /2f; // Monster Height
		double pw = getWidth() / 2f; // Monster Width
		int Xin = getGridX(Mazerunner.SQUARE_SIZE);
		int Zin = getGridZ(Mazerunner.SQUARE_SIZE);
		colX = false;
		colZ = false;
		colY = false;

		int signX = (int) Math.signum(velocity.getX()); // Direction of the
														// velocity in X
		int signZ = (int) Math.signum(velocity.getZ()); // Direction of the
														// velocity in Z

		ArrayList<Integer> tempindex = new ArrayList<Integer>();

		// Get indices of the arraylist with collidable objects
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((Xin + i) >= 0 && (Xin + i) < Mazerunner.maze[0].length && (Zin + j) >= 0 && (Zin + j) < Mazerunner.maze.length) {
					if (Mazerunner.objectindex[Zin + j][Xin + i] >= 0) { // <
																			// zero
																			// means
																			// there
																			// is
																			// nothing
																			// so
																			// no
																			// index
						tempindex.add(Mazerunner.objectindex[Zin + j][Xin + i]);
					}
				}
			}
		}

		// CollisionY
		levelObject templvlo = null;
		double maxY = -100;
		for(int i = 0; i< tempindex.size();i++){
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
			if(tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz+pw)
			|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz+pw)
			|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz-pw)
			|| tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz-pw)){					
				colY=true;
				if(maxY < tempobj.getmaxDistY(locationY-ph)){
					templvlo = tempobj;
					maxY = tempobj.getmaxDistY(locationY-ph);
				}
					
			}				
		}
		
		
		if(colY){locationY += maxY; collisionreaction(templvlo);}else{updateY();}
		py = getLocationY();
		
		// collision X
		double maxX = 100;
		for (int i = 0; i < tempindex.size(); i++) {
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));
			if (tempobj.isCollision(px + velocity.getX() + pw * signX, py - ph , pz + pw) 
			|| tempobj.isCollision(px + velocity.getX() + pw * signX, py - ph , pz - pw)) {
				colX = true;
				collisionreaction(tempobj);

				maxX = tempobj.getmaxDistX(locationX + pw * signX)- signX* eps;
				break;
			}
		}
		for (Monster mo : Mazerunner.monsterlijst) {
			if (mo != this) {
				if (mo.isCollision(px + velocity.getX() + pw * signX, py - ph , pz + pw) 
				||  mo.isCollision(px + velocity.getX() + pw * signX, py - ph , pz - pw)) {
					maxX = Math.min(maxX, mo.getmaxDistX(locationX + pw * signX));
					colX = true;

				}
			}
		}

		if (colX) {
			locationX += maxX;			
		} else {
			updateX();
		}
		locationX = Math.round(locationX*1E10)/1E10d;
		px = locationX;

		// collsion Z with wall
		double maxZ = 100;
		for (int i = 0; i < tempindex.size(); i++) {

			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));
			if (tempobj.isCollision(px + pw, py - ph , pz + pw * signZ + velocity.getZ()) 
			||  tempobj.isCollision(px - pw, py - ph , pz + pw * signZ + velocity.getZ())) {
				colZ = true;
				maxZ = tempobj.getmaxDistZ(locationZ + pw * signZ) - signZ * eps;
				collisionreaction(tempobj);
				break;
			}
		}
		// with eachother
		for (Monster mo : Mazerunner.monsterlijst) {
			if (mo != this) {
				if (mo.isCollision(px + pw, py - ph , pz + pw * signZ + velocity.getZ()) 
				||  mo.isCollision(px - pw, py - ph , pz + pw * signZ + velocity.getZ())) {
					maxZ = Math.min(maxZ, mo.getmaxDistZ(locationZ + pw * signZ));
					colZ = true;

				}
			}
		}

		if (colZ) {
			locationZ += maxZ;
		} else {
			updateZ();
		}
		locationZ = Math.round(locationZ*1E10)/1E10d;
		pz = getLocationZ();

	}

	public void updateV(int deltaTime) {

		velocity.scale(0.0);
		// Movement to dir vector
		velocity.add(dir.getX() * speed * deltaTime * 0.5, -0.005 * deltaTime, dir.getZ() * speed * deltaTime * 0.5);
		
		// for low frame rates, prevents shooting through walls
		if(Math.abs(velocity.getX())>0.5){velocity.setX(Math.signum(velocity.getX())*0.5);}
		if(Math.abs(velocity.getZ())>0.5){velocity.setZ(Math.signum(velocity.getZ())*0.5);}
	}

	public void updateX() {
		locationX += velocity.getX();
	}

	public void updateY() {
		locationY += velocity.getY();
	}

	public void updateZ() {
		locationZ += velocity.getZ();
	}

	@Override
	public void display() {
		if (flickercounter>0){
			flickercounter-=1;
		}
		if (playerSight() || distanceToPlayer < 5) {
			if (flickercounter%10<5){
			glPushMatrix();

			glTranslated(locationX, locationY, locationZ);
			if(dir.getZ()!=0 && dir.getX()!=0)rotateV();

			// Drawing the next model of the monster (for animation)
			int buffer = Models.monster.get(monsterframe % 20);
			glScaled(0.7, 0.7, 0.7);
			glCallList(buffer);

			glPopMatrix();
			if(!Mazerunner.pause)monsterframe++;
		}}
	}

	public void rotateV() {
		float x = (float) dir.getX();
		float z = (float) dir.getZ();
		FloatBuffer m = Utils.Utils.createFloatBuffer(z, 0, -x, 0, 0, 1, 0, 0, x, 0, z, 0, 0, 0, 0, 1);
		glMultMatrix(m);
	}

	@Override
	public boolean isCollision(double x, double y, double z) {

		return x >= (locationX - width / 2f) && x <= (locationX + width / 2f) && z >= (locationZ - width / 2f) && z <= (locationZ + width / 2f) && y >= locationY-height/2f && y <= locationY + height/2f;
	}

	@Override
	public double getmaxDistX(double X) {
		// double right = locationX+width/2;
		// double left = locationX - width/2;
		// if(X>locationX) return right-X;
		// return left-X;
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		// double back = locationZ+width/2;
		// double front = locationZ - width/2;
		// if(Z>locationZ) return back-Z;
		// return front-Z;
		return 0;
	}

	public String toString() {
		return locationX + " " + locationY + " " + locationZ;
	}

	public static void setPlayerloc(Vector playerlocatie) {
		playerloc = playerlocatie;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	public void collisionreaction(levelObject obj) {
		if (obj instanceof Spikes) {
			spikereaction();
		}
	}

	public void spikereaction() {
		if (immunitycounter == 0) {
			health.addHealth(-10);
			if (health.getHealth() <= 0) {
				isDead = true;
			}
			immunitycounter += 1;
			flickercounter=60;
			locationY += 0.3;
		}
	}

	public boolean isPlaying(){
		return isplaying;
	}
	
	public void setPlaying(boolean isplaying){
		this.isplaying = isplaying;
	}
	
	public int getID(){
		return ID;
	}
	
	public void addHealth(int num){this.health.addHealth(num); if(health.getHealth()<=0)isDead=true; }
}
