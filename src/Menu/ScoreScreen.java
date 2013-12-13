package Menu;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

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
				float width = (float) Text.getWidth(50, "Success!");
				System.out.println(width);
				Text.draw((Display.getWidth()+width)/2f, Display.getHeight()/2f, 50, "Success!");
			}
			else if (score== -200){
				Text.draw(Display.getWidth()/2f, Display.getHeight()/2f, 50, "Failure!");
			}
			while(Keyboard.next()){
				if(Keyboard.getEventKey()==Keyboard.KEY_RETURN)loop=false;
			}
			if(Display.isCloseRequested()){}
			Display.update();
			Display.sync(60);
		}
	}
}
