package com.custardgames.framework.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.custardgames.framework.Game;
import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.Player;
import com.custardgames.framework.gfx.Background;
import com.custardgames.framework.gfx.Camera;
import com.custardgames.framework.input.InputHandler;
import com.custardgames.framework.resources.LevelResources;

public class LevelHandler
{
	public String path;
	public boolean running;
    public List<Entity> entities= new ArrayList<Entity>();
    public LevelResources levelResources = new LevelResources();
	public Camera cameraScreen = new Camera(this, Game.nativeWidth, Game.nativeHeight);
	public InputHandler input;
    
    public LevelHandler(String path, InputHandler input)
    {    	
    	this.path = path;
    	this.input = input;
    	LevelLoader levelTiles = new LevelLoader(this);
    	Player player = new Player(this);
    	entities.add(new Background(this));
        entities.addAll(levelTiles.getTiles());
        entities.add(cameraScreen);
        entities.add(player);
        cameraScreen.setTarget(player);
       
    }
    
	public void start() 
	{
		running = true;
	}

	public void stop() 
	{
		running = false;
	}
	
	public void tick() 
	{
    	if (running)
    	{
    		for (int l=1; l<=5; l++)
			{
	    		cameraScreen.setStates(entities);
	    		for (int x=0; x<entities.size(); x++)
		    	{
	    			if (entities.get(x).layer == l)
		    		{
		    			if (entities.get(x).shouldTick)
		    			{
		    				entities.get(x).tick();
		    			}
		    		}
		    	}
			}
    	}
	}

	public BufferedImage render() 
	{
		if (running)
		{
			for (int l=1; l<=5; l++)
			{
		    	for (int x=0; x<entities.size(); x++)
		    	{
		    		if (entities.get(x).layer == l)
		    		{
			    		if (entities.get(x).shouldRender)
			    		{
			    			entities.get(x).render();
			    		}
		    		}
		    	}
			}
		}
		return cameraScreen.getImage();
	}
    
    
    
}
