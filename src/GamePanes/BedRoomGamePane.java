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

public class BedRoomGamePane extends GraphicsPane implements ActionListener {

	private MainApplication program; 
	public GImage background = new GImage("res/bedrooms.png");
	public int playerX = 722, playerY = 474;
	private ArrayList <GRect> walls = new ArrayList <GRect>();
	private GImage choice1, choice2; 
	private GButton killHim, spareHim;
	ChoiceHandler choiceHandler = new ChoiceHandler();  

	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		program.setDoorsBedRoom();
		program.setItemsBedRoom();
		program.setGUI(); 
	}
	
	@Override
	public void showContents() {
		//1. walls
		for (int i=0; i<8; i++) {program.add(walls.get(i));}
		//2. background image
		program.add(background);
		//3. player and monster and npc
		program.add(program.player.getImage(), playerX, playerY);
		program.player.setX(playerX);
		program.player.setY(playerY);
		program.player.getInventory();
		program.add(program.monster.getImage(), playerX + 20, playerY + 50);
		program.monster.setX(playerX + 20);
		program.monster.setY(playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		if(!program.NPC.isDead()) {
			program.add(program.NPC.getImage(), 540, 450);
			program.NPC.setX(540);
			program.NPC.setY(450);
		}else {
			walls.remove(walls.size() - 1);
		}
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
		program.player.getInventory().setHighlightVisible(true);
		//7. interactive choices
		choice1 = new GImage("res/interactive choices/Choice 1.png", 500, 555);
		choice2 = new GImage("res/interactive choices/Choice 2.png", 500, 600); 
		choice1.setSize(150, 40);
		choice2.setSize(150, 40); 
		killHim = new GButton("", 500, 555, 150, 40);
		spareHim = new GButton("", 500, 600, 150, 40);
		program.add(choice1);
		program.add(choice2);
		program.add(killHim);
		program.add(spareHim);
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
		//3. player and monster and npc
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
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
		if (obj == program.quit) {
			System.exit(0);
		}
		
		if (obj == killHim) {
			program.NPC.setDead(true);
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
	}	
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
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
		GRect npc = new GRect(100, 150, 32, 32);
		npc.setVisible(false);
		walls.add(npc);
	}
}
