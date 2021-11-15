import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private MapPane map;
	private Timer timer;
	private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 100, y = 100;
	private Item item = new Item("Box",new GImage ("res/player/PCU1.png"));
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		map = new MapPane(program);
		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	public boolean canWalk() {
		//GObject a = getElementAt(player.getX(),player.getY());
		return true;
	}
	
	private boolean keyE(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_E)   {return true;}
		else {return false;}
	}

	@Override
	public void showContents() {
		map.showContents();
		program.add(player.getImage(), x, y);
		player.setX(x);
		player.setY(y);
		program.add(monster.getImage(), x + 32, y + 32);
		monster.setX(x + 32);
		monster.setY(y + 32);
		player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		program.add(item.getImage(), x + 100, y + 100);
		item.setX(x + 100);
		item.setY(y + 100);
	}

	@Override
	public void hideContents() {
		map.hideContents();
		program.remove(player.getImage());
	}

	@Override
	public void mousePressed(MouseEvent e) {GObject obj = program.getElementAt(e.getX(), e.getY());}
	
	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
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
	public void keyReleased(KeyEvent e) {player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(player);}
	
}
