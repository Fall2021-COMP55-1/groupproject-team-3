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
	private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private ArrayList<Item> items = new ArrayList <Item>();
	private Item itemKnife = new Item("Knife",new GImage ("res/inventory/Small Knife.png"), ItemType.WEAPON);
	private Item itemKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY);
	private int x = 482, y = 510;
	private GRect doorBed = new GRect(64,128,64,64);
	ArrayList <GRect> walls = new ArrayList <GRect>();
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		background = new GImage("res/livingroom.png");
		timer = new Timer(100, this);
		timer.setInitialDelay(6000);
		timer.start();
		doorBed.setVisible(false);
	}
	
	private boolean keyE(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_E)   {return true;}
		else {return false;}
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
		
	public boolean checkCollision() {
		Iterator<GRect> iterate = walls.iterator();
		while(iterate.hasNext()) {
			GRect temp = iterate.next();
			if(player.sprite.getBounds().intersects(temp.getBounds())) {
				
				if(player.getDX() > 0)   {
					if(player.getDY() > 0)   {player.sprite.move(-2, -2);}
					if(player.getDY() < 0)   {player.sprite.move(-2, 2);}
					if(player.getDY() == 0)   {player.sprite.move(-4, 0);}
				}
				if(player.getDX() < 0)   {
					if(player.getDY() > 0)   {player.sprite.move(2, -2);}
					if(player.getDY() < 0)   {player.sprite.move(2, 2);}
					if(player.getDY() == 0)   {player.sprite.move(4, 0);}
				}
				if(player.getDY() < 0)   {
					if(player.getDX() > 0)   {player.sprite.move(-2, 2);}
					if(player.getDX() < 0)   {player.sprite.move(2, 2);}
					if(player.getDX() == 0)   {player.sprite.move(0, 4);}
				}
				if(player.getDY() > 0)   {
					if(player.getDX() > 0)   {player.sprite.move(-2, -2);}
					if(player.getDX() < 0)   {player.sprite.move(2, -2);}
					if(player.getDX() == 0)   {player.sprite.move(0, -4);}
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
		program.add(player.getImage(), x, y);
		player.setX(x);
		player.setY(y);
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		program.add(doorBed);
		items.add(itemKnife);
		items.add(itemKey);
		items.get(0).setX(x);
		items.get(0).setY(y);
		items.get(1).setX(200);
		items.get(1).setY(100);
		for (int i = 0; i < items.size(); ++i)   {
			program.add(items.get(i).getImage(), items.get(i).getX(), items.get(i).getY());
		}
		
	}

	private void grab(int i)   {
		player.grabItem(items.get(i));
		items.get(i).setPickedUp(true);
		program.remove(items.get(i).getImage());
		program.add(items.get(i).getInvSprite());
	}
	
	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(player.getImage());
		for(int i=0; i<13; i++) {
			program.remove(walls.get(i));
		}
		program.remove(doorBed);
		program.remove(monster.getImage());
		for (int i = 0; i < items.size(); ++i) {
			program.remove(items.get(i).getImage());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == doorBed) {
			program.switchToBedRoom();
		}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		checkCollision();
		
		if(keyE(e))   {	
			for (int i = 0; i < items.size(); ++i)   {
				if(player.getDirection() == "Up")   {
					if(items.get(i).getY() >= player.getY() - 64 && items.get(i).getY() <= player.getY() - 32)   {
						grab(i);}
				}
				if(player.getDirection() == "Down")   {
					if(items.get(i).getY() <= player.getY() + 64 && items.get(i).getY() >= player.getY() + 32)   {
						grab(i);}	
				}
				if(player.getDirection() == "Left")   {
					if(items.get(i).getX() >= player.getX() - 64 && items.get(i).getX() <= player.getX() - 32)   {
						grab(i);}
				}
				if(player.getDirection() == "Right")   {
					if(items.get(i).getX() <= player.getX() + 64 && items.get(i).getX() >= player.getX() + 32)   {
						grab(i);}
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(player);}
	
}
