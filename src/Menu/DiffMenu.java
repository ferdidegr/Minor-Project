package Menu;

import org.newdawn.slick.Color;

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
		selectedColor();
	}
	
	public void selectedColor(){
		for(MenuButton mb: lijst){
			mb.setNormalTextColor(Color.white);
			mb.setMouseoverTextColor(Color.black);
		}
		switch (Menu.difficulty) {
			case EASY:
				lijst.get(0).setTextColor(Color.green);
			break;
			case MEDIUM:
				lijst.get(1).setTextColor(Color.green);
			break;
			case HARD:
				lijst.get(2).setTextColor(Color.green);
			break;
			case INSANE:
				lijst.get(3).setTextColor(Color.green);
			break;
		
		}
	}
}
