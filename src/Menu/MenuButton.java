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
		initFont();
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
		
		text(x +width/2, y+height/2, 1, name );
	}
	
	/** Schrijft tekst vanaf de coordinaten x,y met tekst s
	 *  
	 * @param x
	 * @param y
	 * @param scale
	 * @param s
	 */
	public void text(int x, int y,float scale, String s){
		int lineh = myfont.getHeight();
		int width = myfont.getWidth(s);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glTranslatef(x - width/2, y + lineh/2, 0);
		GL11.glScalef(scale, -scale, scale);
		myfont.drawString(0, 0, s,Color.white);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/** Initialiseert het font
	 *  !Definieer hier het juiste font bestand!
	 * 
	 */
	public void initFont(){
			try {
			Double size = new Double(0.7 * height);
			float fontsize = size.floatValue();
			InputStream inputStream	= ResourceLoader.getResourceAsStream("BEBAS.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(fontsize); // set font size
			myfont = new TrueTypeFont(awtFont2, true);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
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
