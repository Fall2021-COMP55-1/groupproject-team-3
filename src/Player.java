import java.awt.event.KeyEvent;
import java.util.ArrayList;
import acm.graphics.GImage;

public class Player extends Entity{
	private GImage sprite = getImage();
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, dx = 0, dy = 0, stepsTaken = 0;
	private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
	private Inventory inventory;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
	
	public Player(int x, int y)   {
		super(x, y);
		inventory = new Inventory();
		addImages();
	}
	
	public int getX()   {return x;}
	
	public int getY()   {return y;}
	
	public Inventory getInv()   {return inventory;}
	
	public void setInv(Inventory inventory)   {this.inventory = inventory;}
	
	@Override
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
	
	public void keyPressed(KeyEvent e) {
		
		if (keyUp(e)) {upPressed = true;}
		if (keyDown(e)) {downPressed = true;}
		if (keyLeft(e)) {leftPressed = true;}
		if (keyRight(e)) {rightPressed = true;}
		
		if (upPressed) {
			if(leftPressed || rightPressed)   {dy = -2;}
			else {dy = -4;}
			move(Player.dir.UP);
		}
		if (downPressed) {
			if(leftPressed || rightPressed)   {dy = 2;}
			else   {dy = 4;}
			move(Player.dir.DOWN);
        }
		if (leftPressed) {
			if(upPressed || downPressed)   {dx = -2;}
			else   {dx = -4;}
			move(Player.dir.LEFT);
        }
		if (rightPressed) {
			if(upPressed || downPressed)   {dx = 2;}
			else   {dx = 4;}
			move(Player.dir.RIGHT);
        }
		x = (int) sprite.getX();
		y = (int) sprite.getY();
	}
	
	public void keyReleased(KeyEvent e) {
		if (keyUp(e)) {upPressed = false;}
		if (keyDown(e)) {downPressed = false;}
		if (keyLeft(e)) {leftPressed = false;}
		if (keyRight(e)) {rightPressed = false;}
		
		if (upPressed)   {dy = -4;}
		else if (downPressed)   {dy = 4;}
		else   {dy = 0;}
		
		if (leftPressed)   {dx = -4;}
		else if (rightPressed)   {dx = 4;}
		else   {dx = 0;}
		
	}
		
	private void move(dir direction)   {
		int legTracker;
		if (stepsTaken == 8)   {stepsTaken = 0;}
		if (stepsTaken < 4)   {legTracker = 0;}
		else   {legTracker = 1;}
		if(direction == dir.UP)   {loadImage(spriteUp.get(legTracker).getImage());}
		if(direction == dir.DOWN)   {loadImage(spriteDown.get(legTracker).getImage());}
		if(direction == dir.LEFT)   {loadImage(spriteLeft.get(legTracker).getImage());}
		if(direction == dir.RIGHT)   {loadImage(spriteRight.get(legTracker).getImage());}
		move(dx, dy);
		
		/*// Check the would-be position to see how far you can move
		int moveToX = getX() + dx;
		int moveToY = getY() + dy;
		//GObject temp = program.getElementAt(moveToX, moveToY);
		
		// Convert pixel space to tile space and access it in MapPane
		int moveTileX = moveToX / 32;
		int moveTileY = moveToY / 32;*/
		stepsTaken = stepsTaken + 1;
	}
		
	private void move(int dx, int dy)   {
		sprite.move(dx, dy);
		this.x = (int) (sprite.getX() + dx);
		this.y = (int) (sprite.getY() + dy);
	}
}

