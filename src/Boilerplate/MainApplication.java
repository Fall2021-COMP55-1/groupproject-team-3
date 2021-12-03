package Boilerplate;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

import Entity.*;
import Item.*;
import GamePanes.*;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MainApplication extends GraphicsProgram {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 640;
	public static final String MUSIC_FOLDER = "sounds";
		
	private GraphicsPane curScreen;
	private MenuPane menu;
	private NewGamePane newGame;
	private OptionPane options;
	private Credits credits; 
	private BedRoomGamePane bedroom;
	private GoodEndPane goodEnd;
	private BadEndPane badEnd;
	
	
	public Player player = new Player(0, 0, this);
	public NPC NPC = new NPC(540, 450, this);
	public Monster monster = new Monster(0, 0, MonsterType.TALL);
	public Timer monsterTimer = new Timer(100, this);
	
	public boolean fromBedtoLiving = false;
	
	private ArrayList<Item> items = new ArrayList <Item>();
	
	public static MusicBox music = new MusicBox();
	
	//label
	public GLabel keyUsed = null;
	public GLabel lockedDoor = null;
	public GLabel wrongItem = null;
	
	//pause and resume
	private GImage pauseImg;
	public GButton pauseButton;
	public boolean paused = false;
	public GButton resume = new GButton("", 296, 180, 208, 95);
	private GImage resumeImg = new GImage("res/texture/Resume.png",296,180);
	public GButton quit = new GButton("", 296, 420, 208, 95);
	private GImage quitImg = new GImage("res/texture/Quit.png",296, 420);
	
	//player health gui
	private GParagraph healthPoints; 
	private static final int heartRootX = 75, heartRootY = 610, heartWidth = 30;
	ArrayList <GImage> playerHearts = new ArrayList <GImage>(); 	
	
	//doors in bedroom map
	public Door inLivingMap, inLeftBed, inRightBed, outLeftBed, outRightBed;
	//doors in living room map
	public Door inBedMap, inBath, outBath, winning;
	
	//---------------------------------------------------------------------------
	
	/* GUI
	 * -----------------------------
	 * setGUI()
	 * addGUI()
	 * removeGUI()
	 * updatePlayerHeartsGUI(int hp)
	 */
	public void setGUI() {
		pauseImg = new GImage("res/texture/pause.png", 768, 0); 
		pauseImg.setSize(32, 32);
		pauseImg.setVisible(true);
		pauseButton = new GButton("", 768, 0, 32, 32); 
		healthPoints = new GParagraph("HP:", 50, 625);
		healthPoints.setColor(Color.white); 
		healthPoints.setFont("Arial-12");
	}
	public void addGUI() {
		this.add(pauseImg);
		this.add(pauseButton);
		this.add(healthPoints);
		updatePlayerHeartsGUI(this.player.getHP());
	}
	public void removeGUI() {
		this.remove(pauseImg);
		this.remove(pauseButton);
		this.remove(healthPoints);
		updatePlayerHeartsGUI(0);
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
				this.add(heart); 
			}
		}
		else if (dif < 0) {
			dif = dif * -1; // Absolute value
			for (int i = 0; i < dif; i++) {
				int end = playerHearts.size() - 1;
				GImage heart = playerHearts.get(end);
				this.remove(heart);
				playerHearts.remove(end);
			}
		}
	}
	
	/* Doors
	 * --------------------------------------------
	 * setDoorsLiving()
	 * removeDoorsLiving()
	 * setDoorsBedRoom()
	 * removeDoorsBedRoom()
	 * unlockDoor(Door door, KeyEvent e)
	 * openDoor(Door door, KeyEvent e)
	 * openDoor(Door door, KeyEvent e, int x, int y)
	 */
	public void setDoorsLiving() {
		//door to bedroom map
		inBedMap = new Door(64,150,64,20, true);
		inBedMap.setRoomType(RoomType.BEDROOMS);
		//door to bathroom
		inBath = new Door(672,380,32,20, false);
		//door to hallway from bathroom
		outBath = new Door(672,283,32,10, false);
		winning = new Door(480,540,65,10, true);
		winning.setRoomType(RoomType.OUT);
		this.add(inBedMap.getRect());
		this.add(inBath.getRect());
		this.add(outBath.getRect());
		this.add(winning.getRect());
	}
	public void removeDoorLiving() {
		this.remove(inBedMap.getRect());
		this.remove(inBath.getRect());
		this.remove(outBath.getRect());
		this.remove(winning.getRect());
	}
	public void setDoorsBedRoom() {
		//door to living room map
		inLivingMap = new Door(704,510,64,4, false);
		//door to left bedroom
		inLeftBed = new Door(128,340,32,20, true);
		//door to right bedroom
		inRightBed = new Door(672,340,32,20, true);
		inRightBed.setRoomType(RoomType.BEDROOMR);
		//door to hallway from left bedroom
		outLeftBed = new Door(128,252,32,5, false);
		//door to hallway from right bedroom
		outRightBed = new Door(672,252,32,5, false);
		this.add(inLivingMap.getRect());
		this.add(inLeftBed.getRect());
		this.add(inRightBed.getRect());
		this.add(outLeftBed.getRect());
		this.add(outRightBed.getRect());
	}
	public void removeDoorBedRoom() {
		this.remove(inLivingMap.getRect());
		this.remove(inLeftBed.getRect());
		this.remove(inRightBed.getRect());
		this.remove(outLeftBed.getRect());
		this.remove(outRightBed.getRect());
	}
	public void unlockDoor(Door door, KeyEvent e) {
		if(getSelectedItem()!=null) {
			if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(getSelectedItem().getRoomType()==door.getRoomType()) {
					if(wrongItem!=null) {wrongItem.setVisible(false);} 
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					keyUsed = new GLabel("Unlocked the door with key!", 210, 550);
					keyUsed.setColor(Color.white);
					this.add(keyUsed);
					door.unlock();
					this.player.getInventory().deleteItem(this,getSelectedItem());
					this.remove(getSelectedItem().getInvSprite());
					this.label5sec(keyUsed);
				}else {
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					wrongItem = new GLabel("Wrong item!", 210, 575);
					wrongItem.setColor(Color.white);
					this.add(wrongItem);
					this.label5sec(wrongItem);
				}	
			}
		}
	}
	public void openDoor(Door door, KeyEvent e) {
		if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!door.isLocked()) {
				if(door == inBedMap) {
					this.switchToBedRoom();
				}else if (door == winning) {
					this.switchToGoodEnd();
				}else if (door == inLivingMap) {
					this.fromBedtoLiving = true;
					this.switchToNewGame();
				}else if(door == inRightBed) {
					this.remove(this.player.getImage());
					this.add(this.player.getImage(), 672,219);
				}
			}else {
				if(wrongItem!=null) {wrongItem.setVisible(false);}
				lockedDoor = new GLabel("Door is locked!", 210, 550);
				lockedDoor.setColor(Color.white);
				this.add(lockedDoor);
				this.label5sec(lockedDoor);
			}
		}
	}
	public void openDoor(Door door, KeyEvent e, int x, int y) {
		if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			this.remove(this.player.getImage());
			this.add(this.player.getImage(), x,y);
		}
	}
	
	/* Items
	 * ---------------------------
	 * setItemsLiving()
	 * setItemsBedRoom()
	 * getSelectedItem()
	 * setSelectedItem(KeyEvent e)
	 * addItem(Item item)
	 * numItems()
	 * itemAt(int i)
	 * grab(Item item)
	 */
	public void setItemsLiving() {
		Item itemKnife1 = new Item("Knife",new GImage ("res/inventory/Small Knife.png"), ItemType.WEAPON, "livingR");
		itemKnife1.setX(734);
		itemKnife1.setY(259);
		itemKnife1.setDescription("Knife to kill");
		this.addItem(itemKnife1);
		Item bedroomKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, "livingR");
		bedroomKey.setX(200);
		bedroomKey.setY(100);
		bedroomKey.setRoomType(RoomType.BEDROOMS);
		bedroomKey.setDescription("Key to hallway of bedrooms");
		this.addItem(bedroomKey);
	}
	public void setItemsBedRoom() {
		Item winningKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, "bedR");
		winningKey.setX(422);
		winningKey.setY(217);
		winningKey.setRoomType(RoomType.OUT);
		winningKey.setDescription("Key of the house");
		this.addItem(winningKey);
		Item itemKey2 = new Item("Key", new GImage("res/inventory/Small Key.png"), ItemType.KEY, "bedR");
		itemKey2.setX(116);
		itemKey2.setY(184);
		itemKey2.setRoomType(RoomType.BEDROOMR);
		itemKey2.setDescription("Key to master bedroom");
		this.addItem(itemKey2);
	}
	public Item getSelectedItem() {
		return this.player.getInventory().getSelectedItem();
	}
	public void setSelectedItem(KeyEvent e) {
		Inventory playerInv = this.player.getInventory();
		if(e.getKeyCode()==KeyEvent.VK_1) {
			if (playerInv.setSelectedItem(0)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_2) {
			if (playerInv.setSelectedItem(1)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_3) {
			if (playerInv.setSelectedItem(2)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_4) {
			if (playerInv.setSelectedItem(3)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_5) {
			if (playerInv.setSelectedItem(4)) {
				playerInv.drawSelectedItem();
			}
		}
	}
	public void addItem(Item item) {items.add(item);}
	public int numItems() {return items.size();}
	public Item itemAt(int i) {return items.get(i);}
	public void grab(Item item)   {
		this.player.grabItem(item);
		item.setPickedUp(true);
		this.remove(item.getImage());
		this.add(item.getInvSprite());
	}
	
	/* Pause and Resume
	 * ----------------
	 * pause()
	 * resume()
	 */
	public void pause() { 
		this.add(resumeImg);
		this.add(quitImg);
		this.add(resume);
		this.add(quit);
		paused = true;
	}
	public void resume() {
		this.remove(resume);
		this.remove(quit);
		this.remove(resumeImg);
		this.remove(quitImg);
		paused = false;
	}
	
	/* Labels
	 * -----------------------
	 * removeLabels()
	 * label5sec(GLabel label)
	 */
	public void removeLabels() {
		if(keyUsed!=null) {this.remove(keyUsed);}
		if(wrongItem!=null) {this.remove(wrongItem);}
		if(lockedDoor!=null) {this.remove(lockedDoor);}
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
	
	//collision detect
	public boolean checkCollision(ArrayList<GRect> walls) {
		Iterator<GRect> iterate = walls.iterator();
		while(iterate.hasNext()) {
			GRect temp = iterate.next();
			if(this.player.sprite.getBounds().intersects(temp.getBounds())) {
				
				if(this.player.getDX() > 0)   {
					if(this.player.getDY() > 0)   {this.player.sprite.move(-2, -2);}
					if(this.player.getDY() < 0)   {this.player.sprite.move(-2, 2);}
					if(this.player.getDY() == 0)   {this.player.sprite.move(-4, 0);}
				}
				if(this.player.getDX() < 0)   {
					if(this.player.getDY() > 0)   {this.player.sprite.move(2, -2);}
					if(this.player.getDY() < 0)   {this.player.sprite.move(2, 2);}
					if(this.player.getDY() == 0)   {this.player.sprite.move(4, 0);}
				}
				if(this.player.getDY() < 0)   {
					if(this.player.getDX() > 0)   {this.player.sprite.move(-2, 2);}
					if(this.player.getDX() < 0)   {this.player.sprite.move(2, 2);}
					if(this.player.getDX() == 0)   {this.player.sprite.move(0, 4);}
				}
				if(this.player.getDY() > 0)   {
					if(this.player.getDX() > 0)   {this.player.sprite.move(-2, -2);}
					if(this.player.getDX() < 0)   {this.player.sprite.move(2, -2);}
					if(this.player.getDX() == 0)   {this.player.sprite.move(0, -4);}
				}
				return true;
			}
		}
		return false;
	}
	
	//music
		public static MusicBox getMusic() {return music;}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	protected void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	/* switchToScreen(newGraphicsPane)
	 * -------------------------------
	 * will simply switch from making one pane that was currently
	 * active, to making another one that is the active class.
	 */
	protected void switchToScreen(GraphicsPane newScreen) {
		if(curScreen != null) {
			curScreen.hideContents();
		}
		newScreen.showContents();
		curScreen = newScreen;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseReleased(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseClicked(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(curScreen != null) {
			curScreen.mouseMoved(e);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(curScreen != null) {
			if(!paused) {
				curScreen.keyPressed(e);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyReleased(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(curScreen != null) {
			curScreen.keyTyped(e);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		monster.move(this.player);
		if(monster.touchPlayer())   {
			if (this.player.getHP() <= 0)   {
				monsterTimer.stop();
			}else {
				monsterTimer.setInitialDelay(2000);
				monsterTimer.restart();
			}
			this.player.setHP(this.player.getHP() - 1);
			if(this.player.getHP()==0) {this.switchToBadEnd();}
			this.updatePlayerHeartsGUI(this.player.getHP());
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Let's make something awesome!");
		File Audio = new File("res/music/TestAudio.wav");
		music.PLAY(Audio);
		menu = new MenuPane(this);
		newGame = new NewGamePane(this);
		options = new OptionPane(this);
		credits = new Credits(this);
		bedroom = new BedRoomGamePane(this);
		goodEnd = new GoodEndPane(this);
		badEnd = new BadEndPane(this);
		setupInteractions();
		switchToMenu();
	}

	public void switchToMenu() {switchToScreen(menu);}
	
	public void switchToNewGame() {switchToScreen(newGame);}
		
	public void switchToOptions() {switchToScreen(options);}
	
	public void switchToCredits() {switchToScreen(credits);}

	public void switchToBedRoom() {switchToScreen(bedroom);}
	
	public void switchToGoodEnd() {switchToScreen(goodEnd);}
	
	public void switchToBadEnd() {switchToScreen(badEnd);}

	public static void main(String[] args) {new MainApplication().start();}

}
