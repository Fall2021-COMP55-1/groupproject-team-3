package Boilerplate;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Entity.*;
import Item.*;
import GamePanes.*;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class MainApplication extends GraphicsProgram {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 640;
	public static final String MUSIC_FOLDER = "sounds";

	private GraphicsPane curScreen;
	private PausePane pause;
	private MenuPane menu;
	private NewGamePane newGame;
	private SavePane save;
	private OptionPane options;
	private Credits credits; 
	private BedRoomGamePane bedroom;
	private GoodEndPane goodEnd;
	private BadEndPane badEnd;
	public boolean fromBedtoLiving = false;
	public boolean fromPausetoBed = false;
	public boolean fromPausetoLiving = false;
	public Player player = new Player(0, 0);
	private ArrayList<Item> items = new ArrayList <Item>();
	public int lastPlayerX, lastPlayerY;
	
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public int numItems() {
		return items.size();
	}
	
	public Item itemAt(int i) {
		return items.get(i);
	}
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	protected void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	/* switchToScreen(newGraphicsPane)
	 * -------------------------------
	 * will simply switch from making one pane that was currently
	 * active, to making another one that is the active class.
	 */
	protected void switchToScreen(GraphicsPane newScreen) {
		if(curScreen != null) {
			curScreen.hideContents();
		}
		newScreen.showContents();
		curScreen = newScreen;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseReleased(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseClicked(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseMoved(e);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyPressed(e);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyReleased(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyTyped(e);
		}
	}
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	
	}

	public void run() {
		System.out.println("Let's make something awesome!");
		pause = new PausePane(this);
		menu = new MenuPane(this);
		newGame = new NewGamePane(this);
		save = new SavePane(this);
		options = new OptionPane(this);
		credits = new Credits(this);
		bedroom = new BedRoomGamePane(this);
		goodEnd = new GoodEndPane(this);
		badEnd = new BadEndPane(this);
		setupInteractions();
		switchToMenu();
	}

	public void switchToMenu() {switchToScreen(menu);}

	public void switchToPause() {switchToScreen(pause);}
	
	public void switchToNewGame() {switchToScreen(newGame);}
	
	public void switchToSave() {switchToScreen(save);}
	
	public void switchToOptions() {switchToScreen(options);}
	
	public void switchToCredits() {switchToScreen(credits);}

	public void switchToBedRoom() {switchToScreen(bedroom);}
	
	public void switchToGoodEnd() {switchToScreen(goodEnd);}
	
	public void switchToBadEnd() {switchToScreen(badEnd);}

	public static void main(String[] args) {new MainApplication().start();}

}
