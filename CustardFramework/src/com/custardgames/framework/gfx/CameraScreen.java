package com.custardgames.framework.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.Sprite;
import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.resources.ResourceManager;

public class CameraScreen extends Sprite
{        
	private BufferedImage cameraView;
	private Entity target;
    
	public CameraScreen(LevelHandler level, int screenWidth, int screenHeight) 
	{
		super(level);
		setLocation(0, 0);
		setSize(screenWidth, screenHeight);
		cameraView=new BufferedImage(screenWidth, screenHeight, 1);
		shouldRender = true;
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
    
    public void clearScreen()
    {
    	Graphics g = cameraView.getGraphics();
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, width, height);
    }
    
    public void drawImage(BufferedImage image, int x, int y, int width, int height)
    {
    	Graphics g = cameraView.getGraphics();
    	g.drawImage(image, (int)(x-xCo), (int)(y-yCo), width, height, null);
    }
    
    public void drawEntity(Sprite object)
    {
    	drawImage(ResourceManager.get_instance().getImage(object.imageID), object.getImageX(), object.getImageY(), object.imgWidth, object.imgHeight);
    }
    
    public void setStates(List<Entity> entity)
    {
    	for (int x=0; x<entity.size(); x++)
    	{
	    	if (intersects(entity.get(x)))
	    		entity.get(x).shouldRender = true;
	    	else
	    		entity.get(x).shouldRender = false;
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
	public void handleCollision(Entity other, String direction) 
	{
		// TODO Auto-generated method stub
		
	}

}
