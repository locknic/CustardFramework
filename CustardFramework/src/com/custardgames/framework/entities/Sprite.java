package com.custardgames.framework.entities;

import java.util.HashMap;
import java.util.Map;

import com.custardgames.framework.level.LevelHandler;
import com.custardgames.framework.resources.ResourceAnimation;
import com.custardgames.framework.resources.ResourceAnimationFrame;
import com.custardgames.framework.resources.ResourceManager;

public class Sprite extends Entity
{
	public String currentAnimation;
	public Map<String, ResourceAnimation> animations;

	public String imageID;
	public int imgWidth;
	public int imgHeight;
	public int imgX;
	public int imgY;

	public boolean fixed;
	public int xDir;
	public int yDir;
	public float maxSpeedX;
	public float currentVelocityX;
	public float accelerationX;
	public float decelerationX;
	public float maxSpeedY;
	public float currentVelocityY;
	public float accelerationY;

	public final float gravity = getPerSecond(20);
	public final float maxGravity = getPerSecond(400);

	public boolean leftCollision;
	public boolean rightCollision;
	public boolean upCollision;
	public boolean downCollision;
	public boolean inWater;

	public Sprite(LevelHandler level)
	{
		super(level);
		currentAnimation = null;
		animations = new HashMap<String, ResourceAnimation>();
		maxSpeedY = 0;
		maxSpeedX = 0;
		accelerationX = 0;
		decelerationX = 0;
		accelerationY = 0;
		fixed = true;
		reset();
	}
	
	public void reset()
	{
		currentVelocityX = 0;
		currentVelocityY = 0;
		xDir = 0;
		yDir = 0;
		leftCollision = false;
		rightCollision = false;
		upCollision = false;
		downCollision = false;
	}

	public int getImageX()
	{
		return (int) (xCo + imgX);
	}

	public int getImageY()
	{
		return (int) (yCo + imgY);
	}

	public void setImage(String imageID)
	{
		if (width == 0)
		{
			width = ResourceManager.get_instance().getImage(imageID).getWidth();
		}
		if (height == 0)
		{
			height = ResourceManager.get_instance().getImage(imageID).getHeight();
		}

		setImage(imageID, 0, 0, width, height);
	}

	public void setImage(String imageID, int imgWidth, int imgHeight)
	{
		setImage(imageID, (width / 2) - (imgWidth / 2), (height / 2) - (imgHeight / 2), imgWidth, imgHeight);
	}

	public void setImage(String imageID, int imgX, int imgY, int imgWidth, int imgHeight)
	{
		this.imageID = imageID;
		this.imgX = imgX;
		this.imgY = imgY;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}

	public void move()
	{
		if (fixed == false)
		{
			currentVelocityX = calculateVelocity(xDir, currentVelocityX, maxSpeedX, accelerationX, decelerationX);
			currentVelocityY = calculateVelocity(yDir, currentVelocityY, maxSpeedY, accelerationY, 0);
			currentVelocityY = calculateVelocity(1, currentVelocityY, maxGravity, gravity, 0);
			
			if (inWater)
			{
				currentVelocityX = (currentVelocityX/20)*16;
				currentVelocityY = (currentVelocityY/20)*16;
				inWater = false;
			}

			if (currentVelocityX != 0)
			{
				String direction = getDirection(currentVelocityX, true);

				xCo += currentVelocityX;
				Entity collision = checkCollision(direction);
				if (collision != null)
				{
					while (this.intersects(collision))
					{
						if (currentVelocityX > 0)
							xCo--;
						else
							xCo++;
					}
					currentVelocityX = 0;
				}
			}

			if (currentVelocityY != 0)
			{
				String direction = getDirection(currentVelocityY, false);
				if (currentVelocityY >= 1)
				{
					downCollision = false;
				}

				yCo += currentVelocityY;
				Entity collision = checkCollision(direction);
				if (collision != null)
				{
					while (this.intersects(collision))
					{
						if (currentVelocityY > 0)
							yCo--;
						else
							yCo++;
					}
					currentVelocityY = 0;
				}
			}

			if (yDir != 0)
				System.out.println("Velocity: " + currentVelocityY + ", Y Coordinate: " + yCo);
		}
	}

