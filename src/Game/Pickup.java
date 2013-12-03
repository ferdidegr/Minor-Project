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

	int type = 0;
	float size;
	float origsize;
	boolean on = true;
	boolean beenaway = true;
	int stijn=0;

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

	public void check(Player player) {
		if (Math.sqrt(Math.pow(player.locationX - this.locationX, 2) + Math.pow(player.locationZ - this.locationZ, 2)) < size) {
			if (beenaway) {
				StatusBars.addHealth(1);
				on = !on;
				System.out.println("stijn: "+ stijn++);
				beenaway = false;
				//stijn++;
			}
			
		} else {
			beenaway = true;
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
}
