package com.custardgames.framework.entities;

import com.custardgames.framework.Game;
import com.custardgames.framework.level.LevelHandler;

public class MovingSprites extends Sprite
{
	public float maxSpeed;
	public int xDir;
	public int yDir;
	
	protected boolean noClip = false;
	
	public MovingSprites(LevelHandler level)
	{
		super(level);
	}
	
	public void move()
	{
		if (xDir!=0)
		{
			xCo += xDir * (maxSpeed/Game.ticksPerSecond);
			if (checkCollision())
			{
				xCo -= xDir * (maxSpeed/Game.ticksPerSecond);
			}
		}
		if (yDir!=0)
		{
			yCo += yDir * (maxSpeed/Game.ticksPerSecond);
			if (checkCollision())
			{
				yCo -= yDir * (maxSpeed/Game.ticksPerSecond);
			}
		}
	}
	
	public boolean checkCollision()
	{
		if (noClip == false)
		{
			for (int x=0; x<level.entities.size(); x++)
			{
				if (level.entities.get(x)!=this && level.entities.get(x).shouldTick)
				{
					if (intersects(level.entities.get(x)))
					{
						this.handleCollision(level.entities.get(x));
						level.entities.get(x).handleCollision(this);
						if (level.entities.get(x).willBlock)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void tick()
	{
		move();
	}

	@Override
	public void handleCollision(Entity other) {
		// TODO Auto-generated method stub
		
	}
	
}
