package Game;
import static org.lwjgl.opengl.GL11.*;

public class EndObelisk extends levelObject{

	public EndObelisk(double x, double y, double z) {
		super(x, y, z);		
	}

	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		for(int i=0; i<4; i++){
			glRotated(90, 0, 1, 0);
			glBegin(GL_QUADS);
			
			glNormal3d(-1, 0, 0);
			glVertex3d(-0.5, 0, -0.5);
			glVertex3d(-0.5, 0, 0.5);
			glVertex3d(-0.5, 7, 0.5);
			glVertex3d(-0.5, 7, -0.5);
			
			glEnd();
			
		}
		glPopMatrix();
		
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		double width = Mazerunner.SQUARE_SIZE/2f;			
		return x>(locationX-width) && x<(locationX+width) && z>(locationZ-width) && z<(locationZ+width);
		
	}

	@Override
	public double getmaxDistX(double X) {
		if(locationX>X)return locationX-Mazerunner.SQUARE_SIZE/2f-X;
		return locationX+Mazerunner.SQUARE_SIZE/2f-X;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		if(locationZ>Z)return locationZ-Mazerunner.SQUARE_SIZE/2f-Z;
		return locationZ+Mazerunner.SQUARE_SIZE/2f-Z;
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