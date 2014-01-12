package Game;
/**
 * Abstract class for all objects in the level.
 * 
 * @author ZL
 *
 */
public abstract class levelObject extends GameObject implements VisibleObject{
	/**
	 * Set location
	 * @param x location X
	 * @param y location Y
	 * @param z	location Z
	 */
	public levelObject(double x, double y, double z){
		super(x, y, z);
	}
	/**
	 * Is there collision with this
	 * @param x xLocation of someone else
	 * @param y yLocation of someone else
	 * @param z zLocation of someone else
	 * @return true when there is collision, false otherwise
	 */
	public abstract boolean isCollision(double x, double y, double z);
	/**
	 * Maximum distance in X till you collide with this
	 * @param X
	 * @return
	 */
	public abstract double getmaxDistX(double X);
	/**
	 * Maximum distance in Y till you collide with this
	 * @param Y
	 * @return
	 */
	public abstract double getmaxDistY(double Y);
	/**
	 * Maximum distance in Z till you collide with this
	 * @param Z
	 * @return
	 */
	public abstract double getmaxDistZ(double Z);
	/**
	 * If this is a movable, then update the movement.
	 * Not always needed.
	 * @param deltaTime
	 */
	public abstract void update(int deltaTime);
	/**
	 * When this is a object that can be toggled by the user
	 */
	public abstract void change();
}
