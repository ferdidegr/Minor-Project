package Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureImpl;

import Menu.Text;

public class Score {
	private static int score;
	private static Text scorenr;
	
	public static void init(int sc) {
		score = sc;
		scorenr = new Text(15, "Score:  " + score);
		scorenr.initFont();
	}

	public static void draw(){
		
		glColor3f(1.0f, 0.0f, 0.0f);
		glPushMatrix();
		glLoadIdentity();
		
		glTranslatef(1000f, 50f,0);
		
		glPushMatrix();
		glScalef(-1, 1, 1);
		glEnable(GL_TEXTURE_2D);
		TextureImpl.unbind();
		scorenr.draw(Display.getWidth()/2, -scorenr.getHeight()/2, -1);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		
		glPopMatrix();
	}
	
	public static void addScore(int sc){
		System.out.println("add score");
		score += sc;
	}

	public static int getScore() {return score;}
	public static void setScore(int sc) {score = sc;}
}
