package Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

public class Floor extends levelObject{
	private double width;
	private double height;
	private int up;
	
	public Floor(double x, double y, double z,double width, double height, int up) {
		super(x, y, z);
		setHeight(height);
		setWidth(width);
		setUp(up);
	}
	public void setUp(int up){
		this.up = (int) Math.signum(up);
	}
	public void setWidth(double width){
		this.width = width;		
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	@Override
	public void display() {
		glColor3f(0.0f, 0.0f, 1.0f);		
		glBegin(GL_QUADS);
		glNormal3d(0, 1, 0);
		glVertex3d(this.locationX, this.locationY,this.locationZ);
		glVertex3d(this.locationX, this.locationY,this.locationZ+height);
		glVertex3d(this.locationX+width, this.locationY,this.locationZ+height);
		glVertex3d(this.locationX+width, this.locationY,this.locationZ);
		glEnd();
		
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		
		return y<this.locationY && x>this.locationX && x<this.locationX+width &&
				z>this.locationZ && z<this.locationZ+height;
	}

}
