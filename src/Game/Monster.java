package Game;
import static org.lwjgl.opengl.GL11.*;
public class Monster extends levelObject{

	private int SQUARE_SIZE;
	public static Vector playerloc = new Vector(0, 0, 0);
	
	public Monster(double x, double y, double z, int SQUARE_SIZE) {
		super(x, y, z);
		this.SQUARE_SIZE  =SQUARE_SIZE;
		// TODO Auto-generated constructor stub
	}
	
	public void update(int deltaTime){
		Vector vec = new Vector(locationX, locationY, locationZ);
		double dX = 
		
		locationX += (playerloc.getX()-locationX)/1000 * deltaTime;

		locationZ += (playerloc.getZ()-locationZ)/1000 * deltaTime;
	}
	
	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		Graphics.renderCube(SQUARE_SIZE, false, false, false, false);		
		glPopMatrix();
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void setPlayerloc(Vector playerlocatie){
		playerloc = playerlocatie;
	}
	
}
