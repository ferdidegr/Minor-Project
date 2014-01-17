package Game;

import Menu.Menu;
import Utils.Sound;


/**
 * Class to keep track of the health of all living things in the maze.
 * All living things should have a health object.
 * 
 * @author ZL
 *
 */
public class Health {
	private int health;				// current health
	private int maxHealth;			// initial maximum health
	private boolean isPlayer;		// Is the carrier of this health the player
	private Sound sound;
	/**
	 * Constructor: initializes the health
	 * @param health integer value
	 */
	public Health(int health, boolean isPlayer){
		setmaxHealth(health);
		setHealth(health);
		this.isPlayer=isPlayer;
		sound=Menu.getSkitter();
	}
	/**
	 * set the constant int maximum health
	 * @param health integer value
	 */
	public void setmaxHealth(int health){
		this.maxHealth = health;
	}
	/**
	 * set the current health. Normally not used.
	 * @param health health as an int
	 */
	public void setHealth(int health){
		this.health = health;
	}
	/**
	 * Get the current health value
	 * @return the integer value of health
	 */
	public int getHealth(){
		return health;
	}
	/**
	 * adds health.
	 * @param addition the amount you are adding
	 */
	public void addHealth(int addition){
		this.health+=addition;
		if(health>maxHealth){
			setHealth(maxHealth);
		}
		if (isPlayer && addition<0){
			sound.playHurt();
			
		}
	}
	/**
	 * Gives you the maximum health at the beginning
	 * @return int maxhealth
	 */
	public int getmaxHealth(){
		return maxHealth;
	}
	/**
	 * Returns the fraction of health/maxhealth
	 * @return double fraction
	 */
	public double getHealthfraction(){
		return (double) health/maxHealth;
	}
}
