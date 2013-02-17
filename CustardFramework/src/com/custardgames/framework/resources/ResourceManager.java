package com.custardgames.framework.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceManager 
{
	private static ResourceManager _instance = new ResourceManager();
	private Map<String, BufferedImage> images;
	private Map<String, ResourceAnimationFrame> animationFrames;
	
	public ResourceManager()
	{
		images = new HashMap<String, BufferedImage>();
		animationFrames = new HashMap<String, ResourceAnimationFrame>();
	}
	
	public static ResourceManager get_instance()
	{
		return _instance;
	}
	
	public void addImage(String name, BufferedImage tile)
	{
		if (images.containsKey(name))
			images.remove(name);
		images.put(name, tile);
	}
	
	public void addImage(String name, String location)
	{
		try
		{
			BufferedImage currentImage=ImageIO.read(this.getClass().getClassLoader().getResource(location));
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
	
	public void addAnimationFrame(String animationID, String location, ResourceAnimationFrame animationFrame)
	{
		addImage(animationID, location);
		addAnimationFrame(animationID, animationFrame);
	}

	public void addAnimationFrame(String animationID, ResourceAnimationFrame animationFrame)
	{
		animationFrames.put(animationID, animationFrame);
	}
	
	public ResourceAnimationFrame getAnimationFrame(String animationID)
	{
		return animationFrames.get(animationID);
	}
}
