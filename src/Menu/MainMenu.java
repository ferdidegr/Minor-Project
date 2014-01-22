package Menu;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
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
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover, counter,"Credits", MenuButton.Alignment.CENTER));			counter++;
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
			displayCredits();
			break;
		case 7:
			Menu.setState(GameState.EXIT);
			break;
			
		default: break;
		}
	
	}
	
	public void display(){
		super.display();
	}
	
	public void displayCredits(){
		boolean loop = true;
		int fontsize = (int) (40 * Display.getHeight()/768f);
		String[] tekst = {"Credits", "Miranda van Doorn", "Karin van Garderen", "Ferdi de Graaff","Zhi-Li Liu"};
		int[] width = new int[5];
		int height = (int) Menu.mainfont.getHeight(fontsize);
		for(int i = 0; i < tekst.length; ++i){
			width[i] = (int) Menu.mainfont.getWidth(fontsize, tekst[i]);
		}
		glPushMatrix();
		glOrtho(1, 1, 1, 1, 1, -1);
		while(loop){
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)
			|| Keyboard.isKeyDown(Keyboard.KEY_SPACE)
			|| Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				loop = false;
			}
			glClear(GL_COLOR_BUFFER_BIT);

			for(int i = 0 ; i < tekst.length; ++i){
				Menu.mainfont.draw((Display.getWidth()-width[i])/2, height+height*i*2, fontsize, tekst[i]);
			}
			glPopMatrix();
			Display.update();
			Display.sync(60);
		}
	}
	
}
