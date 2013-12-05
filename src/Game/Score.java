package Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureImpl;

//import Menu.Text2;
import Utils.Text;

public class Score {
	private static int score;
//	private static Text2 scorenr;
	
	public static void init(int sc) {
		score = sc;
//		scorenr = new Text2(15, "Score:  " + score);
//		scorenr.initFont();
	}

	public static void draw(){		
		
		Text.draw(Display.getWidth()/2, 0, 15, "Score:  " + score);

	}
	
	public static void addScore(int sc){
		System.out.println("add score");
		score += sc;
	}

	public static int getScore() {return score;}
	public static void setScore(int sc) {score = sc;}
}
