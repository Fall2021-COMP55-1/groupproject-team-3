package GamePanes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class BadEndPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GLabel lostGame;
	private GButton Quit;

	public BadEndPane(MainApplication app) {
		this.program = app;
		lostGame = new GLabel("wasted!",255,200);
		lostGame.setColor(Color.black);
		Quit = new GButton("Exit", 255, 400, 250, 60);
	}
	
	@Override
	public void showContents() {
		program.add(lostGame);
		program.add(Quit);
	}

	@Override
	public void hideContents() {
		program.remove(lostGame);
		program.remove(Quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Quit) {System.exit(0);}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}

