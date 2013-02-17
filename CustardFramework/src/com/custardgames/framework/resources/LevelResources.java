package com.custardgames.framework.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class LevelResources 
{
	private Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public void addImage(String name, BufferedImage tile)
	{
		images.put(name, tile);
	}
	
	public void addImage(String name, String location)
	{
		try {
			BufferedImage currentImage=ImageIO.read(new File(location));
			addImage(name, currentImage);
		} catch (IOException e) {
			System.out.println("Error! Trouble loading file from location: "+location);
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(String imageID)
	{
		return images.get(imageID);
	}
	
}
