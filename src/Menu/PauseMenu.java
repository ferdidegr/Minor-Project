package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import Game.Mazerunner;

public class PauseMenu{
	
	private static Text welkom = new Text(30, "Welkom bij Trapper");
	private static ButtonList lijst;
	
	public PauseMenu(){
		lijst = new ButtonList();
		welkom = new Text(30, "Spel gepauzeerd");
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public static void init(int buttonwidth, int buttonheight){
		welkom.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2* buttonheight, Textures.start, Textures.startover,1, "Resume Game"));
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 3* buttonheight, Textures.start, Textures.startover,2, "Main Menu"));
	}
	
	public static int checkButtons(int bottom) {
		return lijst.checkButtons(bottom);
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.GAME);
			break;
			
		case 2:
			Menu.setState(GameState.MAIN);
			break;
			
			default: break;
		}
	}
	
	public static void display(){
		lijst.display();
		welkom.draw(Menu.getScreenx()/2, Menu.getScreeny() - welkom.getHeight(), 1);
	}
}
