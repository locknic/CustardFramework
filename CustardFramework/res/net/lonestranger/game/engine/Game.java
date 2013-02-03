// Game.java
//
// LoneStranger's base code for LD48_11
//
// This is derived from JumpingJack code by 
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th
// from the book Killer Game Programming in Java published by 
// O'Reilly.  http://www.oreilly.com/catalog/killergame/
//
// I stripped all the game logic and left the parts for loading 
// images and sounds and the game loop.

/*   -----
   Pausing/Resuming/Quiting are controlled via the frame's window
   listener methods.

   Active rendering is used to update the JPanel. See WormP for
   another example, with additional statistics generation.

   Using Java 3D's timer: J3DTimer.getValue()
     *  nanosecs rather than millisecs for the period
	 [actually, I switched it to Java 1.5.0 System.nanoTimer() -LS]
	
   The MidisLoader, ClipsLoader, ImagesLoader, and ImagesPlayer
   classes are used for music, images, and animation.

*/

package net.lonestranger.game.engine;

import javax.swing.*;

import net.lonestranger.common.MidisLoader;

import java.awt.*;
import java.awt.event.*;


public class Game extends JFrame implements WindowListener
{
  private static int DEFAULT_FPS = 30;      // 40 is too fast! 

  private GamePanel gamePanel;        // where the game is drawn
  private MidisLoader midisLoader;


  public Game(long period)
  { super("LD48_15 Game - ???");

    // load the background MIDI sequence
    midisLoader = new MidisLoader();
    //midisLoader.load("musicname", "somefile.mid");
    //midisLoader.play("musicname", true);   // repeatedly play it

    Container c = getContentPane();    // default BorderLayout used
    gamePanel = new GamePanel(this, period);
    c.add(gamePanel, "Center");

    addWindowListener( this );
    pack();
    setResizable(false);
    setVisible(true);
  }  // end of Game() constructor


  // ----------------- window listener methods -------------

  public void windowActivated(WindowEvent e) 
  { gamePanel.resumeGame();  }

  public void windowDeactivated(WindowEvent e) 
  { gamePanel.pauseGame();  }


  public void windowDeiconified(WindowEvent e) 
  {  gamePanel.resumeGame();  }

  public void windowIconified(WindowEvent e) 
  {  gamePanel.pauseGame(); }


  public void windowClosing(WindowEvent e)
  {  gamePanel.stopGame();  
     midisLoader.close();  // not really required
  }


  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

  // ----------------------------------------------------

  public static void main(String args[])
  { 
    long period = (long) 1000.0/DEFAULT_FPS;
    System.out.println("fps: " + DEFAULT_FPS + "; period: " + period + " ms");
    new Game(period*1000000L);    // ms --> nanosecs 
  }

} // end of Game class


