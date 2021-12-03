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
	private GImage background, choice1, choice2, pauseImge, NPC; 
	
	private Timer monsterTimer;
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	
	private int playerX = 722, playerY = 474;
	
	ArrayList <GRect> walls = new ArrayList <GRect>();
	private GLabel keyUsed = null, lockedDoor = null, wrongItem = null;
	private Door inLivingMap, inLeftBed, inRightBed, outLeftBed, outRightBed;
	private GButton killHim, spareHim, pauseButton;
	
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
	}
	
	public void setDoors() {
		//door to living room map
		inLivingMap = new Door(704,510,64,4, false);
		//door to left bedroom
		inLeftBed = new Door(128,340,32,20, true);
		//door to right bedroom
		inRightBed = new Door(672,340,32,20, true);
		inRightBed.setRoomType(RoomType.BEDROOMR);
		//door to hallway from left bedroom
		outLeftBed = new Door(128,252,32,5, false);
		//door to hallway from right bedroom
		outRightBed = new Door(672,252,32,5, false);
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
		GRect npc = new GRect(100, 150, 30, 30);
		npc.setVisible(false);
		walls.add(npc);
	}
	
	public void setGUI() {
		pauseImge = new GImage("res/texture/pause.png", 768, 0); 
		pauseImge.setSize(32, 32);
		pauseImge.setVisible(true);
		pauseButton = new GButton("", 768, 0, 32, 32); 
		healthPoints = new GParagraph("HP:", 50, 625);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
		
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40); 
		
		NPC = new GImage("res/NPC/NPC1.png", 100, 150);
		NPC.setSize(30, 30); 
	}
	
	public void setItems() {
		Item winningKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, "bedR");
		winningKey.setX(422);
		winningKey.setY(217);
		winningKey.setRoomType(RoomType.OUT);
		winningKey.setDescription("Key of the house");
		program.addItem(winningKey);
		Item itemKey2 = new Item("Key", new GImage("res/inventory/Small Key.png"), ItemType.KEY, "bedR");
		itemKey2.setX(116);
		itemKey2.setY(184);
		itemKey2.setRoomType(RoomType.BEDROOMR);
		itemKey2.setDescription("Key to master bedroom");
		program.addItem(itemKey2);
	}
	
	
	@Override
	public void showContents() {
		//walls
		for (int i=0; i<8; i++) {program.add(walls.get(i));}
		//background image
		program.add(background);

		//player and monster
		program.add(program.player.getImage(), playerX, playerY);
		program.player.setX(playerX);
		program.player.setY(playerY);
		program.player.getInventory();
		
		program.add(monster.getImage(), playerX + 16, playerY + 50);
		monster.setX(playerX + 16);
		monster.setY(playerY + 50);
		monsterTimer.setInitialDelay(3000);
		monsterTimer.start();
		
		
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
		program.add(inLivingMap.getRect());
		program.add(inLeftBed.getRect());
		program.add(inRightBed.getRect());
		program.add(outLeftBed.getRect());
		program.add(outRightBed.getRect());
		
		program.player.getInventory().setHighlightVisible(true);
		addgui();
		
		program.add(NPC);
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
		program.remove(inLivingMap.getRect());
		program.remove(inLeftBed.getRect());
		program.remove(inRightBed.getRect());
		program.remove(outLeftBed.getRect());
		program.remove(outRightBed.getRect());

		program.remove(program.player.getImage());
		program.remove(monster.getImage());
		for(int i=0; i<7; i++) {
			program.remove(walls.get(i));
		}
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		program.remove(Inventory.INVENTORY_IMG);
		if(keyUsed!=null) {program.remove(keyUsed);}
		if(lockedDoor!=null) {program.remove(lockedDoor);}
		if(wrongItem!=null) {program.remove(wrongItem);}
		
		removegui();
		monsterTimer.stop();
		//redBox still shows
		program.player.getInventory().setHighlightVisible(false);
		
		program.remove(NPC);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == pauseButton) {
			monsterTimer.stop();
			program.pause();
		}
		if (obj == program.resume) {
			program.resume();
			monsterTimer.setInitialDelay(0);
			monsterTimer.restart();
		}	
		if (obj == program.quit) {
			System.exit(0);
		}
		
		if (obj == killHim) {
			program.remove(NPC);
			program.remove(choice1);
			program.remove(choice2);
		}
		if (obj == spareHim) {
			// Nothing happens so the NPC lives 
			program.remove(choice1);
			program.remove(choice2);
		}
	
		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {
			playerInv.drawSelectedItem();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		
		program.checkCollision(walls);
		
		//back to livingroom map
		openDoor(inLivingMap,e);
		
		//get into left bedroom
		openDoor(inLeftBed,e,128,219);
		
		//unlock the right bedroom with key
		unlockDoor(inRightBed,e);
		
		//get into right bedroom
		openDoor(inRightBed,e);
		
		//get out of left bedroom
		openDoor(outLeftBed,e,128,374);
		
		//get out of right bedroom
		openDoor(outRightBed,e,672,374);
		
		//pick up item with E
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "bedR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
				grab(program.itemAt(i));
			}
		}
		
		//to select item with 12345 key
		setSelectedItem(e);
	}
	
	public void unlockDoor(Door door, KeyEvent e) {
		if(getSelectedItem()!=null) {
			if(program.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(getSelectedItem().getRoomType()==door.getRoomType()) {
					if(wrongItem!=null) {wrongItem.setVisible(false);} 
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					keyUsed = new GLabel("Unlocked the door with key!", 210, 550);
					keyUsed.setColor(Color.white);
					program.add(keyUsed);
					door.unlock();
					program.player.getInventory().deleteItem(program,getSelectedItem());
					program.remove(getSelectedItem().getInvSprite());
					label5sec(keyUsed);
				}else {
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					wrongItem = new GLabel("Wrong item!", 210, 575);
					wrongItem.setColor(Color.white);
					program.add(wrongItem);
					label5sec(wrongItem);
				}	
			}
		}
	}
	
	public void openDoor(Door door, KeyEvent e) {
		if(program.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!door.isLocked()) {
				if(door == inLivingMap) {
					program.fromBedtoLiving = true;
					program.switchToNewGame();
				}else if(door == inRightBed) {
					program.remove(program.player.getImage());
					program.add(program.player.getImage(), 672,219);
				}
			}else {
				if(wrongItem!=null) {wrongItem.setVisible(false);}
				lockedDoor = new GLabel("Door is locked!", 210, 550);
				lockedDoor.setColor(Color.white);
				program.add(lockedDoor);
				label5sec(lockedDoor);
			}
		}
	}
	
	public void openDoor(Door door, KeyEvent e, int x, int y) {
		if(program.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), x,y);
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
	
	public void setSelectedItem(KeyEvent e) {
		Inventory playerInv = program.player.getInventory();
		if(e.getKeyCode()==KeyEvent.VK_1) {
			if (playerInv.setSelectedItem(0)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_2) {
			if (playerInv.setSelectedItem(1)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_3) {
			if (playerInv.setSelectedItem(2)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_4) {
			if (playerInv.setSelectedItem(3)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_5) {
			if (playerInv.setSelectedItem(4)) {
				playerInv.drawSelectedItem();
			}
		}
	}
	
	public void updatePlayerHeartsGUI(int hp) {	
		int heartLen = playerHearts.size();
		int dif = hp - heartLen;
		if (dif > 0) {
			for (int i = 0; i < dif; i++) {
				GImage heart = new GImage("res/texture/HP.png", heartRootX + ((heartLen + i) * heartWidth), heartRootY);
				heart.setSize(25, 20);
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
		program.add(pauseImge);
		program.add(pauseButton);
		program.add(healthPoints);
		
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
		updatePlayerHeartsGUI(program.player.getHP());
	}

	public void removegui() {
		program.remove(pauseImge);
		program.remove(pauseButton);
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
			if (program.player.getHP() <= 0)   {
				monsterTimer.stop();
			}else {
				monsterTimer.setInitialDelay(2000);
				monsterTimer.restart();
			}
			program.player.setHP(program.player.getHP() - 1);
			if(program.player.getHP()==0) {program.switchToBadEnd();}
			updatePlayerHeartsGUI(program.player.getHP());
			System.out.println("Player has been hit and their HP is now: " + program.player.getHP());
		}
	}
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
}
