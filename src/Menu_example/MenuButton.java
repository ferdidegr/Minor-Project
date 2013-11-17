package Menu_example;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class MenuButton {
	private int x, y, ID;
	Texture normal = null, mouseover = null;
	public static int width, height;
	public static int mousex, mousey;
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param normal
	 * @param Mouseover
	 */
	public MenuButton (int x , int y , Texture normal, Texture Mouseover, int ID){
		this.x = x;
		this.y = y;
		this.normal = normal;
		this.mouseover = Mouseover;
		this.ID = ID;
	}
	/**
	 * draw the buttons
	 */
	public void draw(){
		glEnable(GL_TEXTURE_2D);
		
		if(!isButton(mousex, mousey)){normal.bind();}
		if(isButton(mousex, mousey)){mouseover.bind();}
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 1);
		glVertex2i(this.x, this.y);
		glTexCoord2d(0, 0);
		glVertex2i(this.x, this.y+height);
		glTexCoord2d(1, 0);
		glVertex2i(this.x+width, this.y+height);
		glTexCoord2d(1, 1);
		glVertex2i(this.x+width, this.y);
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	/**
	 * checks if it is in this button
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isButton(int x, int y){
		return x>this.x && x<(this.x+width) && y>this.y &&(y<this.y+height);
	}
	/**
	 * set Static parameters that define the dimensions of the button
	 * @param inwidth
	 * @param inheight
	 */
	public static void setDimensions(int inwidth, int inheight){
		width = inwidth;
		height = inheight;
	}
	/**
	 * report mouse location to all buttonss
	 * @param x
	 * @param y
	 */
	public static void setMouse(int x, int y){
		mousex = x;
		mousey = y;
	}
	
	public int getID(){ return ID;}
}
