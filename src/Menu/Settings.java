package Menu;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;


public class Settings extends ButtonList {
	
	private Text titel;
	
	/** Deze klasse definieert het Settings menu. 
	 * 
	 * @author Karin
	 *
	 */
	public Settings(){
		super();
		titel = new Text(30, "Settings");
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		titel.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2*buttonheight, Textures.start, Textures.startover,1, "Main Menu"));
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 3*buttonheight, Textures.start, Textures.startover,2, "Undefined"));
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
	
	public void display(){
		super.display();
		titel.draw(Menu.getScreenx()/2, Menu.getScreeny() - titel.getHeight(), 1);
	}
}
