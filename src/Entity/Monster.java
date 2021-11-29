package Entity;
import java.util.ArrayList;

import acm.graphics.GImage;

public class Monster extends Entity{
	private GImage sprite = getImage();
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, dx = 0, dy = 0, stepsTaken = 0, width, height;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
	private Player player;
			
	public Monster(int x, int y, MonsterType type)   {
		super(x, y);
		addImages();
		loadImage(this.spriteDown.get(0).getImage());
	}
	
	@Override
	public int getX()   {return x;}
	
	@Override
	public int getY()   {return y;}
	
		
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
		stepsTaken = stepsTaken + 1;
		if (touchPlayer())   {
				player.setHP(player.getHP() - 1);
				if(player.getHP() >= 0)   {
					System.out.println("Player has been hit and their HP is now: " + player.getHP());
				}
		}
	}
	
	public boolean touchPlayer() {
		if(player == null)   {return false;}
		if(this.sprite.getBounds().intersects(player.sprite.getBounds()))   {return true;}
		return false;
	}
			
	private void move(int x, int y)   {
		sprite.move(dx, dy);
		this.x = (int) (sprite.getX() + dx);
		this.y = (int) (sprite.getY() + dy + 16);
	}	
	
	public void move(Player player)   {
		this.player = player;
		if(player.getX() < x)   {move(dir.LEFT);}
		if(player.getX() > x)   {move(dir.RIGHT);}
		if(player.getY() < y)   {move(dir.UP);}
		if(player.getY() > y)   {move(dir.DOWN);}
	}
}

