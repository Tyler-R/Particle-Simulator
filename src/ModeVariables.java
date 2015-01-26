/**
 * class that holds all the variables that affect the user interface.<br>
 * this class really should not exist but i found it a lot easier for testing to have 
 * all the variables i change on a regular basis to be in the same class
 * */
public class ModeVariables {
	//make it so the static class cannot be instantiated.
	private ModeVariables(){
		throw new AssertionError();
	}
	
	/*variables for mouse class*/
	public static boolean spawnGravityWell = false;
	public static boolean spawnTarget = false;
	public static boolean spawnParticleSpawner = false;
	public static boolean spawnRepeller = false;

	/*variables for Screen class*/
	public static boolean drawVectors = false;
	public static boolean wind = false;
	public static boolean renderParticlesAsBalls = false;
	public static int numberOfParticles = 10000;
	public static int maxNumberOfCirclesToDraw = 2000;
	public static int maxNumberOfParticlesToDraw = 10000;
	public static boolean walls = false;
	public static boolean chaseMouse = true; //when false causes explosion
	public static boolean reset = false;
}
