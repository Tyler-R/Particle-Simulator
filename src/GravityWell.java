
public class GravityWell {
	/**vector that contains the x and y position of the gravityWell*/
	private Vector location;
	/**mass of the gravity well*/
	private int mass = 100;
	private int gravitationalConstant = 10;
	
	private GravityWell(int x, int y){
		this.location = new Vector(x, y);
	}
	
	private GravityWell(int x, int y, int strength, int mass){
		this.location = new Vector(x, y);
		this.gravitationalConstant = strength;
		this.mass = mass;
	}
	
	/**
	 * Applies a gravitational force to the particle.
	 * 
	 * @param particle - particle that will have the gravitational force applied to it.
	 */
	public void applyGravity(Particle particle){
		
		Vector force = Vector.subtract(location, particle.getLocation());
		double distance = force.getMagnitude();
		double gravitationalForce = (gravitationalConstant * particle.getMass() * mass) / (distance * distance);
		force.normalize();
		
		force.multiply(gravitationalForce);
		
		particle.applyForce(force);
	}
	
	/**
	 * Returns the x position of the gravity well.
	 * 
	 * @return the x position of the gravity well.
	 * */
	public int getX(){
		return (int) location.getX();
	}
	
	/**
	 * Returns the y position of the gravity well.
	 * 
	 * @return the y position of the gravity well.
	 * */
	public int getY(){
		return (int) location.getY();
	}
	
	public static GravityWell createGravityWell(int x, int y, int strength, int mass) {
		return new GravityWell(x, y, strength, mass);
	}

	public static GravityWell createGravityWell(int x, int y) {
		return new GravityWell(x, y);
	}
}
