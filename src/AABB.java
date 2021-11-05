
public class AABB {
	public double minX, minY, maxX, maxY;
	public int width, height;
	
	AABB(double minX, double minY, int width, int height){
		this.minX = minX; 
		this.minY = minY;
		this.width = width;
		this.height = height;
		maxX = minX+width;
		maxY = minY+height;
		
	}
	
	public boolean Collision(AABB box) {
		if(box.minX > maxX || minX > box.maxX) {
			return false;
		}
		if(box.minY > maxY || minY > box.maxY) {
			return false;
		}
		return true;
	}
	
	public void update(double X, double Y) {
		minX+=X;
		minY+=Y;
		maxX+=X;
		maxY+=Y;
	}
	
}
