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

public class StatusBars {
	private int health;
	private int maxhealth=100;
	
	public StatusBars(int maxhealth) {
		this.health = maxhealth;
	}

	public void draw(Player player, int SQUARE_SIZE){
		
		glColor3f(1.0f, 0.0f, 0.0f);
		glPushMatrix();
		glLoadIdentity();
		for (int i=0;i<maxhealth;i++){
			if(i<= health){
				glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
			}else {
				glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
			}
			drawBlock();
			glTranslatef(1f,0,0);
		}
		
		glPopMatrix();
		

	}
	
	public void drawBlock(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(1.0f, 0.0f);
		glVertex2f(1.0f, 10.0f);
		glVertex2f(0.0f, 10.0f);
		glEnd();
	}
}
