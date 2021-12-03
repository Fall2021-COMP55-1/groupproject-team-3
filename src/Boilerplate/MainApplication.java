package Boilerplate;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.FloatControl;

import Entity.*;
import Item.*;
import GamePanes.*;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

public class MainApplication extends GraphicsProgram {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 640;
	public static final String MUSIC_FOLDER = "sounds";
		
	private GraphicsPane curScreen;
	private MenuPane menu;
	private NewGamePane newGame;
	private SavePane save;
	private OptionPane options;
	private Credits credits; 
	private BedRoomGamePane bedroom;
	private GoodEndPane goodEnd;
	private BadEndPane badEnd;

	public Player player = new Player(0, 0, this);
	public boolean fromBedtoLiving = false, fromPausetoBed = false,fromPausetoLiving = false;
	private ArrayList<Item> items = new ArrayList <Item>();
	public boolean paused = false;
	public GButton resume = new GButton("", 296, 180, 208, 95);
	private GImage resumeImg = new GImage("res/texture/Resume.png",296,180);
	public GButton quit = new GButton("", 296, 420, 208, 95);
	private GImage quitImg = new GImage("res/texture/Quit.png",296, 420);
	public static MusicBox music = new MusicBox();
	
	public void pause() { 
		this.add(resumeImg);
		this.add(quitImg);
		this.add(resume);
		this.add(quit);
		paused = true;
	}
	
	public void resume() {
		this.remove(resume);
		this.remove(quit);
		this.remove(resumeImg);
		this.remove(quitImg);
		paused = false;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public static MusicBox getMusic() {return music;}
	
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
			if(!paused) {
				curScreen.keyPressed(e);
			}
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
		File Audio = new File("res/music/TestAudio.wav");
		music.PLAY(Audio);
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
	
	public void switchToNewGame() {switchToScreen(newGame);}
	
	public void switchToSave() {switchToScreen(save);}
	
	public void switchToOptions() {switchToScreen(options);}
	
	public void switchToCredits() {switchToScreen(credits);}

	public void switchToBedRoom() {switchToScreen(bedroom);}
	
	public void switchToGoodEnd() {switchToScreen(goodEnd);}
	
	public void switchToBadEnd() {switchToScreen(badEnd);}

	public static void main(String[] args) {new MainApplication().start();}

}
