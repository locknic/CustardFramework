package com.custardgames.framework.level.tile;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.level.LevelHandler;

public class FrontWallTile extends Tile {

	public FrontWallTile(LevelHandler level) {
		super(level);
		layer = 4;
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
