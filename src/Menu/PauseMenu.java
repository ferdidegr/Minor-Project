package Menu;


import Utils.Sound;
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
		
		lijst.add(new MenuButton(2* y, Textures.start, Textures.startover,1, "Resume game"));
		lijst.add(new MenuButton(4* y, Textures.start, Textures.startover,2, "Settings"));
		lijst.add(new MenuButton(6* y, Textures.start, Textures.startover,3, "Main Menu"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Sound.resume();
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
		Menu.bebas.draw(Menu.getScreenx()/2, 0, 30, "PAUSE");
		Sound.pause();
	}
	

}
