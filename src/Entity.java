import java.awt.Image;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GRect;

public class Entity {
	private GImage sprite = new GImage("res/player/PCD1.png");
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, width = 32, height = 32;
	private boolean visible;
		
	public Entity(int x, int y)   {
		this.x = x;
		this.y = y;
		visible = true;
		addImages();
	}
	
	public void addImages() {
		spriteUp = new ArrayList<GImage>(2);
		spriteDown = new ArrayList<GImage>(2);
		spriteLeft = new ArrayList<GImage>(2);
		spriteRight = new ArrayList<GImage>(2);
	}
		
	public int getX()   {return x;}
		
	public int getY()   {return y;}
	
	public void setX(int x)   {this.x = x;}
	
	public void setY(int y)   {this.y = y;}
		
	public GImage getImage()   {return sprite;}
		
	public boolean isVisible()   {return visible;}
		
	public GRect getBounds()   {return new GRect(x, y, width, height);}
		
	public void setVisible(boolean visible)   {this.visible = visible;}
		
	protected void loadImage(Image image)   {sprite.setImage(image);}
	
}
