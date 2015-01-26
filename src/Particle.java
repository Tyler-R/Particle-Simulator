import java.awt.Graphics;
import java.util.Random;

public class Particle {
	private Vector location;
	private Vector velocity;
	private Vector acceleration;
	
	/**Maximum x and y velocity the particle can achieve*/
	private static int speedLimit = 10;
	private int color;
	private int radius;
	private double mass = 1;
	/**The system time the particle will be dead at*/
	private long deathTime;
	private boolean isImmortal = true;
	
	/**
	 * Creates a new particle with the given x, and y position, and
	 * a color corresponding to the color parameter.
	 * 
	 * @param x - x position of particle.
	 * @param y - y position of particle.
	 * @param color - numeric representation of the particles color
	 * 
	 * */
	private Particle(int x, int y, int color){
		location = new Vector(x, y);
		velocity = new Vector(0, 0);
		acceleration = new Vector(0,0);
		
		setColor(color);
		setLimit();
		
		Random random = new Random();
		setRadius(random.nextInt(7) + 3);
	}
	
	/**
	 * Creates a new particle with the given x, and y position,
	 * a color corresponding to the color parameter, and a lifespan in seconds.
	 * 
	 * @param x - x position of particle.
	 * @param y - y position of particle.
	 * @param color - numeric representation of the particles color
	 * @param lifespan - how long the particle will be alive for in seconds.  
	 * Once the particles lifespan runs out it will die.
	 * 
	 * */
	private Particle(int x, int y, int color, int lifespan){
		this(x,y,color);
		this.isImmortal = false;
		//1000 Millisecond = 1 seconds thus we multiply the lifespan by 1000.
		this.deathTime = (lifespan * 1000) + System.currentTimeMillis();
	}
	
	/**
	 * Causes the particle to accelerate in the opposite direction of the target.
	 * 
	 * @param target - vector whose x and y values represent a location 
	 * that the particle will accelerate away from.
	 * */
	public void explosion(Vector target){
		acceleration = Vector.subtract(target, location);
		
		acceleration.normalize();
		acceleration.divide(mass);
		acceleration.multiply(-0.5);
	}
	
	/**
	 * Applies a vector force that effects the acceleration of the particle.  
	 * The force is dampened by mass to correspond to the formula Acceleration = Force/Mass
	 * 
	 * @param force - force that will have it's x and y values added to the acceleration of the particle.
	 * */
	public void applyForce(Vector force){
		Vector f = Vector.divide(force, mass);
		f.normalize();
		acceleration.add(force);
	}
	
	
	public void update(){			
		velocity.add(acceleration);
		velocity.limit();
		
		//bounce off the walls if the walls are set to be solid
		if(ModeVariables.walls){
			if(location.getX() + velocity.getX() >= Frame.SCREEN_WIDTH || location.getX() <= 0){
				velocity.setX(velocity.getX() * -1);
			}
			
			if(location.getY() + velocity.getY() >= Frame.SCREEN_HEIGHT || location.getY() <= 0){
				velocity.setY(velocity.getY() * -1);
			}
		}
		
		location.add(velocity);
		acceleration.multiply(0);
	}
	
	/**
	 * sets the maximum x and y value that the velocity vector of the particle can reach.
	 * */
	private void setLimit(){
		velocity.setLimit(speedLimit, speedLimit);
	}
	
