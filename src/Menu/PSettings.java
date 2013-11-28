package Menu;

import Game.StatusBars;

public class PSettings extends ButtonList {
private static Text welkom;
	
	public PSettings(){
		super();
		welkom = new Text(30, "In-game settings");
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		welkom.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2* buttonheight, Textures.start, Textures.startover,1, "Back"));
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
		welkom.draw(Menu.getScreenx()/2, Menu.getScreeny() - welkom.getHeight(), 1);
	}
}
