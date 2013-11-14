import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class MenuTest {
	private TrueTypeFont myfont;
	
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		}
		catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init OpenGL here
		Display.setTitle("Main menu");
		
		while (!Display.isCloseRequested()) {
			
			// render OpenGL here
			display();
			Display.update();
		}
		Display.destroy();
	}
	public void display() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glTranslatef(200, 400, 0);
		GL11.glScalef(1, -1, 1);
		myfont.drawString(0, 0, "Start");
		myfont.drawString(0, myfont.getLineHeight(), "Highscores",Color.white);
		myfont.drawString(0, 2*myfont.getLineHeight(), "Help",Color.white);
		myfont.drawString(0, 3*myfont.getLineHeight(), "Exit",Color.white);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);

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

	public static void main(String[] argv) {
		MenuTest displayExample = new MenuTest();
		displayExample.start();
	}
}
