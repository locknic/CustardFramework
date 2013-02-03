package com.custardgames.framework;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.custardgames.framework.input.InputHandler;
import com.custardgames.framework.level.LevelHandler;

public class Game extends Canvas implements Runnable
{
	public static final String name = "Custard Framework";
	
    public static final int nativeWidth = 400;
    public static final int nativeHeight = 225;
    public static final double screenScale = 3;
    public static final int windowWidth = (int) (nativeWidth*screenScale);
    public static final int windowHeight = (int) (nativeHeight*screenScale);
    
    public static final int ticksPerSecond = 60;
    
    private boolean running=false;
    
    public InputHandler input=new InputHandler(this);
    public LevelHandler level=new LevelHandler("res", input);
    
    public Game(){
    	this.setMinimumSize(new Dimension(windowWidth, windowHeight));
        this.setMaximumSize(new Dimension(windowWidth, windowHeight));
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        
        JFrame frame=new JFrame(Game.name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public synchronized void start()
    {
        running=true;
        new Thread(this).start();
        level.start();
    }
    
    public void stop()
    {
        running=false;
    }
    
    public void run()
    {
        double nsPerTick = 1000000000.0 / ticksPerSecond;
        double unprocessed = 0;
        long lastTime = System.nanoTime();
        
        int frames = 0;
        int ticks = 0;
        long lastTimer = System.currentTimeMillis();
 
        while(running)
        {
            long now = System.nanoTime();
            long lastFrame = System.currentTimeMillis();
            unprocessed += (now-lastTime)/nsPerTick;
            lastTime = now;
            while(unprocessed >= 1)
            {
                ticks++;
                tick();
                unprocessed -= 1;
            }
            if (unprocessed < 1)
            {
                frames++;
                render();
            }
            
            long sleepCancel = System.currentTimeMillis()-lastFrame;
            
            try 
            {
                int sleepTime = 1;
                if(sleepCancel<sleepTime)
                {
                    Thread.sleep((int)(sleepTime-sleepCancel));
                }
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (System.currentTimeMillis()-lastTimer>=1000)
            {
                lastTimer = System.currentTimeMillis();
                System.out.println(ticks+" ticks, "+frames+" fps");
                frames = 0;
                ticks = 0;
            }
        }
    }
    
    public void tick()
    {
    	level.tick();
    }
    
    public void render()
    {
    	BufferStrategy bs = getBufferStrategy();
    	if (bs == null){
    		createBufferStrategy(3);
    		return;
    	}
    	
        Graphics g = bs.getDrawGraphics();
        g.drawImage(level.render(), 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args) 
    {
        new Game().start();
    }
}
