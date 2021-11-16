import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private MapPane map;
	private Timer timer;
	private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 384, y = 288, numTimes = 0;
	ArrayList <GRect> walls = new ArrayList <GRect>();
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
		map = new MapPane(program);
		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.start();
		
	}
	
	public void setWalls() {
		GRect wall1 = new GRect(0,0,320, 640);
		walls.add(wall1);
		wall1.setColor(Color.red);
		wall1.setFilled(true);
		wall1.setVisible(true);
		GRect wall2 = new GRect(480,0,320,640);
		walls.add(wall2);
		GRect wall3 = new GRect(320,0, 160, 256);
		walls.add(wall3);
		GRect wall4 = new GRect(320,352,160,288);
		walls.add(wall4);
		
	}
	
	
	public boolean checkCollision() {
		Iterator<GRect> iterate = walls.iterator();
		while(iterate.hasNext()) {
			GRect temp = iterate.next();
			if(player.sprite.getBounds().intersects(temp.getBounds())) {
				player.sprite.move(1, 0);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void showContents() {
		map.showContents();
		program.add(player.getImage(), x, y);
		program.add(monster.getImage(), x + 32, y + 32);
		player.getInv();
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
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
		//tried checking collision, it worked but it completely stops moving 
		if(!checkCollision()) {
			player.keyPressed(e);
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(player);	}
	
}
