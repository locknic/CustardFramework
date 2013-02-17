package com.custardgames.framework.entities;

import com.custardgames.framework.level.LevelHandler;

public abstract class Sprite extends Entity
{
	public String imageID;
	public int imgWidth;
	public int imgHeight;
	public int imgX;
	public int imgY;
	
	public Sprite(LevelHandler level)
	{
		super(level);
	}
	
	public void setImage(String imageID)
	{
		if (width == 0){
			width = level.levelResources.getImage(imageID).getWidth();
		}
		if (height == 0){
			height = level.levelResources.getImage(imageID).getHeight();
		}
		
		this.imageID = imageID;
		imgWidth = width;
		imgHeight = height;
		imgX = 0;
		imgY = 0;
	}
	
	public void setImage(String imageID, int imgWidth, int imgHeight)
	{
		this.imageID = imageID;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		imgX = (width/2)-(imgWidth/2);
		imgY = (height/2)-(imgHeight/2);
	}
	
	public void setImage(String imageID, int imgX, int imgY, int imgWidth, int imgHeight)
	{
		this.imageID = imageID;
		this.imgX = imgX;
		this.imgY = imgY;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}

	public int getImageX()
	{
		return (int) (xCo+imgX);
	}
	
	public int getImageY()
	{
		return (int) (yCo+imgY);
	}
	
	public void render() 
	{
		level.cameraScreen.drawEntity(this);
	}
	
}
