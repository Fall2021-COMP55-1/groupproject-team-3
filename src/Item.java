import java.util.ArrayList;
import acm.graphics.GRect;

public class Item {
	
	public ArrayList<Item> items;
	private int x, y, count, width = 32, height = 32;
	private String name, desc;
	private boolean used, pickedUp;
	

	public Item (String name)   {this.name = name;}
	
	private int getX()   {return x;}
	
	private int getY()   {return y;}
	
	private int getCount()   {return count;}
	
	private String getName()   {return name;}
	
	private String getDescription()   {return desc;}
	
	public GRect getBounds()   {return new GRect(x, y, width, height);}
	
	private boolean isUsed()   {return used;}
	
	private boolean isPickedUp()   {return pickedUp;}
	
	private void setX(int x)   {this.x = x;}
	
	private void setY(int y)   {this.y = y;}
	
	private void setCount(int count)   {this.count = count;}
	
	private void setName(String name)   {this.name = name;}
	
	private void setDescription(String desc)   {this.desc = desc;}
	
	private void setUsed(boolean used)   {this.used = used;}
	
	private void setPickedUp(boolean pickedUp)   {this.pickedUp = pickedUp;}
	
}