import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Player extends Entity{
	private GImage sprite = getImage();
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, dx = 0, dy = 0, stepsTaken = 0;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
		
	public Player(int x, int y)   {
		super(x, y);
		addImages();
	}
	
	public int getDX()   {return dx;}
	
	public int getDY()   {return dy;}
	
	public void setDX(int dx)   {this.dx = dx;}
	
	public void setDY(int dy)   {this.dy = dy;}
	
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
		
	protected void move(dir direction)   {
		int legTracker;
		if (stepsTaken == 8)   {stepsTaken = 0;}
		if (stepsTaken < 4)   {legTracker = 0;}
		else   {legTracker = 1;}
		if(direction == dir.UP)   {loadImage(spriteUp.get(legTracker).getImage());}
		if(direction == dir.DOWN)   {loadImage(spriteDown.get(legTracker).getImage());}
		if(direction == dir.LEFT)   {loadImage(spriteLeft.get(legTracker).getImage());}
		if(direction == dir.RIGHT)   {loadImage(spriteRight.get(legTracker).getImage());}
		move(dx, dy);
		
		// Check the would-be position to see how far you can move
		int moveToX = getX() + dx;
		int moveToY = getY() + dy;
		//GObject temp = program.getElementAt(moveToX, moveToY);
		
		// Convert pixel space to tile space and access it in MapPane
		int moveTileX = moveToX / 32;
		int moveTileY = moveToY / 32;
		stepsTaken = stepsTaken + 1;
	}
		
	private void move(int x, int y)   {
		sprite.move(x, y);
		this.x = this.x + x;
		this.y = this.y + y;
	}	
}

