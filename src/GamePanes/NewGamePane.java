package GamePanes;

import Boilerplate.*;

import Item.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class NewGamePane extends GraphicsPane implements ActionListener {

	private MainApplication program; 
	public GImage background = new GImage("res/livingroom.png");
	public int playerX = 482, playerY = 510;
	private ArrayList <GRect> walls = new ArrayList <GRect>(); 
	private GLabel goal = null;
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		program.setDoorsLiving();
		program.setItemsLiving();
		program.setGUI();
	}
	
	
		
	@Override
	public void showContents() {
		//1. walls
		for (int i=0; i<14; i++) {program.add(walls.get(i));}
		//2. background image
		program.add(background);
		//3. player and monster and npc and Goal Label
		if (program.fromBedtoLiving) {
			program.add(program.player.getImage(),85,172);
			program.player.setX(85);
			program.player.setY(172);
			program.add(program.monster.getImage(), 35, 102);
			program.monster.setX(35);
			program.monster.setY(102);
			if(program.NPC.isDead() == false)   {
				program.add(program.NPC.getImage(), 540, 450);
				program.NPC.setX(540);
				program.NPC.setY(450);
			}
			if(program.NPC.isDead())   {
				walls.remove(walls.size() - 1);
			}
			program.fromBedtoLiving=false;
			program.monsterTimer.setInitialDelay(1000);
		} else {
			program.add(program.player.getImage(), playerX, playerY);
			program.player.setX(playerX);
			program.player.setY(playerY);
			program.add(program.monster.getImage(), playerX + 20, playerY + 50);
			program.add(program.NPC.getImage(), 540, 450);
			program.NPC.setX(540);
			program.NPC.setY(450);
			program.monster.setX(playerX + 20);
			program.monster.setY(playerY + 50);
			program.monsterTimer.setInitialDelay(3000);
			goal = new GLabel("Your goal is to escape from this house! Good luck!", 450, 440);
			goal.setColor(Color.white);
			program.label5sec(goal);
			program.add(goal);
		}
		program.monsterTimer.start();
		program.player.getInventory();
		//4. Inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//5. items on the map
		for (int i = 0; i < program.numItems(); i++)   {
			if (program.itemAt(i).getMap()=="livingR" && !program.itemAt(i).isPickedUp()) {
				program.add(program.itemAt(i).getImage(), program.itemAt(i).getX(), program.itemAt(i).getY());
			}
		}
		//6. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}	
		//7. GUI
		program.addGUI();
	}

	@Override
	public void hideContents() {
		//1. walls
		for(int i=0; i<13; i++) {
			program.remove(walls.get(i));
		}
		//2. background
		program.remove(background);
		//2.5 doors
		program.removeDoorLiving();
		//3. player and monster and npc and Goal Label
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
		program.remove(program.NPC.getImage());
		program.monsterTimer.stop();
		program.remove(goal);
		//4. Inventory hot bar image
		program.remove(Inventory.INVENTORY_IMG);
		//5. items on the map
		for (int i = 0; i < program.numItems(); ++i)   {
			program.remove(program.itemAt(i).getImage());
		}
		//6. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//6.5 labels of wrongItem, lockedDoor, keyUsed
		program.removeLabels();
		//7. GUI
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
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}
	
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
		GRect npc = new GRect(540, 450, 32, 32);
		npc.setVisible(false);
		walls.add(npc);
	}
}
