package Game;
import static org.lwjgl.opengl.GL11.*;

public class MoveableWall extends levelObject{
	
	private double bottom, height=3;
	private wallState wallstate = wallState.UP;
	
	public MoveableWall(double x, double y, double z) {
		super(x, y, z);
		this.bottom = y;
	}

	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		for(int i = 0 ; i<4; i++){
			glRotated(90*i, 0, 1, 0);
			glNormal3d(0, 1, 1);
			glBegin(GL_QUADS);
				glVertex3d(-0.5, bottom, +0.5);	
				glVertex3d(0.5, bottom, +0.5);	
				glVertex3d(0.5, bottom+height, +0.5);	
				glVertex3d(-0.5, bottom+height, +0.5);	
			glEnd();
		}
		glNormal3d(0, 1, 0);
		glBegin(GL_QUADS);
			glVertex3d(-0.5, bottom+height, -0.5);
			glVertex3d(-0.5, bottom+height, 0.5);
			glVertex3d(0.5, bottom+height, 0.5);
			glVertex3d(0.5, bottom+height, -0.5);
		glEnd();
		glPopMatrix();
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		double ss  = Mazerunner.SQUARE_SIZE;		
		return x>(locationX-ss/2) && x<(locationX+ss/2) && z<(locationZ+ss/2) && z>(locationZ-ss/2)  && y<(bottom+height);	
		
	}

	@Override
	public double getmaxDistX(double X) {
		double ss  = Mazerunner.SQUARE_SIZE;
		if(locationX-ss/2>X)return locationX-Mazerunner.SQUARE_SIZE/2f-X;
		else if(locationX+ss/2<X)return locationX+Mazerunner.SQUARE_SIZE/2f-X;
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		return (bottom+height)-Y;		
	}

	@Override
	public double getmaxDistZ(double Z) {
		double ss  = Mazerunner.SQUARE_SIZE;
		
		if(locationZ-ss/2>Z)return locationZ-Mazerunner.SQUARE_SIZE/2f-Z;
		else if(locationZ+ss/2<Z)return locationZ+Mazerunner.SQUARE_SIZE/2f-Z;
		return 0;
	}

	@Override
	public void update(int deltaTime) {
		if(wallstate == wallState.ASCENDING){bottom += deltaTime*0.004;}
		if(wallstate == wallState.DESCENDING){bottom -= deltaTime*0.004;}
		if(bottom > 0){bottom=0; wallstate = wallState.UP;}
		if(bottom < -height){bottom = - height; wallstate = wallState.DOWN;}			
	}

	@Override
	public void change() {
		if(wallstate == wallState.DOWN){wallstate = wallState.ASCENDING;}
		if(wallstate == wallState.UP){wallstate = wallState.DESCENDING;}
	}
	
	public boolean isPriority(){
		if(wallstate == wallState.ASCENDING) return true;		
		return false;
	}
	
	private enum wallState{
		UP, ASCENDING, DESCENDING, DOWN;
	}

}
