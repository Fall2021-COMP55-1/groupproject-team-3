import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Tile {
	public String map[][];
	private int mapW = 25;
	private int mapH = 20;
	private int tilesetW = 3;
	private int tilesetH = 13;
	private BufferedImage tileset;
	private BufferedImage tilesets[];
	
	Tile(){
		/*File file = new File("res/ls.png");
		tilesets = new BufferedImage[tilesetW*tilesetH];
		try {
			FileInputStream istream = new FileInputStream(file);
			tileset = ImageIO.read(istream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int counter = 0;
		for (int i=0; i<tilesetH; i++) {
			for (int j=0; j<tilesetW; j++) {
				tilesets[counter]=tileset.getSubimage(j*32, i*32, 32, 32);
				counter++;
			}
		}
		for (int i=0; i<tilesetW*tilesetH; i++) {
			File outputfile = new File("res/tiles/tile"+(i+1)+".png");
			try {
				ImageIO.write(tilesets[i], "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		File mapFile = new File("res/map.txt");
		map = new String[mapH][mapW];
		Scanner scanner = null;
		try {
			scanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(scanner.hasNextLine()) {
			String[] lines = new String[mapH];
			for (int i=0; i<mapH; i++) {
				lines[i]= scanner.nextLine();
				map[i]=lines[i].split(" ");
	        }
	    }
		
		
		for(int i=0; i<mapH; i++) {
			for(int j=0; j<mapW; j++) {
				map[i][j]="res/tiles/tile"+map[i][j]+".png";
			}
		}
	}
	
	
	
}		

