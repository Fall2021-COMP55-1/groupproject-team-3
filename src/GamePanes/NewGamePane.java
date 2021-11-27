package GamePanes;

import Boilerplate.*;
import Item.*;
import Entity.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background;
	private Timer timer;
	//private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 482, y = 510;
	private Door doorBed, doorBath, outBath;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	private GImage MainMenu, ResumeGame, SaveGame, Quit; 
	private GButton MainMenu2, ResumeGame1, SaveGame1, Quit1; 
	
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
			
	}

	public void setDoors() {
		doorBed = new Door(64,150,64,20, true);
		doorBath = new Door(672,380,32,20, false);
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
		program.addItem(itemKnife1);
		
		Item itemKey1 = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, "livingR");
		itemKey1.setX(200);
		itemKey1.setY(100);
		program.addItem(itemKey1);
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
		for (int i=0; i<13; i++) {program.add(walls.get(i));}
		
		program.add(background);
		if (!program.fromBed) {
			program.add(program.player.getImage(), x, y);
			program.player.setX(x);
			program.player.setY(y);
		} else {
			program.add(program.player.getImage(),85,172);
			program.player.setX(85);
			program.player.setY(172);
		}
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		program.player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		program.add(doorBed.getRect());
		program.add(doorBath.getRect());
		program.add(outBath.getRect());
		
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="livingR" && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
			if (program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getInvSprite());
			}
		}
		
		
		program.add(MainMenu);
		program.add(MainMenu2);
		program.add(ResumeGame);
		program.add(ResumeGame1);
		program.add(SaveGame);
		program.add(SaveGame1);
		program.add(Quit);
		program.add(Quit1);
		
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
			program.remove(program.itemAt(i).getInvSprite());
		}
		
		program.remove(MainMenu);
		program.remove(MainMenu2);
		program.remove(ResumeGame);
		program.remove(ResumeGame1);
		program.remove(SaveGame);
		program.remove(SaveGame1);
		program.remove(Quit);
		program.remove(Quit1);
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
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		checkCollision();
		if(program.player.sprite.getBounds().intersects(doorBed.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.switchToBedRoom();
		}
		
		if(program.player.sprite.getBounds().intersects(doorBath.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,250);
		}
		
		if(program.player.sprite.getBounds().intersects(outBath.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.remove(program.player.getImage());
			program.add(program.player.getImage(), 672,400);

		}
		
		
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "livingR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
				grab(program.itemAt(i));
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(program.player);}
	
}
