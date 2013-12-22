package ParticleSystem;
import static org.lwjgl.opengl.GL11.*;
import Utils.Vector;

public class Particle {
	private Vector position;
	private Vector velocity;
	private Vector acceleration;
	private double size;
	private double age;
	private boolean updated = false;
	private static Vector attractor;
	
	/**
	 * 
	 * @param position begin position of the particle - if emitted by the emitter it is the position of the emitter
	 * @param velocity begin velocity
	 * @param acceleration begin acceleration , is assumed to be not changing
	 * @param size size of the particle
	 */
	public Particle(Vector position, Vector velocity, Vector acceleration, double size){
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.size = size;
	}
	/**
	 * Update the velocity and location of the particle
	 */
	public void update(int deltaTime){
		age += deltaTime;
		if(age>=200 && updated == false){
			acceleration.add(acceleration.getY()*Math.signum(velocity.getX())*-0.7*16.5/deltaTime,0,acceleration.getY()*Math.signum(velocity.getZ())*-0.7*16.5/deltaTime);
			updated = true;
		}
		velocity.add(acceleration.clone().scale(deltaTime/16.5));		
		position.add(velocity.clone().scale(deltaTime/16.5));		
	}
	/**
	 * Draw the particle
	 */
	public void display(){
		glPointSize((float) size);
		glEnable(GL_BLEND);
		if(age < 100){	glColor4f(1f, 1f,(float) (1f- 0.5f*age/100), 0.5f);}
		else if(age < 500){ glColor4f(1f, (float)(0.5f - 0.5f*(age-100)/400f),0, 0.8f);}
		else{ glColor4f(1f, 0,0, 0.5f);}
		glBegin(GL_POINTS);
		glVertex3d(position.getX(), position.getY(),position.getZ());
		glEnd();
		glDisable(GL_BLEND);
	}
	/**
	 * Attach an attractor for all particles
	 * @param attractorin
	 */
	public static void setAttractor(Vector attractorin){
		attractor = attractorin;
	}
	
	public double getAge(){ return age;}
}
