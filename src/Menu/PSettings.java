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
		
		lijst.add(new MenuButton(2* y, Textures.start, Textures.startover,1, "Back"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.PAUSE);
			break;
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		Menu.bebas.draw(Menu.getScreenx()/2, 0, 30, "IN GAME SETTINGS");

	}
}
