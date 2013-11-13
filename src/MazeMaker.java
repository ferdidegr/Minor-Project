import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;



public class MazeMaker {
	private int width;
	private int height;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int menubarwidth;
	private Texture texmenubar;
	private ArrayList<Button> buttonlist = new ArrayList<Button>();
	private boolean mousedown = false;
	
	public void start() throws LWJGLException, InterruptedException, IOException{
		/*
		 * Select resolution
		 */
		Chooser keuze = new Chooser();
		while(keuze.getDisplay()==null){
			Thread.sleep(500);
		}
		/*
		 * Create Display
		 */
		Display.create();
		/*
		 * Initialize screen parameters
		 */
		width = Display.getWidth();
		height = Display.getHeight();
		left = 0;
		right = width;
		top = height;
		bottom = 0;
		menubarwidth = width/6;
		/*
		 * Initialize openGL
		 */
		initGL();
		/*
		 * Initialize Buttons
		 */
		initButtons();
		/*
		 * Load Textures
		 */
		texmenubar = IO.readtexture("res/Wooden_floor_original.jpg");
		/*
		 * Main loop
		 */
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			Mousepoll();
			keyboardpoll();
			display();
			drawMenu();
			Display.update();Display.sync(60);
			
		}
	}
	
	public void display(){	
		
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glVertex2f(0, 0);
		glVertex2f(100, 0);
		glVertex2f(100, 100);
		glVertex2f(0f, 100f);
		glEnd();
		
		
	}
		
	public void drawMenu(){
		
		/*
		 * Menu balk
		 */
		glEnable(GL_TEXTURE_2D);
		texmenubar.bind();
		glBegin(GL_QUADS);
		
		glVertex2f(right-menubarwidth, bottom);
		glTexCoord2f(0, 0);
		glVertex2f(right-menubarwidth, top);
		glTexCoord2f(0, 1);
		glVertex2f(right, top);
		glTexCoord2f(1, 0);
		glVertex2f(right, bottom);
		glTexCoord2f(1, 1);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		/*
		 * Buttons
		 */
		for(Button knop:buttonlist){
			knop.drawButton();
		}
	}
	
	public void keyboardpoll(){
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			left+=10;
			right+=10;
			initGL();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			left-=10;
			right-=10;
			initGL();
		}
		
	}
	
	public void Mousepoll(){
		int x = Mouse.getX();
		int y = Mouse.getY();
		Button temp = buttonlist.get(0);
		if(temp.isButton(x, y)){
			if(Mouse.isButtonDown(0) && !mousedown){
				String test = JOptionPane.showInputDialog("Size");
				System.out.println(test);
				mousedown = true;
			}
		}
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)){mousedown = false;}
	}
	
	public void initButtons(){
		
		float buttonsize = menubarwidth*0.4f;
		
		buttonlist.add(new Button(right-0.95f*menubarwidth, top-0.1f*buttonsize, buttonsize));
	}
	
	public void initGL(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		// Now we set up our viewpoint.
		glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
		glLoadIdentity();									// REset the current matrix.
		glOrtho(left, right, bottom, top, 1, -1);
		glMatrixMode(GL_MODELVIEW);	
		
	  
	}
	public static void main(String[] args){
		MazeMaker maker = new MazeMaker();
		try {
			maker.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
