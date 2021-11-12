import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane implements ActionListener {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private MapPane map;
	private Timer timer;
	private Player player = new Player(0, 0);
	private Monster monster = new Monster(0, 0, MonsterType.TALL);
	private int x = 100, y = 100, numTimes = 0;
	
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
	public void keyPressed(KeyEvent e) {player.keyPressed(e);}
	
	@Override
	public void keyReleased(KeyEvent e) {player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {monster.move(player);	}
	
}
