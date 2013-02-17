package com.custardgames.framework.level;

import java.util.ArrayList;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.gfx.CameraScreen;

public class LevelLayer extends ArrayList <Entity>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void tick(CameraScreen graphics)
	{
		for (int x=0; x<this.size(); x++)
    	{
			this.get(x).tick();
    	}
	}
	
	public void render()
	{
		for (int x=0; x<this.size(); x++)
    	{
			this.get(x).render();
    	}
	}
}
