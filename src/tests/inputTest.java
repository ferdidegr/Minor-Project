
package tests;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import Utils.InputField;
import Utils.Text;


public class inputTest {
	private InputField ipf, ipf2;
	
	public void start() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(1280, 1024));
		Display.create();
		initGL();
		Text font = new Text("BEBAS.TTF");
		ipf = new InputField(12, 30, Display.getWidth()/4, Display.getHeight()/2,font);
		ipf2 = new InputField(12, 30, Display.getWidth()/4, 10,font);
		ipf.setX((int) ((Display.getWidth()-ipf.getWidth())/2));
		ipf.setY((int) ((Display.getHeight()-ipf.getHeight())/2));
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			glRectd(0, 0, 50, 50);
			ipf.poll();
			ipf2.poll();
						
			while(Keyboard.next()){
				if(Keyboard.getEventKey()==Keyboard.KEY_F1)System.out.println(ipf.getString());
			}
			
			Display.update();Display.sync(60);
		}
		
	}
	

	
	public void initGL(){
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(),  Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0, 0, 0, 1);
	}
	
	public static void main(String[] args){
		try {
			new inputTest().start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}
