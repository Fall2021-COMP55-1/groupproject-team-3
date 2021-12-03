package GamePanes;
import Boilerplate.*;
import Item.*;
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
	private GImage background, pauseImg; 
	private Timer monsterTimer;
	private int playerX = 482, playerY = 510;
	private ArrayList <GRect> walls = new ArrayList <GRect>(); 
	private ArrayList <GObject> GUI = new ArrayList <GObject>();
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
		GRect wall2 = new GRect(0,64,160,96);
		GRect wall3 = new GRect(0,160, 32,480);
		GRect wall4 = new GRect(32,224,128,128);
		GRect wall5 = new GRect(160,256,288,128);
		GRect wall6 = new GRect(32,512,448,128);
		GRect wall7 = new GRect(480,544,64,96);
		GRect wall8 = new GRect(544,512,256,128);
		GRect wall9 = new GRect(576,448,224,64);
		GRect wall10 = new GRect(768,64,32,384);
		GRect wall11 = new GRect(576,64,192,128);
		GRect wall12 = new GRect(576,192,64,96);
		GRect wall13 = new GRect(576,288,192,96);
		
		walls.add(wall1); walls.add(wall2); walls.add(wall3); walls.add(wall4); walls.add(wall5); walls.add(wall6); walls.add(wall7);
		walls.add(wall8); walls.add(wall9); walls.add(wall10); walls.add(wall11); walls.add(wall12); walls.add(wall13);
		
		for (int i = 0; i < 13; ++i)   {walls.get(i).setFilled(true);}
		
		GRect npc = new GRect(540, 450, 32, 32);
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
			program.addPlayer(85, 172);
			program.addMonster(35, 102);
			
			if(program.NPC.isDead() == false)   {program.addNPC(540, 450);}
			else  {walls.remove(walls.size() - 1);}
			
			program.fromBedtoLiving=false;
			monsterTimer.setInitialDelay(1000);
		} else {
			program.addPlayer(playerX, playerY);
			program.addMonster(playerX + 20, playerY + 50);
			program.addNPC(540, 450);
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
		program.add(goal);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(program.player.getImage());
		
		for(int i=0; i<13; i++) {program.remove(walls.get(i));}
		
		program.removeDoorLiving();
		
		program.remove(program.monster.getImage());
		
		program.remove(Inventory.INVENTORY_IMG);
		
		for (int i = 0; i < program.numItems(); ++i)   {program.remove(program.itemAt(i).getImage());}
		
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {program.remove(program.player.getInventory().itemAt(i).getInvSprite());}
		
		program.removeLabels();
		
		removegui();
		monsterTimer.stop();
		//redBox still shows
		program.player.getInventory().setHighlightVisible(false);
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
		
		if (obj == program.quit) {System.exit(0);}
		
		//click item in hot bar to select
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {playerInv.drawSelectedItem();}
		
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
		GUI.add(pauseImg); GUI.add(pauseButton); GUI.add(healthPoints); 
		
		for(int i = 0; i < 3; ++i)   {program.add(GUI.get(i));}
		
		updatePlayerHeartsGUI(program.player.getHP());
	}

	public void removegui() {
		for(int i = 0; i < 3; ++i)   {program.remove(GUI.get(i));}
		
		updatePlayerHeartsGUI(0);
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {
		program.monster.move(program.player);
		if(program.monster.touchPlayer())   {
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
