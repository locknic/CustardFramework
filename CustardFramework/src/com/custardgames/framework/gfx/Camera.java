package com.custardgames.framework.gfx;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.MovingSprites;
import com.custardgames.framework.entities.Sprite;
import com.custardgames.framework.level.LevelHandler;

public class Camera extends MovingSprites
{        
	private BufferedImage cameraView;
	private Entity target;
    
	public Camera(LevelHandler level, int screenWidth, int screenHeight) 
	{
		super(level);
		setLocation(0, 0);
		setSize(screenWidth, screenHeight);
		cameraView=new BufferedImage(screenWidth, screenHeight, 1);
		shouldTick = true;
		shouldRender = true;
		layer = 5;
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
		tick();
	}
	
    public BufferedImage getImage()
    {
    	return cameraView;
    }
    
    public void drawImage(BufferedImage image, int x, int y, int width, int height)
    {
    	Graphics g = cameraView.getGraphics();
    	g.drawImage(image, (int)(x-xCo), (int)(y-yCo), width, height, null);
    }
    
    public void drawEntity(Sprite object)
    {
    	drawImage(level.levelResources.getImage(object.imageID), object.getImageX(), object.getImageY(), object.imgWidth, object.imgHeight);
    }
    
    public void setStates(List<Entity> entity)
    {
    	for (int x=0; x<entity.size(); x++)
    	{
    		if (entity.get(x).intersects(getTickBox()))
    		{
		    	entity.get(x).shouldTick = true;
		    	if (intersects(entity.get(x)))
		    	{
		    		entity.get(x).shouldRender = true;
		    	}
		    }
    	}
    }
    
    public Rectangle getTickBox()
    {
    	Rectangle tickBox = new Rectangle();
    	tickBox.setSize((int)(width*1.5), (int)(height*1.5));
    	tickBox.setLocation((int)(xCo+(width/2)-(tickBox.width/2)), (int)(yCo+(height/2)-(tickBox.height/2)));
    	return tickBox;
    }
    
	@Override
	public void tick() 
	{
		setCenter(target.getCenterX(), target.getCenterY());
	}

	@Override
	public void handleCollision(Entity other) 
	{
		// TODO Auto-generated method stub
		
	}

}
