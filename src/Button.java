import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;


public class Button {
	private float left, right, top, bottom;
	private static float bar_left, bar_right, bar_top, bar_bottom;
	
	public Button(float x_top_left,float y_top_left, float size){
		this.left = x_top_left;
		this.right = this.left + size;
		this.top = y_top_left;
		this.bottom = this.top-size;
	}
	
	public float getLeft(){ return left;}
	public float getRight(){ return right;}
	public float getTop(){ return top;}
	public float getBottom(){return bottom;}
	
	public void drawButton(){
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glVertex2f(left, top);
		glVertex2f(right, top);
		glVertex2f(right, bottom);
		glVertex2f(left, bottom);
		glEnd();
	}
	
	public boolean isButton(int x , int y){
		return (x>left && x<right && y<top && y>bottom);
	}
	
	public void update(){
		
		
	}
	
	public static void setStatics(float inbar_left, float inbar_right, float inbar_top, float inbar_bottom){
		bar_left = inbar_left;
		bar_right = inbar_right;
		bar_top = inbar_top;
		bar_bottom = inbar_bottom;
	}
	
}
