package GamePanes;
import java.awt.Color;
import java.awt.event.MouseEvent;

import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WinPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GLabel win;
	private GButton Quit;

	public WinPane(MainApplication app) {
		this.program = app;
		win = new GLabel("You escaped the house!",255,200);
		win.setColor(Color.black);
		Quit = new GButton("Go back to Main Menu", 255, 400, 250, 60);
	}
	
	@Override
	public void showContents() {
		program.add(win);
		program.add(Quit);
	}

	@Override
	public void hideContents() {
		program.remove(win);
		program.remove(Quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Quit) {program.switchToMenu();}
	}
}
