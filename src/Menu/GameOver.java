package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import Utils.Text;
import Game.Mazerunner;
import Game.Sound;

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
		
		lijst.add(new MenuButton(x, 2*y, Textures.start, Textures.startover,1, "Restart"));
		lijst.add(new MenuButton(x, 4*y, Textures.start, Textures.startover,2, "Main Menu"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.GAME);
//			Sound.playMusic("background_game");
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				Menu.game.start("GOED.maze");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		Text.draw(Menu.getScreenx()/2, 0, 30, "GAME OVER");

	}
}
