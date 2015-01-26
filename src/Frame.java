import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class Frame extends JFrame{
	private Screen screen;
	private UserInterface userInterface;
	
	//width and height of frame
	private static final int SCALE = 5;
	public static final int SCREEN_WIDTH = 160 * SCALE; //800
	public static final int SCREEN_HEIGHT = 120 * SCALE; //600
	public static final int UI_WIDTH = 160;
	public static final int UI_HEIGHT = 120 * SCALE; //600
	
	//title of frame
	private static final String TITLE = "Rendering practice";

	public Frame(){
		userInterface = new UserInterface();
		screen = new Screen(userInterface);
		screen.start();
		
		setSize(SCREEN_WIDTH + UI_WIDTH, SCREEN_HEIGHT);
		setTitle(TITLE);
		
		setLayout(new BorderLayout());
		
		//screen must be added before set visible
		add(screen, BorderLayout.CENTER);
		add(userInterface, BorderLayout.EAST);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		//setResizable(false);

		init();
	}
	
	private void init(){
		
	}
	
	public static void main(String[] args){
		Frame frame = new Frame();
		
		//instructions for user
		
		System.out.println("left click to cause an explosion");
		System.out.println("right click to place an object once it has been selected with buttons 4-7");
		System.out.println("pressing 1 will draw the velocity vectors of each particle");
		System.out.println("pressing 2 will switch between drawing the particles as balls and as particles");
		System.out.println("pressing 3 will make the outside walls of the frame impassable");
		System.out.println("pressing 4 causes your right click to spawn a gravity well");
		System.out.println("pressing 5 causes your right click to spawn another target to chase just like your mouse");
		System.out.println("pressing 6 causes your right click to spawn a particle spawner that will spawn new particles that will disapear after 10 seconds");
		System.out.println("pressing 7 causes your right click to spawn a repeller that pushes particles away from it");
		System.out.println("pressing space will cause wind to blow the particles to the right");
		System.out.println("pressing 'c' reset all the objects that have been placed on the screen");
		
	}
}