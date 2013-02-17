package com.custardgames.framework.level.tile.special;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.Player;
import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.level.tile.CollisionTile;
import com.custardgames.framework.resources.ResourceManager;

public class SpawnTileBottom extends CollisionTile
{
	
	public SpawnTileBottom(LevelHandler level)
	{
		super(level);
		willBlock = false;
		ResourceManager.get_instance().addImage("candle-on-bottom", "res/tiles/candle-on-bottom.png");
	}

	
	@Override
	public void handleCollision(Entity other, String direction)
	{
		if (other instanceof Player)
		{
			if (level.input.down)
			{
				setImage("candle-on-bottom");
			}
		}
	}

}