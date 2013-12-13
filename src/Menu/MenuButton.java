package Menu;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import Utils.Text;

public class MenuButton {
	private int x, y, ID;
	Texture normal = null, mouseover = null;
	public static int width, height;
	public static int mousex, mousey;
	private TrueTypeFont myfont;	
	private String name;
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param normal
	 * @param Mouseover
	 */
	public MenuButton (int x , int y , Texture normal, Texture Mouseover, int ID, String name){
		this.x = x;
		this.y = y;
		this.normal = normal;
		this.mouseover = Mouseover;
		this.ID = ID;
		this.name = name;
		
		//TODO: Volgens mij kan dit beter ergens anders gebeuren, bedenken waar!
		
	}
	/**
	 * draw the buttons
	 */
	public void draw(){
		// Enable transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// Enable textures
		glEnable(GL_TEXTURE_2D);
		// Choose bind depending on mouse event
		if(!isButton(mousex, mousey)){normal.bind();}
		if(isButton(mousex, mousey)){mouseover.bind();}
		
		glColor4f(1.0f, 1.0f, 1.0f,1.0f);
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
		glDisable(GL_BLEND);
		
//		content.draw(x +width/2, y-height/2, 1);
		Menu.bebas.draw(x, y, (float) (0.7*height), name);

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
	 * report mouse location to all buttons
	 * @param x
	 * @param y
	 */
	public static void setMouse(int x, int y){
		mousex = x;
		mousey = y;
	}
	
	public int getID(){ return ID;}
}
