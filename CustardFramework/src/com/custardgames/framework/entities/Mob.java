package com.custardgames.framework.entities;

import com.custardgames.framework.level.LevelHandler;

public abstract class Mob extends MovingSprites
{
	
	
	public Mob(LevelHandler level) 
	{
		super(level);
		willBlock = true;
		layer = 3;
	}

}
