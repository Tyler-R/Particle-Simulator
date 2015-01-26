import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class UserInterface extends JPanel{
	JButton clear;
	
	private ButtonGroup spawnerGroup = new ButtonGroup(); 
	
	private JRadioButton spawnGravityWell;
	private JRadioButton spawnTarget;
	private JRadioButton spawnParticleSpawner;
	private JRadioButton spawnRepeller;
	
	private JCheckBox drawVectors;
	private JCheckBox solidWalls;
	private JCheckBox drawBalls;
	private JCheckBox wind;
	
	private JSlider particleColorRed;
	private JSlider particleColorGreen;
	private JSlider particleColorBlue;
	
	private int particleColor = 0x9B30FF;
	
	public UserInterface() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initCheckBoxes();
		addCheckBoxes();
		
		initRadioButtons();
		addRadioButtonsToGroup();
		addRadioButtons();
		
		add(wind);
		
		initButtons();
		addButtons();
		
		initSlider();
		addSlider();
		
		clear.setFocusable(false);
		spawnGravityWell.setFocusable(false);
		spawnTarget.setFocusable(false);
		spawnParticleSpawner.setFocusable(false);
		spawnRepeller.setFocusable(false);
		drawVectors.setFocusable(false);
		solidWalls.setFocusable(false);
		drawBalls.setFocusable(false);
		wind.setFocusable(false);
		particleColorRed.setFocusable(false);
		particleColorGreen.setFocusable(false);
		particleColorBlue.setFocusable(false);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	private void initCheckBoxes() {
		drawVectors = new JCheckBox("Draw Vectors (1)");
		drawBalls = new JCheckBox("Draw Balls (2)");
		solidWalls = new JCheckBox("solid walls (3)");
		wind = new JCheckBox("Wind (space)");
	}
	
	private void addCheckBoxes() {
		drawVectors.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.drawVectors = !ModeVariables.drawVectors;
			}
		});
		
		drawVectors.setAlignmentX(LEFT_ALIGNMENT);
		add(drawVectors);
		
		
		drawBalls.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.renderParticlesAsBalls = !ModeVariables.renderParticlesAsBalls;
			}
		});
		
		drawBalls.setAlignmentX(LEFT_ALIGNMENT);
		add(drawBalls);
		
		
		solidWalls.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.walls = !ModeVariables.walls;
			}
		});
		
		solidWalls.setAlignmentX(LEFT_ALIGNMENT);
		add(solidWalls);
		
		
		wind.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.wind = !ModeVariables.wind;
			}
		});
		
		wind.setAlignmentX(LEFT_ALIGNMENT);
	}

	private void initRadioButtons() {
		spawnGravityWell = new JRadioButton("Spawn Gravity Well (4)");
		spawnTarget = new JRadioButton("Spawn Target (5)");
		spawnParticleSpawner = new JRadioButton("Spawn Particle Spawner (6)");
		spawnRepeller = new JRadioButton("Spawn Repeller (7)");
	}
	
	private void addRadioButtonsToGroup(){
		spawnerGroup.add(spawnGravityWell);
		spawnerGroup.add(spawnTarget);
		spawnerGroup.add(spawnParticleSpawner);
		spawnerGroup.add(spawnRepeller);
	}
	
	private void addRadioButtons(){
		spawnGravityWell.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.spawnGravityWell = true;
				ModeVariables.spawnTarget = false;
				ModeVariables.spawnParticleSpawner = false;
				ModeVariables.spawnRepeller = false;
			}
		});
		
		spawnGravityWell.setAlignmentX(LEFT_ALIGNMENT);
		add(spawnGravityWell);
		
		spawnTarget.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.spawnGravityWell = false;
				ModeVariables.spawnTarget = true;
				ModeVariables.spawnParticleSpawner = false;
				ModeVariables.spawnRepeller = false;
			}
		});
		
		spawnTarget.setAlignmentX(LEFT_ALIGNMENT);
		add(spawnTarget);
		
		spawnParticleSpawner.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.spawnGravityWell = false;
				ModeVariables.spawnTarget = false;
				ModeVariables.spawnParticleSpawner = true;
				ModeVariables.spawnRepeller = false;
			}
		});
		
		spawnParticleSpawner.setAlignmentX(LEFT_ALIGNMENT);
		add(spawnParticleSpawner);
		
		spawnRepeller.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.spawnGravityWell = false;
				ModeVariables.spawnTarget = false;
				ModeVariables.spawnParticleSpawner = false;
				ModeVariables.spawnRepeller = true;
			}
		});
		
		spawnRepeller.setAlignmentX(LEFT_ALIGNMENT);
		add(spawnRepeller);
	}

	private void initButtons() {
		clear = new JButton("Clear");
	}
	
	private void addButtons(){
		
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ModeVariables.reset = true;
			}
		});
		
		clear.setAlignmentX(LEFT_ALIGNMENT);
		add(clear);
	}
	
	private void initSlider() {
		particleColorRed = new JSlider(0, 0xFF, 0x9B);
		particleColorGreen = new JSlider(0, 0xFF, 0x30);
		particleColorBlue = new JSlider(0, 0xFF, 0xFF);
	}
	
	private void addSlider() {
		particleColorRed.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				particleColor = new Color(particleColorRed.getValue(), particleColorGreen.getValue(), particleColorBlue.getValue()).getRGB();
				
			}
			
		});
		
		
		particleColorGreen.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				particleColor = new Color(particleColorRed.getValue(), particleColorGreen.getValue(), particleColorBlue.getValue()).getRGB();
			}
			
		});
		
		particleColorBlue.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				particleColor = new Color(particleColorRed.getValue(), particleColorGreen.getValue(), particleColorBlue.getValue()).getRGB();
			}
			
		});
		
		
		
		add(particleColorRed);
		add(particleColorGreen);
		add(particleColorBlue);
	}
	
	public int getColor() { 
		return particleColor;
	}
	
	public void click(KeyEvent e) { 
		int keyPressed = e.getKeyCode();
		
		if(keyPressed == KeyEvent.VK_1){
			drawVectors.doClick();
			
		}if(keyPressed == KeyEvent.VK_2){
			drawBalls.doClick();
			
		}if(keyPressed == KeyEvent.VK_3){
			solidWalls.doClick();
			
		}if(keyPressed == KeyEvent.VK_4){
			spawnGravityWell.doClick();
			
		}if(keyPressed == KeyEvent.VK_5){
			spawnTarget.doClick();
			
		}if(keyPressed == KeyEvent.VK_6){
			spawnParticleSpawner.doClick();
			
		}if(keyPressed == KeyEvent.VK_7){
			spawnRepeller.doClick();
			
		}if(keyPressed == KeyEvent.VK_SPACE){
			wind.doClick();
			
		}if(keyPressed == KeyEvent.VK_C){
			clear.doClick();
		}
		
	}
	
}
