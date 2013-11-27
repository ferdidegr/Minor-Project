package Game;
import static org.lwjgl.opengl.GL11.*;
public class Monster extends levelObject{

	private int SQUARE_SIZE;
	private double width;
	private double height;
	private double speed = 0.005;
	public static Vector playerloc = new Vector(0, 0, 0);
	
	public Monster(double x, double y, double z, double width, double height,  int SQUARE_SIZE) {
		super(x, y, z);
		this.width = width;
		this.height= height;
		this.SQUARE_SIZE  =SQUARE_SIZE;
		// TODO Auto-generated constructor stub
	}
	
	public void update(int deltaTime){
		Vector vec = new Vector(locationX, locationY, locationZ);
		Vector toPlayer = playerloc.clone();
		vec.scale(-1);
		toPlayer.add(vec);
		toPlayer.normalize2D();
		
		locationX += toPlayer.getX()*speed * deltaTime*0.5;

		locationZ += toPlayer.getZ()*speed * deltaTime*0.5;
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
		
		return x>=(locationX-width) && x<=(locationX+width) && z>=(locationZ-width) && z<=(locationZ+width) && y>=0 && y<height;
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
