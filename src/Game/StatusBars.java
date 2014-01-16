package Game;

import static org.lwjgl.opengl.GL11.*;

import java.text.DecimalFormat;

import org.lwjgl.opengl.Display;

import Menu.Menu;
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
	public void draw(int timeleft){
		
		double barwidth = 150* Display.getWidth()/1024f;		// Bar width in pixels
		int borderwidth = 2;									// Border width in pixels
		double dwidth = Display.getWidth();						// get current display width
		int plhealth = player.getHealth().getHealth();
		double healthwidth = (barwidth - 2* borderwidth)*plhealth/player.getHealth().getmaxHealth();
		
		double runperc = (double)player.getRunMod()/player.runcountermax;
		double runwidth = Math.max((barwidth - 2* borderwidth)*runperc,0);
		
		float fontSize = 15 * Display.getWidth() / 1024f;
		double height = Menu.mainfont.getHeight(fontSize);
		double width = Menu.mainfont.getWidth(fontSize, "WWWWWWWWWWWWWWWWWWWWWWW");
		double healthtextwidth = Menu.mainfont.getWidth(fontSize, plhealth+"/100");
		double runtextwidth = Menu.mainfont.getWidth(fontSize, (int)(Math.max(runperc*100,0))+"%");
		int second = timeleft/1000 % 60;
		int minute = timeleft/1000 / 60;
		
		
		glPushMatrix();
		glLoadIdentity();
		
		Menu.mainfont.draw((float)(dwidth - Menu.mainfont.getWidth(fontSize, "10:00"))/2, (float)(height), fontSize, "Time:  " + minute + ":" + df.format(second));
		Menu.mainfont.draw((float) (dwidth*0.8+barwidth-healthtextwidth), (float)(height), fontSize, plhealth +"/"+ player.getHealth().getmaxHealth());
		Menu.mainfont.draw((float)(dwidth - width), (float)(2*height), fontSize, "Health:");
		Menu.mainfont.draw((float) (dwidth*0.8+barwidth-runtextwidth), (float)(3*height), fontSize, (int)(Math.max(runperc*100,0))+"%");
		Menu.mainfont.draw((float)(dwidth - width), (float)(4*height), fontSize, "Fatigue:");
		Menu.mainfont.draw((float)(dwidth - width), (float)(5*height), fontSize, "Score:  " + score);
		Menu.mainfont.draw((float)(dwidth - width), (float)(6*height), fontSize, "Monsters:  " + Mazerunner.monsterlijst.size()+"/"+Mazerunner.scorpcount);
		Menu.mainfont.draw((float)(dwidth - width), (float)(7*height), fontSize, "C4:  " + Mazerunner.c4Count);
		
		/*
		 * Draw the health bar
		 */
		glColor4d(0, 0, 0, 1);
		glRectd(dwidth*0.8, 2.1*height,dwidth*0.8+barwidth, 2.9*height);
		glColor4d(0, 1, 0, 1);
		glRectd(dwidth*0.8+borderwidth, 2.1*height+borderwidth,dwidth*0.8+borderwidth+healthwidth, 2.9*height-borderwidth);
		/*
		 * Draw the run bar
		 */
		glColor4d(0, 0, 0, 1);
		glRectd(dwidth*0.8, 4.1*height,dwidth*0.8+barwidth, 4.9*height);
		glColor4d(1, 1, 0, 1);
		glRectd(dwidth*0.8+borderwidth, 4.1*height+borderwidth,dwidth*0.8+borderwidth+runwidth, 4.9*height-borderwidth);
		
		glPopMatrix();
	}
	/**
	 * method to add score (Needs a better place to implement
	 * @param sc
	 */
	public void addScore(int sc){
//		System.out.println("add score");
		score += sc;
		score = Math.max(0, score);
	}
	
	public int getScore() {return score;}
	public void setScore(int sc) {score = sc;}
}
