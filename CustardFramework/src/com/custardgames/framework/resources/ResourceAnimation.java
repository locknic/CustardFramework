package com.custardgames.framework.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourceAnimation
{
	public List<Integer> animationDelta;
	public List<String> animation;

	public String currentAnimationFrame;
	public String endAnimationFrame;
	public int tickCount;
	public int lastTick;
	public int duration;
	public boolean looping;
	public boolean running;

	public ResourceAnimation()
	{
		animationDelta = new ArrayList<Integer>();
		animation = new ArrayList<String>();
		looping = false;
		running = false;
		tickCount = 0;
		lastTick = 0;
		duration = 0;
	}

	public void addAimationFrame(int delta, String id, int xOffset, int yOffset, int width, int height)
	{
		ResourceManager.get_instance().addAnimationFrame(id, new ResourceAnimationFrame(id, xOffset, yOffset, width, height));
		addAnimationFrame(delta, id);
	}
	
	public void addAnimationFrame(int delta, String id)
	{
		if(endAnimationFrame==null)
			endAnimationFrame=id;
		animationDelta.add(delta);
		animation.add(id);
	}
	
	public void setEndAnimationFrame(String id)
	{
		endAnimationFrame = id;
	}
	
	public void setLooping(boolean looping)
	{
		this.looping = looping;
	}
	
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public void run()
	{
		if (running == false)
		{
			running = true;
			if (looping)
			{
				tickCount = 0;
				lastTick = 0;
			}
		}
	}
	
	public void stop()
	{
		running = false;
		tickCount = 0;
		lastTick = 0;
	}
	
	public String tick()
	{
		if (animation.size() > 1)
		{
			if (running)
			{
				tickCount++;
				if (tickCount <= lastTick + duration)
				{
					for (int x = 0; x < animationDelta.size(); x++)
					{
						if (tickCount < lastTick + animationDelta.get(x))
						{
							currentAnimationFrame = animation.get(x);
							return currentAnimationFrame;
						}
					}
				}
				else
				{
					running = false;
				}
			}
		}
		return endAnimationFrame;
	}
}
