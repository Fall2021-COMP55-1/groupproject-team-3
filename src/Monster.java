import java.util.ArrayList;
import acm.graphics.GImage;

public class Monster extends Entity{
	private GImage sprite = getImage();
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, dx = 0, dy = 0, stepsTaken = 0, width, height;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
			
	public Monster(int x, int y, MonsterType type)   {
		super(x, y);
		addImages();
		loadImage(this.spriteDown.get(0).getImage());
	}
	
	@Override
	public int getX()   {return x;}
	
	@Override
	public int getY()   {return y;}
	
	private int getWidth()   {return width;}
	
	private int getHeight()   {return height;}
	
	public void setX()   {this.x = x;}
	
	public void setY()   {this.y = y;}
	
	private void setWidth(int width)   {this.width = width;}
	
	private void setHeight(int height)   {this.height = height;}
	
		
	@Override
	public void addImages() {
		spriteUp = new ArrayList<GImage>(2);
		spriteDown = new ArrayList<GImage>(2);
		spriteLeft = new ArrayList<GImage>(2);
		spriteRight = new ArrayList<GImage>(2);
				
		for (int i = 1; i <= 2; ++i)   {
			//replace player sprites with monster sprites later
			spriteUp.add(new GImage("res/monsters/Tall Monster.png"));
			spriteDown.add(new GImage("res/monsters/Tall Monster.png"));
			spriteLeft.add(new GImage("res/monsters/Tall Monster.png"));
			spriteRight.add(new GImage("res/monsters/Tall Monster.png"));
		}
	}
			
	
	private void move(dir direction)   {
		int legTracker;
		if (stepsTaken == 8)   {stepsTaken = 0;}
		if (stepsTaken < 4)   {legTracker = 0;}
		else   {legTracker = 1;}
		if(direction == dir.UP)   {
			loadImage(spriteUp.get(legTracker).getImage());
			dx = 0; dy = -4;
		}
		if(direction == dir.DOWN)   {
			loadImage(spriteDown.get(legTracker).getImage());
			dx = 0; dy = 4;
		}
		if(direction == dir.LEFT)   {
			loadImage(spriteLeft.get(legTracker).getImage());
			dx = -4; dy = 0;
		}
		if(direction == dir.RIGHT)   {
			loadImage(spriteRight.get(legTracker).getImage());
			dx = 4; dy = 0;
		}
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
			
	private void move(int x, int y)   {
		sprite.move(dx, dy);
		this.x = (int) (sprite.getX() + dx);
		this.y = (int) (sprite.getY() + dy + 16);
	}	
	
	protected void move(Player player)   {
		if(player.getX() < x)   {move(dir.LEFT);}
		if(player.getX() > x)   {move(dir.RIGHT);}
		if(player.getY() < y)   {move(dir.UP);}
		if(player.getY() > y)   {move(dir.DOWN);}
	}
}

