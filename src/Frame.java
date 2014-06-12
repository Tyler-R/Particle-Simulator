import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame{
	private Screen screen;
	
	//width and height of frame
	private static final int SCALE = 5;
	public static final int WIDTH = 160 * SCALE; //800
	public static final int HEIGHT = 120 * SCALE; //600
	
	//title of frame
	private static final String TITLE = "Rendering practice";
	
	//private Thread thread;
	
	//private boolean running = false;
	
	/**Buffers*//*
	private BufferStrategy bs;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	*/
	public Frame(){
		
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		//screen must be before set visible
		screen = new Screen();
		add(screen);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		//setResizable(false);
		//setBackground(Color.red);
		
		//createBufferStrategy(3);
		//bs = getBufferStrategy();
		
		
		
		init();
	}
	
	private void init(){
		
		screen.repaint();
		//start();
		
	}
	/*
	public synchronized void start(){
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		
		try{
			thread.join();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		/*
		try {
			image = ImageIO.read(new File("full pixels.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<500; i++){
			for(int j=0; j<500; j++){
				pixels[i * j] =  Color.red.getRGB();
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		
		for(int i=0; i<500; i++){
			for(int j=0; j<500; j++){
				g.drawImage(image, image.getRGB(i, j), i, 1,1, null);
			}
		}
		
		g.drawImage(image,50,50,50,50,null);
		g.fillRect(100, 100, 50, 50);
		g.dispose();
*//*
		while(running){
			
			screen.repaint();
			//System.out.println(Color.white.getRGB());
		}
		
		
		
		
		
		
	}
*/
	
	public static void main(String[] args){
		Frame frame = new Frame();
	}
}

class TestGC{
	public void finalize(){
		System.out.println("in testGC finalzie()");
	}
	
}