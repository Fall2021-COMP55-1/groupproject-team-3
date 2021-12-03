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
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background, pauseImg, NPC; 
	
	private Timer monsterTimer;
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int playerX = 482, playerY = 510;
	
	private ArrayList <GRect> walls = new ArrayList <GRect>(); 
	private GLabel goal = null;
	
	private GButton pauseButton;
	
	//health
	private GParagraph healthPoints; 
	private static final int heartRootX = 75, heartRootY = 610, heartWidth = 30;
	ArrayList <GImage> playerHearts = new ArrayList <GImage>(); 	
	
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		program.setDoorsLiving();
		setItems();
		setGUI();
		background = new GImage("res/livingroom.png");
		monsterTimer = new Timer(100, this);
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
		GRect npc = new GRect(540, 450, 30, 30);
		npc.setVisible(false);
		walls.add(npc);
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
		pauseImg = new GImage("res/texture/pause.png", 768, 0); 
		pauseImg.setSize(32, 32);
		pauseImg.setVisible(true);
		pauseButton = new GButton("", 768, 0, 32, 32); 
		healthPoints = new GParagraph("HP:", 50, 625);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
		
		NPC = new GImage("res/NPC/NPC2.png", 540, 450); 
		NPC.setSize(30, 30); 
		goal = new GLabel("Your goal is to escape from this house! Good luck!", 450, 440);
		goal.setColor(Color.white);
		program.add(goal);
		program.label5sec(goal);
	}
	
	@Override
	
	public void showContents() {
		//walls
		for (int i=0; i<14; i++) {program.add(walls.get(i));}
		//background image
		program.add(background);
		
		//player and monster location
		if (program.fromBedtoLiving) {
			program.add(program.player.getImage(),85,172);
			program.player.setX(85);
			program.player.setY(172);
			program.add(monster.getImage(), 35, 102);
			monster.setX(35);
			monster.setY(102);
			program.fromBedtoLiving=false;
			monsterTimer.setInitialDelay(1000);
		} else {
			program.add(program.player.getImage(), playerX, playerY);
			program.player.setX(playerX);
			program.player.setY(playerY);
			program.add(monster.getImage(), playerX + 20, playerY + 50);
			monster.setX(playerX + 20);
			monster.setY(playerY + 50);
			monsterTimer.setInitialDelay(3000);
		}
		monsterTimer.start();
		program.player.getInventory();
		//Inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//doors
		program.addDoorLiving();
		
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
		
		program.add(NPC);
		program.add(goal);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(program.player.getImage());
		for(int i=0; i<13; i++) {
			program.remove(walls.get(i));
		}
		
		program.removeDoorLiving();
		
		program.remove(monster.getImage());
		
		program.remove(Inventory.INVENTORY_IMG);
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		
		program.removeLabels();
		
		removegui();
		monsterTimer.stop();
		//redBox still shows
		program.player.getInventory().setHighlightVisible(false);
		
		program.remove(NPC);
		program.remove(goal);
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
		
		//click item in hot bar to select
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {
			playerInv.drawSelectedItem();
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		
		program.checkCollision(walls);
		
		//unlock the bedroom map door with key
		program.unlockDoor(program.inBedMap,e);

		//get into the bedroom map
		program.openDoor(program.inBedMap,e);
		
		//get into the bathroom door
		program.openDoor(program.inBath, e, 672,250);
		
		//get out of bathroom
		program.openDoor(program.outBath,e,672,400);
		
		//unlocked the house door with key
		program.unlockDoor(program.winning, e);

		//get outta house
		program.openDoor(program.winning,e);
		
		//pick up item by E
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "livingR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
				program.grab(program.itemAt(i));
			}
		}
		
		//to select item with 12345 key
		program.setSelectedItem(e);
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
		program.add(pauseImg);
		program.add(pauseButton);
		program.add(healthPoints);
		updatePlayerHeartsGUI(program.player.getHP());
	}

	public void removegui() {
		program.remove(pauseImg);
		program.remove(pauseButton);
		program.remove(healthPoints);
		updatePlayerHeartsGUI(0);
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

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
}
