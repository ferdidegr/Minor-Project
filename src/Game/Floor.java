package Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Floor extends levelObject{
	private double width;
	private double height;
	private int up;
	private int SQUARE_SIZE;
	private Texture tex;
	
	public Floor(double x, double y, double z,double width, double height, int up, int SQUARE_SIZE, Texture tex) {
		super(x, y, z);
		setHeight(height);
		setWidth(width);
		setUp(up);
		setSZ(SQUARE_SIZE);
		setTex(tex);
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
	public void setSZ(int SQUARE_SIZE){
		this.SQUARE_SIZE= SQUARE_SIZE;
	}
	public void setTex(Texture tex){
		this.tex = tex;
	}
	@Override
	public void display() {
		
		glEnable(GL_TEXTURE_2D);
		tex.bind();
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		glBegin(GL_QUADS);
		glNormal3d(0, 1, 0);
		glTexCoord2d(0, height/SQUARE_SIZE);
		glVertex3d(this.locationX, this.locationY,this.locationZ);
		glTexCoord2d(0, 0);
		glVertex3d(this.locationX, this.locationY,this.locationZ+height);
		glTexCoord2d(width/SQUARE_SIZE, 0);
		glVertex3d(this.locationX+width, this.locationY,this.locationZ+height);
		glTexCoord2d(width/SQUARE_SIZE, height/SQUARE_SIZE);
		glVertex3d(this.locationX+width, this.locationY,this.locationZ);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		
		return y<this.locationY && x>this.locationX && x<this.locationX+width &&
				z>this.locationZ && z<this.locationZ+height;
	}

}
