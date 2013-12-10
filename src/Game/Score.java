package Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureImpl;

import Utils.Text;

public class Score {
	private static int score;

	public static void init(int sc) {
		score = sc;
	}

	public static void draw(){		
		Text.draw(Display.getWidth()/2, 0, 15, "Score:  " + score);
		Text.draw(Display.getWidth()/2, 20, 15, "Monsters:  " + Mazerunner.monsterlijst.size());
	}
	
	public static void addScore(int sc){
		System.out.println("add score");
		score += sc;
	}

	public static int getScore() {return score;}
	public static void setScore(int sc) {score = sc;}
}
