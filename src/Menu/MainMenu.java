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
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		
		lijst.add(new MenuButton(Menu.getScreenx()/3, 2* buttonheight, Textures.start, Textures.startover,1, "Start game"));
		lijst.add(new MenuButton(Menu.getScreenx()/3, 4* buttonheight, Textures.start, Textures.startover,2, "Settings"));
		lijst.add(new MenuButton(Menu.getScreenx()/3, 6* buttonheight, Textures.start, Textures.startover,3, "Exit"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.GAME);
			if (Mazerunner.getSound()==true){
			Sound.playMusic("background_game");
			}
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				Menu.game.start();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glPopMatrix();
			buttonID=0;
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
		Text.draw(Display.getWidth()*2f/5, 0, 30, "WELKOM BIJ TRAPPER");
	}
	
}
