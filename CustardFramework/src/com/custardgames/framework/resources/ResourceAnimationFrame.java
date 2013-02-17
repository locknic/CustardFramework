package com.custardgames.framework.resources;

public class ResourceAnimationFrame
{
	private String imageId;
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	

	public ResourceAnimationFrame(String id, int xOffset, int yOffset, int width, int height)
	{
		this.imageId = id;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
	}
	
	public final String getImageId()
	{
		return imageId;
	}

	public final void setImageId(String imageId)
	{
		this.imageId = imageId;
	}
	
	public int getxOffset()
	{
		return xOffset;
	}
	
	public int getyOffset()
	{
		return yOffset;
	}
	
	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
