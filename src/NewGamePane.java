import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GParagraph para;
	private GImage player = new GImage("res/player/PCU1.png");
	private ArrayList<GImage> spriteUp;
	private ArrayList<GImage> spriteDown;
	private ArrayList<GImage> spriteLeft;
	private ArrayList<GImage> spriteRight;
	private int x=100, y=100, dx, dy;
	private int stepsTaken = 0;
	private boolean sPressed = false, wPressed = false, aPressed = false, dPressed = false;
	
	public void move() {
		
		x+=dx;
		y+=dy;
	}
	
	public void addImages() {
		spriteUp = new ArrayList<GImage>(2);
		spriteDown = new ArrayList<GImage>(2);
		spriteLeft = new ArrayList<GImage>(2);
		spriteRight = new ArrayList<GImage>(2);
		spriteUp.add(new GImage("res/player/PCU1.png"));
		spriteUp.add(new GImage("res/player/PCU2.png"));
		spriteDown.add(new GImage("res/player/PCD1.png"));
		spriteDown.add(new GImage("res/player/PCD2.png"));
		spriteLeft.add(new GImage("res/player/PCL1.png"));
		spriteLeft.add(new GImage("res/player/PCL2.png"));
		spriteRight.add(new GImage("res/player/PCR1.png"));
		spriteRight.add(new GImage("res/player/PCR2.png"));
		
	}
	
	public NewGamePane(MainApplication app) {
		
		this.program = app;
		addImages();
		para = new GParagraph("The new game pane", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		player.setImage(spriteDown.get(0).getImage());
		program.add(player, x, y);
		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(player);
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_S) {sPressed = true;}
		if (e.getKeyCode() == KeyEvent.VK_W) {wPressed = true;}
		if (e.getKeyCode() == KeyEvent.VK_A) {aPressed = true;}
		if (e.getKeyCode() == KeyEvent.VK_D) {dPressed = true;}
		
		if (sPressed) {
			player.setImage(spriteDown.get(stepsTaken).getImage());
			dy=4;
			player.move(dx, dy);
        }
		if (wPressed) {
			player.setImage(spriteUp.get(stepsTaken).getImage());
			dy=-4;
			player.move(dx, dy);
        }
		if (aPressed) {
			player.setImage(spriteLeft.get(stepsTaken).getImage());
			dx=-4;
			player.move(dx, dy);
        }
		if (dPressed) {
			player.setImage(spriteRight.get(stepsTaken).getImage());
			dx=4;
			player.move(dx, dy);
        }
		move();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		/*currently only works once. Can go diagonally in one direction, then in another without letting the first key you
		pressed go, but after trying once again, while still holding the first key, no longer detects that the first key
		pressed is still being pressed*/
		if (e.getKeyCode() == KeyEvent.VK_S) {sPressed = false;}
		if (e.getKeyCode() == KeyEvent.VK_W) {wPressed = false;}
		if (e.getKeyCode() == KeyEvent.VK_A) {aPressed = false;}
		if (e.getKeyCode() == KeyEvent.VK_D) {dPressed = false;}
		
		if (sPressed)   {
			dy = 4;
		}
		else if (wPressed)   {
			dy = -4;
		}
		else   {
			dy=0;
		}
		
		if (aPressed)   {
			dx = -4;
		}
		else if (dPressed)   {
			dx = 4;
		}
		else   {
			dx = 0;
		}
		
		sPressed = false;
		wPressed = false;
		aPressed = false;
		dPressed = false;
	}
}
