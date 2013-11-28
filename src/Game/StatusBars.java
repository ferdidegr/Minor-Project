package Game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import Menu.Text;

public class StatusBars {
	private int health;
	private int maxhealth=100;
	private float squaresize = 1f;
	private Text titel;
	
	public StatusBars(int health) {
		this.health = health;
		titel = new Text(15, "Health");
		titel.initFont();
	}

	public void draw(){
		
		float barwidth = (maxhealth + 2) * squaresize;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		glPushMatrix();
		glLoadIdentity();
		glTranslatef(1000f, 50f,0);
		
		//titel.draw(titel.getWidth()/2, -titel.getHeight(), -1);
		for (int i=0; i<(maxhealth +2); i++){
			glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
			drawBorder();
			glTranslatef(1f,0,0);
		}
		glTranslatef(-barwidth, 10.0f, 0.0f);
		for (int i=0;i<barwidth;i++){
			if(i<= health && i>0){
				glColor4f(0.0f, 1.0f, 0.0f, 0.7f);
			}else {
				glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
			}
			drawBlock();
			glTranslatef(1f,0,0);
		}
		glTranslatef(-barwidth, 1.0f, 0.0f);
		for (int i=0; i<(maxhealth +2); i++){
			glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
			drawBorder();
			glTranslatef(1f,0,0);
		}
		
		glPopMatrix();
		

	}
	
	public void drawBlock(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(squaresize, 0.0f);
		glVertex2f(squaresize, -10.0f);
		glVertex2f(0.0f, -10.0f);
		glEnd();
	}
	public void drawBorder(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(squaresize, 0.0f);
		glVertex2f(squaresize, -1.0f);
		glVertex2f(0.0f, -1.0f);
		glEnd();
	}
	
	public void addHealth(int hp){
		health += hp;
	}
	
	public void minHealth(int hp){
		health -= hp;
	}
}
