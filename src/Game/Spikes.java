package Game;
import static org.lwjgl.opengl.GL11.*;
import Utils.Graphics;

public class Spikes extends levelObject{
private static int spike =0;
	public Spikes(double x, double y, double z) {
		super(x, y, z);		
		if(spike == 0){
			spike = glGenLists(1);
			glNewList(spike, GL_COMPILE);
			Graphics.renderSpike(Mazerunner.SQUARE_SIZE/2f, Mazerunner.SQUARE_SIZE);
			glEndList();
		}
	}

	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX+Mazerunner.SQUARE_SIZE/2f, locationY-Mazerunner.SQUARE_SIZE, locationZ+Mazerunner.SQUARE_SIZE/2f);		
		glCallList(spike);
		
		glPopMatrix();
		
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		boolean col = y<this.locationY && x>this.locationX && x<(this.locationX+Mazerunner.SQUARE_SIZE) &&
				z>this.locationZ && z<(this.locationZ+Mazerunner.SQUARE_SIZE);			
		return col;
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
