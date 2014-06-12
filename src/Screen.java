import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;


public class Screen extends JPanel implements Runnable{
	private BufferedImage image;
	
	public int[] pixels;

	private Vector mousePosition = new Vector(0, 0);
	
	private ArrayList<Particle> particleList = new ArrayList<Particle>();
	
	private boolean running = false;
	private Thread thread;
	
	private static Random random = new Random();
	
	public Screen(){
		//creates the main buffered image
		image = new BufferedImage(Frame.WIDTH, Frame.HEIGHT, BufferedImage.TYPE_INT_RGB);
		//links the pixels array with the bufferedImage object
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
		setFocusable(true);
		addMouseMotionListener(new Mouse(mousePosition));
		addKeyListener(new Key(this));
		init();
	}
	
	private void init(){
		createParticles();
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {	
		double NS_PER_FRAME = 1000000000 / 60.0;
		int frames = 0;
		while (running){
			
		  double start = System.nanoTime();
		  repaint();
		  frames++;
		  
		  try {
			Thread.sleep((long)(start + NS_PER_FRAME - System.nanoTime()) / 1000000);
		  } catch (InterruptedException e) {
			System.out.println("thread in main game loop could not sleep");
		  }
		  
		  if(frames >= 60){
			  //System.out.println("Frames = " + frames);
			  frames = 0;
		  }
		}
	}
	
	public void paintComponent(Graphics g){
		clearPixels();
		
		loadParticles();
		
		g.drawImage(image, 0, 0, 800, 600, null);
		
		if(ModeVariables.drawVectors){
			drawVectorsOfParticles(g);	
		}
	}
	
	private void drawVectorsOfParticles(Graphics g) {
		Iterator<Particle> it = particleList.iterator();
		while(it.hasNext()){
			Particle particle = it.next();
			g.setColor(new Color(particle.getColor()));
			
			if(particle.insideBounds(Frame.WIDTH, Frame.HEIGHT)){
				particle.draw(g);
			}
		}
		
	}

	private void clearPixels(){
		for(int i=0; i<800 * 600; i++){
			pixels[i] = 0x000000;
		}
	}
	
	private void createParticles(){
		/*
		for(int x=400; x< 410; x++){
			for(int y=300; y<310; y++){
				particleList.add(new Particle(x, y, random.nextInt(0xFFFFFF), mousePosition));
			}
		}
		*/
		
		for(int i=0; i<ModeVariables.numberOfParticles; i++){
			particleList.add(new Particle(random.nextInt(Frame.WIDTH), random.nextInt(Frame.HEIGHT), random.nextInt(0xFFFFFF), mousePosition));
		}
	}
	
	public void initializeNewParticles(){
		particleList = new ArrayList<Particle>();
		createParticles();
	}
	
	private void loadParticles(){
		Iterator<Particle> it = particleList.iterator();
		while(it.hasNext()){
			Particle particle = it.next();
			particle.update();
			
			if(particle.insideBounds(Frame.WIDTH, Frame.HEIGHT)){
				pixels[particle.getX() + particle.getY() * 800] = particle.getColor();	
			}
		}
	}
}
