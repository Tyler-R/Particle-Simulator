

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener, MouseMotionListener{
	
	Vector mousePosition;
	
	public Mouse(Vector mousePosition){
		this.mousePosition = mousePosition;
	}


	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {

	}


	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {
		System.out.println("mouse x = " + mousePosition.getX());
		System.out.println("mouse y = " + mousePosition.getY());
	}

	public void mouseReleased(MouseEvent arg0) {
		
		
	}

	public void mouseDragged(MouseEvent arg0) {
		
		
	}

	public void mouseMoved(MouseEvent mouse) {
		mousePosition.setX(mouse.getX());
		mousePosition.setY(mouse.getY());
	}

}
