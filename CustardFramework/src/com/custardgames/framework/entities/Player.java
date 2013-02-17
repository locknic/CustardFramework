package com.custardgames.framework.entities;

import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.resources.ResourceAnimation;
import com.custardgames.framework.resources.ResourceAnimationFrame;
import com.custardgames.framework.resources.ResourceManager;

public class Player extends Mob
{

	public int deaths;
	
	public Player(LevelHandler level)
	{
		super(level);
		setLocation(200, 200);
		setSize(18, 42);
		setImage("player", -8, -22, 64, 64);
		
		level.soundHandler.loadSound("jump", "res/sounds/jump.wav");
		level.soundHandler.loadSound("footsteps", "res/sounds/footstep.wav");
		level.soundHandler.loadSound("pain", "res/sounds/pain.wav");
		
		ResourceManager.get_instance().addAnimationFrame("player", "res/player/player.png", new ResourceAnimationFrame("player", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-walk1", "res/player/player-walk1.png", new ResourceAnimationFrame("player-walk1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-walk2", "res/player/player-walk2.png", new ResourceAnimationFrame("player-walk2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-jump1", "res/player/player-jump1.png", new ResourceAnimationFrame("player-jump1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-jump2", "res/player/player-jump2.png", new ResourceAnimationFrame("player-jump2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-jump3", "res/player/player-jump3.png", new ResourceAnimationFrame("player-jump3", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-fall1", "res/player/player-fall1.png", new ResourceAnimationFrame("player-fall1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-fall2", "res/player/player-fall2.png", new ResourceAnimationFrame("player-fall2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-fall3", "res/player/player-fall3.png", new ResourceAnimationFrame("player-fall3", -8, -22, 64, 64));

		ResourceManager.get_instance().addAnimationFrame("player-left", "res/player/player-left.png", new ResourceAnimationFrame("player-left", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-walk1", "res/player/player-left-walk1.png", new ResourceAnimationFrame("player-left-walk1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-walk2", "res/player/player-left-walk2.png", new ResourceAnimationFrame("player-left-walk2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-jump1", "res/player/player-left-jump1.png", new ResourceAnimationFrame("player-left-jump1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-jump2", "res/player/player-left-jump2.png", new ResourceAnimationFrame("player-left-jump2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-jump3", "res/player/player-left-jump3.png", new ResourceAnimationFrame("player-left-jump3", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-fall1", "res/player/player-left-fall1.png", new ResourceAnimationFrame("player-left-fall1", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-fall2", "res/player/player-left-fall2.png", new ResourceAnimationFrame("player-left-fall2", -8, -22, 64, 64));
		ResourceManager.get_instance().addAnimationFrame("player-left-fall3", "res/player/player-left-fall3.png", new ResourceAnimationFrame("player-left-fall3", -8, -22, 64, 64));

		// Right animations
		ResourceAnimation playerWalk = new ResourceAnimation();
		playerWalk.setEndAnimationFrame("player");
		playerWalk.addAnimationFrame(10, "player-walk1");
		playerWalk.addAnimationFrame(20, "player");
		playerWalk.addAnimationFrame(30, "player-walk2");
		playerWalk.addAnimationFrame(40, "player");
		playerWalk.setDuration(40);
		playerWalk.setLooping(true);
		animations.put("player-walk", playerWalk);

		ResourceAnimation playerJump = new ResourceAnimation();
		playerJump.setEndAnimationFrame("player-jump3");
		playerJump.addAnimationFrame(1, "player-jump1");
		playerJump.addAnimationFrame(2, "player-jump2");
		playerJump.addAnimationFrame(10, "player-jump3");
		playerJump.setDuration(10);
		animations.put("player-jump", playerJump);

		ResourceAnimation playerStopJump = new ResourceAnimation();
		playerStopJump.setEndAnimationFrame("player");
		playerStopJump.addAnimationFrame(7, "player-jump3");
		playerStopJump.addAnimationFrame(8, "player-jump2");
		playerStopJump.addAnimationFrame(9, "player-jump1");
		playerStopJump.addAnimationFrame(10, "player");
		playerStopJump.setDuration(10);
		animations.put("player-stopJump", playerStopJump);

		ResourceAnimation playerFall = new ResourceAnimation();
		playerFall.setEndAnimationFrame("player-fall3");
		playerFall.addAnimationFrame(4, "player-fall1");
		playerFall.addAnimationFrame(8, "player-fall2");
		playerFall.setDuration(8);
		animations.put("player-fall", playerFall);

		// Left animations
		ResourceAnimation playerLeftWalk = new ResourceAnimation();
		playerLeftWalk.setEndAnimationFrame("player-left");
		playerLeftWalk.addAnimationFrame(10, "player-left-walk1");
		playerLeftWalk.addAnimationFrame(20, "player-left");
		playerLeftWalk.addAnimationFrame(30, "player-left-walk2");
		playerLeftWalk.addAnimationFrame(40, "player-left");
		playerLeftWalk.setDuration(40);
		playerLeftWalk.setLooping(true);
		animations.put("player-left-walk", playerLeftWalk);

		ResourceAnimation playerLeftJump = new ResourceAnimation();
		playerLeftJump.setEndAnimationFrame("player-left-jump3");
		playerLeftJump.addAnimationFrame(1, "player-left-jump1");
		playerLeftJump.addAnimationFrame(2, "player-left-jump2");
		playerJump.addAnimationFrame(10, "player-left-jump3");
		playerLeftJump.setDuration(10);
		animations.put("player-left-jump", playerLeftJump);

		ResourceAnimation playerLeftStopJump = new ResourceAnimation();
		playerLeftStopJump.setEndAnimationFrame("player-left");
		playerLeftStopJump.addAnimationFrame(7, "player-left-jump3");
		playerLeftStopJump.addAnimationFrame(8, "player-left-jump2");
		playerLeftStopJump.addAnimationFrame(9, "player-left-jump1");
		playerStopJump.addAnimationFrame(10, "player");
		playerLeftStopJump.setDuration(10);
		animations.put("player-left-stopJump", playerLeftStopJump);

		ResourceAnimation playerLeftFall = new ResourceAnimation();
		playerLeftFall.setEndAnimationFrame("player-left-fall3");
		playerLeftFall.addAnimationFrame(4, "player-left-fall1");
		playerLeftFall.addAnimationFrame(8, "player-left-fall2");
		playerLeftFall.setDuration(8);
		animations.put("player-left-fall", playerLeftFall);

		maxSpeedX = getPerSecond(240);
		accelerationX = getPerSecond(20);
		decelerationX = getPerSecond(60);
		maxSpeedY = getPerSecond(400);
		accelerationY = getPerSecond(160);
		setMaxJump(0.1);
	}

	public void die()
	{
		super.die();
		playSound("pain");
		deaths++;
		spawn();
	}
	
	@Override
	public void tick()
	{
		super.tick();

		if (level.input.left)
		{
			if (facingRight)
				facingRight = false;
			xDir = -1;
		}
		else if (level.input.right)
		{
			if (!facingRight)
				facingRight = true;
			xDir = 1;
		}
		else
		{
			xDir = 0;
		}

		if (level.input.up)
			jump(true);
		else
			jump(false);

		animate();
	}

	public void animate()
	{
		if (facingRight)
		{
			if (Math.abs(currentVelocityX) > 1 && downCollision)
			{
				switchAnimation("player-walk");
				changeSound("footsteps");
			}
			else if (currentVelocityY < 0)
			{
				changeSound(null);
				if (yDir != 0)
				{
					if (currentAnimation != "player-left-jump" && currentAnimation != "player-jump")
						playSound("jump");
					switchAnimation("player-jump");
				}
				else if (currentAnimation == "player-jump")
				{
					switchAnimation("player-stopJump");
				}
			}
			else if (currentVelocityY > 1)
			{
				changeSound(null);
				switchAnimation("player-fall");
			}
			else
			{
				switchAnimation(null);
				changeSound(null);
				setImage("player", -8, -22, 64, 64);
			}
		}
		else
		{
			if (Math.abs(currentVelocityX) > 1 && downCollision)
			{
				switchAnimation("player-left-walk");
				changeSound("footsteps");
			}
			else if (currentVelocityY < 0)
			{
				changeSound(null);
				if (yDir != 0)
				{
					if (currentAnimation != "player-left-jump" && currentAnimation != "player-jump")
						playSound("jump");
					switchAnimation("player-left-jump");
				}
				else if (currentAnimation == "player-left-jump")
				{
					switchAnimation("player-left-stopJump");
				}
			}
			else if (currentVelocityY > 1)
			{
				changeSound(null);
				switchAnimation("player-left-fall");
			}
			else
			{
				switchAnimation(null);
				changeSound(null);
				setImage("player-left", -8, -22, 64, 64);
			}
		}
		if (currentAnimation != null)
		{
			animations.get(currentAnimation).run();
		}
	}

	@Override
	public void handleCollision(Entity other, String direction)
	{
		if (direction.equals("down") && other.willBlock && downCollision == false)
		{
			playSound("footsteps");
		}
		
		
		
		super.handleCollision(other, direction);
	}

}
