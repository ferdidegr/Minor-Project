package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import Utils.Sound;
import Utils.Text;
import Game.Mazerunner;

public class GameOver extends ButtonList {
	
	
	public GameOver(){
		super();		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		
		lijst.add(new MenuButton(2*y, Textures.start, Textures.startover,1, "Restart", MenuButton.Alignment.CENTER));
		lijst.add(new MenuButton(4*y, Textures.start, Textures.startover,2, "Main Menu", MenuButton.Alignment.CENTER));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
		
			Menu.setState(GameState.GAME);
//			Sound.playMusic("background_game");
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				Menu.game.start(Menu.levelList.get(Menu.currentlevel));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Menu.game=null;
			glPopMatrix();
			buttonID=0;

			break;
			
		case 2:
			Menu.setState(GameState.MAIN);
			Menu.game=null;
			default: break;
		}
	}
	
	public void display(){
		super.display();		
		Menu.mainfont.draw(Menu.getScreenx()/2, 0, 30, "GAME OVER");

	}
}
