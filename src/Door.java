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

public class Door {
	private GRect doorBound;
	private RoomType roomType;
	private boolean locked;
	
	Door(int x, int y, int width, int height, boolean locked){
		doorBound = new GRect(x,y,width,height);
		doorBound.setVisible(true);
		locked = locked;
	}
	
	public boolean isLocked() {
		return locked;
	}
	public GRect getRect() {
		return doorBound;
	}
}
