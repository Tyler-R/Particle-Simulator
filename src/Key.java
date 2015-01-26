import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Key extends KeyAdapter{
	private Screen screen;
	private UserInterface userInterface;
	
	public Key(Screen screen, UserInterface userInterface) {
		this.screen = screen;
		this.userInterface = userInterface;
	}
	
	//temporary user interface for program
	public void keyPressed(KeyEvent e) {
		userInterface.click(e);
	}
}
