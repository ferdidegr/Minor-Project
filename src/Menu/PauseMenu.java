package Menu;


import org.lwjgl.opengl.Display;



import static org.lwjgl.opengl.GL11.*;
import Utils.Text;
import Game.Mazerunner;

public class PauseMenu extends ButtonList{	
	
	
	public PauseMenu(){
		super();		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		
		lijst.add(new MenuButton(2* y, Textures.start, Textures.startover,1, "Resume game", MenuButton.Alignment.CENTER));
		lijst.add(new MenuButton(4* y, Textures.start, Textures.startover,2, "Settings", MenuButton.Alignment.CENTER));
		lijst.add(new MenuButton(6* y, Textures.start, Textures.startover,3, "Main Menu", MenuButton.Alignment.CENTER));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			
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
		double width = Menu.mainfont.getWidth(30*Display.getHeight()/768f, "PAUSE");
		Menu.mainfont.draw((float) ((Display.getWidth()-width)/2), 0, 30*Display.getHeight()/768f, "PAUSE");
	
	}
	

}
