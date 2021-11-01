import java.awt.Graphics;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class NewGamePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GParagraph para;
	private GImage spritesheet;
	
	private static final int width=32, height=32;
	
	
	
	public NewGamePane(MainApplication app) {
		
		this.program = app;
		spritesheet = new GImage("res/texture/sheet.png", 0, 0);
		para = new GParagraph("The new game pane", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		program.add(spritesheet);
		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(spritesheet);
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
	}
}
