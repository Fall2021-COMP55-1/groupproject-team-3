package GamePanes;
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
	}
	public void pause() {
		ResumeGame.setVisible(true);
		Quit.setVisible(true);
		program.add(ResumeGame);
		program.add(Quit);
	}

	public void resume() {
		ResumeGame.setVisible(false);
		program.remove(ResumeGame);
		Quit.setVisible(false);
		program.remove(Quit);
	}
	
	@Override
	public void showContents() {
		program.add(ResumeGame);
		program.add(Quit);
	}

	@Override
	public void hideContents() {
		program.remove(ResumeGame);
		program.remove(Quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == ResumeGame) {program.switchToNewGame();}
		if (obj == Quit) {program.switchToMenu();}
	}
}
