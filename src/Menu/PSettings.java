package Menu;


import Utils.Text;
import Game.StatusBars;

public class PSettings extends ButtonList {

	
	public PSettings(){
		super();
		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		int counter = 1;
		
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Sensitivity", MenuButton.Alignment.CENTER));		counter++;

		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover, counter,"Back", MenuButton.Alignment.CENTER));			counter++;
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int buttonID){
		switch(buttonID){
		case 1:
			Menu.setState(GameState.MOUSE);
			break;
		case 2:
			Menu.setState(GameState.PAUSE);
			break;
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		Menu.mainfont.draw(Menu.getScreenx()/2, 0, 30, "IN GAME SETTINGS");

	}
}
