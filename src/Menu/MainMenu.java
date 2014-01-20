package Menu;
import java.io.IOException;

import org.lwjgl.opengl.Display;








import Utils.Text;
import Editor.MazeMaker;
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
		int counter = 1;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Start game", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Editor", MenuButton.Alignment.CENTER));			counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Settings", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover, counter,"Highscores", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover, counter,"Help", MenuButton.Alignment.CENTER));			counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Exit", MenuButton.Alignment.CENTER));			counter++;
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.SELECTLVL);
			break;
		case 2:
			boolean fullscreen = Display.isFullscreen();
			glPushAttrib(GL_ENABLE_BIT);
			glPushMatrix();
			try{
				Display.setFullscreen(false);
				new MazeMaker().start(false);
				Display.setFullscreen(fullscreen);
			}catch(Exception e){}
			glPopMatrix();
			glPopAttrib();
			Menu.levelList = Utils.Utils.loadLevelList(Menu.progres, Menu.cheat);
			MazechooserMenu temp = (MazechooserMenu) Menu.menus.get(GameState.SELECTLVL);
			temp.resetlist();
			temp.init(0, 0);
			break;
		case 3:
			Menu.setState(GameState.SETTINGS);
			break;
		case 4:
			Menu.setState(GameState.HIGHSCORE);
			break;
		case 5:
			Menu.setState(GameState.HELP);
			break;
		case 6:
			Menu.setState(GameState.EXIT);
			break;
			
		default: break;
		}
	
	}
	
	public void display(){
		super.display();
	}
	
}
