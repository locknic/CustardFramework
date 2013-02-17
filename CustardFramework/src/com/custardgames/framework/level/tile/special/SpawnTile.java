package com.custardgames.framework.level.tile.special;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.Mob;
import com.custardgames.framework.entities.Player;
import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.level.tile.CollisionTile;
import com.custardgames.framework.resources.ResourceManager;

public class SpawnTile extends CollisionTile
{
	
	public SpawnTile(LevelHandler level)
	{
		super(level);
		willBlock = false;
		ResourceManager.get_instance().addImage("candle-on-top", "res/tiles/candle-on-top.png");
		ResourceManager.get_instance().addImage("candle-off-top", "res/tiles/candle-off-top.png");
	}

	public void setSpawn(Mob player)
	{
		player.setSpawn(this);
		setImage("candle-on-top");
	}
	
	public void turnOff()
	{
		setImage("candle-off-top");
	}
	
	@Override
	public void handleCollision(Entity other, String direction)
	{
		if (other instanceof Player)
		{
			if (level.input.down)
			{
				setSpawn((Player) other);
			}
		}
	}

}
