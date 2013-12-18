package Menu;
import java.io.IOException;

import org.lwjgl.opengl.Display;



import Utils.Text;
import Game.*;
import static org.lwjgl.opengl.GL11.*;

/** Deze klasse definieert het Main menu. 
 * 
 * @author Karin
 *
 */
public class MainMenu extends ButtonList {	
		
	public MainMenu(){
		super();

	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		
		lijst.add(new MenuButton(2* y, Textures.start, Textures.startover,1, "Start game"));
		lijst.add(new MenuButton(4* y, Textures.start, Textures.startover,2, "Settings"));
		lijst.add(new MenuButton(6* y, Textures.start, Textures.startover,3, "Exit"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.SELECTLVL);
			break;
			
		case 2:
			Menu.setState(GameState.SETTINGS);
			break;
		case 3:
			Menu.setState(GameState.EXIT);
			break;
			
		default: break;
		}
	
	}
	
	public void display(){
		super.display();
		Menu.bebas.draw(Display.getWidth()*2f/5, 0, 30, "WELKOM BIJ TRAPPER");
	}
	
}
