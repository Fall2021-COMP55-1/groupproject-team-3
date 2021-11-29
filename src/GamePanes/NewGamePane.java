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
import java.util.Date;
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
	ArrayList <GRect> walls = new ArrayList <GRect>();
	private GImage MainMenu, ResumeGame, SaveGame, Quit, HP1, HP2, HP3; 
	private GButton MainMenu2, ResumeGame1, SaveGame1, Quit1; 
	private GRect redBox = null;
	private GLabel description = null, usedKey = null, lockedDoor = null, wrongItem = null;
	private GParagraph healthPoints; 
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		setDoors();
		setItems();
		background = new GImage("res/livingroom.png");
		timer = new Timer(100, this);
		timer.setInitialDelay(6000);
		timer.start();
		
		MainMenu = new GImage("res/texture/Game Menu.png", 50, 555); 
		MainMenu.setSize(150, 40);
		MainMenu2 = new GButton("", 50, 555, 150, 40); 
		ResumeGame = new GImage("res/texture/Resume Game.png", 255, 200); 
		ResumeGame.setSize(250, 60); 
		ResumeGame.setVisible(false);
		ResumeGame1 = new GButton("", 255, 200, 250, 60); 
		SaveGame = new GImage("res/texture/Save Game.png", 255, 300);
		SaveGame.setSize(250, 60);  
		SaveGame.setVisible(false); 
		SaveGame1 = new GButton("", 255, 300, 250, 60); 
		Quit = new GImage("res/texture/Quit.png", 255, 400);
		Quit.setSize(250, 60);
		Quit.setVisible(false); 
		Quit1 = new GButton("", 255, 400, 250, 60); 
		
		healthPoints = new GParagraph("HP:", 220, 590);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
		HP1 = new GImage("res/texture/HP.png", 245, 580);
		HP1.setSize(30, 20);
		HP2 = new GImage("res/texture/HP.png", 270, 580);
		HP2.setSize(30, 20); 
		HP3 = new GImage("res/texture/HP.png", 295, 580);
		HP3.setSize(30, 20); 
			
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
		
	
		program.add(MainMenu);
		program.add(MainMenu2);
		program.add(ResumeGame);
		program.add(ResumeGame1);
		program.add(SaveGame);
		program.add(SaveGame1);
		program.add(Quit);
		program.add(Quit1);
		program.add(healthPoints);
		program.add(HP1);
		program.add(HP2);
		program.add(HP3);
		
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
		
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		
		if(redBox!=null) {program.remove(redBox);}
		if(description!=null) {program.remove(description);}
		if (usedKey!=null) {program.remove(usedKey);}
		if(lockedDoor!=null) {program.remove(lockedDoor);}
		if(wrongItem!=null) {program.remove(wrongItem);}
		
		program.remove(MainMenu);
		program.remove(MainMenu2);
		program.remove(ResumeGame);
		program.remove(ResumeGame1);
		program.remove(SaveGame);
		program.remove(SaveGame1);
		program.remove(Quit);
		program.remove(Quit1);
		program.remove(healthPoints);
		program.remove(HP1); 
		program.remove(HP2);
		program.remove(HP3);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == MainMenu2) {
			ResumeGame = new GImage("res/texture/Resume Game.png", 255, 200); 
			ResumeGame.setSize(250, 60);
			ResumeGame1 = new GButton("", 255, 200, 250, 60); 
			program.add(ResumeGame); 
			SaveGame = new GImage("res/texture/Save Game.png", 255, 300); 
			SaveGame.setSize(250, 60);
			SaveGame1 = new GButton("", 255, 300, 250, 60);
			program.add(SaveGame);
			Quit = new GImage("res/texture/Quit.png", 255, 400);
			Quit.setSize(250, 60);
			Quit1 = new GButton("", 255, 400, 250, 60);
			program.add(Quit); 
		}
		
		if (obj == ResumeGame) {	 
			program.remove(ResumeGame);
			program.remove(SaveGame);
			program.remove(Quit);
		}
		if (obj == SaveGame) {
			program.remove(ResumeGame);
			program.remove(SaveGame);
			program.remove(Quit);
		}
		if (obj == Quit) {
			program.remove(ResumeGame);
			program.remove(SaveGame);
			program.remove(Quit);
			//program.switchToMenu();  
		}
		
		//click item in hot bar to select
		Item temp=null;
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			if (obj == program.player.getInventory().invItems.get(i).getInvSprite()) {
				if(redBox!=null) {
					program.remove(redBox);
					program.remove(description);
				}
				temp= program.player.getInventory().invItems.get(i);
				redBox = new GRect(obj.getX(),obj.getY(),32,32);
				redBox.setColor(Color.red);
				program.add(redBox);
				description = new GLabel(temp.getDescription(),210,576);
				description.setColor(Color.white);
				program.add(description);
				program.player.getInventory().setSelectedItem(temp);
			}
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		checkCollision();
		
		//unlocked the bedroom map door with key
		if(getSelectedItem()!=null) {
			if(program.player.sprite.getBounds().intersects(doorBed.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(getSelectedItem().getRoomType()==RoomType.BEDROOMS) {
					if(wrongItem!=null) {if (wrongItem.isVisible()) {wrongItem.setVisible(false);}} 
					if(lockedDoor!=null) {if(lockedDoor.isVisible()) {lockedDoor.setVisible(false);}}
					usedKey = new GLabel("Unlocked the door with key!", 210, 544);
					usedKey.setColor(Color.white);
					program.add(usedKey);
					doorBed.unlock();
					program.player.getInventory().deleteItem(getSelectedItem());
					program.remove(getSelectedItem().getInvSprite());
					program.remove(redBox);
					program.remove(description);
					label5sec(usedKey);
				}else {
					if(lockedDoor!=null) {if(lockedDoor.isVisible()) {lockedDoor.setVisible(false);}}
					wrongItem = new GLabel("Wrong item!", 210, 544);
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
				if(wrongItem!=null) {if (wrongItem.isVisible()) {wrongItem.setVisible(false);}} 
				lockedDoor = new GLabel("Door is locked!", 210, 544);
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
		
		//attemted to select item with 12345 key
		/*if(e.getKeyCode()==KeyEvent.VK_1) {
			selectItem(270,608);
		}else if(e.getKeyCode()==KeyEvent.VK_2) {
			selectItem(300,608);
		}else if(e.getKeyCode()==KeyEvent.VK_3) {
			selectItem(330,608);
		}else if(e.getKeyCode()==KeyEvent.VK_4) {
			selectItem(360,608);
		}else if(e.getKeyCode()==KeyEvent.VK_5) {
			selectItem(390,608);
		}*/
		
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
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {
		monster.move(program.player);

	}
	
}
