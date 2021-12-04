package GamePanes;
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
import acm.graphics.GRectangle;

public class BedRoomGamePane extends GraphicsPane implements ActionListener {

	private MainApplication program; 
	private GImage background = new GImage("res/bedrooms.png"), choice1, choice2, pauseImge; 
	private Timer monsterTimer;
	private int playerX = 722, playerY = 474;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	ArrayList <GObject> GUI = new ArrayList <GObject>();
	private GButton killHim, spareHim, pauseButton;

	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		program.setDoorsBedRoom();
		program.setItemsBedRoom();
		program.setGUI(); 
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
		
		GRect npc = new GRect(102, 152, 10, 10);
		npc.setVisible(false);
		walls.add(npc);
	}
	
	public void setGUI() {
		pauseImge = new GImage("res/texture/pause.png", 768, 0); 
		pauseImge.setSize(32, 32);
		pauseImge.setVisible(true);
		pauseButton = new GButton("", 768, 0, 32, 32); 
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
		//1. walls
		for (int i=0; i<8; i++) {program.add(walls.get(i));}
		//2. background image
		program.add(background);

		//player and monster
		program.addPlayer(playerX, playerY);

		//3. player and monster and npc
		program.player.getInventory();
		if(program.NPC.isDead() == false)   {program.addNPC(100, 150);}
		if(program.NPC.isDead())   {walls.remove(walls.size() - 1);}
		program.addMonster(playerX + 16,  playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		//inventory hot bar image
		//4. inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//5. items on the map
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="bedR"  && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
		}
		//6. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//doors
		program.addDoorBedRoom();
		program.player.getInventory().setHighlightVisible(true);
		//7. interactive choices
		
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40);
		
		//8. GUI
		program.addGUI();
	}
	
	@Override
	public void hideContents() {
		//1. walls
		for(int i=0; i<7; i++) {program.remove(walls.get(i));}
		//2. background image
		program.remove(background);
		//2.5 doors
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
		program.monsterTimer.stop();
		program.remove(program.NPC.getImage());
		//4. inventory hot bar image 
		program.remove(Inventory.INVENTORY_IMG);
		//5. items on the map
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		//6. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		program.player.getInventory().setHighlightVisible(false);
		//7. interactive choices
		program.remove(choice1);
		program.remove(choice2);
		program.remove(killHim);
		program.remove(spareHim);
		//7.5 labels of wrongItem, lockedDoor, keyUsed
		program.removeLabels();
		//8. GUI
		program.removeGUI();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == program.pauseButton) {
			program.monsterTimer.stop();
			program.pause();
		}
		if (obj == program.resume) {
			program.resume();
			program.monsterTimer.setInitialDelay(0);
			program.monsterTimer.restart();
		}	
		
		if (obj == program.quit) {System.exit(0);}
		
		if (obj == killHim || obj == spareHim)   {
			if (obj == killHim)   {program.NPC.setDead(true);}
			program.remove(choice1);
			program.remove(choice2);
			program.paused = false;
			program.monsterTimer.start();
		}
	
		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {
			playerInv.drawSelectedItem();
		}
	}
	
	private void interactiveChoices() {
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		// Interactive choices showing up when intersecting with NPC		
		if(program.player.sprite.getBounds().intersects(program.NPC.sprite.getBounds()) && e.getKeyCode()==KeyEvent.VK_E) {
			if(program.getSelectedItem().type == ItemType.WEAPON) {
				interactiveChoices(); 
				program.paused = true;
				program.monsterTimer.stop();
				
			}
		}
		
		program.checkCollision(walls);
		
		//back to living room map
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
		
		//to interact with npc
		double biggerH = program.NPC.sprite.getHeight()+10;
		double biggerW = program.NPC.sprite.getWidth()+10;
		double biggerX = program.NPC.sprite.getX()-5;
		double biggerY = program.NPC.sprite.getY()-5;
		GRectangle biggerBounds = new GRectangle(biggerX, biggerY, biggerH, biggerW);
		if(e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(biggerBounds)) {
			//make the buttons for interactive choices appear
			if(program.getSelectedItem()==program.itemKnife) {
				//make buttons for killing the npc or spare the npc
			}
		}
	}	
	
	public void addgui() {
		GUI.add(pauseImge); GUI.add(pauseButton); GUI.add(choice1); GUI.add(choice2); GUI.add(killHim); GUI.add(spareHim);
		
		for (int i = 0; i < 7; ++ i)   {program.add(GUI.get(i));}
		program.updatePlayerHeartsGUI(program.player.getHP());
		
	}

	public void removegui() {
		for (int i = 0; i < 7; ++ i)   {program.remove(GUI.get(i));}
		program.updatePlayerHeartsGUI(0);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}
	
}
