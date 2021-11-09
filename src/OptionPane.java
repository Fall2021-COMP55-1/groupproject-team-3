import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import Game.TitleScreenHandler;
import acm.graphics.GImage;
import acm.graphics.GObject;


public class OptionPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GImage img;
	private GParagraph para;	
	

	public OptionPane(MainApplication app) {
		this.program = app;
		img = new GImage("res/texture/Options.png", 0, 0);
		img.setSize(800, 640);
		para = new GParagraph(" ", 150, 300);
		para.setFont("Arial-24");
		
	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(para);
		
	}

	@Override
	public void hideContents() {
		program.remove(img);
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText(" ");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == img) {
			program.switchToMenu();
		}
	}

}

