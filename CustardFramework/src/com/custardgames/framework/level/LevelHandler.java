package com.custardgames.framework.level;

import java.awt.image.BufferedImage;

import com.custardgames.framework.Game;
import com.custardgames.framework.entities.Player;
import com.custardgames.framework.gfx.CameraScreen;
import com.custardgames.framework.input.InputHandler;
import com.custardgames.framework.resources.ResourceManager;
import com.custardgames.framework.sound.SoundHandler;

public class LevelHandler
{
	public String path;
	public boolean running;
	public int tickCount;

	public LevelLayer backLayer;
	public LevelLayer collisionLayer;
	public LevelLayer frontLayer;

	public CameraScreen graphics;
	public SoundHandler soundHandler;
	public InputHandler input;
	
	public Player player;
	
	public LevelHandler(String path, InputHandler input)
	{
		this.path = path;
		this.input = input;
		init();
	}

	public void init()
	{
		graphics = new CameraScreen(this, Game.nativeWidth, Game.nativeHeight);
		soundHandler = new SoundHandler();
		backLayer = new LevelLayer();
		collisionLayer = new LevelLayer();
		frontLayer = new LevelLayer();
		player = new Player(this);
		LevelLoader levelTiles = new LevelLoader(this);

		backLayer.addAll(levelTiles.getFloor());
		collisionLayer.addAll(levelTiles.getWall());
		frontLayer.addAll(levelTiles.getRoof());

		collisionLayer.add(player);
		graphics.setTarget(player);
		
		ResourceManager.get_instance().addImage("screen-start", path+"/screen-start.png");
		ResourceManager.get_instance().addImage("screen-end", path+"/screen-end.png");
		
		System.out.println("screen-start");
		graphics.setImage("screen-start");
		running = true;
		tick();
		render();
		running = false;
	}
	
	public void start()
	{
		running = true;
	}

	public void stop()
	{
		soundHandler.unloadAll();
		init();
		graphics.setImage("screen-end");
		running = true;
		tick();
		render();
		running = false;
	}

	public void tick()
	{
		tickCount++;
		if (running)
		{
			backLayer.tick(graphics);
			collisionLayer.tick(graphics);
			frontLayer.tick(graphics);
			graphics.tick();
			graphics.setStates(backLayer);
			graphics.setStates(collisionLayer);
			graphics.setStates(frontLayer);
		}
		else
		{
			if (input.down)
			{
				if (graphics.imageID.equals("screen-start"))
				{
					graphics.setImage("");
					start();
				}
				else
				{
					graphics.setImage("screen-start");
				}
			}
		}
	}

	public BufferedImage render()
	{
		if (running)
		{
			graphics.clearScreen();
			backLayer.render();
			collisionLayer.render();
			frontLayer.render();
			graphics.render();
		}
		return graphics.getImage();
	}

}
