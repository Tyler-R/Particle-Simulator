import java.awt.Graphics;

/**
 * representation of an Euclidean Vector
 * */
public class Vector {
	/**x value of vector*/
	private double x;
	/**y value of vector*/
	private double y;
	private double limitX;
	private double limitY;

	/**
	 * Constructs a new Euclidean Vector with an initial x and y value.
	 * 
	 * @param x - initial x value of vector.
	 * @param y - initial y value of vector.
	 * */
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add the x and y value of the parameter v2 to this vectors x and y value.
	 * 
	 * @param v2 - vector whose x and y value are added to this vectors x and y value.
	 * */
	public void add(Vector v2){
		x += v2.getX();
		y += v2.getY();
	}
	
	public static Vector add(Vector v1, Vector v2){
		return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}
	
	/**
	 * Subtract the x and y values of v2 from this vectors x and y values.
	 * 
	 * @param v2 - vector whose x and y value are subtracted from this vectors x and y value.
	 * */
	public void subtract(Vector v2){
		x -= v2.getX();
		y -= v2.getY();
	}
	
	public static Vector subtract(Vector v1, Vector v2){
		return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
	}
	
	/**
	 * Multiply the x and y values of this vector by the scalar value parameter.
	 * 
	 * @param value - scalar value used to multiply the x and y value of the vector.
	 * */
	public void multiply(double value){
		x *= value;
		y *= value;
	}
	
	public static Vector multiply(Vector v1, double scale) {
		return new Vector(v1.getX() * scale, v1.getY() *scale);
	}
	
	/**
	 * Divide the x and y values of this vector by the scalar value parameter.
	 * 
	 * @param value - scalar value used to multiply the x and y value of the vector.
	 * */
	public void divide(double value){
		x /= value;
		y /= value;
	}
	
	public static Vector divide(Vector v1, double scale){
		return new Vector(v1.getX() / scale, v1.getY() / scale);
	}
	
	
	public void normalize(){
		double m = getMagnitude();
		
		if(m != 0){
			divide(m);
		}
	}
	
	/**
	 * If the x and y value of the vector is above or below the x and y limit of the vector
	 * set the x and y value to be equal to +/- of the limit.
	 * */
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
	
	/**
	 * Returns the x value of the vector.
	 * 
	 * @return the x position of the vector.
	 * */
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the y value of the vector.
	 * 
	 * @return the y position of the vector.
	 * */
	public double getY(){
		return y;
	}
	
	public double getMagnitude(){
		return Math.sqrt(((x*x) + (y*y)));
	}
	
	/**
	 * Returns the x limit of the vector.
	 * 
	 * @return the x limit of the vector.
	 * */
	public double getXLimit(){
		return limitX;
	}
	
	/**
	 * Returns the y limit of the vector.
	 * 
	 * @return the y limit of the vector.
	 * */
	public double getYLimit(){
		return limitY;
	}
	
	/**
	 * Sets the x and y limit of the vector.
	 * 
	 * @param xLimit - the new x limit of the vector.
	 * @param yLimit - the new y limit of the vector.
	 * */
	public void setLimit(double xLimit, double yLimit){
		limitX = xLimit;
		limitY = yLimit;
	}
	
	/**
	 * Sets the x value of the vector.
	 * 
	 * @param x - new x value of vector.
	 * */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * Sets the y value of the vector.
	 * 
	 * @param y - new y value of vector.
	 * */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * 
	 * 
	 * @param g - the Graphics context in which to paint.
	 * */
	public static void draw(Graphics g, Vector v1, Vector v2, int scale){
		g.drawLine((int)v1.getX(), (int)v1.getY(), (int)(v2.getX() * scale + v1.getX()), (int)(v2.getY() * scale + v1.getY()));
	}
	
	/**
	 * @return returns a string in the format "(x, y)" with x and y being the vectors x and y values
	 * */
	public String toString(){
		return "(" + x + ", " + y + ") ";
	}
}
