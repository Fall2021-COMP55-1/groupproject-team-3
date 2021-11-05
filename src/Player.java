import java.awt.Image;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Player {
	private int x, y, width = 32, height = 32;
	private GImage sprite = new GImage("res/player/PCU1.png");
	private boolean visible;
	
	public Player(int x, int y)   {
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	public int getX()   {return x;}
	
	public int getY()   {return y;}
	
	public GImage getImage()   {return sprite;}
	
	public boolean isVisible()   {return visible;}
	
	public GRect getBounds()   {return new GRect(x, y, width, height);}
	
	public void setVisible(boolean visible)   {this.visible = visible;}
	
	protected void loadImage(Image image)   {sprite.setImage(image);}
	
	protected void move(int x, int y)   {
		sprite.move(x, y);
		this.x = this.x + x;
		this.y = this.y + y;
	}
	
}
