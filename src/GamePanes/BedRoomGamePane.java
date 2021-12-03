package GamePanes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import Item.*;
import Boilerplate.*;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class BedRoomGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background, choice1, choice2, pauseImge; 
	private Timer monsterTimer;
	private int playerX = 722, playerY = 474;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	ArrayList <GObject> GUI = new ArrayList <GObject>();
	private GButton killHim, spareHim, pauseButton;
	ChoiceHandler choiceHandler = new ChoiceHandler();  
	
	//health
	private GParagraph healthPoints; 
	private static final int heartRootX = 75, heartRootY = 610, heartWidth = 30;
	ArrayList <GImage> playerHearts = new ArrayList <GImage>(); 

	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		program.setDoorsBedRoom();
		setItems();
		setGUI();
		background = new GImage("res/bedrooms.png");
		monsterTimer = new Timer(100, this);
	}
	
	public void setWalls() {
		GRect wall1 = new GRect(0,0,800,96);
		GRect wall2 = new GRect(0,256,800,96);
		GRect wall3 = new GRect(0,448, 704 ,192);
		GRect wall4 = new GRect(0,0,32,640);
		GRect wall5 = new GRect(768,0,32,640);
		GRect wall6 = new GRect(352,32,64,224);
		GRect wall7 = new GRect(704,512,64,128);
		
		walls.add(wall1); walls.add(wall2); walls.add(wall3); walls.add(wall4); walls.add(wall5); walls.add(wall6); walls.add(wall7);
		
		for (int i = 0; i < 7; ++i)   {walls.get(i).setFilled(true);}
		
		GRect npc = new GRect(100, 150, 32, 32);
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
		program.addPlayer(playerX, playerY);
		program.player.getInventory();
		if(program.NPC.isDead() == false)   {program.addNPC(540, 450);}
		if(program.NPC.isDead())   {walls.remove(walls.size() - 1);}
		program.addMonster(playerX + 16,  playerY + 50);
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
		program.addDoorBedRoom();
		program.player.getInventory().setHighlightVisible(true);
		addgui();
	}
	
	@Override
	public void hideContents() {
		program.remove(background);
		program.removeDoorBedRoom();
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
		
		for(int i = 0; i < 7; i++) {program.remove(walls.get(i));}
		
		for(int i = 0; i < program.numItems(); ++i)   {program.remove(program.itemAt(i).getImage());}
		
		for(int i = 0; i < program.player.getInventory().numInvItems(); i++) {program.remove(program.player.getInventory().itemAt(i).getInvSprite());}
		
		program.remove(Inventory.INVENTORY_IMG);
		program.removeLabels();
		
		removegui();
		monsterTimer.stop();
		//redBox still shows
		program.player.getInventory().setHighlightVisible(false);
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
		
		if (obj == killHim || obj == spareHim)   {
			if (obj == killHim)   {program.NPC.setDead(true);}
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
		program.openDoor(program.inLivingMap,e);
		
		//get into left bedroom
		program.openDoor(program.inLeftBed,e,128,219);
		
		//unlock the right bedroom with key
		program.unlockDoor(program.inRightBed,e);
		
		//get into right bedroom
		program.openDoor(program.inRightBed,e);
		
		//get out of left bedroom
		program.openDoor(program.outLeftBed,e,128,374);
		
		//get out of right bedroom
		program.openDoor(program.outRightBed,e,672,374);
		
		//pick up item with E
		for (int i=0; i<program.numItems(); i++) {
			if(program.itemAt(i).getMap() == "bedR" && e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(program.itemAt(i).getImage().getBounds())) {
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
		GUI.add(pauseImge); GUI.add(pauseButton); GUI.add(healthPoints); GUI.add(choice1); GUI.add(choice2); GUI.add(killHim); GUI.add(spareHim);
		
		for (int i = 0; i < 7; ++ i)   {program.add(GUI.get(i));}
		updatePlayerHeartsGUI(program.player.getHP());
		
	}

	public void removegui() {
		for (int i = 0; i < 7; ++ i)   {program.remove(GUI.get(i));}
		updatePlayerHeartsGUI(0);
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		program.player.keyReleased(e);}

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
		}
	}
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
}
