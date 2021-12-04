package GamePanes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Item.*;
import Boilerplate.*;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class BedRoomGamePane extends GraphicsPane implements ActionListener {

	private MainApplication program; 
	private GImage background = new GImage("res/bedrooms.png");
	private int playerX = 722, playerY = 474;
	private ArrayList <GRect> walls = new ArrayList <GRect>();
	private GImage choice1, choice2;
	private GButton killHim , spareHim;

	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
	}

<<<<<<< HEAD
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
	
	

=======
>>>>>>> branch 'main' of https://github.com/Fall2021-COMP55-1/groupproject-team-3.git
	@Override
	public void showContents() {
		//1. walls
		for (int i=0; i<walls.size(); i++) {program.add(walls.get(i));}
		//2. background image
		program.add(background);
		//3. doors
		program.Doors(true,MapType.BEDR);
		//4. player and monster and npc
		program.addPlayer(playerX, playerY);
		program.player.getInventory();
<<<<<<< HEAD
		if(program.NPC.isDead() == false)   {program.addNPC(100, 150);}
		if(program.NPC.isDead())   {walls.remove(walls.size() - 1);}
		program.addMonster(playerX + 16,  playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		//inventory hot bar image
		//4. inventory hot bar image
=======
		program.addMonster(playerX + 20,  playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		if(!program.NPC.isDead()) {program.addNPC(540, 450);
		}else {walls.remove(walls.size() - 1);}
		//5. inventory hot bar image
>>>>>>> branch 'main' of https://github.com/Fall2021-COMP55-1/groupproject-team-3.git
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//6. items on the map
		program.Items(true, MapType.BEDR);
		//7. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
<<<<<<< HEAD
		//doors
		program.addDoorBedRoom();
		program.player.getInventory().setHighlightVisible(true);
		//7. interactive choices
		
=======
		//8. interactive choices
>>>>>>> branch 'main' of https://github.com/Fall2021-COMP55-1/groupproject-team-3.git
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40);
<<<<<<< HEAD
		
		//8. GUI
=======
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
		//9. GUI
>>>>>>> branch 'main' of https://github.com/Fall2021-COMP55-1/groupproject-team-3.git
		program.addGUI();
	}
	
	@Override
	public void hideContents() {
		//1. walls
		for(int i=0; i<walls.size(); i++) {program.remove(walls.get(i));}
		//2. background image
		program.remove(background);
		//3. doors
		program.Doors(false,MapType.BEDR);
		//3 player and monster and npc
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
		program.remove(program.NPC.getImage());		
		program.monsterTimer.stop();
		//4. inventory hot bar image 
		program.remove(Inventory.INVENTORY_IMG);
		//5. items on the map
		program.Items(false, MapType.BEDR);
		//6. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
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
<<<<<<< HEAD
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
=======
		program.pauseMenu(obj);
>>>>>>> branch 'main' of https://github.com/Fall2021-COMP55-1/groupproject-team-3.git
	
		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {playerInv.drawSelectedItem();}
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
		program.pickUpItem(e);
		
		//to select item with 12345 key
		program.setSelectedItem(e);
		
		/* to interact with npc
		 * the reason I made biggerBounds is 
		 * when player gets close to the npc not goes through, 
		 * interaction should be available
		 */
		double biggerH = program.NPC.sprite.getHeight()+10;
		double biggerW = program.NPC.sprite.getWidth()+10;
		double biggerX = program.NPC.sprite.getX()-5;
		double biggerY = program.NPC.sprite.getY()-5;
		GRectangle biggerBounds = new GRectangle(biggerX, biggerY, biggerH, biggerW);
		if(e.getKeyCode()==KeyEvent.VK_E && program.player.sprite.getBounds().intersects(biggerBounds)) {
			if(program.itemKnife.isPickedUp()) {
				System.out.println("You have knife to kill npc");
				if(program.getSelectedItem()==program.itemKnife) {
					//make buttons for killing the npc or spare the npc
				}
			}else {
				//make the buttons for interactive choices (no killing involved)
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}
	
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
}
