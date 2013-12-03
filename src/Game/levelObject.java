package Game;

public abstract class levelObject extends GameObject implements VisibleObject{
	public levelObject(double x, double y, double z){
		super(x, y, z);
	}
	
	public abstract boolean isCollision(double x, double y, double z);
	public abstract double getmaxDistX(double X);
	public abstract double getmaxDistY(double Y);
	public abstract double getmaxDistZ(double Z);
	public abstract void update(int deltaTime);
}
