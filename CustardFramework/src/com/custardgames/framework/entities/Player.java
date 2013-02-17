package com.custardgames.framework.entities;

import com.custardgames.framework.level.LevelHandler;

public class Player extends Mob{

	public Player(LevelHandler level) {
		super(level);
		setLocation(90, 90);
		setSize(13, 22);
		setImage("player1", -10, -10, 32, 32);
		level.levelResources.addImage("player1", "res/Astronaut.png");
		layer = 3;
		maxSpeed = (float) 100;
	}

	@Override
	public void tick() 
	{
		super.tick();

		if (level.input.up)
		{
			yDir = -1;
		}
		else if (level.input.down)
		{
			yDir = 1;
		}
		else
		{
			yDir = 0;
		}
		
		if (level.input.left)
		{
			xDir = -1;
		}
		else if (level.input.right)
		{
			xDir = 1;
		}
		else
		{
			xDir = 0;
		}

	}
	
	@Override
	public void handleCollision(Entity other) {
		// TODO Auto-generated method stub
		
	}

}
