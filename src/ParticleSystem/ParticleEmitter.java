package ParticleSystem;

import java.util.ArrayList;

import Utils.Vector;

/**
 * A simple particle emitter
 * @author ZL
 *
 */
public class ParticleEmitter {
	private ArrayList<Particle> particlelist = new ArrayList<>();
	private Vector position;
	private double vx, vy, vz;
	private double ax, ay, az;
	private double pointsize;
	private double life;
	/**
	 * Constructor of a particle emitter
	 * @param position	location of the emitter
	 * @param vx		maximum begin velocity in x direction of the emitted particle
	 * @param vy		maximum begin velocity in y direction of the emitted particle
	 * @param vz		maximum begin velocity in z direction of the emitted particle
	 * @param ax		begin acceleration in x direction of the emitted particle
	 * @param ay		begin acceleration in x direction of the emitted particle
	 * @param az		begin acceleration in x direction of the emitted particle
	 * @param pointsize	size of the particle
	 */
	public ParticleEmitter(Vector position, double vx, double vy, double vz, double ax, double ay, double az, double pointsize, double life){
		this.position = position;
		this.ax =ax;
		this.ay = ay;
		this.az = az;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		this.pointsize = pointsize;
		this.life = life;
	}
	/**
	 * add a new particle to the list
	 * @param particle
	 */
	public void add(Particle particle){
		particlelist.add(particle);
	}
	/**
	 * Generate a new particle
	 */
	public void emit(){
		add(new Particle(position.clone(), 
				new Vector((Math.random()>0.5?1:-1)*Math.random()*vx, (Math.random()>0.5?1:-1)*Math.random()*vy, (Math.random()>0.5?1:-1)*Math.random()*vz),
				new Vector(ax, ay, az),
				pointsize));
	}
	/**
	 * Call the update function of all particles
	 */
	public void update(int deltaTime){
		ArrayList<Particle> deathlist = new ArrayList<Particle>();
		
		for(Particle p:particlelist){
			p.update(deltaTime);
			if(p.getAge() > life && Math.random()>0.8){ deathlist.add(p);}
		}
		
		for(Particle p : deathlist){
			particlelist.remove(p);
		}
	}
	/**
	 * Display all particles
	 */
	public void display(){
		for(Particle p:particlelist){
				p.display();
		}
	}
	/**
	 * Give the amount of particles currently alive
	 * @return the size of the particle list
	 */
	public int getListSize(){
		return particlelist.size();
	}
	/**
	 * Get the location of this particle
	 * @return a vector containing this position
	 */
	public Vector getLocation(){
		return position;
	}
	/**
	 * Set the location of this emitter
	 * @param location
	 */
	public void setlocation(Vector location){
		this.position = location;
	}
}
