import java.util.ArrayList;

import acm.graphics.GImage;

public class Inventory {
	public ArrayList<Item> invItems = new ArrayList<Item>(5);
	boolean active = false;
	public static final int INVENTORY_X = 200;
	public static final int INVENTORY_Y = 576;
	public static final GImage INVENTORY_IMG = new GImage("res/inventory/HotBar.png");
	private int listSpacing, selectedItem = 0;
	
	public Inventory()   {	
		
	}
	
	public int getListSpacing() {return listSpacing;}
	
	public int getSelectedItem() {return selectedItem;}

	public void setListSpacing(int listSpacing) {this.listSpacing = listSpacing;}

	public void setSelectedItem(int selectedItem) {this.selectedItem = selectedItem;}
	
	public void addItem()   {
		
	}

	
	
	

}
