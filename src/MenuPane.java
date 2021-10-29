import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
								
	private GButton rect;
	private GButton newGame;
	private GButton loadGame;
	private GButton options;
	private GButton exitGame;
	private final int BUTTON_SIZE = 50;
	private final int WIDTH = 100;
	private final int HEIGHT = 50;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		rect = new GButton("Next", app.getWidth()/2-BUTTON_SIZE/2, app.getHeight()/2-BUTTON_SIZE/2, BUTTON_SIZE, BUTTON_SIZE);
		rect.setFillColor(Color.RED);
		newGame = new GButton("New Game", app.getWidth()/2-WIDTH/2, app.getHeight()/2-HEIGHT/2, WIDTH, HEIGHT);
		newGame.setFillColor(Color.yellow);
		loadGame = new GButton("Load Game", app.getWidth()/2-WIDTH/2, app.getHeight()/2+HEIGHT/2 +10, WIDTH, HEIGHT);
		loadGame.setFillColor(Color.yellow);
		
	}
	

	@Override
	public void showContents() {
		program.add(rect);
		program.add(newGame);
		program.add(loadGame);
	}

	@Override
	public void hideContents() {
		program.remove(rect);
		program.remove(newGame);
		program.remove(loadGame);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
	}
}
