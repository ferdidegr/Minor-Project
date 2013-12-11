package Game;

import Utils.Sound;

public class Health {
	private int health;
	private int maxHealth;
	private boolean isPlayer;
	/**
	 * 
	 * @param health
	 */
	public Health(int health, boolean isPlayer){
		setmaxHealth(health);
		setHealth(health);
		this.isPlayer=isPlayer;
	}
	/**
	 * 
	 * @param health
	 */
	public void setmaxHealth(int health){
		this.maxHealth = health;
	}
	/**
	 * 
	 * @param health
	 */
	public void setHealth(int health){
		this.health = health;
	}
	/**
	 * 
	 * @return
	 */
	public int getHealth(){
		return health;
	}
	/**
	 * 
	 * @param addition
	 */
	public void addHealth(int addition){
		this.health+=addition;
		if(health>maxHealth){
			setHealth(maxHealth);
		}
		if (isPlayer && addition<0){
			Sound.playEffect("hurt");
		}
	}
}
