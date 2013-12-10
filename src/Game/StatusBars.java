package Game;

import static org.lwjgl.opengl.GL11.*;

import java.text.DecimalFormat;

import org.lwjgl.opengl.Display;

import Utils.Text;

public class StatusBars {
	private static int health, score;
	private static int maxhealth = 100;
	private static float squaresize = 1f;
	
	public static void init(int hp, int sc) {
		setHealth(hp);
		setScore(sc);
	}

	public static void draw(){
		
		float barwidth = (maxhealth + 2) * squaresize;		
		
		glPushMatrix();
		glLoadIdentity();
		float fontSize = 15 * Display.getWidth() / 1024f;
		double height = Text.getHeight(fontSize);
		double width = Text.getWidth(fontSize, "Textbreedthe lalalalalalalaa");
		int second = Mazerunner.timer/1000 % 60;
		int minute = Mazerunner.timer/1000 / 60;
		DecimalFormat df = new DecimalFormat("00");
		
		Text.draw((float)(Display.getWidth() - width), (float)(height), 15, "Time:  " + minute + ":" + df.format(second));
		Text.draw((float)(Display.getWidth() - width), (float)(2*height), 15, "Health:");
		Text.draw((float)(Display.getWidth() - width), (float)(3*height), 15, "Score:  " + score);
		Text.draw((float)(Display.getWidth() - width), (float)(4*height), 15, "Monsters:  " + Mazerunner.monsterlijst.size());
		
		glTranslatef((float)(Display.getWidth()-1.5*barwidth), 50f,0);
		
		for (int i=0; i<(maxhealth +2); i++){
			glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
			drawBorder();
			glTranslatef(1f,0,0);
		}
		glTranslatef(-barwidth, 10.0f, 0.0f);
		
		for (int i=0; i<barwidth; i++){
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
	
	public static void drawBlock(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(squaresize, 0.0f);
		glVertex2f(squaresize, -10.0f);
		glVertex2f(0.0f, -10.0f);
		glEnd();
	}
	public static void drawBorder(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(squaresize, 0.0f);
		glVertex2f(squaresize, -1.0f);
		glVertex2f(0.0f, -1.0f);
		glEnd();
	}
	
	public static void addHealth(int hp){
		System.out.println("add health");
		if((health + hp) <= maxhealth){
			health += hp;
		}
		else health=maxhealth;
	}
	
	public static void minHealth(int hp){
		System.out.println("min health");
		if((health - hp) >= 0){
			health -= hp;
		} else {
			Mazerunner.isdood = true;
		}
	}
	
	public static void addScore(int sc){
		System.out.println("add score");
		score += sc;
	}
	
	public static int getHealth() {return health;}
	public static void setHealth(int hp) {health = hp;}

	public static int getScore() {return score;}
	public static void setScore(int sc) {score = sc;}
}
