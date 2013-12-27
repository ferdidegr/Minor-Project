package Game;
import static org.lwjgl.opengl.GL11.*;


public class Hatch extends levelObject{
	private float rotangle = 0;
	private float rotspeed = 0f;
	private HatchState hatchstatus;
	public Hatch(double x, double y, double z) {
		super(x, y, z);
		hatchstatus=(Math.random()>0.5?HatchState.CLOSED:HatchState.OPEN);
		rotangle = (hatchstatus==HatchState.OPEN?90:0);
	}
	
	@Override
	public void display() {
		glNormal3d(0, 1, 0);
		
		glPushMatrix();
		glTranslated(locationX+0.001, locationY, locationZ);
		glRotatef(-rotangle, 0, 0, 1);
		
		glBegin(GL_QUADS);
		glVertex3d(0, 0, 0);
		glVertex3d(0, 0, Mazerunner.SQUARE_SIZE);
		glVertex3d(Mazerunner.SQUARE_SIZE/2.0, 0, Mazerunner.SQUARE_SIZE);
		glVertex3d(Mazerunner.SQUARE_SIZE/2.0, 0, 0);
		glEnd();		
		
		glPopMatrix();
		
		glPushMatrix();
		glTranslated(locationX+Mazerunner.SQUARE_SIZE-0.001, locationY, locationZ);
		glRotatef(rotangle, 0, 0, 1);
		
		glBegin(GL_QUADS);
		glVertex3d(0, 0, 0);
		glVertex3d(-Mazerunner.SQUARE_SIZE/2.0, 0, 0);
		glVertex3d(-Mazerunner.SQUARE_SIZE/2.0, 0, Mazerunner.SQUARE_SIZE);
		glVertex3d(0, 0, Mazerunner.SQUARE_SIZE);
		
		
		glEnd();		
		
		glPopMatrix();
		
	}
	public void change(){
		if(hatchstatus==HatchState.CLOSED)hatchstatus = HatchState.OPENING;
		if(hatchstatus==HatchState.OPEN)hatchstatus = HatchState.CLOSING;
	}
	@Override
	public boolean isCollision(double x, double y, double z) {
		if(hatchstatus==HatchState.CLOSED){ 
			return y<0 && y> -1 && x>locationX && x<locationX+Mazerunner.SQUARE_SIZE && z>locationZ && z<locationZ+Mazerunner.SQUARE_SIZE;
		}
		return false;
	}

	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		return locationY-Y;
	}

	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(int deltaTime) {
		
		if(rotangle >90) {rotangle =90;rotspeed*=0;hatchstatus = HatchState.OPEN;}
		if(rotangle <0) {rotangle =0;rotspeed*=0;hatchstatus = HatchState.CLOSED;}
		if(hatchstatus == HatchState.OPENING){rotspeed=0.1f;}
		if(hatchstatus == HatchState.CLOSING){rotspeed= -0.1f;}
		rotangle =rotangle%360 + rotspeed * deltaTime;
	}
	
}

enum HatchState{
	OPEN, OPENING, CLOSING, CLOSED;
}