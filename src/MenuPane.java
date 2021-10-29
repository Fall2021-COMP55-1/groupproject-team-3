import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
								
	private GButton rect;
	private GButton newGame;
	private GButton loadGame;
	private GButton options;
	private GButton exitGame;
	private GImage background;
	private final int BUTTON_SIZE = 50;
	private final int WIDTH = 200;
	private final int HEIGHT = 80;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		background = new GImage("res/texture/Main Menu.png", 0, 0);
		background.setSize(800,600);
		rect = new GButton("Next", app.getWidth()/2-BUTTON_SIZE/2, app.getHeight()/2-BUTTON_SIZE/2, BUTTON_SIZE, BUTTON_SIZE);
		newGame = new GButton("", app.getWidth()/2-WIDTH/2-7, 180, WIDTH, HEIGHT);
		newGame.sendToFront();
		loadGame = new GButton("", app.getWidth()/2-WIDTH/2-7, 286, WIDTH, HEIGHT);
		options = new GButton("", app.getWidth()/2-WIDTH/2-7, 392, WIDTH, HEIGHT);
		exitGame = new GButton("", app.getWidth()/2-WIDTH/2-7, 500, WIDTH, HEIGHT);
	}
	

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(newGame);
		program.add(loadGame);
		program.add(options);
		program.add(exitGame);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(rect);
		program.remove(newGame);
		program.remove(loadGame);
		program.remove(options);
		program.remove(exitGame);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
		if (obj == newGame) {
			program.switchToNewGame();
		}
	}
}
