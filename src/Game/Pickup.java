package Game;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import Utils.Graphics;
import Utils.Material;

public class Pickup extends levelObject {
	public Pickup(double x, double y, double z, int type, float size) {
		super(x, y, z);
		this.type = type;
		this.size = size;
		this.origsize = size;
	}

	int type;
	float size;
	float origsize;
	boolean on = false;
	

	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		Material.setMtlPickup(on);
		// Graphics.renderSphere(size/2);
		Graphics.renderCube(size, false, false, false, false);

		glPopMatrix();

		// TODO Auto-generated method stub

	}

	public boolean check(Player player) {
		return ((Math.abs(player.locationX - this.locationX)) + (Math.abs(player.locationZ - this.locationZ)) < size);
		}

	public void effect(){
		System.out.println("effect!");
		switch(this.type){
		case 0: {StatusBars.addHealth(30);break;}
		case 1: {StatusBars.minHealth(30);break;}
		}
	}
	@Override
	public boolean isCollision(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(int deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}
}
