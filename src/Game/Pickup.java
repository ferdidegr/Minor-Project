package Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import Utils.Graphics;
import Utils.Material;

public class Pickup extends levelObject {
	int type;
	float size=1/4f;
	float origsize;
	boolean on = false;
	static int[][] maze = Mazerunner.maze;
	static int [][] alpu = new int[maze.length][maze[0].length];
	
	
	public Pickup(double x, double y, double z, int type, float size) {
		super(x, y, z);
		this.type = type;
		this.size = size;
		this.origsize = size;
	}
	
	public Pickup(boolean drop){
		super(0,0,0);
		Random random = new Random();
		this.locationX=0;
		this.locationZ=0;
		this.locationY=0;
		this.type=random.nextInt(4);
		while(maze[this.getGridZ(Mazerunner.SQUARE_SIZE)][this.getGridX(Mazerunner.SQUARE_SIZE)]>0 && maze[this.getGridZ(Mazerunner.SQUARE_SIZE)][this.getGridX(Mazerunner.SQUARE_SIZE)]<10 || alpu[this.getGridZ(Mazerunner.SQUARE_SIZE)][this.getGridX(Mazerunner.SQUARE_SIZE)]==1){
			locationZ=random.nextInt(maze.length)*Mazerunner.SQUARE_SIZE;
			locationX=random.nextInt(maze[0].length)*Mazerunner.SQUARE_SIZE;
		}
		alpu[this.getGridZ(Mazerunner.SQUARE_SIZE)][this.getGridX(Mazerunner.SQUARE_SIZE)]=1;
		locationX+=0.5;
		locationZ+=0.5;
		if (drop){
			locationY=Mazerunner.SQUARE_SIZE*5f;
		}
		else{
			locationY=Mazerunner.SQUARE_SIZE/2f;
		}
		
		//System.out.println("Pickup op punt "+locationX+", "+locationZ);
		
	}
	
	public void display() {
		glPushMatrix();
		glDisable(GL_TEXTURE_2D);
		glTranslated(locationX, locationY, locationZ);
		Material.setMtlPickup(on);
		// Graphics.renderSphere(size/2);
		Graphics.renderCube(size, false, false, false, false);
		glEnable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public boolean check(Player player) {
		return Math.abs(player.locationX - this.locationX)<size && Math.abs(player.locationZ - this.locationZ) < size;
		}

	public void effect(){
		alpu[this.getGridZ(Mazerunner.SQUARE_SIZE)][this.getGridX(Mazerunner.SQUARE_SIZE)]=0;
		switch(this.type){
		case 0: {Mazerunner.player.getHealth().addHealth(30);break;}
		case 1: {Mazerunner.player.getHealth().addHealth(-30);break;}
		case 2: {Mazerunner.status.addScore(50);break;}
		case 3: {Mazerunner.status.addScore(-50);break;}
		}
	}
	
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
	

	public boolean isCollision(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void update(int deltaTime) {
		// TODO Auto-generated method stub
	}

	public void change() {
		// TODO Auto-generated method stub
	}
}
