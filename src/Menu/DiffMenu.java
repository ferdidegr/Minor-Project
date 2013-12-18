package Menu;

public class DiffMenu extends ButtonList{

	@Override
	public void init(int x, int y) {
		lijst.add(new MenuButton(2*y, Textures.start, Textures.startover,1, "Difficulty Easy"));
		lijst.add(new MenuButton(4*y, Textures.start, Textures.startover,2, "Difficulty Medium"));
		lijst.add(new MenuButton(6*y, Textures.start, Textures.startover,3, "Difficulty Hard"));
		lijst.add(new MenuButton(8*y, Textures.start, Textures.startover,4, "Difficulty Insane"));
		lijst.add(new MenuButton(12*y, Textures.start, Textures.startover,5, "Back"));
		
	}
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setDifficulty(Difficulty.EASY);
			break;
		case 2:
			Menu.setDifficulty(Difficulty.MEDIUM);
			break;
		case 3:
			Menu.setDifficulty(Difficulty.HARD);
			break;
		case 4:
			Menu.setDifficulty(Difficulty.INSANE);
			break;
		case 5:
			Menu.setState(GameState.SETTINGS);

			break;			

			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		String output = "CURRENT DIFFICULTY: " + Menu.getDifficulty();
		double stringwidth = Menu.bebas.getWidth(30, output);
		Menu.bebas.draw((int)(Menu.getScreenx()-stringwidth)/2, 0, 30, output );
	}
}
