package Game;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.PriorityQueue;

import Intelligence.*;
import Utils.Graphics;
import Utils.Models;
import Utils.Vector;

public class Monster extends levelObject {

	private int SQUARE_SIZE;
	private double width;
	private double height;
	private double speed = 0.005;
	protected Vector velocity = new Vector(0, 0, 0);
	public static Vector playerloc = new Vector(0, 0, 0);
	private Vector toPlayer;
	private Vector dir = new Vector(0, 0, -1);
	private boolean colX, colZ, colY;
	protected double distanceToPlayer;
	private boolean followplayer = true;
	private int Count = 0;
	public boolean isDead = false;
	private boolean PlayerinSight = true;
	private boolean wait;
	private Health health;
	private int immunitycounter;
	private int flickercounter;
	private int monsterframe = 0;
	private double distanceToTarget;
	private Vector target;
	private ArrayList<Node> Route;
	private AStar pathfinding;
	private boolean isplaying;
	

	public Monster(double x, double y, double z, double width, double height, int SQUARE_SIZE) {
		super(x, y, z);
		this.width = width;
		this.height = height;
		this.SQUARE_SIZE = SQUARE_SIZE;
		wait = false;
		this.health = new Health(30, false);
		pathfinding = new AStar();
		isplaying=false;
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
		} else {

			// Check the count, to know whether the monster has been stuck for a
			// while, or can see the player
			checkSituation();
			//if(findPath() && !Route.isEmpty()){
			//	followRoute();
			//} else {
			//	randomWalk();
			//}
			
			
			if (followplayer) {
				dirToPlayer();
			} else {
				randomWalk();
			}
			
			avoidWalls();

			dir.normalize2D();

			avoidPlayer();

			updateV(deltaTime);

			collision();

		}
		if(isDead){
			Count = 0;
			Intelligence.addAvoid(playerloc.clone());
			System.out.println("Monster is dood");
			Mazerunner.status.addScore(100);
		}
	}
	
	/**
	 * Lets the monster go to a given location in a straight path.
	 * @param loc
	 */
	public void goTo(Vector loc){
		Vector vec = new Vector(locationX, locationY, locationZ);
		vec.scale(-1);
		Vector dir = loc.clone();
		dir.add(vec);
		distanceToTarget = dir.length2D();
		dir.normalize2D();
	}
	
	public boolean findPath(){
		if(pathfinding.setRoute(new Vector(locationX, 0, locationZ), playerloc)){
			Route = pathfinding.findRoute();
			System.out.println("Route gevonden");
			System.out.println(Route);
			return true;
		}
		return false;
	}
	
	public void followRoute(){
		Node firstpoint = Route.get(0);
		Vector target = new Vector(firstpoint.getX(), locationY, firstpoint.getY());
		System.out.println("goTo bereikt");
		goTo(target);
	}

	/**
	 * Controleert de count en past deze aan. Wanneer er collision is: count
	 * verhogen. Wanneer count groter dan een threshold value is, wordt
	 * geswitcht van followplayer (wel/niet) modus.
	 * 
	 */
	public void checkSituation() {
		if (colX | colZ) {
			Count++;
		}
		if (Count > 200 | playerSight()) {
			followplayer = !followplayer;
			Count = 0;
		}
	}

	/**
	 * Wanneer het monster in een gebied staat dat vermeden dient te worden,
	 * draait hij om.
	 */
	public void avoidPlayer() {
		wait = false;
		if (Intelligence.inAvoidArea(playerloc.clone())) {
			System.out.println("Weg hier!");
			dir.scale(-1);
			wait = true;
		}
	}

	/**
	 * Voert een random walk uit. Wanneer er sprake is van collision wordt de
	 * richting veranderd, anders loopt monster door.
	 */
	public void randomWalk() {

		if (colX | colZ) {
			dir.rotate2D((float)(Math.random() * 2 * Math.PI));
			Count++;
		} else {
			Count++;
		}
	}

	/**
	 * Controleert of het monster vast zit. (wordt nu niet gebruikt, in plaats
	 * daarvan checkcount)
	 * 
	 * @return
	 */
	public boolean isStuck() {
		if (colX | colZ) {
			Count++;
		} else {
			Count = 0;
		}
		return Count > 200;
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
		toPlayer();
		if (colX) {
			dir.add(0.0, 0.0, Math.signum(toPlayer.getZ()));
		}
		if (colZ) {
			dir.add(Math.signum(toPlayer.getX()), 0.0, 0.0);
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

		double maxX = 100;
		// collision X
		for (int i = 0; i < tempindex.size(); i++) {
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));
			if (tempobj.isCollision(px + velocity.getX() + pw * signX, py - ph / 2f, pz + pw) || tempobj.isCollision(px + velocity.getX() + pw * signX, py - ph / 2f, pz - pw)) {
				colX = true;
				collisionreaction(tempobj);

				maxX = tempobj.getmaxDistX(locationX + pw * signX);
				break;
			}
		}
		for (Monster mo : Mazerunner.monsterlijst) {
			if (mo != this) {
				if (mo.isCollision(px + velocity.getX() + pw * signX, py - ph / 2f, pz + pw) || mo.isCollision(px + velocity.getX() + pw * signX, py - ph / 2f, pz - pw)) {
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
			if (tempobj.isCollision(px + pw, py - ph / 2f, pz + pw * signZ + velocity.getZ()) || tempobj.isCollision(px - pw, py - ph / 2f, pz + pw * signZ + velocity.getZ())) {
				colZ = true;
				maxZ = tempobj.getmaxDistZ(locationZ + pw * signZ);
				collisionreaction(tempobj);
				break;
			}
		}
		// with eachother
		for (Monster mo : Mazerunner.monsterlijst) {
			if (mo != this) {
				if (mo.isCollision(px + pw, py - ph / 2f, pz + pw * signZ + velocity.getZ()) || mo.isCollision(px - pw, py - ph / 2f, pz + pw * signZ + velocity.getZ())) {
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

		// CollisionY
		boolean islift = false;
		for (int i = 0; i < tempindex.size(); i++) {
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));
			if (tempobj.isCollision(px + pw, py + velocity.getY() - ph, pz + pw) 
			||  tempobj.isCollision(px - pw, py + velocity.getY() - ph, pz + pw) 
			||  tempobj.isCollision(px - pw, py + velocity.getY() - ph, pz - pw) 
			||  tempobj.isCollision(px + pw, py + velocity.getY() - ph, pz - pw)) {
				colY=true;
				if(!islift){
					locationY+=tempobj.getmaxDistY(locationY-ph);
					collisionreaction(tempobj);
				}else{
					if(tempobj instanceof MoveableWall){
						MoveableWall tempmw = (MoveableWall) tempobj;
						if(tempmw.isPriority()){
							locationY+=tempobj.getmaxDistY(locationY-ph);
							collisionreaction(tempobj);
						}
					}
				}
				if(tempobj instanceof MoveableWall)islift=true;
			}	
		}

		if (colY) {
		} else {
			updateY();
		}
		py = getLocationY();

	}

	public void updateV(int deltaTime) {

		velocity.scale(0.0);
		// Movement to dir vector
		if (!wait)
			velocity.add(dir.getX() * speed * deltaTime * 0.5, -0.005 * deltaTime, dir.getZ() * speed * deltaTime * 0.5);
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
		if (PlayerinSight || distanceToPlayer < 5) {
			if (flickercounter%10<5){
			glPushMatrix();

			glTranslated(locationX, locationY, locationZ);
			if (!isStuck() && !wait)
				rotateV();

			// Drawing the next model of the monster (for animation)
			int buffer = Models.monster.get(monsterframe % 20);
			glScaled(0.7, 0.7, 0.7);
			glCallList(buffer);

			glPopMatrix();
			monsterframe++;
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

		return x >= (locationX - width / 2f) && x <= (locationX + width / 2f) && z >= (locationZ - width / 2f) && z <= (locationZ + width / 2f) && y >= locationY-height/2f && y < locationY + height/2f;
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
		}
	}

	public boolean isPlaying(){
		return isplaying;
	}
	
	public void setPlaying(boolean isplaying){
		this.isplaying = isplaying;
	}
}
