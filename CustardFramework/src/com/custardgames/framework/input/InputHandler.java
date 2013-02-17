package com.custardgames.framework.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.custardgames.framework.Game;

public class InputHandler implements KeyListener
{
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    public InputHandler(Game game)
    {
        game.addKeyListener(this);
    }

    public void keyTyped(KeyEvent e) 
    {
    }

    public void keyPressed(KeyEvent e) 
    {
        toggle(e, true);
    }

    public void keyReleased(KeyEvent e) 
    {
        toggle(e, false);
    }

    private void toggle(KeyEvent e, boolean b) 
    {
        if (e.getKeyCode()==KeyEvent.VK_UP) 
        {
            up=b;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN) 
        {
            down=b;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT) 
        {
            left=b;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) 
        {
            right=b;
        }
    }
    public void releaseAll()
    {
        up=down=left=right=false;
    }
}
