import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Player {
	private GImage sprite = new GImage("res/player/PCU1.png");
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 100, y = 100, dx = 0, dy = 0, stepsTaken = 0, width = 32, height = 32;
	private boolean visible;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
	
	public Player(int x, int y)   {
		this.x = x;
		this.y = y;
		visible = true;
		addImages();
		loadImage(spriteDown.get(0).getImage());
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
		stepsTaken = stepsTaken + 1;
	}
	
	public int getDX()   {return dx;}
	
	public int getDY()   {return dy;}
	
	public int getX()   {return x;}
	
	public int getY()   {return y;}
	
	public GImage getImage()   {return sprite;}
	
	public boolean isVisible()   {return visible;}
	
	public GRect getBounds()   {return new GRect(x, y, width, height);}
	
	public void setVisible(boolean visible)   {this.visible = visible;}
	
	protected void loadImage(Image image)   {sprite.setImage(image);}
	
	private void move(int x, int y)   {
		sprite.move(x, y);
		this.x = this.x + x;
		this.y = this.y + y;
	}
	
	public void setDX(int dx)   {this.dx = dx;}
	
	public void setDY(int dy)   {this.dy = dy;}
	
}
