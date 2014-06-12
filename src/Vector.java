import java.awt.Graphics;


public class Vector {
	private double x;
	private double y;
	private double limitX;
	private double limitY;

	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector v2){
		x += v2.getX();
		y += v2.getY();
	}
	
	public void subtract(Vector v2){
		x -= v2.getX();
		y -= v2.getY();
	}
	
	public static Vector subtract(Vector v1, Vector v2){
		return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
	}
	
	public void multiply(double value){
		x *= value;
		y *= value;
	}
	
	public void divide(double value){
		x /= value;
		y /= value;
	}
	
	public void normalize(){
		double m = getMagnitude();
		
		if(m != 0){
			divide(m);
		}
	}
	
	public void limit(){
		if(x > limitX){
			x = limitX;
		}else if(x < (-1 * limitX)){
			x = (-1 * limitX);
		}
		
		
		if(y > limitY){
			y = limitY;
		}else if(y < (-1 * limitY)){
			y = (-1 * limitY);
		}
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public double getMagnitude(){
		return Math.sqrt(((x*x) + (y*y)));
	}
	
	public void setLimit(double x, double y){
		limitX = x;
		limitY = y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public static void draw(Graphics g, Vector v1, Vector v2){
		g.drawLine((int)v1.getX(), (int)v1.getY(), (int)(v2.getX() * 10 + v1.getX()), (int)(v2.getY() * 10 + v1.getY()));
	}
	
	/**
	 * @return returns a string in the format "(x, y)"
	 * */
	public String toString(){
		return "(" + x + ", " + y + ") ";
	}
	
}
