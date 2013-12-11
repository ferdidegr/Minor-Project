package Game;

import static org.lwjgl.opengl.GL11.*;

import java.text.DecimalFormat;

import org.lwjgl.opengl.Display;

import Utils.Text;

public class StatusBars {
	private int score;	
	private Player player;
	private DecimalFormat df = new DecimalFormat("00");
	public void init(int sc, Player player) {
		setScore(sc);
		setPlayer(player);
	}
	/**
	 * Give the status bar access to the player object, this is needed to retrieve the health of the player
	 * @param player
	 */
	private void setPlayer(Player player) {
		this.player = player;		
	}
	/**
	 * Draw the health bar and all other overlay
	 */
	public void draw(){
		
		double barwidth = 150* Display.getWidth()/1024f;		// Bar width in pixels
		int borderwidth = 2;									// Border width in pixels
		double dwidth = Display.getWidth();						// get current display width
		int plhealth = player.getHealth().getHealth();
		double healthwidth = (barwidth - 2* borderwidth)*plhealth/player.getHealth().getmaxHealth();
		float fontSize = 15 * Display.getWidth() / 1024f;
		double height = Text.getHeight(fontSize);
		double width = Text.getWidth(fontSize, "WWWWWWWWWWWWWWWWWWWWWWW");
		double healthtextwidth = Text.getWidth(fontSize, plhealth+"/100");
		int second = Mazerunner.timer/1000 % 60;
		int minute = Mazerunner.timer/1000 / 60;
		
		
		glPushMatrix();
		glLoadIdentity();
		
		Text.draw((float)(dwidth - Text.getWidth(fontSize, "10:00"))/2, (float)(height), fontSize, "Time:  " + minute + ":" + df.format(second));
		Text.draw((float) (dwidth*0.8+barwidth-healthtextwidth), (float)(height), fontSize, plhealth +"/"+ player.getHealth().getmaxHealth());
		Text.draw((float)(dwidth - width), (float)(2*height), fontSize, "Health:");
		Text.draw((float)(dwidth - width), (float)(3*height), fontSize, "Score:  " + score);
		Text.draw((float)(dwidth - width), (float)(4*height), fontSize, "Monsters:  " + Mazerunner.monsterlijst.size());
		
		glColor4d(0, 0, 0, 1);
		glRectd(dwidth*0.8, 2.1*height,dwidth*0.8+barwidth, 2.9*height);
		glColor4d(0, 1, 0, 1);
		glRectd(dwidth*0.8+borderwidth, 2.1*height+borderwidth,dwidth*0.8+borderwidth+healthwidth, 2.9*height-borderwidth);
		
		glPopMatrix();
	}
	/**
	 * method to add score (Needs a better place to implement
	 * @param sc
	 */
	public void addScore(int sc){
		System.out.println("add score");
		score += sc;
	}
	
	public int getScore() {return score;}
	public void setScore(int sc) {score = sc;}
}
