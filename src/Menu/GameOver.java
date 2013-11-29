package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import Game.Mazerunner;
import Game.Sound;

public class GameOver extends ButtonList {
	
	private Text titel;
	
	public GameOver(){
		super();
		titel = new Text(30, "Game Over");
		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param buttonwidth
	 * @param buttonheight
	 */
	public void init(int buttonwidth, int buttonheight){
		titel.initFont();
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 2*buttonheight, Textures.start, Textures.startover,1, "Restart"));
		lijst.add(new MenuButton(Menu.getScreenx()/2, Menu.getScreeny() - 3*buttonheight, Textures.start, Textures.startover,2, "Main Menu"));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		switch(buttonID){
		
		case 1:
			Menu.setState(GameState.GAME);
			Sound.playMusic("background_game");
			Mazerunner game = new Mazerunner();
			glPushMatrix();
			try {
				game.start();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glPopMatrix();
			buttonID=0;
			break;
			
		case 2:
			Menu.setState(GameState.MAIN);
			
			default: break;
		}
	}
	
	public void display(){
		super.display();
		titel.draw(Menu.getScreenx()/2, Menu.getScreeny() - titel.getHeight(), 1);
	}
}
