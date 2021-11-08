import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private MapPane map;
	private Player player = new Player(0, 0);
	private int x = 100, y = 100;
	private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
	
	//Shortcut to check on whether WASD / arrow keys are being pressed
	private boolean keyUp(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)   {return true;}
		else {return false;}	
	}
	
	private boolean keyDown(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)   {return true;}
		else {return false;}	
	}
	
	private boolean keyLeft(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)   {return true;}
		else {return false;}
	}
	
	private boolean keyRight(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)   {return true;}
		else {return false;}
	}
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		map = new MapPane(program);
	}

	public boolean canWalk() {
		//GObject a = getElementAt(player.getX(),player.getY());
		return true;
		
	}
	
	@Override
	public void showContents() {
		map.showContents();
		program.add(player.getImage(), x, y);
	}

	@Override
	public void hideContents() {
		map.hideContents();
		program.remove(player.getImage());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
	
	//moves the sprite of the character via WASD and Arrow keys, and changes direction sprite faces
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (keyUp(e)) {upPressed = true;}
		if (keyDown(e)) {downPressed = true;}
		if (keyLeft(e)) {leftPressed = true;}
		if (keyRight(e)) {rightPressed = true;}
		
		if (upPressed) {
			if(leftPressed || rightPressed)   {player.setDY(-2);}
			else {player.setDY(-4);}
			player.move(Player.dir.UP);
		}
		if (downPressed) {
			if(leftPressed || rightPressed)   {player.setDY(2);}
			else   {player.setDY(4);}
			player.move(Player.dir.DOWN);
        }
		if (leftPressed) {
			if(upPressed || downPressed)   {player.setDX(-2);;}
			else   {player.setDX(-4);}
			player.move(Player.dir.LEFT);
        }
		if (rightPressed) {
			if(upPressed || downPressed)   {player.setDX(2);;}
			else   {player.setDX(4);}
			player.move(Player.dir.RIGHT);
        }
	}
	
	//manages the use case of user attempting to use multiple movement directions at once, to use diagonal movement
	@Override
	public void keyReleased(KeyEvent e) {
		if (keyUp(e)) {upPressed = false;}
		if (keyDown(e)) {downPressed = false;}
		if (keyLeft(e)) {leftPressed = false;}
		if (keyRight(e)) {rightPressed = false;}
		
		if (upPressed)   {player.setDY(-4);}
		else if (downPressed)   {player.setDY(4);}
		else   {player.setDY(0);}
		
		if (leftPressed)   {player.setDX(-4);}
		else if (rightPressed)   {player.setDX(4);}
		else   {player.setDX(0);}
		
	}
}
