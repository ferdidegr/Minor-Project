import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class MainMenu {
	private int width=800;
	private int height=600;
	private TrueTypeFont myfont;
	private boolean exit = false;
//	private boolean lcont = false;
//	private GameState gamestate = GameState.MENU;
	
	public void start() throws LWJGLException, IOException{
		
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.setTitle("Main menu");
		Display.create();
		init();
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT );
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			/*
			 * Mouse poll
			 */
		//	mousepoll();
			if(exit)break;
			display();
			Display.update();Display.sync(60);
		}
	}
	/**
	 * display menu items
	 */
	public void display(){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glTranslatef(200, 400, 0);
		GL11.glScalef(1, -1, 1);
		
		//CREATE BUTTONS
		float ybuttonsize = 50;
		float xbuttonsize = 200;
		MenuButton startbutton = new MenuButton(xbuttonsize, ybuttonsize, "Start");
		MenuButton leveleditor = new MenuButton(xbuttonsize, ybuttonsize, "Level Editor");
		MenuButton highscores = new MenuButton(xbuttonsize, ybuttonsize, "Highscores");
		MenuButton settings = new MenuButton(xbuttonsize, ybuttonsize, "Settings");
		MenuButton exit = new MenuButton(xbuttonsize, ybuttonsize, "exit");
		
		//DRAW BUTTONS
		
		startbutton.drawButton();
		GL11.glTranslatef(0, ybuttonsize, 0);
		leveleditor.drawButton();
		GL11.glTranslatef(0, ybuttonsize, 0);
		highscores.drawButton();
		GL11.glTranslatef(0, ybuttonsize, 0);
		settings.drawButton();
		GL11.glTranslatef(0, ybuttonsize, 0);
		exit.drawButton();

		//FINISH
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
	}
	/**
	 * Initialize program
	 */
	public void init(){
		
		GL11.glViewport(0, 0, width, height);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT );
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);       
		
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0,height, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// load font from file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("Snowtop Caps.ttf");
 
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(40f); // set font size
			myfont = new TrueTypeFont(awtFont2, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Checks if the user used his mouse
	 * @throws LWJGLException
	 * @throws IOException 
	 */
	/*public void mousepoll() throws LWJGLException, IOException{
		int x = Mouse.getX();
		int y = Mouse.getY();
		int lineheight = myfont.getLineHeight();
		
		switch(gamestate){
			case MENU:{	
				if(x>200 && x<400 && y<400 && y>(400-lineheight)){
					wirebox(400);
					
					if(Mouse.isButtonDown(0)){
						Game spel = new Game();
						Display.destroy();
						int score =spel.start();
						Display.setDisplayMode(new DisplayMode(width, height));
						Display.create();
						init();
						if(score<highscores.get(4).getScore()){
							
							String result = "";		
							while(result.length()!=3){
								result = JOptionPane.showInputDialog("Highscore! Enter your name (3 chars)");
								if(result==null){
									result= new String("");
								}
							}
							
							highscores.add(new HighScore(result, score));
							Collections.sort(highscores);
							try{
								FileOutputStream fos = new FileOutputStream("highscores.high");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								ArrayList<HighScore> tempoutput = new ArrayList<HighScore>();
								for(int i = 0 ; i < 5 ; i ++){
									tempoutput.add(highscores.get(i));
								}
								oos.writeObject(tempoutput);
								oos.close();
							}catch(FileNotFoundException e){
								
							} catch (IOException e) {
								
							}
						}
						highscore();
					}
				}
				/*
				 * Highscore
				 */
				/*if(x>200 && x<600 && y<(400-lineheight) && y>(400-2*lineheight)){
					wirebox(400-myfont.getLineHeight());
					
					if(Mouse.isButtonDown(0)){
						highscore();
					}
				}
				
					/*
					 * help
					 */
					/*if(x>200 && x<600 && y<(400-2*lineheight) && y>(400-3*lineheight)){
						wirebox(400-2*myfont.getLineHeight());
						
						if(Mouse.isButtonDown(0)){
							help();
						}
					}
				
					/*
					 * exit
					 */
					/*if(x>200 && x<600 && y<(400-3*lineheight) && y>(400-4*lineheight)){
						wirebox(400-3*myfont.getLineHeight());
						
						if(Mouse.isButtonDown(0)){
							exit = true;
							
						}
					}
					break;
				}
			case HELP:{
				if(x>600 && x<800 && y<(myfont.getLineHeight()*0.8f+30) && y>30){
					
					GL11.glLineWidth(5);
					GL11.glColor3f(0f, 0f, 0.5f);
					GL11.glBegin(GL11.GL_LINE_LOOP);
					GL11.glVertex2i(610, 80);
					GL11.glVertex2i(780, 80);
					GL11.glVertex2i(780, 30);
					GL11.glVertex2i(610, 30);
					GL11.glEnd();
					
					if(Mouse.isButtonDown(0)){
						lcont=true;
					}
					
				break;
			}
			}
			case HIGHSCORE:{
					if(x>600 && x<800 && y<(myfont.getLineHeight()*0.8f+30) && y>30){
					
					GL11.glLineWidth(5);
					GL11.glColor3f(0f, 0f, 0.5f);
					GL11.glBegin(GL11.GL_LINE_LOOP);
					GL11.glVertex2i(610, 80);
					GL11.glVertex2i(780, 80);
					GL11.glVertex2i(780, 30);
					GL11.glVertex2i(610, 30);
					GL11.glEnd();
					
					if(Mouse.isButtonDown(0)){
						lcont=true;
					}
					
				break;
			}
			}
		}
	}*/
	/**
	 * when the mouse is hovering above a menu item that menu item is enboxed within a wire box
	 * @param y height of the top left corner
	 */
	public void wirebox(int y){
		GL11.glLineWidth(5);
		GL11.glColor3f(0f, 0f, 0.5f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2i(150, y);
		GL11.glVertex2i(150, y-myfont.getLineHeight());
		
		GL11.glVertex2i(600, y-myfont.getLineHeight());
		GL11.glVertex2i(600, y);
		GL11.glEnd();
	}
	
	public void text(int x, int y,float scale, String s){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, Display.getHeight()-y, 0);
		GL11.glScalef(scale, -scale, scale);
		myfont.drawString(0, 0, s,Color.white);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
	}
	/**
	 * Main to start the game
	 * @param args
	 * @throws IOException 
	 * @throws LWJGLException 
	 */
	public static void main(String[] args) throws LWJGLException, IOException{
		MainMenu menu = new MainMenu();
		try {
			menu.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
