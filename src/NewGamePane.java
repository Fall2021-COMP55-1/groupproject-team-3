import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 

	private MapPane map;
	
	private Player player = new Player(0, 0);
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	
	private int x = 100, y = 100, dx = 0, dy = 0, stepsTaken = 0;
	private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
	//private double playerX=100, playerY=100;
	
	
	//Shortcut to check on whether WASD / arrow keys are being pressed
	private boolean keyUp(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)   {return true;}
		else {return false;}	
	}
	
	private boolean keyDown(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)   {return true;}
		else {return false;}	
	}
	
	private boolean keyLeft(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)   {return true;}
		else {return false;}
	}
	
	private boolean keyRight(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)   {return true;}
		else {return false;}
	}
	
	//shortcut for movement. Works with "up", "down", "left," and "right" as inputs for dir
	private void move(String dir)   {
		int legTracker;

		if (stepsTaken == 8)   {stepsTaken = 0;}
		if (stepsTaken < 4)   {legTracker = 0;}
		else   {legTracker = 1;}
		if (dir == "up")   {player.loadImage(spriteUp.get(legTracker).getImage());}
		if (dir == "down")   {player.loadImage(spriteDown.get(legTracker).getImage());}
		if (dir == "left")   {player.loadImage(spriteLeft.get(legTracker).getImage());}
		if (dir == "right")   {player.loadImage(spriteRight.get(legTracker).getImage());}
		player.move(dx, dy);
		stepsTaken = stepsTaken + 1;
	}
	
	public void addImages() {
		spriteUp = new ArrayList<GImage>(2);
		spriteDown = new ArrayList<GImage>(2);
		spriteLeft = new ArrayList<GImage>(2);
		spriteRight = new ArrayList<GImage>(2);
		
		for (int i = 1; i <= 2; ++i)   {
			spriteUp.add(new GImage("res/player/PCU" + i + ".png"));
			spriteDown.add(new GImage("res/player/PCD" + i + ".png"));
			spriteLeft.add(new GImage("res/player/PCL" + i + ".png"));
			spriteRight.add(new GImage("res/player/PCR" + i + ".png"));
		}
		
	}
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		addImages();
		
		map = new MapPane(program);
		
	}

	@Override
	public void showContents() {
		map.showContents();
		player.loadImage(spriteDown.get(0).getImage());
		program.add(player.getImage(), x, y);

	}

	@Override
	public void hideContents() {
		map.hideContents();
		program.remove(player.getImage());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
	
	//moves the sprite of the character via WASD and Arrow keys, and changes direction sprite faces
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (keyUp(e)) {upPressed = true;}
		if (keyDown(e)) {downPressed = true;}
		if (keyLeft(e)) {leftPressed = true;}
		if (keyRight(e)) {rightPressed = true;}
		
		if (upPressed) {
			if(leftPressed || rightPressed)   {dy = -2;}
			else {dy = -4;}
			move("up");
		}
		if (downPressed) {
			if(leftPressed || rightPressed)   {dy = 2;}
			else   {dy = 4;}
			move("down");
        }
		if (leftPressed) {
			if(upPressed || downPressed)   {dx = -2;}
			else   {dx = -4;}
			move("left");
        }
		if (rightPressed) {
			if(upPressed || downPressed)   {dx = 2;}
			else   {dx = 4;}
			move("right");
        }
	}
	
	//manages the use case of user attempting to use multiple movement directions at once, to use diagonal movement
	@Override
	public void keyReleased(KeyEvent e) {
		if (keyUp(e)) {upPressed = false;}
		if (keyDown(e)) {downPressed = false;}
		if (keyLeft(e)) {leftPressed = false;}
		if (keyRight(e)) {rightPressed = false;}
		
		if (upPressed)   {dy = -4;}
		else if (downPressed)   {dy = 4;}
		else   {dy = 0;}
		
		if (leftPressed)   {dx = -4;;}
		else if (rightPressed)   {dx = 4;;}
		else   {dx = 0;}
		
	}
}
