package Menu;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import Game.Mazerunner;
import Utils.Sound;
import Utils.Text;

public class MazechooserMenu extends ButtonList{
	static ArrayList<String> MazeList = new ArrayList<String>();
	
	public MazechooserMenu() {
		super();		
	}
	
	/** Maak hier de knoppen en voeg toe aan "lijst"
	 * 
	 * @param x
	 * @param y
	 */
	public void init(int x, int y){
		/*
		 * Check the level dir for maze files
		 */
		int height = MenuButton.height;
		String currentdir = System.getProperty("user.dir");		
		String[] files = new File(currentdir+"\\levels").list();
		
		for(String name:files){
			if(name.toLowerCase().endsWith(".maze")){
				MazeList.add(name);
			}
		}
		int counter = 0;
		for(String name:MazeList){
			lijst.add(new MenuButton(x, (counter)*height , Textures.start, Textures.startover,counter, name.split(".maze")[0]));
			counter++;
		}
		
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public static void actionButtons(int buttonID){
		int score = 0;
		if(buttonID >=0 && buttonID<MazeList.size()){			
			Menu.setState(GameState.GAME);
			Menu.currentlevel = buttonID;
			Sound.playMusic("background_game");
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				score= Menu.game.start(MazeList.get(buttonID));
				System.out.println(Display.isCloseRequested());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glPopMatrix();			
		}else{
			Menu.setState(GameState.MAIN);
		}
		System.out.println(score);
		if(score>=-200){
		
			ScoreScreen.displayScoreatGO(score);
		
		}
	}
	
	public void display(){
		super.display();
	}	

}
