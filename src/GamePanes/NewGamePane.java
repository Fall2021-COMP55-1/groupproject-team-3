package GamePanes;

import Boilerplate.*;
import Item.*;
import Entity.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background;
	private Timer timer;
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 482, y = 510;
	private Door doorBed, doorBath, outBath;
	private ArrayList <GRect> walls = new ArrayList <GRect>(); 
	private GLabel usedKey = null, lockedDoor = null, wrongItem = null;
	private GImage MainMenu, HP1, HP2, HP3; 
	private GButton MainMenu2;
	private GParagraph healthPoints; 
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		setDoors();
		setItems();
		setGUI();
		background = new GImage("res/livingroom.png");
		timer = new Timer(100, this);
		timer.setInitialDelay(6000);
		timer.start();
	}

	public void setDoors() {
		//door to bedroom map
		doorBed = new Door(64,150,64,20, true);
		doorBed.setRoomType(RoomType.BEDROOMS);
		//door to bathroom
		doorBath = new Door(672,380,32,20, false);
		//door to hallway from bathroom
		outBath = new Door(672,283,32,10, false);
	}
	
	public void setWalls() {
		GRect wall1 = new GRect(0,0,800,64);
		wall1.setFilled(true);
		walls.add(wall1);
		GRect wall2 = new GRect(0,64,160,96);
		wall2.setFilled(true);
		walls.add(wall2);
		GRect wall3 = new GRect(0,160, 32,480);
		wall3.setFilled(true);
		walls.add(wall3);
		GRect wall4 = new GRect(32,224,128,128);
		wall4.setFilled(true);
		walls.add(wall4);
		GRect wall5 = new GRect(160,256,288,128);
		wall5.setFilled(true);
		walls.add(wall5);
		GRect wall6 = new GRect(32,512,448,128);
		wall6.setFilled(true);
		walls.add(wall6);
		GRect wall7 = new GRect(480,544,64,96);
		wall7.setFilled(true);
		walls.add(wall7);
		GRect wall8 = new GRect(544,512,256,128);
		wall8.setFilled(true);
		walls.add(wall8);
		GRect wall9 = new GRect(576,448,224,64);
		wall9.setFilled(true);
		walls.add(wall9);
		GRect wall10 = new GRect(768,64,32,384);
		wall10.setFilled(true);
		walls.add(wall10);
		GRect wall11 = new GRect(576,64,192,128);
		wall11.setFilled(true);
		walls.add(wall11);
		GRect wall12 = new GRect(576,192,64,96);
		wall12.setFilled(true);
		walls.add(wall12);
		GRect wall13 = new GRect(576,288,192,96);
		wall13.setFilled(true);
		walls.add(wall13);
	}
	
	public void setItems() {
		Item itemKnife1 = new Item("Knife",new GImage ("res/inventory/Small Knife.png"), ItemType.WEAPON, "livingR");
		itemKnife1.setX(734);
		itemKnife1.setY(259);
		itemKnife1.setDescription("Knife to kill");
		program.addItem(itemKnife1);
		
		Item bedroomKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, "livingR");
		bedroomKey.setX(200);
		bedroomKey.setY(100);
		bedroomKey.setRoomType(RoomType.BEDROOMS);
		bedroomKey.setDescription("Key to hallway of bedrooms");
		program.addItem(bedroomKey);
	}
	
	public void setGUI() {
		MainMenu = new GImage("res/texture/pause.png", 768, 0); 
		MainMenu.setSize(32, 32);
		MainMenu.setVisible(true);
		MainMenu2 = new GButton("", 768, 0, 32, 32); 
		healthPoints = new GParagraph("HP:", 50, 620);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
		HP1 = new GImage("res/texture/HP.png", 75, 610);
		HP1.setSize(30, 20);
		HP2 = new GImage("res/texture/HP.png", 100, 610);
		HP2.setSize(30, 20); 
		HP3 = new GImage("res/texture/HP.png", 125, 610);
		HP3.setSize(30, 20);
	}
	
	public boolean checkCollision() {
		Iterator<GRect> iterate = walls.iterator();
		while(iterate.hasNext()) {
			GRect temp = iterate.next();
			if(program.player.sprite.getBounds().intersects(temp.getBounds())) {
				
				if(program.player.getDX() > 0)   {
					if(program.player.getDY() > 0)   {program.player.sprite.move(-2, -2);}
					if(program.player.getDY() < 0)   {program.player.sprite.move(-2, 2);}
					if(program.player.getDY() == 0)   {program.player.sprite.move(-4, 0);}
				}
				if(program.player.getDX() < 0)   {
					if(program.player.getDY() > 0)   {program.player.sprite.move(2, -2);}
					if(program.player.getDY() < 0)   {program.player.sprite.move(2, 2);}
					if(program.player.getDY() == 0)   {program.player.sprite.move(4, 0);}
				}
				if(program.player.getDY() < 0)   {
					if(program.player.getDX() > 0)   {program.player.sprite.move(-2, 2);}
					if(program.player.getDX() < 0)   {program.player.sprite.move(2, 2);}
					if(program.player.getDX() == 0)   {program.player.sprite.move(0, 4);}
				}
				if(program.player.getDY() > 0)   {
					if(program.player.getDX() > 0)   {program.player.sprite.move(-2, -2);}
					if(program.player.getDX() < 0)   {program.player.sprite.move(2, -2);}
					if(program.player.getDX() == 0)   {program.player.sprite.move(0, -4);}
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	
	public void showContents() {
		//walls
		for (int i=0; i<13; i++) {program.add(walls.get(i));}
		//background image
		program.add(background);
		//if from start, player location
		if (!program.fromBed) {
			program.add(program.player.getImage(), x, y);
			program.player.setX(x);
			program.player.setY(y);
		//if from the bedroom map, player location
		} else {
			program.add(program.player.getImage(),85,172);
			program.player.setX(85);
			program.player.setY(172);
		}
		//monster location
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		
		program.player.getInv();
		//Inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//doors
		program.add(doorBed.getRect());
		program.add(doorBath.getRect());
		program.add(outBath.getRect());
		//items on the map
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="livingR" && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
		}
		//items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
		addgui();
	}

	private void grab(Item item)   {
		program.player.grabItem(item);
		item.setPickedUp(true);
		program.remove(item.getImage());
		program.add(item.getInvSprite());
	}
	
	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(program.player.getImage());
		for(int i=0; i<13; i++) {
			program.remove(walls.get(i));
		}
		program.remove(doorBed.getRect());
		program.remove(doorBath.getRect());
		program.remove(outBath.getRect());
		program.remove(monster.getImage());
		
		program.remove(Inventory.INVENTORY_IMG);
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		
		if (usedKey!=null) {program.remove(usedKey);}
		if(lockedDoor!=null) {program.remove(lockedDoor);}
		if(wrongItem!=null) {program.remove(wrongItem);}
		
		removegui();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == MainMenu2) {
			program.switchToPause();
		}
		
		//click item in hot bar to select
		Inventory playerInv = program.player.getInventory();
		playerInv.setSelectedItem(obj);
		playerInv.drawSelectedItem(program);

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		checkCollision();
		
		//unlocked the bedroom map door with key
		if(getSelectedItem()!=null) {
			if(program.player.sprite.getBounds().intersects(doorBed.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(getSelectedItem().getRoomType()==RoomType.BEDROOMS) {
					if(wrongItem!=null) {wrongItem.setVisible(false);} 
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					usedKey = new GLabel("Unlocked the door with key!", 210, 550);
					usedKey.setColor(Color.white);
					program.add(usedKey);
					doorBed.unlock();
					program.player.getInventory().deleteItem(getSelectedItem());
					program.remove(getSelectedItem().getInvSprite());
					label5sec(usedKey);
				}else {
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					wrongItem = new GLabel("Wrong item!", 210, 575);
					wrongItem.setColor(Color.white);
					program.add(wrongItem);
					label5sec(wrongItem);
				}
				
			}
		}

		//get into the bedroom map
		if(program.player.sprite.getBounds().intersects(doorBed.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!doorBed.isLocked()) {
				program.switchToBedRoom();
			}else {
				if(wrongItem!=null) {wrongItem.setVisible(false);}
				lockedDoor = new GLabel("Door is locked!", 210, 550);
				lockedDoor.setColor(Color.white);
				program.add(lockedDoor);
				label5sec(lockedDoor);
			}
		}
		
		//get into the bathroom door
		if(program.player.sprite.getBounds().intersects(doorBath.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,250);
		}
		
		//get out of bathroom
		if(program.player.sprite.getBounds().intersects(outBath.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,400);

		}
		
		//pick up item by E
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "livingR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
				grab(program.itemAt(i));
			}
		}
		
		//to select item with 12345 key
		Inventory playerInv = program.player.getInventory();
		if(e.getKeyCode()==KeyEvent.VK_1) {
			if (playerInv.setSelectedItem(0)) {
				playerInv.drawSelectedItem(program);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_2) {
			if (playerInv.setSelectedItem(1)) {
				playerInv.drawSelectedItem(program);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_3) {
			if (playerInv.setSelectedItem(2)) {
				playerInv.drawSelectedItem(program);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_4) {
			if (playerInv.setSelectedItem(3)) {
				playerInv.drawSelectedItem(program);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_5) {
			if (playerInv.setSelectedItem(4)) {
				playerInv.drawSelectedItem(program);
			}
		}
		
	}
	
	public void label5sec(GLabel label) {
		//label disappears in 5 sec
		int delay = 5000;
	    ActionListener taskPerformer = new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {
	        label.setVisible(false);;
	    	}
	    };
	    javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
	    tick.setRepeats(false);
	    tick.start();
	}
	
	public Item getSelectedItem() {
		return program.player.getInventory().getSelectedItem();
	}
	
	public void addgui() {	
		program.add(MainMenu);
		program.add(MainMenu2);
		program.add(healthPoints);
		program.add(HP1);
		program.add(HP2);
		program.add(HP3);
	}

	public void removegui() {
		program.remove(MainMenu);
		program.remove(MainMenu2);
		program.remove(healthPoints);
		program.remove(HP1); 
		program.remove(HP2);
		program.remove(HP3);
	}

	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {
		monster.move(program.player);

	}
	
}