package GamePanes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

import Entity.*;
import Item.*;
import Boilerplate.*;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class BedRoomGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background;
	
	private Timer monsterTimer;
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	
	private int x = 722, y = 474;
	
	ArrayList <GRect> walls = new ArrayList <GRect>();
	private GLabel usedKey = null, lockedDoor = null, wrongItem = null;
	private Door doorLiving, doorBedL, doorBedR, doorHallL, doorHallR;
	
	private GImage choice1, choice2, MainMenu; 
	private GButton killHim, spareHim, MainMenu2;
	
	ChoiceHandler choiceHandler = new ChoiceHandler();  
	
	//health
	private GParagraph healthPoints; 
	private static final int heartRootX = 75, heartRootY = 610, heartWidth = 30;
	ArrayList <GImage> playerHearts = new ArrayList <GImage>(); 

	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		setDoors();
		setItems();
		setGUI();
		background = new GImage("res/bedrooms.png");
		monsterTimer = new Timer(100, this);
		monsterTimer.setInitialDelay(6000);
		monsterTimer.start();
	}
	
	public void setDoors() {
		//door to living room map
		doorLiving = new Door(704,510,64,4, false);
		//door to left bedroom
		doorBedL = new Door(128,340,32,20, true);
		//door to right bedroom
		doorBedR = new Door(672,340,32,20, true);
		doorBedR.setRoomType(RoomType.BEDROOMR);
		//door to hallway from left bedroom
		doorHallL = new Door(128,252,32,5, false);
		//door to hallway from right bedroom
		doorHallR = new Door(672,252,32,5, false);
	}
	
	public void setWalls() {
		GRect wall1 = new GRect(0,0,800,96);
		wall1.setFilled(true);
		walls.add(wall1);
		GRect wall2 = new GRect(0,256,800,96);
		wall2.setFilled(true);
		walls.add(wall2);
		GRect wall3 = new GRect(0,448, 704 ,192);
		wall3.setFilled(true);
		walls.add(wall3);
		GRect wall4 = new GRect(0,0,32,640);
		wall4.setFilled(true);
		walls.add(wall4);
		GRect wall5 = new GRect(768,0,32,640);
		wall5.setFilled(true);
		walls.add(wall5);
		GRect wall6 = new GRect(352,32,64,224);
		wall6.setFilled(true);
		walls.add(wall6);
		GRect wall7 = new GRect(704,512,64,128);
		wall7.setFilled(true);
		walls.add(wall7);
	}
	
	public void setGUI() {
		MainMenu = new GImage("res/texture/pause.png", 768, 0); 
		MainMenu.setSize(32, 32);
		MainMenu.setVisible(true);
		MainMenu2 = new GButton("", 768, 0, 32, 32); 
		healthPoints = new GParagraph("HP:", 50, 620);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
		
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40); 
	}
	
	public void setItems() {
		Item itemKnife2 = new Item("Knife",new GImage("res/inventory/Small Knife.png"), ItemType.WEAPON, "bedR");
		itemKnife2.setX(422);
		itemKnife2.setY(217);
		itemKnife2.setDescription("Knife to Kill");
		program.addItem(itemKnife2);
		Item itemKey2 = new Item("Key", new GImage("res/inventory/Small Key.png"), ItemType.KEY, "bedR");
		itemKey2.setX(116);
		itemKey2.setY(184);
		itemKey2.setRoomType(RoomType.BEDROOMR);
		itemKey2.setDescription("Key to master bedroom");
		program.addItem(itemKey2);
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
		for (int i=0; i<7; i++) {program.add(walls.get(i));}
		//background image
		program.add(background);
		//player
		program.add(program.player.getImage(), x, y);
		program.player.setX(x);
		program.player.setY(y);
		//monster
		program.add(monster.getImage(), x + 30, y + 50);
		monster.setX(x + 32);
		monster.setY(y + 32);
		
		program.player.getInv();
		//inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//items on the map
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="bedR"  && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
		}
		//items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//doors
		program.add(doorLiving.getRect());
		program.add(doorBedL.getRect());
		program.add(doorBedR.getRect());
		program.add(doorHallL.getRect());
		program.add(doorHallR.getRect());
		
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
		program.remove(doorLiving.getRect());
		program.remove(doorBedL.getRect());
		program.remove(doorBedR.getRect());
		program.remove(doorHallL.getRect());
		program.remove(doorHallR.getRect());

		program.remove(program.player.getImage());
		for(int i=0; i<7; i++) {
			program.remove(walls.get(i));
		}
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		
		if(usedKey!=null) {program.remove(usedKey);}
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
		if (obj == killHim) {
			// Something will happen here
			program.remove(choice1);
			program.remove(choice2);
		}
		if (obj == spareHim) {
			// Something will happen here
			program.remove(choice1);
			program.remove(choice2);
		}
	
		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		playerInv.setSelectedItem(obj);
		playerInv.drawSelectedItem(program);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		checkCollision();
		
		//back to livingroom map
		if(program.player.sprite.getBounds().intersects(doorLiving.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.fromBed = true;
			program.switchToNewGame();
		}
		//get into left bedroom
		if(program.player.sprite.getBounds().intersects(doorBedL.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 128,219);
		}
		//unlock the right bedroom with key
		if(getSelectedItem()!=null) {
			if(program.player.sprite.getBounds().intersects(doorBedR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if (getSelectedItem().getRoomType()==RoomType.BEDROOMR) {
					if(wrongItem!=null) {wrongItem.setVisible(false);} 
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					usedKey = new GLabel("Opened the door with key!", 210, 544);
					usedKey.setColor(Color.white);
					program.add(usedKey);
					doorBedR.unlock();
					program.player.getInventory().deleteItem(getSelectedItem());
					program.remove(getSelectedItem().getInvSprite());
					label5sec(usedKey);
				}else {
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					wrongItem = new GLabel("Wrong item!", 210, 544);
					wrongItem.setColor(Color.white);
					program.add(wrongItem);
					label5sec(wrongItem);
				}
			}
		}
		//get into right bedroom
		if(program.player.sprite.getBounds().intersects(doorBedR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!doorBedR.isLocked()) {
				program.remove(program.player.getImage());
				program.add(program.player.getImage(), 672,219);
			}else {
				if(wrongItem!=null) {wrongItem.setVisible(false);}
				lockedDoor = new GLabel("Door is locked!", 210, 544);
				lockedDoor.setColor(Color.white);
				program.add(lockedDoor);
				label5sec(lockedDoor);
			}
		}
		//get out of left bedroom
		if(program.player.sprite.getBounds().intersects(doorHallL.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 128,374);
		}
		//get out of right bedroom
		if(program.player.sprite.getBounds().intersects(doorHallR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,374);
		}
		//pick up item with E
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "bedR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
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
		//label to disappear after 5 sec
		int delay = 5000;
	    ActionListener taskPerformer = new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {
	        label.setVisible(false);
	    	}
	    };
	    javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
	    tick.setRepeats(false);
	    tick.start();
	}
	
	public Item getSelectedItem() {
		return program.player.getInventory().getSelectedItem();
	}
	
	public void updatePlayerHeartsGUI(int hp) {
		int heartLen = playerHearts.size();
		int dif = hp - heartLen;
		if (dif > 0) {
			for (int i = 0; i < dif; i++) {
				GImage heart = new GImage("res/texture/HP.png", heartRootX + ((heartLen + i) * heartWidth), heartRootY);
				heart.setSize(30, 20);
				heart.setVisible(true); 
				playerHearts.add(heart);
				program.add(heart); 
			}
		}
		else if (dif < 0) {
			dif = dif * -1; // Absolute value
			for (int i = 0; i < dif; i++) {
				int end = playerHearts.size() - 1;
				GImage heart = playerHearts.get(end);
				program.remove(heart);
				playerHearts.remove(end);
			}
		}
	}
	
	public void addgui() {	
		program.add(MainMenu);
		program.add(MainMenu2);
		program.add(healthPoints);
		
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
		updatePlayerHeartsGUI(program.player.getHP());
	}

	public void removegui() {
		program.remove(MainMenu);
		program.remove(MainMenu2);
		program.remove(healthPoints);
		
		program.remove(choice1);
		program.remove(choice2);
		program.remove(killHim);
		program.remove(spareHim);
		updatePlayerHeartsGUI(0);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {
		monster.move(program.player);
		if(monster.touchPlayer())   {
			program.player.setHP(program.player.getHP() - 1);
			updatePlayerHeartsGUI(program.player.getHP());
			if(program.player.getHP() >= 0)   {
				System.out.println("Player has been hit and their HP is now: " + program.player.getHP());
			}

			if (program.player.getHP() <= 0)   {
				monsterTimer.stop();
			}

			else {
				 monsterTimer.restart();
			}
		}
	}
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
}
