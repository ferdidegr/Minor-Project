import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;


public class MenuButton {

	private String name;
	private TrueTypeFont myfont;
	private float x_size;
	private float y_size;
	
	/**
	 * 
	 * @param x_top_left relative to the left edge of the menu bar as a function of the menubar width
	 * @param y_top_left relative to the top as a function of the buttonsize
	 * @param ID Identifier for the button
	 * 
	 * right-0.95f*menubarwidth, top-0.1f*buttonsize
	 */
	public MenuButton(float x_size,float y_size, String name){
		
		this.name = name;
		this.x_size = x_size;
		this.y_size = y_size;
		

	}
	
	/**
	 * Draw the button on the screen	
	 */
	public void drawButton(){
		loadFont();
		
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glVertex2f(0, 0);
		glVertex2f(x_size, 0);
		glVertex2f(x_size, y_size);
		glVertex2f(0, y_size);
		glEnd();
		myfont.drawString(0, myfont.getLineHeight(), name);
		
	}
	
	/**
	 * Checks if the mouse is on this button
	 * @param x X-coordinate in world coordinates
	 * @param y	Y-coordinate in world coordinates
	 * @return true if the mouse is on this button
	 */
//	public boolean isButton(int x , int y){
//		return (x>left && x<right && y<top && y>bottom);
//	}
	/**
	 * Update the world coordinates of this button
	 */
//	public void update(){
//		left = bar_right - (1- x_top_left)*bar_width;
//		right = left + button_size;
//		top = bar_top - (y_top_left)*button_size;
//		bottom = top-button_size;		
//	}
	
	/**
	 * Menu bar location in world coordinates
	 * @param inbar_left
	 * @param inbar_right
	 * @param inbar_top
	 * @param inbar_bottom
	 */
//	public static void setStatics(float inbar_left, float inbar_right, float inbar_top, float inbar_bottom){
//		bar_left = inbar_left;
//		bar_right = inbar_right;
//		bar_top = inbar_top;
//		bar_bottom = inbar_bottom;
//		bar_width = (bar_right- bar_left);
//		button_size = bar_width*0.4f;
//	}	
	
	
	public void loadFont(){
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("Snowtop Caps.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(50f); // set font size
			myfont = new TrueTypeFont(awtFont2, true);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
	/*
	 * Getters
	 */
	/*
	public float getLeft(){ return left;}
	public float getRight(){ return right;}
	public float getTop(){ return top;}
	public float getBottom(){return bottom;}
	public String getName(){ return this.name;}
	public static float getbarBottom(){return bar_bottom;}
	public static float getbarTop(){return bar_top;}
	public static float getbarLeft(){return bar_left;}
	public static float getbarRight(){return bar_right;}
} */
