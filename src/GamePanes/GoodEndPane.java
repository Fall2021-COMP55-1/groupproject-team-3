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

public class GoodEndPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GLabel wonGame;
	private GButton Quit;

	public GoodEndPane(MainApplication app) {
		this.program = app;
		wonGame = new GLabel("You escaped the house!",255,200);
		wonGame.setColor(Color.black);
		Quit = new GButton("Exit", 255, 400, 250, 60);
	}
	
	@Override
	public void showContents() {
		program.add(wonGame);
		program.add(Quit);
	}

	@Override
	public void hideContents() {
		program.remove(wonGame);
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
