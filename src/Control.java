/**
 * The Control class is an abstract class containing only basic functionality such
 * as getters for all possible commands.
 * <p>
 * An update method is also included, demanding all subclasses to implement it. 
 * This method will be called just before any getters are called. The reason for this
 * is to allow the subclass to set all fields to the most recent input.
 * <p>
 * For the purposes of the assignment, it might seem unnecessary to have the 
 * actual user input in a separate class from this class. Indeed there is no other
 * subclass to Control other than UserInput, but this might change once some sort of
 * AI controlling of GameObjects is desired. Any AI that needs to control an object
 * in the game can use the same methods any human player would use, which makes it a 
 * lot more intuitive to program an AI.
 * 
 * @author Mattijs Driel
 * 
 */
public abstract class Control
{
	protected boolean forward = false;
	protected boolean back = false;
	protected boolean left = false;
	protected boolean right = false;
	protected boolean jump = false;
	protected boolean run = false;
	protected boolean view_coord = false;
	double Vy=0;
	protected int dX = 0;
	protected int dY = 0;
	
	/**
	 * @return Returns true if forward motion is desired.
	 */
	public boolean getForward()
	{
		return forward;
	}
	/**
	 * set Forward 
	 */
	public void setForward(boolean s){
		forward = s;
	}
	
	/**
	 * @return Returns true if backwards motion is desired.
	 */
	public boolean getBack()
	{
		return back;
	}
	
	/**
	 * set Back
	 */
	public void setBack(boolean s){
		back = s;
	}
	
	/**
	 * @return Returns true if left sidestepping motion is desired.
	 */
	public boolean getLeft()
	{
		return left;
	}
	
	/**
	 * set Left
	 */
	public void setLeft(boolean s){
		left = s;
	}
	
	/**
	 * @return Returns true if right side stepping motion is desired.
	 */
	public boolean getRight()
	{
		return right;
	}
	
	/**
	 * set right
	 */
	public void setRight(boolean s){
		right = s;
	}
	
	public void setJump(boolean jump){
		this.jump=jump;
	}
	/**
	 * Gets of a jump is performed
	 * @return jump true or false
	 */
	public boolean getJump(){
		return jump;
	}
	/**
	 * Gets Vy
	 * @return jump true or false
	 */
	public double getVy(){
		return Vy;
	}
	/**
	 * Gets if a run is performed
	 * @return jump true or false
	 */
	public boolean getRun(){
		return run;
	}
	
	/**
	 * Gets the amount of rotation desired on the horizontal plane.
	 * @return The horizontal rotation.
	 */
	public int getdX()
	{
		return dX;
	}
	
	/**
	 * Gets the amount of rotation desired on the vertical plane.
	 * @return The vertical rotation.
	 */
	public int getdY()
	{
		return dY;
	}
	
	/**
	 * Updates the fields of the Control class to represent the
	 * most up-to-date values. 
	 */
	public abstract void update();
}
