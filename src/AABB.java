public class AABB {
	private int height;
	private int width;
	private double minX, minY, maxX, maxY;
	
	//Not really sure about this variable
	
	public AABB(double x, double y, int width, int height) {
		minX = x;
		minY = y;
		this.height = height;
		this.width = width;
		maxX = x + width;
		maxY = y + height;
		
	}
	
	
	public boolean intersectAABB( AABB box) {
			if( box.getX() > maxX || minX > box.getMaxX()) return false;
			if( box.getY()> maxY || minY > box.getMaxY() ) return false;
			return true;
			}
	
	
	
	public boolean intersectCircleAABB(double x, double y, float r) {
		float d = 0.0f;
		if( x < minX ) d += (x - minX)*(x - minX);
		if( x > maxX ) d += (x - maxX)*(x - maxX);
		if( y < minY ) d += (y - minY)*(y - minY);
		if( y > maxY ) d += (y - maxY)*(y - maxY);
		return d < r*r;
	}
	public double getY() {
		return minY;
		
	}
	public double getX() {
		return minX;
		
	}
	public double getMaxY() {
		return maxY;
		
	}
	public double getMaxX() {
		return maxY;
		
	}
	
}
