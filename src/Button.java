import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;


public class Button {
	private float left, right, top, bottom, x_top_left, y_top_left;
	private static float bar_left, bar_right, bar_top, bar_bottom, button_size, bar_width;
	private int ID;
	/**
	 * 
	 * @param x_top_left relative to the left edge of the menu bar as a function of the menubar width
	 * @param y_top_left relative to the top as a function of the buttonsize
	 * @param ID Identifier for the button
	 * 
	 * right-0.95f*menubarwidth, top-0.1f*buttonsize
	 */
	public Button(float x_top_left,float y_top_left, int ID){
		this.left = bar_right - (1- x_top_left)*bar_width;
		this.right = this.left + button_size;
		this.top = bar_top - (y_top_left)*button_size;
		this.bottom = this.top-button_size;
		this.ID = ID;
		this.x_top_left = x_top_left;
		this.y_top_left = y_top_left;
	}
	/**
	 * Draw the button on the screen	
	 */
	public void drawButton(){
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glVertex2f(left, top);
		glVertex2f(right, top);
		glVertex2f(right, bottom);
		glVertex2f(left, bottom);
		glEnd();
	}
	/**
	 * Checks if the mouse is on this button
	 * @param x X-coordinate in world coordinates
	 * @param y	Y-coordinate in world coordinates
	 * @return true if the mouse is on this button
	 */
	public boolean isButton(int x , int y){
		return (x>left && x<right && y<top && y>bottom);
	}
	/**
	 * Update the world coordinates of this button
	 */
	public void update(){
		left = bar_right - (1- x_top_left)*bar_width;
		right = left + button_size;
		top = bar_top - (y_top_left)*button_size;
		bottom = top-button_size;		
	}
	/**
	 * Menu bar location in world coordinates
	 * @param inbar_left
	 * @param inbar_right
	 * @param inbar_top
	 * @param inbar_bottom
	 */
	public static void setStatics(float inbar_left, float inbar_right, float inbar_top, float inbar_bottom){
		bar_left = inbar_left;
		bar_right = inbar_right;
		bar_top = inbar_top;
		bar_bottom = inbar_bottom;
		bar_width = (bar_right- bar_left);
		button_size = bar_width*0.4f;
	}	
	
	/*
	 * Getters
	 */
	public float getLeft(){ return left;}
	public float getRight(){ return right;}
	public float getTop(){ return top;}
	public float getBottom(){return bottom;}
	public int getID(){ return this.ID;}
	public static float getbarBottom(){return bar_bottom;}
	public static float getbarTop(){return bar_top;}
	public static float getbarLeft(){return bar_left;}
	public static float getbarRight(){return bar_right;}
}
