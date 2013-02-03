package com.custardgames.framework.level.tile;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.level.LevelHandler;

public class CollisionTile extends Tile{

	public CollisionTile(LevelHandler level) {
		super(level);
		willBlock = true;
		layer = 3;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCollision(Entity other) {
		// TODO Auto-generated method stub
		
	}

}
