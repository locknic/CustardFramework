package com.custardgames.framework.entities;

import java.awt.Rectangle;

import com.custardgames.framework.Game;
import com.custardgames.framework.level.LevelHandler;

public abstract class Entity
{
	public LevelHandler level;
	
	public float xCo;
	public float yCo;
	public int width;
	public int height;
	
	public boolean running = false;
	public boolean shouldRender = false;
	public boolean willBlock = false;
	
	public Entity(LevelHandler level)
	{
		this.level = level;
		running = true;
	}
	
	public int getCenterX()
	{
		return (int) (xCo+width/2);
	}
	
	public int getCenterY()
	{
		return (int) (yCo+height/2);
	}
	
	public float getPerSecond(float number)
	{
		return number/Game.ticksPerSecond;
	}
	
	public float getTotalTicks(float number)
	{
		return number*Game.ticksPerSecond;
	}
	
	public void setLocation(int x, int y)
	{
		this.xCo = x;
		this.yCo = y;
	}
	
	public void setCenter(int x, int y)
	{
		this.xCo = x-width/2;
		this.yCo = y-height/2;
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public boolean intersects(Entity other)
	{
		Rectangle hitbox = new Rectangle((int)xCo, (int)yCo, width, height);
		return hitbox.intersects(other.xCo, other.yCo, other.width, other.height);
	}
	
	public boolean intersects(Rectangle other)
	{
		Rectangle hitbox = new Rectangle((int)xCo, (int)yCo, width, height);
		return hitbox.intersects(other);
	}
	
	public abstract void tick();
	
	public abstract void render();
	
	public abstract void handleCollision(Entity other, String direction);
	
}
