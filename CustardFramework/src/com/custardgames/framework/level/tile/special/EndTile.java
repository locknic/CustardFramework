package com.custardgames.framework.level.tile.special;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.level.tile.CollisionTile;

public class EndTile extends CollisionTile
{

	public EndTile(LevelHandler level)
	{
		super(level);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleCollision(Entity other, String direction)
	{
		level.stop();
	}
}
