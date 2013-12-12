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

		lijst.add(new MenuButton(x, 4*y, Textures.start, Textures.startover,1, "Main Menu"));
		lijst.add(new MenuButton(x, 6*y, Textures.start, Textures.startover,2, "Difficulty Easy"));
		lijst.add(new MenuButton(x, 8*y, Textures.start, Textures.startover,3, "Difficulty Medium"));
		lijst.add(new MenuButton(x, 10*y, Textures.start, Textures.startover,4, "Difficulty Hard"));
		lijst.add(new MenuButton(x, 12*y, Textures.start, Textures.startover,5, "Difficulty Insane"));
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
			
		case 2:
			Menu.setDifficulty(Difficulty.EASY);
			break;
		case 3:
			Menu.setDifficulty(Difficulty.MEDIUM);
			break;
		case 4:
			Menu.setDifficulty(Difficulty.HARD);
			break;
		case 5:
			Menu.setDifficulty(Difficulty.INSANE);
			break;
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		Text.draw(Menu.getScreenx()/2, 0, 30, "SETTINGS");
		Text.draw(Menu.getScreenx()/2, 40, 30, "CURRENT DIFFICULTY: " + Menu.getDifficulty());
	}
}
