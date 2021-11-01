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
	private ArrayList<GImage> spriteUp;
	private ArrayList<GImage> spriteDown;
	private ArrayList<GImage> spriteLeft;
	private ArrayList<GImage> spriteRight;
	private int x=100, y=100, dx, dy;
	
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
		program.add(spriteDown.get(0), x,y);
		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(spriteDown.get(0));
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S) {
			//spriteDown.get(0).move(0, 2);
			dy=2;
        }
		if (e.getKeyCode() == KeyEvent.VK_W) {
			//spriteDown.get(0).move(0, -2);
			dy=-2;
        }
		if (e.getKeyCode() == KeyEvent.VK_A) {
			//spriteDown.get(0).move(-2, 0);
			dx=-2;
        }
		if (e.getKeyCode() == KeyEvent.VK_D) {
			//spriteDown.get(0).move(2, 0);
			dx=2;
        }
		move();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
