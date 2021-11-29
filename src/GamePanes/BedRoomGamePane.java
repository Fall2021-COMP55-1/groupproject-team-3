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
	private Timer timer;
	//private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 722, y = 474;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	private Door doorLiving, doorBedL, doorBedR, doorHallL, doorHallR;
	private GImage choice1, choice2, MainMenu, ResumeGame, SaveGame, Quit, HP1, HP2, HP3; 
	private GButton killHim, spareHim, MainMenu2, ResumeGame1, SaveGame1, Quit1;
	ChoiceHandler choiceHandler = new ChoiceHandler();  	
	private GRect redBox = null;
	private GLabel description = null, usedKey = null, lockedDoor = null;
	private GParagraph healthPoints; 
	
	
	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		setDoors();
		setItems();
		background = new GImage("res/bedrooms.png");
		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.start();
		
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40); 
		
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
		doorLiving = new Door(704,510,64,4, false);
		doorBedL = new Door(128,340,32,20, true);
		doorBedR = new Door(672,340,32,20, true);
		doorBedR.setRoomType(RoomType.BEDROOMR);
		doorHallL = new Door(128,252,32,5, false);
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
		for (int i=0; i<7; i++) {program.add(walls.get(i));}
		
		program.add(background);
		program.add(program.player.getImage(), x, y);
		program.player.setX(x);
		program.player.setY(y);
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		program.player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="bedR"  && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
		}
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
			
		
		program.add(doorLiving.getRect());
		program.add(doorBedL.getRect());
		program.add(doorBedR.getRect());
		program.add(doorHallL.getRect());
		program.add(doorHallR.getRect());
		
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
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
		if(redBox!=null) {
			program.remove(redBox);
		}
		if(description!=null) {
			program.remove(description);
		}
		if(usedKey!=null) {
			program.remove(usedKey);
		}
		
		program.remove(choice1);
		program.remove(choice2);
		program.remove(killHim);
		program.remove(spareHim);
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
			//program.switchToMenu(); If using this line of code then going back to the game will cause a glitch with the Main Menu. 
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
		
		if(program.player.sprite.getBounds().intersects(doorLiving.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.fromBed = true;
			program.switchToNewGame();
		}
		
		if(program.player.sprite.getBounds().intersects(doorBedL.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 128,219);
		}
		
		if(getSelectedItem()!=null) {
			if(getSelectedItem().getRoomType()==RoomType.BEDROOMR && program.player.sprite.getBounds().intersects(doorBedR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(lockedDoor!=null) {program.remove(lockedDoor);}
				usedKey = new GLabel("Opened the door with key!", 210, 544);
				usedKey.setColor(Color.white);
				program.add(usedKey);
				doorBedR.unlock();
				program.player.getInventory().deleteItem(getSelectedItem());
				program.remove(getSelectedItem().getInvSprite());
				int delay = 5000;
			    ActionListener taskPerformer = new ActionListener() {
			    	public void actionPerformed(ActionEvent evt) {
			        program.remove(usedKey);
			    	}
			    };
			    javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
			    tick.setRepeats(false);
			    tick.start();
			}
		}
		
		if(program.player.sprite.getBounds().intersects(doorBedR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!doorBedR.isLocked()) {
				program.remove(program.player.getImage());
				program.add(program.player.getImage(), 672,219);
			}else {
				lockedDoor = new GLabel("Door is locked!", 210, 544);
				lockedDoor.setColor(Color.white);
				program.add(lockedDoor);
				int delay = 5000;
			    ActionListener taskPerformer = new ActionListener() {
			    	public void actionPerformed(ActionEvent evt) {
			        program.remove(lockedDoor);
			    	}
			    };
			    javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
			    tick.setRepeats(false);
			    tick.start();
			}
		}
		if(program.player.sprite.getBounds().intersects(doorHallL.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 128,374);
		}
		
		if(program.player.sprite.getBounds().intersects(doorHallR.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,374);
		}
		
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "bedR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
				grab(program.itemAt(i));
			}
		}
		
	}
	
	public Item getSelectedItem() {
		return program.player.getInventory().getSelectedItem();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(program.player);}
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
}
