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
		program.setGUI(); 
	}

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
		program.addMonster(playerX + 20,  playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		if(!program.NPC.isDead()) {program.addNPC(540, 450);
		}else {walls.remove(walls.size() - 1);}
		//5. inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//6. items on the map
		program.Items(true, MapType.BEDR);
		//7. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//8. interactive choices
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
		//9. GUI
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
		program.pauseMenu(obj);
	
		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {playerInv.drawSelectedItem();}
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
