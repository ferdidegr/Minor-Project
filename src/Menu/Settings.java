package Menu;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;


public class Settings extends ButtonList {
	
	/** Deze klasse definieert het Settings menu. 
	 * 
	 * @author Karin
	 *
	 */
	public Settings(){
		super();
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		lijst.add(new MenuButton(buttonwidth, 2*buttonheight, Textures.start, Textures.startover,1, "Main Menu"));
		lijst.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2, "Undefined"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setState(GameState.MAIN);
			break;
			
			default: break;
		}
	}
	
}
