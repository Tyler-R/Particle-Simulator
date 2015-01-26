import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;


public class Screen extends JPanel implements Runnable {
	private BufferedImage image;
	
	private int[] pixels;
	
	private UserInterface userInterface;

	private Vector mousePosition = new Vector(0, 0);
	private Vector wind = new Vector(1,0);
	
	private ArrayList<Particle> particleList = new ArrayList<Particle>();
	
	private List<GravityWell> gravityWellList = Collections.synchronizedList(new ArrayList<GravityWell>());
	private List<Vector> targetList = Collections.synchronizedList(new ArrayList<Vector>());
	
	private List<ParticleSpawner> particleSpawnerList = Collections.synchronizedList(new ArrayList<ParticleSpawner>());
	
	private List<Repeller> repellerList = Collections.synchronizedList(new ArrayList<Repeller>());
	
	/**
	 * list of particle spawners that have been 
	 * disposed of but need to have their threads stopped in a efficient manner
	 * */
	private ArrayList<ParticleSpawner> threadKiller = new ArrayList<ParticleSpawner>();
	
	
	private boolean running = false;
	private Thread thread;
	
	
	private static Random random = new Random();
	
	
	public static final int FPS = 60;
	/**amount of time it takes to render and tick 60 frames in seconds*/
	private double renderTime = 0.0;
	/**counter of the amount of time passed since 60 frames has last been hit*/
	private double loopTime = 0.0;
	
	
	public Screen(UserInterface userInterface) {
		//enable double duffering
		super(true);
		//creates the main buffered image
		image = new BufferedImage(Frame.SCREEN_WIDTH, Frame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		//links the pixels array with the bufferedImage object
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.userInterface = userInterface;
		
		//set up the mouse and keyboard
		Mouse mouse = new Mouse(this);
		setFocusable(true);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		addKeyListener(new Key(this, userInterface));
		
		//start the program
		init();
	}
	
	private void init(){
		createParticles();
		targetList.add(mousePosition);
		loadExample();
	}
	
	/**
	 * Load a gravityWell, repeller, particleSpawner and two targets into the simulation
	 * as a quick demo of the capabilities of the program.
	 * */
	private void loadExample(){
		spawnGravityWell(250,250,10,100);
		spawnTarget(500,300);
		spawnTarget(200,500);
		spawnRepeller(300,300,4000);
		spawnParticleSpawner(50,50,5,3);
	}
	
	/**
	 * Starts the programs main game thread
	 * */
	public void start(){
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("could not stop main game loop");
		}
	}
	
	
	public void run(){
		double NS_PER_FRAME = 1000000000 / FPS;
		int frames = 0;
		
		while (running){
			double start = System.nanoTime();
			
			tick();
			render();
			
			loopTime += (System.nanoTime() - start) / 1000000000.0;
		  	frames++;
		  	
		  	try {
			  	Thread.sleep((long)(start + NS_PER_FRAME - System.nanoTime()) / 1000000);
		  		//Thread.sleep(0);
		  	} catch (InterruptedException e) {
			  	System.out.println("thread in main game loop could not sleep");
		  	} catch (Exception e){
			  	//if the sleep function is passed a negative number that means that the program is lagging.
			  	//System.out.println("The program is lagging");
		  	}
		  
		  	if(frames >= FPS){
		  		//dispose of one thread every second so that the program doesn't lag when dropping numerous threads on a reset.
		  		if(!threadKiller.isEmpty()){ //should be moved to the particle spawner class so it is responsible for its own cleanup
		  			threadKiller.get(0).stop();
		  			threadKiller.remove(0);
		  		}
		  		renderTime = loopTime;
		  		System.out.println(renderTime);
		  		System.out.println(userInterface.getColor());
		  		loopTime = 0.0; //reset loop time
		  		
			  	frames = 0;
			  	
			  	if(isLagging()){
			  		System.out.println("removing particles");
					for(int i=0; i<particleList.size() / 10; i++){
						particleList.remove(0);
					}
				}
			  	
			  	System.out.println("num particles = " + particleList.size());
		  	}
		}
	}
	
