package Menu;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Utils.Text;

public class ScoreScreen {
	public static void displayScoreatGO(int score){
		boolean loop = true;
		Menu.initview();
		while(loop){
			glClear(GL_COLOR_BUFFER_BIT);
			
			if(score>=0){
				float width = (float) Menu.bebas.getWidth(50, "Success!");
				float height = (float) Menu.bebas.getHeight(50);
				System.out.println(width);
				Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, 50, "Success!");
			}
			else if (score== -200){
				float width = (float) Menu.bebas.getWidth(50, "Failure!");
				float height = (float) Menu.bebas.getHeight(50);
				Menu.bebas.draw((Display.getWidth()-width)/2f,(Display.getHeight()-height)/2f, 50, "Failure!");
			}
			while(Keyboard.next()){
				if(Keyboard.getEventKey()==Keyboard.KEY_RETURN)loop=false;
			}
			if(Display.isCloseRequested()){}
			Display.update();
			if(Display.wasResized()){
				Menu.reshape();
				glMatrixMode(GL_PROJECTION);					// We'll use orthogonal projection.
				glLoadIdentity();									// REset the current matrix.
				glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
				glMatrixMode(GL_MODELVIEW);	}
			Display.sync(60);
			
		}
		
	}
	
}
