package Menu_example;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Menu {
	private ArrayList<MenuButton> knoppen = new ArrayList<MenuButton>();
	private int top, bottom, scrollspeed = 20;
	
	/**
	 * ************************************
	 * Main loop
	 * @throws LWJGLException
	 * ************************************
	 */
	public void start() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		bottom = 0;
		top = Display.getHeight();
		initButtons();initGL();
		
		// Main loop, while the X button is not clickeds
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			
			mousepoll();
			display();
			Display.update();
			Display.sync(60);
		}
		
	}
	/**
	 * ************************************
	 * poll Mouse input using events
	 * ************************************
	 */
	public void mousepoll(){
		
		int wheeldx = Mouse.getDWheel(), buttonID = 0;
		if(wheeldx>0){top+=scrollspeed;bottom+=scrollspeed;initview();}
		if(wheeldx<0){top-=scrollspeed;bottom-=scrollspeed;initview();}
		MenuButton.setMouse(Mouse.getX(), Mouse.getY()+bottom);
		while(Mouse.next()){
			/*
			 * Button pressed
			 */
			if(Mouse.getEventButtonState()){
				if(Mouse.getEventButton()==0){
					for(MenuButton knop:knoppen){
						if(knop.isButton(Mouse.getX(), Mouse.getY()+bottom))
						{buttonID = knop.getID();System.out.println(buttonID);break;}
					}
				}
			}
			/*
			 * Button released
			 */
			if(!Mouse.getEventButtonState()){
				
			}
		}
	}
	/**
	 * ************************************
	 * define buttons
	 * ************************************
	 */
	public void initButtons(){
		int buttonwidth = Display.getWidth()/3;
		int buttonheight = Display.getHeight()/7;
		MenuButton.setDimensions(buttonwidth, buttonheight);
		knoppen.add(new MenuButton(buttonwidth, 2*buttonheight, Textures.start, Textures.startover,1));
		knoppen.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2));
	}
	/**
	 * ************************************
	 * Display all objects	
	 * ************************************
	 */
	public void display(){
		drawBackground();		
		
		for(MenuButton knop: knoppen){
			knop.draw();
		}
	}
	/**
	 * ************************************
	 * Draw background, background texture can be mapped
	 * ************************************
	 */
	public void drawBackground(){
		glColor3f(1.0f, 1.0f, 1.0f);						// When using textures, set this to white
		glPushMatrix();
		
		glMatrixMode(GL_PROJECTION);						// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		glMatrixMode(GL_MODELVIEW);	
		
		glEnable(GL_TEXTURE_2D);
		Textures.menubackground.bind();
		glBegin(GL_QUADS);
		glTexCoord2d(0, 1);
		glVertex2i(0, 1);
		glTexCoord2d(0, 0);
		glVertex2i(0, Display.getHeight());
		glTexCoord2d(1, 0);
		glVertex2i(Display.getWidth(), Display.getHeight());
		glTexCoord2d(1, 1);
		glVertex2i(Display.getWidth(), 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		initview();

	}
	/**
	 * ************************************
	 * Initialize openGL
	 * ************************************
	 */
	public void initGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		initview();

	}
	/**
	 * ************************************
	 * Initialize your view window
	 * ************************************
	 */
	public void initview(){
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(0, Display.getWidth(), bottom, top, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
	}
	/**
	 * ************************************
	 * Start the menu
	 * @param args
	 * ************************************
	 */
	public static void main(String[] args){
		Menu menu = new Menu();
		try {
			menu.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