	private void render(){
		paintImmediately(getBounds());
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		
		clearPixels();
		
		//load the pixels color into the pixels array to be drawn 
		//and increase the number of particles to draw if they are set to the number of circles to draw.
		if(!ModeVariables.renderParticlesAsBalls){
			if(ModeVariables.numberOfParticles == ModeVariables.maxNumberOfCirclesToDraw) {
				ModeVariables.numberOfParticles = ModeVariables.maxNumberOfParticlesToDraw;
				initializeNewParticles();
			}
			
			for(Particle particle : particleList){
				if(particle.insideBounds(Frame.SCREEN_WIDTH, Frame.SCREEN_HEIGHT)) {
					pixels[particle.getX() + particle.getY() * Frame.SCREEN_WIDTH] = userInterface.getColor(); //particle.getColor();	
				}
			}
		}
		
		
		g.drawImage(image, 0, 0, Frame.SCREEN_WIDTH, Frame.SCREEN_HEIGHT, null);
		
		//draw the particles either as balls if ball drawing is turned on.
		if(ModeVariables.renderParticlesAsBalls){
			if(ModeVariables.numberOfParticles > ModeVariables.maxNumberOfCirclesToDraw){
				ModeVariables.numberOfParticles = ModeVariables.maxNumberOfCirclesToDraw;
				this.initializeNewParticles();
			}
			
			drawParticlesAsCircles(g);
		}
		
		synchronized(targetList){
			g.setColor(Color.RED);
			Iterator<Vector> it = targetList.iterator();
			//skip the mousePosition target so we don't draw it.
			it.next();
			//draw a dot on the screen for each target.
			while(it.hasNext()){
				Vector target = (Vector) it.next();
				g.drawOval((int) target.getX(), (int) target.getY(), 2, 2);
			}
		}
		
		//draw a dot on the screen for each gravity well.
		synchronized(gravityWellList){
			g.setColor(Color.BLUE);
			for(GravityWell gravity : gravityWellList){
				g.drawOval(gravity.getX(), gravity.getY(), 2, 2);
			}
		}
		
		//draw a dot on the screen for each repeller.
		synchronized(repellerList){
			g.setColor(Color.GREEN);
			for(Repeller repeller : repellerList){
				g.drawOval(repeller.getX(), repeller.getY(), 2, 2);
			}
		}
		
		//draws the velocity vector of each particle
		if(ModeVariables.drawVectors){
			drawVelocityVectorsOfParticles(g);
		}
		
		if(ModeVariables.reset){
			reset();
		}
	}
	
	private void tick(){	
		//add all the particles from the particle spawner to the particle list in this class
		synchronized(particleSpawnerList) {
			for(ParticleSpawner particleSpawner : particleSpawnerList) {
				Object[] particles = particleSpawner.getParticles();
				
				for(Object particle: particles) {
					particleList.add((Particle) particle);
				}
			}
		}
		
		
		/*
		 * Making only 1 call to get the time shaves 
		 * a couple milleSeconds off the game loop, with 50000 particles,
		 * rather than calling it for every particle.
		*/
		long currentTime = System.currentTimeMillis(); 

		//loop through all the particles in the system.
		Iterator<Particle> it = particleList.iterator();
		while(it.hasNext()){
			Particle particle = it.next();
			
			synchronized(gravityWellList){
				//apply all gravity well physics to the particle
				Iterator<GravityWell> gravityIt = gravityWellList.iterator();
				while(gravityIt.hasNext()){
					GravityWell gravityWell = gravityIt.next();
					gravityWell.applyGravity(particle);   
				}
			}
			
			//apply all repeller physics to the particle
			synchronized(repellerList){
				for(Repeller repeller : repellerList) {
					repeller.repel(particle);
				}
			}
			
			if(ModeVariables.chaseMouse){
				//apply a chase force to the particle based on all the targets that exist on the map.
				//this included the mouse position target.
				synchronized(targetList){
					for(Vector target : targetList){
						Vector force = Vector.subtract(target, particle.getLocation());
						force.normalize();
						force.multiply(.5);
						
						particle.applyForce(force);
					}
				}
				
			}else{
				particle.explosion(mousePosition);
			}
			
			//apply the wind physics to the particle
			if(ModeVariables.wind){
				particle.applyForce(wind);	
			}
			
			//update the particle.  This will cause it to move.
			particle.update();
			
			//remove any particles that have existed for longer than their lifespan.
			if(particle.isDead(currentTime)){
				it.remove();
			}
		}
	}
	
	/**
	 * Disposes of all gravityWells, targets, repellers, and particleSpawners 
	 * that have been placed on the screen.
	 * */
	private void reset(){	
		gravityWellList = new ArrayList<GravityWell>();
		targetList = new ArrayList<Vector>();
		
		repellerList = new ArrayList<Repeller>();
		
		targetList.add(mousePosition);
		
		//add the particle spawners to a new arrayList so the threads 
		//can be disposed of in an effective manner that doesn't cause lag.
		for(ParticleSpawner particleSpawner : particleSpawnerList){
			threadKiller.add(particleSpawner);
		}
		
		particleSpawnerList = new ArrayList<ParticleSpawner>();
		
		ModeVariables.reset = false;
	}
	
