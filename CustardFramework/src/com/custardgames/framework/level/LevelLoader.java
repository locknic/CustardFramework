package com.custardgames.framework.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.custardgames.framework.level.tile.BackWallTile;
import com.custardgames.framework.level.tile.CollisionTile;
import com.custardgames.framework.level.tile.FrontWallTile;
import com.custardgames.framework.level.tile.Tile;

public class LevelLoader 
{
	private String tilesetLoc = "res/tileset-basic";
	private String mapLoc = "res/map-basic";
	private int tileSize = 16;
	private LevelHandler level;
	
	private List<BackWallTile> floorTiles = new ArrayList<BackWallTile>();
	private List<CollisionTile> wallTiles = new ArrayList<CollisionTile>();
	private List<Tile> roofTiles = new ArrayList<Tile>();
	
	public LevelLoader(LevelHandler level)
	{
		this.level = level;
		receiveTileSet();
		receiveMap();
	}
	
	public List<Tile> getTiles()
	{
		List<Tile> allTiles = new ArrayList<Tile>();
		allTiles.addAll(floorTiles);
		allTiles.addAll(wallTiles);
		allTiles.addAll(roofTiles);
		return allTiles;
	}
	
	public void receiveTileSet()
    {
        Scanner readFile = null;
		try 
		{
			readFile = new Scanner(new File(tilesetLoc)).useDelimiter(", ");
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        readFile.nextLine();
        readFile.nextLine();
        while (readFile.hasNext())
        {
            String tileNumber = readFile.next();
            String tileLocation = readFile.next();
            System.out.println("Loading tile '"+tileLocation+"'");
            level.levelResources.addImage("tile"+tileNumber, "res/"+tileLocation);
            readFile.nextLine();
        }
        readFile.close();
    }
     
    public void receiveMap()
    {
        Scanner readFile = null;
		try 
		{
			readFile = new Scanner(new File(mapLoc)).useDelimiter(" ");
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        if (readFile.hasNext())
        {
            int mapSizeX=readFile.nextInt();
            int mapSizeY=readFile.nextInt();
            readFile.nextLine();
            readFile.nextLine();
            readFile.nextLine();
            for(int y=0; y<mapSizeY; y++)
            {
                for(int x=0; x<mapSizeX; x++)
                {
                    if (readFile.hasNextInt())
                    {
                    	int tileNumber=readFile.nextInt();
                    	if (tileNumber!=0)
                    	{
                    		BackWallTile fTile= new BackWallTile(level);
                    		fTile.setSize(tileSize, tileSize);
                    		fTile.setLocation(x*tileSize, y*tileSize);
                    		fTile.setImage("tile"+tileNumber);
                            floorTiles.add(fTile);
                    	}
                    }
                }
            }
            readFile.nextLine();
            for(int y=0; y<mapSizeY; y++)
            {
                for(int x=0; x<mapSizeX; x++)
                {
                    if (readFile.hasNextInt())
                    {
                    	int tileNumber=readFile.nextInt();
                    	if (tileNumber!=0)
                    	{
                    		CollisionTile wTile= new CollisionTile(level);
                    		wTile.setSize(tileSize, tileSize);
                    		wTile.setLocation(x*tileSize, y*tileSize);
                    		wTile.setImage("tile"+tileNumber);
                            wallTiles.add(wTile);
                    	}
                    }
                }
            }
            readFile.nextLine();
            for(int y=0; y<mapSizeY; y++)
            {
                for(int x=0; x<mapSizeX; x++)
                {
                    if (readFile.hasNextInt())
                    {
                    	int tileNumber=readFile.nextInt();
                    	if (tileNumber!=0)
                    	{
                    		FrontWallTile rTile= new FrontWallTile(level);
                    		rTile.setSize(tileSize, tileSize);
                    		rTile.setLocation(x*tileSize, y*tileSize);
                    		rTile.setImage("temp"+tileNumber);
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
