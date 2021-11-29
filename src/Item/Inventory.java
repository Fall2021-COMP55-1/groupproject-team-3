package Item;
import java.awt.Color;
import java.util.ArrayList;

import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class Inventory {
	public ArrayList<Item> invItems = new ArrayList<Item>(5);
	boolean active = false;
	public static final int INVENTORY_X = 200;
	public static final int INVENTORY_Y = 576;
	public static final GImage INVENTORY_IMG = new GImage("res/inventory/HotBar.png");
	private int listSpacing=0;
	private Item selectedItem = null;
	private GRect redBox = null;
	private GLabel description = null;
	
	public Inventory()   {	
	}
	
	public int numInvItems() {
		return invItems.size();
	}
	public int getListSpacing() {return listSpacing;}
	
	public Item getSelectedItem() {return selectedItem;}

	public void setListSpacing(int listSpacing) {this.listSpacing = listSpacing;}

	public void setSelectedItem(Item item) {
		this.selectedItem = item;
	}
	
	public void setSelectedItem(GObject obj) {
		for (int i=0; i<numInvItems(); i++) {
			Item detected = invItems.get(i);
			if (obj == detected.getInvSprite()) {
				setSelectedItem(detected);
			}
		}
	}
	
	public void drawSelectedItem(MainApplication program) {
		if(redBox!=null) {
			program.remove(redBox);
			program.remove(description);
		}
		GImage selectedItemSprite = selectedItem.getInvSprite();
		redBox = new GRect(selectedItemSprite.getX(), selectedItemSprite.getY(),32,32);
		redBox.setColor(Color.red);
		program.add(redBox);
		description = new GLabel(selectedItem.getDescription(),210,576);
		description.setColor(Color.white);
		program.add(description);
	}
	
	public void addItem(Item item)   {
		invItems.add(item);
		updateItemGui();
	}
	
	public void deleteItem(Item item) {
		invItems.remove(item);
		updateItemGui();
	}
	
	public void updateItemGui() {
		for(int i = 0; i < invItems.size(); ++i)   {
			invItems.get(i).getInvSprite().setLocation(200 + (32 * i), INVENTORY_Y + 32);
			invItems.get(i).setY(INVENTORY_Y);
		}
	}
	
	public Item itemAt(int i) {
		return invItems.get(i);
	}
}
