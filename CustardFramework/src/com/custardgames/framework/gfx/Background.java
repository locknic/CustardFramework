package com.custardgames.framework.gfx;

import java.util.ArrayList;
import java.util.List;

import com.custardgames.framework.entities.Entity;
import com.custardgames.framework.entities.MovingSprites;
import com.custardgames.framework.level.LevelHandler;

public class Background extends Entity{

	public List<MovingSprites> bgsBack= new ArrayList<MovingSprites>();
	public List<MovingSprites> bgsFront= new ArrayList<MovingSprites>();
	
	public Background(LevelHandler level) {
		super(level);
		layer = 1;
		setCenter(level.cameraScreen.getCenterX(), level.cameraScreen.getCenterY());
		setSize(level.cameraScreen.width, level.cameraScreen.height);
		prepareLayer(bgsBack, "background-back", "res/tiles/background/background-back.png");
		prepareLayer(bgsFront, "background-front", "res/tiles/background/background-front.png");
		
		
		
	}
	
	public int calculateQuantity(int totalSize, int size) {
		return (int) Math.ceil(totalSize/size)+1;
	}
	
	public void prepareLayer(List<MovingSprites> bgs, String name, String backLocation) {
		level.levelResources.addImage(name, backLocation);
		int tileWidth = level.levelResources.getImage(name).getWidth();
		int tileHeight = level.levelResources.getImage(name).getHeight();
		
		for (int x=0; x<calculateQuantity(width, tileWidth); x++) {
			for (int y=0; y<calculateQuantity(height, tileHeight); y++) {
				MovingSprites bgBack = new MovingSprites(level);
				bgBack.setImage(name);
				bgBack.layer = 1;
				bgBack.setLocation((int)xCo-tileWidth+tileWidth*x, (int)yCo-tileHeight+tileHeight*y);
				bgs.add(bgBack);
			}
		}
	}

	@Override
	public void tick() {
		float oldX = xCo;
		float oldY = yCo;

		setCenter(level.cameraScreen.getCenterX(), level.cameraScreen.getCenterY());
		for (int x=0; x<bgsBack.size(); x++)
		{
			bgsBack.get(x).xCo += xCo-oldX;
			bgsBack.get(x).yCo += yCo-oldY;
		}
		for (int y=0; y<bgsFront.size(); y++)
		{
			bgsFront.get(y).xCo += xCo-oldX;
			bgsFront.get(y).yCo += yCo-oldY;
		}
	}
	
	@Override
	public void render() {
		for (int x=0; x<bgsBack.size(); x++)
		{
			bgsBack.get(x).render();
		}
		for (int y=0; y<bgsFront.size(); y++)
		{
			bgsFront.get(y).render();
		}
	}

	@Override
	public void handleCollision(Entity other) {
		// TODO Auto-generated method stub
		
	}

}
