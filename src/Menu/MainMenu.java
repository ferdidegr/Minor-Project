package Menu;
import java.io.IOException;

import org.lwjgl.opengl.Display;

import Game.*;
import static org.lwjgl.opengl.GL11.*;

/** Deze klasse definieert het Main menu. 
 * 
 * @author Karin
 *
 */
public class MainMenu extends ButtonList {
	
	private Text welkom;
	
	public MainMenu(){
		super();
		welkom = new Text(30, "Welkom bij Trapper");
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		welkom.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2* buttonheight, Textures.start, Textures.startover,1, "Start game"));
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 3* buttonheight, Textures.start, Textures.startover,2, "Settings"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.GAMEOVER);
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
	
	public void display(){
		super.display();
		welkom.draw(Menu.getScreenx()/2, Menu.getScreeny() - welkom.getHeight(), 1);
	}
	
}
