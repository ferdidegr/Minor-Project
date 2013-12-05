package Menu;

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
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){

		lijst.add(new MenuButton(Menu.getScreenx()/3, 2*buttonheight, Textures.start, Textures.startover,1, "Main Menu"));
		lijst.add(new MenuButton(Menu.getScreenx()/3, 4*buttonheight, Textures.start, Textures.startover,2, "Undefined"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setState(GameState.MAIN);
			break;
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		Text.draw(Menu.getScreenx()/2, 0, 30, "SETTINGS");
	}
}
