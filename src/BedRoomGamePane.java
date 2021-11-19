import java.awt.Color;
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
import acm.graphics.GRectangle;

public class BedRoomGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage background;
	private Timer timer;
	private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private Item item = new Item("Box",new GImage ("res/player/PCU1.png"));
	private Item itemKey = new Item("Box",new GImage("res/inventory/Key.png"));
	private int x = 722, y = 474;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	
	public BedRoomGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		background = new GImage("res/bedrooms.png");
		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.start();
		
	}
	
	private boolean keyE(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_E)   {return true;}
		else {return false;}
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
		for (int i=0; i<7; i++) {program.add(walls.get(i));}
		
		program.add(background);
		program.add(player.getImage(), x, y);
		player.setX(x);
		player.setY(y);
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		program.add(item.getImage(), x, y);
		item.setX(x);
		item.setY(y);
		
		program.add(itemKey.getImage(), 200, 100);
		itemKey.setX(x);
		itemKey.setY(y);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(player.getImage());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		checkCollision();
		
		if(keyE(e))   {
			if(player.getDirection() == "Up")   {
				if(item.getY() >= player.getY() - 64 && item.getY() <= player.getY() - 32)   {
					player.grabItem(item);
					System.out.println("Item got from up");
				}
			}
			if(player.getDirection() == "Down")   {
				if(item.getY() <= player.getY() + 64 && item.getY() >= player.getY() + 32)   {
					player.grabItem(item);
					System.out.println("Item got from down");}
				
				}
			if(player.getDirection() == "Left")   {
				if(item.getX() >= player.getX() - 64 && item.getX() <= player.getX() - 32)   {
					player.grabItem(item);
					System.out.println("Item got from left");}
			}
			if(player.getDirection() == "Right")   {
				if(item.getX() <= player.getX() + 64 && item.getX() >= player.getX() + 32)   {
					player.grabItem(item);
					System.out.println("Item got from right");}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(player);}
	
}