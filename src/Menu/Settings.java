package Menu;

import org.lwjgl.opengl.Display;

import Utils.Text;


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
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		int counter =1;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Difficulty", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Mouse Sensitivity", MenuButton.Alignment.CENTER));	counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Main Menu", MenuButton.Alignment.CENTER));		counter++;

	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setState(GameState.DIFFICULTY);
		
			break;
		case 2:
			Menu.setState(GameState.MOUSE);
			break;
			
		case 3:
			Menu.setState(GameState.MAIN);

			break;
			

			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		double width = Menu.mainfont.getWidth(30*Display.getHeight()/768f, "SETTINGS");
		Menu.mainfont.draw((float) ((Display.getWidth()-width)/2), 0, 30*Display.getHeight()/768f, "SETTINGS");		
	}
}
