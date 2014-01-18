package Menu;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import Game.Mazerunner;
import Utils.Text;

public class MazechooserMenu extends ButtonList{
		
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
		
		int counter = 0;
		for(String name:Menu.levelList){			
			lijst.add(new MenuButton((counter)*height , Textures.start, Textures.startover,counter, name.split("\\.maze")[0].split("/")[1], MenuButton.Alignment.CENTER));
			counter++;
		}
		// Back to menu button added
		lijst .add(new MenuButton((counter+1)*height , Textures.start, Textures.startover,counter, "BACK TO MENU", MenuButton.Alignment.CENTER));
	}
	
	/** Bepaal hier wat bij verschillende knoppen de bijbehorende actie is.
	 * 
	 * @param buttonID
	 */
	public void actionButtons(int buttonID){
	
		if(buttonID >=0 && buttonID<Menu.levelList.size()){			
			Menu.setState(GameState.GAME);
			Menu.currentlevel = buttonID;			
			Menu.game = new Mazerunner();
			glPushMatrix();
			try {
				Menu.game.start(Menu.levelList.get(buttonID));				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glPopMatrix();
	
		}else if(buttonID==Menu.levelList.size()){
			Menu.setState(GameState.MAIN);

		}

	}
	/**
	 * Display all levels as buttons
	 */
	public void display(){
		super.display();
	}	
	/**
	 * When a new level is made the list should be renewed
	 */
	public void resetlist(){
		lijst.clear();
	}

}
