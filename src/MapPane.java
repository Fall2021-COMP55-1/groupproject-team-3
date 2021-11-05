import acm.graphics.GImage;

public class MapPane extends GraphicsPane{
	
	private MainApplication program;
	private GImage tiles[][];
	private boolean Walkable[][];
	private Tile tile = new Tile();	
	private int tileR = 20;
	private int tileC = 25;
	
	public MapPane(MainApplication app) {
		this.program = app;
		addImages();
		
	}
	
	public void addImages(){
		tiles = new GImage[tileR][tileC];
		for (int i=0; i<tileR; i++) {
			for (int j=0; j<tileC; j++) {
				tiles[i][j]=new GImage(tile.map[i][j]);
			}
		}
	}
	
	@Override
	public void showContents() {
		int xs=0, ys=0;
		for(int i=0; i<tileR; i++) {
			for(int j=0; j<tileC; j++) {
				program.add(tiles[i][j],xs,ys);
				xs+=32;
			}
			xs=0;
			ys+=32;
			
		}
		
	}
	
	

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}
}