	/**
	 * determines if the particle's x and y position are between 0 and the width and height parameters.
	 * 
	 * @param width - x bounds of the 
	 * @return true if the particle is within the bounds of the parameters and false otherwise.
	 * */
	public boolean insideBounds(int width, int height){
		if((getX() < 0) || (getX() >= width || getY() < 0 || getY() >= height)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * returns the x position of the particle
	 * 
	 * @return The x position of the particle.
	 * */
	public int getX(){
		return (int)location.getX();
	}
	
	/**
	 * returns the y position of the particle
	 * 
	 * @return the y position of the particle.
	 * */
	public int getY(){
		return (int)location.getY();
	}
	
	/**
	 * Returns the mass of the particle.
	 * 
	 * @return mass of the particle.
	 * */
	public double getMass(){
		return this.mass;
	}
	
	/**
	 * Returns a reference to the Euclidean vector representation of the location of the particle.
	 * This vector contains the x and y position of the particle.
	 * 
	 * @return Reference to the vector based representation of the x and y location of particle.
	 * */
	public Vector getLocation(){
		//i left this unsafe for added features
		//i should return a copy of the location vector
		return this.location;
	}
	
	/**
	 * Returns the radius of the particle.
	 * 
	 * @return radius of the particle.
	 * */
	public int getRadius(){
		return this.radius;
	}
	
	/**
	 * Returns the color of the particle.
	 * 
	 * @return color of the particle.
	 * */
	public int getColor(){
		return this.color;
	}
	
	/**
	 * Returns whether the particle has existed for longer that its life span based on system time.
	 * 
	 * @return true if the particle has existed for longer than its life span based on system time, 
	 * false if the particle is immortal (has no life span) or has not existed for longer than its life span.  
	 * */
	public boolean isDead(){
		return isDead(System.currentTimeMillis());
	}
	
	/**
	 * Returns whether the particle has existed for longer that its life span and thus is dead.
	 * 
	 * @param currentTimeinMillis - current system time in milliseconds.
	 * 
	 * @return true if the particle has existed for longer than its life span based on the parameter, 
	 * false if the particle is immortal (has no life span) or has not existed for longer than its life span.  
	 * */
	public boolean isDead(long currentTimeinMillis){
		if(isImmortal){
			return false;
		}
		
		if(deathTime < currentTimeinMillis){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Sets the mass of the particle.  If the mass is less than 1 it will be set to 1.
	 * 
	 * @param mass - the new mass of the particle.
	 * */
	public void setMass(int mass){
		if(mass < 1){ //cannot have mass less than 1
			this.mass = 1; //we don't take kindly to light around here
		}else{
			this.mass = mass;
		}
	}
	
	/**
	 * sets the color of the particle.<br> 
	 * If the color is greater than black (2^24 / 0xFFFFFF) then the color is set to black.<br>
	 * If the color is less than 0 it is set to white (0).
	 * 
	 * @param color - new color of particle.
	 * */
	public void setColor(int color){
		if(color >= 0xFFFFFF){
			this.color = 0xFFFFFF; //white
		}else if(color <= 0){
			this.color = 0; //black
		}else{
			this.color = color;
		}
	}
	
	/**
	 * Sets the radius of the particle. 
	 * If the radius is less than 0 it will be set to 0.
	 * 
	 * @param radius - the new radius of the particle.
	 * */
	public void setRadius(int radius){
		if(radius < 0){ //cannot have negative radius
			radius = 0;
		}else{
			this.radius = radius;
		}
	}
	
	/**
	 * Creates and return a new particle with the given x, and y position,
	 * and a color corresponding to the color parameter.
	 * 
	 * @param x - x position of particle.
	 * @param y - y position of particle.
	 * @param color - numeric representation of the particles color
	 * 
	 * @return a new particle with the given color, and a starting location corresponding to the given x and y values.
	 * 
	 * */
	public static Particle createParticle(int x, int y, int color) {
		return new Particle(x, y, color);
	}
	
	/**
	 * Creates and return a new particle with the given x, and y position, 
	 * a color corresponding to the color parameter.
	 * 
	 * @param x - x position of particle.
	 * @param y - y position of particle.
	 * @param color - numeric representation of the particles color
	 * @param lifespan - how long the particle will be alive for in seconds.  
	 * Once the particles life span runs out it will die.
	 * 
	 * @return a new particle with the given color, life span and a starting location corresponding to the given x and y values.
	 * 
	 * */
	public static Particle createParticle(int x, int y, int color, int lifespan) {
		return new Particle(x, y, color, lifespan);
	}
	
	/**
	 * Draws a line based representation of the velocity vector of the particle.
	 * 
	 * @param g - the Graphics context in which to paint.
	 * */
	public void drawVelocityVector(Graphics g){
		Vector.draw(g, location, velocity, 10);
	}
}
