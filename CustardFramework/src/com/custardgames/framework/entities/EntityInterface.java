package com.custardgames.framework.entities;

public interface EntityInterface 
{
	public void tick();
	public void render();
	public void handleCollision(Entity other);
}
