package Menu;
import Game.*;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends ButtonList {
	
	public MainMenu(){
		super();
	}
	
	public void init(int buttonwidth, int buttonheight){
		System.out.println("Init bereikt");
		lijst.add(new MenuButton(buttonwidth, 3*buttonheight, Textures.start, Textures.startover,1));
		lijst.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2));
	}
	
	public static void actionButtons(int buttonID){
		switch(buttonID){
		case 1:
			System.out.println("Nu moet hij starten.");
			Mazerunner game = new Mazerunner();
			glPushMatrix();
			game.start();
			glPopMatrix();
			buttonID=0;
			break;
			
		case 2:
			Menu.setState(GameState.SETTINGS);
			
			default: break;
		}
	}
	
}
