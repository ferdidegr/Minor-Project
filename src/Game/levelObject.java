package Game;

public abstract class levelObject extends GameObject implements VisibleObject{
	public levelObject(double x, double y, double z){
		super(x, y, z);
	}
	
	public abstract boolean isCollision(double x, double y, double z);
}
