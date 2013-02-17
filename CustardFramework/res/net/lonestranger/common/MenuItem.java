package net.lonestranger.common;

import java.awt.Graphics;

public class MenuItem {

	Sprite sprite_on;
	Sprite sprite_off;
	
	// origin to the menu origin.
	int origin_x;
	int origin_y;
	
	String selectionName;
	
	boolean toggle;
	
	public MenuItem(int x, int y, String name, Sprite spriteOn, Sprite spriteOff) {
		// TODO Auto-generated constructor stub

		sprite_on = spriteOn;
		sprite_off = spriteOff;
		
		origin_x = x;
		origin_y = y;
		
		//setSpriteXY(origin_x, origin_y);
		
		toggle = false;
		
		selectionName = name;
		
		// make the sprites not move.
		sprite_on.setStep(0,0);
		sprite_off.setStep(0,0);
		
		sprite_on.setActive(false);
		sprite_off.setActive(false);
		
	}

	public void setImageOn(Sprite on)
	{
		sprite_on = on;
	}
	
	public void setImageOff(Sprite off)
	{
		sprite_off = off;
	}
	
	public void update() {
		// TODO Auto-generated method stub
		sprite_on.updateSprite();
		sprite_off.updateSprite();
	}

	public void draw(Graphics g) {
		
		if (toggle)
			sprite_on.drawSprite(g);
		else
			sprite_off.drawSprite(g);
	}
	
	public void drawOn(Graphics g)
	{
		sprite_on.setActive(true);
		sprite_off.setActive(false);
		
		sprite_on.drawSprite(g);
	}

	public void drawOff(Graphics g)
	{
		sprite_on.setActive(false);
		sprite_off.setActive(true);

		sprite_off.drawSprite(g);
	}
	
	
	public void setSpriteXY(int x, int y)
	{
		sprite_on.setPosition(x, y);
		sprite_off.setPosition(x, y);
	}
	
	public int getX()
	{
		return origin_x;	
	}

	public int getY()
	{
		return origin_y;	
	}	
	
	public void setActive(boolean tf)
	{
		sprite_on.setActive(tf);
		sprite_off.setActive(tf);
	}
	
	
}
