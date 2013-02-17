package com.custardgames.framework.level.tile;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.level.LevelHandler;

public class CollisionTile extends Tile
{

	public CollisionTile(LevelHandler level)
	{
		super(level);
		willBlock = true;
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCollision(Entity other, String direction)
	{
		// TODO Auto-generated method stub

	}

}
