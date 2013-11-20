package Menu;
import java.io.IOException;

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
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		lijst.add(new MenuButton(buttonwidth, 3*buttonheight, Textures.start, Textures.startover,1, "Start game"));
		lijst.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2, "Settings"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Mazerunner game = new Mazerunner();
			glPushMatrix();
			try {
				game.start();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glPopMatrix();
			buttonID=0;
			break;
			
		case 2:
			Menu.setState(GameState.SETTINGS);
			
			default: break;
		}
	}
	
}
