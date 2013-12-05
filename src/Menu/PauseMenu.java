package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import Game.Mazerunner;
import Game.Sound;

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
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 4* buttonheight, Textures.start, Textures.startover,3, "Main Menu"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			if (Mazerunner.getSound()==true){
			Sound.resume();
			}
			Menu.setState(GameState.GAME);
			
			
			break;
			
		case 2:
			Menu.setState(GameState.PSETTINGS);
			break;
		case 3:
			Menu.setState(GameState.TOMAIN);
			
			break;
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		welkom.draw(Menu.getScreenx()/2, Menu.getScreeny() - welkom.getHeight(), 1);
		if (Mazerunner.getSound()==true){
		Sound.pause();
		}
	}
}
