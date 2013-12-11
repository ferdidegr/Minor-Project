
package tests;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import Utils.InputField;
import Utils.Text;


public class inputTest {
	private InputField ipf;
	
	public void start() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		initGL();
		ipf = new InputField(8, 30, 50, 50);
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			ipf.display();
			
			mousepoll();
			
			Display.update();Display.sync(60);
		}
		
	}
	
	public void mousepoll(){
		if(ipf.isField(Mouse.getX(), Display.getHeight() - Mouse.getY())){
			ipf.setFocus();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
