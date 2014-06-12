import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Key extends KeyAdapter{
	private Screen screen;
	
	public Key(Screen screen){
		this.screen = screen;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		
		if(keyPressed == KeyEvent.VK_1){
			if(ModeVariables.drawVectors){
				ModeVariables.drawVectors = false;	
			}else{
				ModeVariables.drawVectors = true;
			}
		}else if(keyPressed == KeyEvent.VK_2){
			ModeVariables.sameLimits = true;
			ModeVariables.sameRandomLimits = false;
			ModeVariables.differentRandomLimits = false;
			screen.initializeNewParticles();
			
		}else if(keyPressed == KeyEvent.VK_3){
			ModeVariables.sameLimits = false;
			ModeVariables.sameRandomLimits = true;
			ModeVariables.differentRandomLimits = false;
			screen.initializeNewParticles();
			
		}else if(keyPressed == KeyEvent.VK_4){
			ModeVariables.sameLimits = false;
			ModeVariables.sameRandomLimits = false;
			ModeVariables.differentRandomLimits = true;
			screen.initializeNewParticles();
		}
	}
}
