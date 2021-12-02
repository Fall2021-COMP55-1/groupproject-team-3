package GamePanes;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class PausePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 

	private GButton ResumeGame, Quit;

	public PausePane(MainApplication app) {
		this.program = app;
		ResumeGame = new GButton("Resume", 255, 200, 250, 60);  
		Quit = new GButton("To Main Menu", 255, 400, 250, 60); 
		
		//when item selected and click menu, redBox is still there
		//it doesn't print then why is it on the screen?
		//program.player.getInventory().setHighlightVisible(false);
	}
	
	@Override
	public void showContents() {
		program.add(ResumeGame);
		program.add(Quit);
		//redBox still shows
		program.player.getInventory().setHighlightVisible(false);

	}

	@Override
	public void hideContents() {
		program.remove(ResumeGame);
		program.remove(Quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == ResumeGame) {
			if(program.fromPausetoBed) {
				program.switchToBedRoom();
			}else {
				program.switchToNewGame();
			}
		}	
		if (obj == Quit) {program.switchToMenu();}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}
