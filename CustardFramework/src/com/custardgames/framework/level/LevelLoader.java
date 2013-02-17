package com.custardgames.framework.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.custardgames.framework.level.tile.BackWallTile;
import com.custardgames.framework.level.tile.CollisionTile;
import com.custardgames.framework.level.tile.FrontWallTile;
import com.custardgames.framework.level.tile.special.DeathTile;
import com.custardgames.framework.level.tile.special.EndTile;
import com.custardgames.framework.level.tile.special.SpawnTile;
import com.custardgames.framework.level.tile.special.SpawnTileBottom;
import com.custardgames.framework.level.tile.special.WaterTile;
import com.custardgames.framework.resources.ResourceManager;

public class LevelLoader
{
	private String tilesetLoc = "res/tileset-basic";
	private String mapLoc = "res/map-basic";
	private int tileSize = 32;
	private LevelHandler level;

	private List<BackWallTile> floorTiles = new ArrayList<BackWallTile>();
	private List<CollisionTile> wallTiles = new ArrayList<CollisionTile>();
	private List<FrontWallTile> roofTiles = new ArrayList<FrontWallTile>();

	private List<String> tileType = new ArrayList<String>();

	public LevelLoader(LevelHandler level)
	{
		this.level = level;
		receiveTileSet();
		receiveMap();
	}

	public List<BackWallTile> getFloor()
	{
		return floorTiles;
	}

	public List<CollisionTile> getWall()
	{
		return wallTiles;
	}

	public List<FrontWallTile> getRoof()
	{
		return roofTiles;
	}

	public void receiveTileSet()
	{
		Scanner readFile = null;
		readFile = new Scanner(this.getClass().getClassLoader().getResourceAsStream(tilesetLoc)).useDelimiter(", ");
		readFile.nextLine();
		readFile.nextLine();
		while (readFile.hasNext())
		{
			String tileNumber = readFile.next();
			String tileLocation = readFile.next();
			readFile.next();
			String type = readFile.next();
			tileType.add(type);
			System.out.println(tileType.size()+", "+type);
			ResourceManager.get_instance().addImage("tile" + tileNumber, "res/" + tileLocation);
			readFile.nextLine();
		}
		readFile.close();
	}

	public void receiveMap()
	{
		Scanner readFile = null;
		readFile = new Scanner(this.getClass().getClassLoader().getResourceAsStream(mapLoc)).useDelimiter(" ");

		if (readFile.hasNext())
		{
			int mapSizeX = readFile.nextInt();
			int mapSizeY = readFile.nextInt();
			readFile.nextLine();
			readFile.nextLine();
			readFile.nextLine();
			for (int y = 0; y < mapSizeY; y++)
			{
				for (int x = 0; x < mapSizeX; x++)
				{
					if (readFile.hasNextInt())
					{
						int tileNumber = readFile.nextInt();
						if (tileNumber != 0)
						{
							BackWallTile fTile = new BackWallTile(level);
							fTile.setSize(tileSize, tileSize);
							fTile.setLocation(x * tileSize, y * tileSize);
							fTile.setImage("tile" + tileNumber);
							floorTiles.add(fTile);
						}
					}
				}
			}
			readFile.nextLine();
			for (int y = 0; y < mapSizeY; y++)
			{
				for (int x = 0; x < mapSizeX; x++)
				{
					if (readFile.hasNextInt())
					{
						int tileNumber = readFile.nextInt();
						if (tileNumber != 0)
						{
							CollisionTile wTile = new CollisionTile(level);
							System.out.println(tileNumber-1);
							if (tileType.get(tileNumber - 1).equalsIgnoreCase("Player Spawn"))
							{
								wTile = new SpawnTile(level);
							}
							else if (tileType.get(tileNumber - 1).equalsIgnoreCase("Player Checkpoint"))
							{
								wTile = new SpawnTile(level);
							}
							else if (tileType.get(tileNumber - 1).equalsIgnoreCase("Player Checkpoint Bottom"))
							{
								wTile = new SpawnTileBottom(level);
							}
							else if (tileType.get(tileNumber - 1).equalsIgnoreCase("Lava"))
							{
								wTile = new DeathTile(level);
							}
							else if (tileType.get(tileNumber - 1).equalsIgnoreCase("End"))
							{
								wTile = new EndTile(level);
							}

							wTile.setSize(tileSize, tileSize);
							wTile.setLocation(x * tileSize, y * tileSize);
							wTile.setImage("tile" + tileNumber);

							if (tileType.get(tileNumber - 1).equalsIgnoreCase("Player Spawn"))
							{
								level.player.setCenter(wTile.getCenterX(), wTile.getCenterY());
								level.player.spawnPoint = (SpawnTile) wTile;
							}
							else if (tileType.get(tileNumber - 1).equalsIgnoreCase("Lava"))
							{
								wTile.setSize(30, 30);
								wTile.setLocation((int) wTile.xCo + 1, (int) wTile.yCo + 1);
							}
							wallTiles.add(wTile);
						}
					}
				}
			}
			readFile.nextLine();
			for (int y = 0; y < mapSizeY; y++)
			{
				for (int x = 0; x < mapSizeX; x++)
				{
					if (readFile.hasNextInt())
					{
						int tileNumber = readFile.nextInt();
						if (tileNumber != 0)
						{
							FrontWallTile rTile = new FrontWallTile(level);
							if (tileType.get(tileNumber - 1).equalsIgnoreCase("Water"))
							{
								rTile = new WaterTile(level);
							}
							rTile.setSize(tileSize, tileSize);
							rTile.setLocation(x * tileSize, y * tileSize);
							rTile.setImage("tile" + tileNumber);
							roofTiles.add(rTile);
						}
					}
				}
			}
			readFile.nextLine();
		}
		readFile.close();
	}

}
