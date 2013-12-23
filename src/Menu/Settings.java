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
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		lijst.add(new MenuButton(2*y, Textures.start, Textures.startover,1, "Difficulty", MenuButton.Alignment.CENTER));
		lijst.add(new MenuButton(4*y, Textures.start, Textures.startover,2, "Main Menu", MenuButton.Alignment.CENTER));

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
			Menu.setState(GameState.MAIN);

			break;
			

			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		Menu.bebas.draw(Menu.getScreenx()/2, 0, 30, "SETTINGS");		
	}
}
