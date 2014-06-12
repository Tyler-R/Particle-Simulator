import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;


public class Particle {
	private Vector location;
	public Vector velocity;
	private Vector acceleration;
	private int color;
	private Vector target;
	private static int speedLimit = 10;

	public Particle(int x, int y, int color, Vector target){
		location = new Vector(x, y);
		velocity = new Vector(0, 0);
		
		setColor(color);
		
		this.target = target;
		setLimit();
	}
	
	public void update(){
		acceleration = Vector.subtract(target, location);
		
		acceleration.normalize();
		acceleration.multiply(0.5);
		
		velocity.add(acceleration);
		velocity.limit();		
		location.add(velocity);
		
		//System.out.println(velocity);
	}
	
	private void setLimit(){
		Random random = new Random();
		if(ModeVariables.sameLimits){
			velocity.setLimit(speedLimit, speedLimit);
		}else if(ModeVariables.sameRandomLimits){
			int limit = random.nextInt(14) + 1;
			velocity.setLimit(limit, limit);
		}else if(ModeVariables.differentRandomLimits){
			velocity.setLimit(random.nextInt(14) + 1, random.nextInt(14) + 1);
		}
	}
	
	public boolean insideBounds(int width, int height){
		if((getX() < 0) || (getX() >= width || getY() < 0 || getY() >= height)){
			return false;
		}else{
			return true;
		}
	}
	
	public int getX(){
		return (int)location.getX();
	}
	
	public int getY(){
		return (int)location.getY();
	}
	
	public int getColor(){
		return this.color;
	}
	
	public void setColor(int color){
		if(color >= 0xFFFFFF){
			this.color = 0xFFFFFF;
			System.out.println("white");
		}else if(color <= 0){
			this.color = 0;
		}else{
			this.color = color;
		}
	}
	
	public void draw(Graphics g){
		Vector.draw(g, location, velocity);
	}
}
