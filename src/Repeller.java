
public class Repeller {
	private Vector location;
	private int repulsionConstant = 10;
	
	
	private Repeller(Vector location, int strength){
		this.location = location;
		this.repulsionConstant = strength;
	}
	
	/**
	 * applies a repulsive force to the particle.
	 * The repulsive force applies a force on the particle's acceleration 
	 * causing the particle to move away from the repeller.
	 * 
	 * @param p - particle that will have the repulsive force applied to it.
	 * */
	public void repel(Particle p){
		Vector force = Vector.subtract(location, p.getLocation());
		double distance = force.getMagnitude();
		
		force.normalize();
		
		double repulsionForce = -1 * repulsionConstant / (distance * distance);
		
		force.multiply(repulsionForce);

		p.applyForce(force);
	}
	
	public int getX(){
		return (int) location.getX();
	}
	
	public int getY(){
		return (int) location.getY();
	}
	
	public static Repeller createRepeller(Vector location, int strength) {
		return new Repeller(location, strength);
	}
}
