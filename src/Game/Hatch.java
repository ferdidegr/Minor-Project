package Game;
import static org.lwjgl.opengl.GL11.*;


public class Hatch extends levelObject{

	public Hatch(double x, double y, double z) {
		super(x, y, z);	
	}

	@Override
	public void display() {
		glPushMatrix();
		glRotatef(0, 0, 0, 1);
		glBegin(GL_QUADS);
		glVertex3d(locationX, 0, locationZ);
		
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