	public String getDirection(float velocity, boolean xAxis)
	{
		String direction = null;
		if (xAxis)
		{
			if (velocity > 0)
			{
				direction = "right";
				leftCollision = false;
			}
			else if (velocity < 0)
			{
				direction = "left";
				rightCollision = false;
			}
		}
		else
		{
			if (velocity > 0)
			{
				direction = "down";
				upCollision = false;
			}
			if (velocity < 0)
			{
				direction = "up";
				downCollision = false;
			}
		}
		return direction;
	}

	public float calculateVelocity(int direction, float velocity, float maxSpeed, float acceleration, float deceleration)
	{
		if (direction > 0)
		{
			if (velocity < 0)
			{
				velocity = calculateDeceleration(velocity, deceleration);
			}
			if (velocity < maxSpeed)
			{
				velocity += direction * acceleration;
			}
			else
			{
				velocity = maxSpeed;
			}
		}
		else if (direction < 0)
		{
			if (velocity > 0)
			{
				velocity = calculateDeceleration(velocity, deceleration);
			}
			if (velocity > -maxSpeed)
			{
				velocity += direction * acceleration;
			}
			else
			{
				velocity = -maxSpeed;
			}
		}
		else if (velocity != 0)
		{
			velocity = calculateDeceleration(velocity, deceleration);
		}

		return velocity;
	}

	public float calculateDeceleration(float velocity, float deceleration)
	{
		if (velocity > 0)
			velocity -= deceleration;
		else if (velocity < 0)
			velocity += deceleration;

		if (velocity > 0 && velocity < deceleration)
			velocity = 0;
		else if (velocity < 0 && velocity > -deceleration)
			velocity = 0;
		return velocity;
	}

	public Entity checkCollision(String direction)
	{
		for (int x = 0; x < level.collisionLayer.size(); x++)
		{
			if (level.collisionLayer.get(x) != this)
			{
				if (intersects(level.collisionLayer.get(x)))
				{
					this.handleCollision(level.collisionLayer.get(x), direction);
					level.collisionLayer.get(x).handleCollision(this, direction);
					if (level.collisionLayer.get(x).willBlock)
					{
						return level.collisionLayer.get(x);
					}
				}
			}
		}
		for (int x = 0; x < level.frontLayer.size(); x++)
		{
			if (level.frontLayer.get(x) != this)
			{
				if (intersects(level.frontLayer.get(x)))
				{
					this.handleCollision(level.frontLayer.get(x), direction);
					level.frontLayer.get(x).handleCollision(this, direction);
					if (level.frontLayer.get(x).willBlock)
					{
						return level.frontLayer.get(x);
					}
				}
			}
		}
		return null;
	}

	public void tick()
	{
		move();
		if (currentAnimation != null)
		{
			String newImageID = animations.get(currentAnimation).tick();
			if (!(newImageID.equals(imageID)))
			{
				ResourceAnimationFrame currentFrame = ResourceManager.get_instance().getAnimationFrame(newImageID);
				imageID = newImageID;
				imgWidth = currentFrame.getWidth();
				imgHeight = currentFrame.getHeight();
				imgX = currentFrame.getxOffset();
				imgY = currentFrame.getyOffset();
			}
		}
	}

	public void render()
	{
		if (shouldRender)
			level.graphics.drawEntity(this);
	}

	public void switchAnimation(String newAnimation)
	{
		if (currentAnimation != null)
			if (!(currentAnimation.equals(newAnimation)))
				animations.get(currentAnimation).stop();
		currentAnimation = newAnimation;
	}

	public void resetDirectionX()
	{
		leftCollision = false;
		rightCollision = false;
	}

	public void resetDirectionY()
	{
		upCollision = false;
		downCollision = false;
	}

	@Override
	public void handleCollision(Entity other, String direction)
	{
		if (direction.equals("left") && other.willBlock)
		{
			leftCollision = true;
		}
		else if (direction.equals("right") && other.willBlock)
		{
			rightCollision = true;
		}
		else if (direction.equals("up") && other.willBlock)
		{
			upCollision = true;
		}
		else if (direction.equals("down") && other.willBlock)
		{
			downCollision = true;
		}
	}

}
