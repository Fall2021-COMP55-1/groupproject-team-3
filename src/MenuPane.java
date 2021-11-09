import java.awt.Color;


import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
								
	private GButton newGame;
	private GButton loadGame;
	private GButton options;
	private GButton exitGame;
	private GImage background;
	private final int WIDTH = 200;
	private final int HEIGHT = 88;


	public MenuPane(MainApplication app) {
		super();
		program = app;
		double X = app.getWidth()/2 - WIDTH/2 -7;
		background = new GImage("res/texture/Main Menu.png", 0, 0);
		background.setSize(800,640);
		newGame = new GButton("", X, 190, WIDTH, HEIGHT);
		loadGame = new GButton("", X, 305, WIDTH, HEIGHT);
		options = new GButton("", X, 418, WIDTH, HEIGHT);
		exitGame = new GButton("", X, 532, WIDTH, HEIGHT);
	}
	

	@Override
	public void showContents() {
		program.add(background);
		program.add(newGame);
		program.add(loadGame);
		program.add(options);
		program.add(exitGame);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(newGame);
		program.remove(loadGame);
		program.remove(options);
		program.remove(exitGame);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == newGame) {
			program.switchToNewGame();
		}
		if (obj == loadGame) {
			program.switchToSave();
		}	
		if (obj == options) {
			program.switchToOptions();
		}
		if (obj == exitGame) {
			System.exit(0);
		}
	}
}
