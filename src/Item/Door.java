package Item;
import acm.graphics.GRect;

public class Door {
	private GRect doorBound;
	private RoomType roomType;
	private boolean locked;
	
	public Door(int x, int y, int width, int height, boolean locked){
		doorBound = new GRect(x,y,width,height);
		doorBound.setVisible(false);
		this.locked = locked;
	}
	
	public void setRoomType(RoomType roomType) {this.roomType=roomType;}
	
	public void unlock() {locked = false;}
	
	public RoomType getRoomType() {return roomType;}
	
	public boolean isLocked() {return locked;}
	
	public GRect getRect() {return doorBound;}
	
}
