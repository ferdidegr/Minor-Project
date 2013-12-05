package Menu;

import org.newdawn.slick.opengl.TextureImpl;

import Utils.Text;
import Game.StatusBars;

public class PSettings extends ButtonList {

	
	public PSettings(){
		super();
		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		
		lijst.add(new MenuButton(Menu.getScreenx()/2, 2* buttonheight, Textures.start, Textures.startover,1, "Back"));
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
		TextureImpl.unbind();
		Text.draw(Menu.getScreenx()/2, 0, 30, "IN GAME SETTINGS");

	}
}
