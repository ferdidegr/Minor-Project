package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import Game.Mazerunner;

public class PauseMenu extends ButtonList{
	
	private static Text welkom;
	
	public PauseMenu(){
		super();
		welkom = new Text(30, "Pauze");
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		welkom.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2* buttonheight, Textures.start, Textures.startover,1, "Resume game"));
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 3* buttonheight, Textures.start, Textures.startover,2, "Settings"));
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
			Menu.setState(GameState.SETTINGS);
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		welkom.draw(Menu.getScreenx()/2, Menu.getScreeny() - welkom.getHeight(), 1);
	}
}
