package Menu;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import Utils.InputField;
import Utils.Sound;
import Utils.Text;
import Game.Mazerunner;

public class Succes extends ButtonList {
	private InputField ipf;
	
	public Succes(){
		super();		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		
		lijst.add(new MenuButton(x, 2*y, Textures.start, Textures.startover,1, "Restart"));
		lijst.add(new MenuButton(x, 4*y, Textures.start, Textures.startover,2, "Start new game"));
		lijst.add(new MenuButton(x, 6*y, Textures.start, Textures.startover,3, "Main Menu"));
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
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				Menu.game.start(MazechooserMenu.MazeList.get(Menu.currentlevel));
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			glPopMatrix();
			buttonID=0;
			break;
		
		case 2:
			Menu.setState(GameState.SELECTLVL);
			Menu.game=null;
			break;
			
		case 3:
			Menu.setState(GameState.MAIN);
			Menu.game=null;
			default: break;
		}
	}
	
	public void display(){
		super.display();		
		Text.draw(Menu.getScreenx()/2, 0, 30, "SUCCES!");
		ipf = new InputField(12, 15, Display.getWidth()/2, Display.getHeight()/2);
		ipf.display();
	}
	
//	public void mousepoll(){
//		if(ipf.isField(Mouse.getX(), Display.getHeight() - Mouse.getY())){
//			ipf.setFocus();
//		}
//	}
}
