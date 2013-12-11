package Game;

public class Health {
	private int health;
	private int maxHealth;
	/**
	 * 
	 * @param health
	 */
	public Health(int health){
		setmaxHealth(health);
		setHealth(health);
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
	}
}
