package net.lonestranger.tilemap;

public class TileMap {

	int width = 0;
	int height = 0;
	
	Tile tile[][];
	
	public TileMap(int w, int h) {
		
		width = w;
		height = h;
		
		init();
		
		
		
	}
	
	public void init()
	{
		init(width, height);
	}
	
	public void init(int w, int h)
	{
		width = w;
		height = h;
		
		tile = new Tile[width][height];
		
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tile[x][y] = new Tile();
		
	}
	
	
	public void output()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
				System.out.print(tile[x][y].val);
			System.out.println("");
		}
	}
}
