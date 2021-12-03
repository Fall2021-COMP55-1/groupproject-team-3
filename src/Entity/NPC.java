package Entity;

import Boilerplate.MainApplication;
import acm.graphics.GImage;

public class NPC extends Entity{
	public GImage sprite = getImage();
	private boolean alive = true;
	MainApplication program;
	
	public NPC(int x, int y, MainApplication program)   {
		super(x, y);
		this.sprite = new GImage("res/NPC/NPC2.png");
	}
	
	public void setDead(boolean alive)   {
		this.alive = alive;
	}
	
	public boolean isDead()   {
		return alive;
	}
	
}
