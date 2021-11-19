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

public class Door {
	private GRect doorBound;
	private RoomType roomType;
	
	Door(int x, int y, int width, int height){
		doorBound = new GRect(x,y,width,height);
		doorBound.setVisible(false);
	}
	
	GRect getRect() {
		return doorBound;
	}
}
