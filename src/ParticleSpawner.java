import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ParticleSpawner implements Runnable{
	private Vector location;
	/**number of particles that are spawned per second*/
	private int frequency = 3;
	/**lifespan of each particle the particleSpawner spawns*/
	private int lifespan = 3;
	private List<Particle> particleList;
	
	private boolean running = false;
	private Thread thread;
	
	private Random random = new Random();
	
	private ParticleSpawner(int x, int y, int frequency, int lifespan){
		this.location = new Vector(x, y);
		this.frequency = frequency;
		this.lifespan = lifespan;
		this.particleList = Collections.synchronizedList(new ArrayList<Particle>());
		
		init();
	}

	private void init(){
		if(frequency > 0){
			start();
		}
	}
	
	public void start(){
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			//System.out.println("joining thread");
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		double NS_PER_FRAME = 1000000000 / frequency;
		int frames = 0;
		while (running){
			
			double start = System.nanoTime();
			spawnParticle();
			frames++;
			
			try {
				Thread.sleep((long)(start + NS_PER_FRAME - System.nanoTime()) / 1000000);
			} catch (InterruptedException e) {
				System.out.println("thread in main game loop could not sleep");
			} catch (Exception e){
				  //the sleep function was getting passed a negative number sometimes that would crash the program
				System.out.println("cannot sleep for a negative amount of time");
			}
			  
			if(frames >= frequency){
				frames = 0;
			}
			
			//System.out.println("running");
		}
	}
	
	private void spawnParticle() {
		
		Particle p = Particle.createParticle(
				(int)location.getX(), 
				(int)location.getY(), 
				random.nextInt(0xFFFFFF),
				lifespan);
		
		synchronized(particleList){
			particleList.add(p);
		}
	}
	
	/**
	 * Removes all particles in the particle spawner and 
	 * returns for use outside of the particle spawner.  
	 * 
	 * @return and array of particles
	 * */
	public Object[] getParticles() {
		Object[] particles;
		synchronized(particleList){
			particles =  particleList.toArray();
			particleList.clear(); 
		}
		return particles; 
	}
	
	public static ParticleSpawner createParticleSpawner(int x, int y, int frequency, int lifespan) {
		return new ParticleSpawner(x, y, frequency, lifespan);
	}
}


