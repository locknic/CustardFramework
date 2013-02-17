package net.lonestranger.common;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Menu {

	private Sprite background;
	private LinkedList<MenuItem> menuitems;
		
	private int origin_x;
	private int origin_y;
	
	private int currentselection;
	private boolean show;
	
	public static final int LIST_END = -1;
	
	public Menu(int ox, int oy, Sprite back)
	{
		background = back;
		
		origin_x = ox;
		origin_y = oy;		
		
		background.setPosition(ox, oy);
		
		menuitems = new LinkedList<MenuItem>();
		
		currentselection = 0;

		show(false);

		
	}
	
	public void AddMenuItem(MenuItem mi)
	{
		AddMenuItem(this.LIST_END, mi);
	}
	
	public void AddMenuItem(int loc, MenuItem mi)
	{
		mi.setSpriteXY(origin_x + mi.getX(), origin_y + mi.getY());
		mi.setActive(false);
		
		System.out.println(new Point(origin_x + mi.getX(), origin_y + mi.getY()));
		
		if (loc == this.LIST_END)
			menuitems.add(mi);
		else
			menuitems.add(loc, mi);
	}
	
	public int keyPress(int key)
	{
		if (key == KeyEvent.VK_UP)
		{
			currentselection--;
			checkBounds();
			return 0;
		}
		
		if (key == KeyEvent.VK_DOWN)
		{
			currentselection++;
			checkBounds();
			return 0;
		}
		
		return -1;
	}
	
	public boolean show()
	{
		return show;
	}
	
	public void show(boolean tf)
	{
		show = tf;
		
		background.setActive(tf);
		
		if (tf == false)
			for (Iterator<MenuItem> i = menuitems.iterator(); i.hasNext(); )
			{
				MenuItem mi = i.next();
				mi.setActive(false);
			}
		
		System.out.println("Menu now " + tf);
	}
	
	public void checkBounds()
	{
		if (currentselection < 0)
			currentselection = menuitems.size() - 1;
		
		if (currentselection >= menuitems.size())
			currentselection = 0;

	}
	
	public void update()
	{
		for (Iterator<MenuItem> i = menuitems.iterator(); i.hasNext(); )
		{
			MenuItem mi = i.next();
			
			mi.update();
			
		}
	}
	
	public void Draw(Graphics g)
	{
		if (show)
		{
			if (background != null)
				background.drawSprite(g);
		
			int count = 0;
			for (Iterator<MenuItem> i = menuitems.iterator(); i.hasNext(); )
			{
				MenuItem mi = i.next();
				System.out.println("selection: " + currentselection + ", i: " + mi.selectionName + ", count: " + count);
				if (currentselection == count)
					mi.drawOn(g);
				else
					mi.drawOff(g);
				
				count++;
				//System.out.println((mi.origin_x + mi.getX()) + "," + (mi.origin_y + mi.getY()));
				
			}
		}	
	}
	
	
}
