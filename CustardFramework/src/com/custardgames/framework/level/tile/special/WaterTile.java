package com.custardgames.framework.level.tile.special;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.Mob;
import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.level.tile.FrontWallTile;

public class WaterTile extends FrontWallTile
{

	public WaterTile(LevelHandler level)
	{
		super(level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCollision(Entity other, String direction)
	{
		if (other instanceof Mob)
		{
			((Mob) other).inWater = true;
		}
	}
}
