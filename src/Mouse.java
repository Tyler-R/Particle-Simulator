

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener, MouseMotionListener{
	
	private Screen screen;
	
	public Mouse(Screen screen){
		this.screen = screen;
	}


	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {

	}


	public void mouseExited(MouseEvent arg0) {

	}
	
	public void mousePressed(MouseEvent mouse) {
		if(mouse.getButton() == MouseEvent.BUTTON1){ //if the user left clicks
			ModeVariables.chaseMouse = false;
		}else if(mouse.getButton() == MouseEvent.BUTTON3){ //if the user right clicks
			
			if(ModeVariables.spawnGravityWell){ //spawn a gravity well
				
				screen.spawnGravityWell(mouse.getX(), mouse.getY(), 100, 10);
				
			}else if(ModeVariables.spawnTarget){ //spawn a target
				screen.spawnTarget(mouse.getX(), mouse.getY());
				
			}else if(ModeVariables.spawnParticleSpawner){ //spawn a particle spawner
				screen.spawnParticleSpawner(mouse.getX(), mouse.getY(), 50, 5);
				
			}else if(ModeVariables.spawnRepeller){ //spawn a repeller
				screen.spawnRepeller(mouse.getX(), mouse.getY(), 4000);
				
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1){
			ModeVariables.chaseMouse = true;
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		
		
	}

	public void mouseMoved(MouseEvent mouse) {
		screen.setMousePosition(mouse.getX(), mouse.getY());
	}

}
