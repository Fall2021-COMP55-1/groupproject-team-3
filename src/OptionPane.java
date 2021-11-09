import java.awt.Color;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;


public class OptionPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GImage img;
	private GButton Credits; 
	private GButton Back; 
	private final int WIDTH = 200; 
	private final int HEIGHT = 88; 

	public OptionPane(MainApplication app) {
		super();
		this.program = app;
		program = app;
		double X = app.getWidth()/2 - WIDTH/2 -7;
		img = new GImage("res/texture/Options.png", 0, 0);
		img.setSize(800, 640);	
		Credits = new GButton("", X, 418, WIDTH, HEIGHT);
		Back = new GButton("", X, 532, WIDTH, HEIGHT);
		
	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(Credits);
		program.add(Back);
		
	}

	@Override
	public void hideContents() {
		program.remove(img);
		program.remove(Credits);
		program.remove(Back);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Back) {
			program.switchToMenu();
		}
		if (obj == Credits) {
			program.switchToCredits();
		}
		
	}

}