	/**
	 * Draw the velocity vector of each particle.
	 * 
	 * @param g - the Graphics context in which to paint.
	 * */
	private void drawVelocityVectorsOfParticles(Graphics g){
		Iterator<Particle> it = particleList.iterator();
		while(it.hasNext()){
			Particle particle = it.next();
			g.setColor(new Color(userInterface.getColor()));
			
			if(particle.insideBounds(Frame.SCREEN_WIDTH, Frame.SCREEN_HEIGHT)){
				particle.drawVelocityVector(g);
			}
		}
	}
	
	/**
	 * Draw all the particles as circles.
	 * 
	 * @param g - the Graphics context in which to paint.
	 * */
	private void drawParticlesAsCircles(Graphics g){		
		Iterator<Particle> it = particleList.iterator();
		while(it.hasNext()){
			Particle particle = it.next();
			g.setColor(new Color(userInterface.getColor()));
			int radius = particle.getRadius();
			g.fillOval(particle.getX() - radius / 2, particle.getY() - radius / 2, radius,radius);
		}
	}
	
	/**
	 * Sets all the pixels color to black (0x000000) to clear the pixels array and thus the screen.
	 * */
	private void clearPixels(){
		for(int i=0; i<Frame.SCREEN_WIDTH * Frame.SCREEN_HEIGHT; i++){
			pixels[i] = 0x000000;
		}
	}
	
	/**
	 * Fills the particleList with new particles.
	 * */
	private void createParticles(){
		for(int i=0; i<ModeVariables.numberOfParticles; i++){
			particleList.add(Particle.createParticle(random.nextInt(Frame.SCREEN_WIDTH), random.nextInt(Frame.SCREEN_HEIGHT), random.nextInt(0xFFFFFF)));
		}
	}
	
	/**
	 * Clears the particle list and fills it with new particles.
	 * */
	public void initializeNewParticles(){
		particleList = new ArrayList<Particle>();
		createParticles();
	}
	
	/**
	 * Sets the x and y value of the mouse position.  
	 * The mouse position in a target the particles will chase.
	 * 
	 * @param x - new x position of mouse.
	 * @param y - new y position of mouse.
	 * */
	public void setMousePosition(double x, double y){
		mousePosition.setX(x);
		mousePosition.setY(y);
	}
	
	/**
	 * creates a gravity well using the give parameters and 
	 * adds it to this component.
	 * 
	 * @param x - x position of gravity well.
	 * @param y - y position of gravity well.
	 * @param strength - strength at which the gravity well will attract particles.
	 * @param mass - mass of the gravity well.  
	 * More massive gravity wells will exert a greater force of attraction on particles
	 * */
	public void spawnGravityWell(int x, int y, int strength, int mass){
		synchronized(gravityWellList){
			gravityWellList.add(GravityWell.createGravityWell(x, y, strength, mass));
		}
	}
	
	/**
	 * Creates a target using the give parameters and 
	 * adds it to this component.
	 * 
	 * @param x - x position of the target.
	 * @param y - y position of the target.
	 * */
	public void spawnTarget(double x, double y){
		synchronized(targetList){
			targetList.add(new Vector(x, y));
		}

	}
	
	/**
	 * Creates a particle spawner using the give parameters and 
	 * adds it to this component.
	 * 
	 * @param x - x position of the new particle spawner.
	 * @param y - y position of the new particle spawner.
	 * @param frequency - number of particles that are spawner per second.
	 * @param lifespan - lifespan of particle in seconds.
	 * */
	public void spawnParticleSpawner(int x, int y, int frequency, int lifespan){
		synchronized(particleSpawnerList){
			particleSpawnerList.add(ParticleSpawner.createParticleSpawner(x, y, frequency, lifespan));
		}
	}
	
	/**
	 * Creates a repeller using the give parameters and 
	 * adds it to this component.
	 * 
	 * @param x - x position of the new repeller.
	 * @param y - y position of the new repeller.
	 * @param strength - strength at which the repeller will repel particles.
	 * */
	public void spawnRepeller(double x, double y, int strength){
		synchronized(repellerList){
			repellerList.add(Repeller.createRepeller(new Vector(x,y), strength));
		}
	}
	
	/**
	 * Determines if the user is lagging or close to lagging.
	 * 
	 * @return true if the program is close to or currently lagging and false otherwise.
	 * */
	private boolean isLagging(){
		if(renderTime < 0.9){ // 0.9 corresponds to 0.9 seconds for the game loop to complete 60 iterations.
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * displays a message giving the user instructions 
	 * if they attempt to add an object which could cause them to lag.
	 * */
	private void printLagMessage(){
		System.out.println("Spawning more would cause the game to lag.  Try reducing the number of particles.");
		System.out.println("if this functionality has not been implemented yet you can click 2 to spawn ball particles"
				+ " or 'c' to clear the screen of everything but the particles to reduce the load\n");
	}
}
