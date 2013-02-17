// GamePanel.java
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

package net.lonestranger.game.engine;

import net.lonestranger.common.ClipsLoader;
import net.lonestranger.common.ImagesLoader;
import net.lonestranger.common.ImagesPlayerWatcher;
import net.lonestranger.common.Sprite;
import net.lonestranger.common.Menu;
import net.lonestranger.common.MenuItem;

import net.lonestranger.network.Report;
import net.lonestranger.settings.SettingsManager;
import net.lonestranger.tilemap.TileMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GamePanel extends JPanel
                     implements Runnable, ImagesPlayerWatcher
{
  SettingsManager settingsManager = new SettingsManager("game.properties");
	
  private static final int PWIDTH = 800;   // size of panel
  private static final int PHEIGHT = 600;
  
  // stores the mouse cursor from the last time it was moved.
  private int mouse_x;
  private int mouse_y;
  
  private static final int NO_DELAYS_PER_YIELD = 16;
  /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */
  private static final int MAX_FRAME_SKIPS = 5;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

  // image, clips loader information files
  private static final String IMS_INFO = "imsInfo.txt";
  private static final String SNDS_FILE = "clipsInfo.txt";
  private static final String PHRS_FILE = "categories.txt";

  // are we taking a screenshot this time around?
  private volatile boolean screenshot = false;
  
  private Thread animator;           // the thread that performs the animation
  private volatile boolean running = false;   // used to stop the animation threa (and kill the game?)
  private volatile boolean isPaused = false;

  private long period;                // period between drawing in _nanosecs_

  private Game gameTop;
  private ClipsLoader clipsLoader;

  //private WordKeeper wordKeeperObj;
  
  private long gameStartTime;   // when the game started
  private int timeSpentInGame;	// for overall program execution

  // used at game termination
  private volatile boolean gameOver = false;
  private int score = 0;

  // for displaying messages
  private Font msgsFont;
  private FontMetrics metrics;

  // off-screen rendering
  private Graphics dbg;
  private Image dbImage = null;
  
  // to display the title/help screen
  private boolean showHelp = false;
  private BufferedImage helpIm;

  private int STATE_MENU = 0;
  private int STATE_GAME = 1;
  private int STATE_OVER = 2;
 
  private int CONDITION_NONE = 0;
  private int CONDITION_LOSE = 1;
  private int CONDITION_WIN  = 2;
  
  
  private int state 	= STATE_GAME;
  private int condition = CONDITION_NONE;
  
  private BufferedImage backgroundImg;

  Cursor oldCursor;
  Cursor invisibleCursor;
  
  Menu menu;
  
  private Report rep;
  
  public GamePanel(Game g, long period)
  {
    gameTop = g;
    this.period = period;

    setDoubleBuffered(false);
    setBackground(Color.white);
    setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

    setFocusable(true);
    requestFocus();    // the JPanel now has focus, so receives key events

    addKeyListener( new KeyAdapter() {
       public void keyPressed(KeyEvent e)
       { processKey(e);  }
     });

    addMouseListener( new MouseAdapter() {
        public void mousePressed(MouseEvent me) 
        { 
        	processMousePressed(me); 
        	
        } 
        
        public void mouseReleased(MouseEvent me)
        {
        	processMouseReleased(me);
        }
    });

    addMouseMotionListener( new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent me)
        { 
        	processMouseMoved(me); 
        }

        public void mouseDragged(MouseEvent me)
        { 
        	processMouseMoved(me);
        	processMousePressed(me);
        }
        
    });
    
    
    
    // initialize the loaders
    ImagesLoader imsLoader = new ImagesLoader(IMS_INFO);
    clipsLoader = new ClipsLoader(SNDS_FILE);

    // initialize the game entities here
    backgroundImg = imsLoader.getImage("transparent"); 
      
   // currently just sends a test packet to the remote server.
   // eventually, it should report scores when the game ends.
 	try {
		rep = new Report();
		rep.setURL("http://localhost/dataStore_ld15/data.php");
		rep.setData("TEST DATA");
		rep.phoneHome();
		//System.out.println(rep.getData());
		//rep.setData(rep.getData());
		//System.out.println(rep.getData());
		
	} catch (MalformedURLException mue) {
		mue.printStackTrace();
	}

  
    
    // prepare title/help screen
    helpIm = imsLoader.getImage("title");
    showHelp = true;    // show at start-up?
    isPaused = true;

    // set up message font
    msgsFont = new Font("SansSerif", Font.BOLD, 24);
    metrics = this.getFontMetrics(msgsFont);

  //  this.pauseGame();
        
  //  TileMap tm = new TileMap(10,10);
  //  tm.output();
    Sprite menuSprite = new Sprite(0,0,210,270, imsLoader,"menu");
        
    // make sprites
    Sprite sp1_on = new Sprite(0,0,160,40, imsLoader,"menuitem1_on");
    Sprite sp1_off = new Sprite(0,0,160,40, imsLoader,"menuitem1_off");
    Sprite sp2_on = new Sprite(0,0,160,40, imsLoader,"menuitem2_on");
    Sprite sp2_off = new Sprite(0,0,160,40, imsLoader,"menuitem2_off");
    Sprite sp3_on = new Sprite(0,0,160,40, imsLoader,"menuitem3_on");
    Sprite sp3_off = new Sprite(0,0,160,40, imsLoader,"menuitem3_off");
    
    // make menu items.  origin is based on the location of the main menu sprite.
    MenuItem mi1 = new MenuItem(24,54, "menuItem1", sp1_on, sp1_off);
    MenuItem mi2 = new MenuItem(24,94, "menuItem2", sp2_on, sp2_off);
    MenuItem mi3 = new MenuItem(24,134,"menuItem3", sp3_on, sp3_off);
    
    
    menu = new Menu(PWIDTH/2 - (menuSprite.getWidth()/2), PHEIGHT/2 - (menuSprite.getHeight()/2), menuSprite);
    menu.AddMenuItem(mi1);
    menu.AddMenuItem(mi2);
    menu.AddMenuItem(mi3);
    
    menu.show(true);
    
  }  // end of GamePanel()


  private void processMouseClick(MouseEvent e)
  {
	  // get mouse location
	//  int mx = e.getX();
	//  int my = e.getY(); 
	  
  }

  private void processMousePressed(MouseEvent e)
  {
	  // get mouse location
	  int mx = e.getX();
	  int my = e.getY(); 
	  
	  if (e.getButton() == e.BUTTON1)
	  {
		  //carObj.gasPressed();		  
	  }

	  if (e.getButton() == e.BUTTON3)
	  {
		  //carObj.brakePressed();
		  //if (carObj.getSpeed() > 20)
		  //clipsLoader.play("brake", false);
	  }
	  
	  
  }
  
  private void processMouseReleased(MouseEvent e)
  {
	  // get mouse location
	  int mx = e.getX();
	  int my = e.getY(); 
	  
	  if (e.getButton() == e.BUTTON1)
	  {
		  //carObj.gasReleased();
	  }
	  if (e.getButton() == e.BUTTON3)
	  {  
		  //carObj.brakeReleased();
	  }
  }  
  
  private void processMouseMoved(MouseEvent e)
  {
	// check for mouse over the game area and highlight that cell.
	int mx = e.getX();
	int my = e.getY();
	
	mouse_x = e.getX();
	mouse_y = e.getY();
		
	if (mouse_x > PWIDTH)
		mouse_x = PWIDTH;
	if (mouse_x < 0)
		mouse_x = 0;
  }
  
  private void processKey(KeyEvent e)
  // handles termination, help, and game-play keys
  {
    int keyCode = e.getKeyCode();

    //System.out.println(state);
    if (state == STATE_GAME)
    {
    // termination keys
	   // listen for esc, q, end, ctrl-c on the canvas to
	    // allow a convenient exit from the full screen configuration
	    if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_END) ||
	        ((keyCode == KeyEvent.VK_C) && e.isControlDown()) )
	      running = false;
		    
	    // help controls
	    if (keyCode == KeyEvent.VK_F1) {
	      if (showHelp) {  // help already being shown
	        showHelp = false;  // switch off
	        isPaused = false;
	               
	        menu.show(false);
	        
    		clipsLoader.play("help_off", false);
	        
	      }
	      else {  // help not being shown
	       showHelp = true;    // show it
	       isPaused = true;    // isPaused may already be true
	             
	       menu.show(true);
	       
	       clipsLoader.play("help_on", false);
	      
	      }
	    }
	    
	    // game-play keys
	    if (!isPaused && !gameOver) 
	    {
	      // move the sprite and ribbons based on the arrow key pressed
	      
	    	
	    	if (keyCode == KeyEvent.VK_ENTER)
	    	{
	    		
	    	}
			else if (keyCode == KeyEvent.VK_LEFT) {

			}
			else if (keyCode == KeyEvent.VK_RIGHT) {

			}
			else if (keyCode == KeyEvent.VK_UP) {
				  
			}
			else if (keyCode == KeyEvent.VK_DOWN) {
			
			}
	    }
	    
	    // unpause by pressing space, so fingers can be in correct spots. 
	    if (( keyCode == KeyEvent.VK_SPACE) && showHelp)	    
	    {
	    	showHelp = false;
	    	isPaused = false;
	    	
	    	menu.show(false);
	    	
	    	clipsLoader.play("help_off", false);
	    
	    }
	    
	    
    }
    else if (state == STATE_OVER)
    {
    	System.out.println("state over");
    }
    
    if (!screenshot)
    {
    	if (keyCode == KeyEvent.VK_F10)
    	{
    		screenshot = true;
    	}
    }
        
  }  // end of processKey()


  public void sequenceEnded(String imageName)
  // called by ImagesPlayer when the animation finishes
  {  
	 //imagePlayer.restartAt(0);   // reset animation for next time


     
  } // end of sequenceEnded()



  public void addNotify()
  // wait for the JPanel to be added to the JFrame before starting
  { super.addNotify();   // creates the peer
    startGame();         // start the thread
  }


  private void startGame()
  // initialise and start the thread
  {
    if (animator == null || !running) {
      animator = new Thread(this);
      animator.start();
    }
    
    BufferedImage cimage = getGraphicsConfiguration().createCompatibleImage(1, 1, Transparency.BITMASK);
    Graphics2D cg = cimage.createGraphics();
    cg.setBackground(new Color(0,0,0,0));
    cg.clearRect(0,0,1,1);
     
    invisibleCursor = this.getToolkit().createCustomCursor(cimage, new Point(0,0), "Invisible");
    //oldCursor = this.getCursor();
    this.setCursor(invisibleCursor);    
    Robot r;
	try {
		r = new Robot();
	    r.mouseMove(PWIDTH/2, PHEIGHT/2);

	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    

  } // end of startGame()


  // ------------- game life cycle methods ------------
  // called by the JFrame's window listener methods


  public void resumeGame()
  // called when the JFrame is activated / deiconified
  { 
	  if (!showHelp)    // CHANGED
	  {
		  isPaused = false;
		  setCursor(invisibleCursor);
	  }
	  
  }

  public void pauseGame()
  // called when the JFrame is deactivated / iconified
  { 
	  isPaused = true;   
	  setCursor(oldCursor);
  }


  public void stopGame()
  // called when the JFrame is closing
  {  
	  running = false;  
	  setCursor(oldCursor);
  }
 
  // ----------------------------------------------

  public void run()
  /* The frames of the animation are drawn inside the while loop. */
  {
    long beforeTime, afterTime, timeDiff, sleepTime;
    long overSleepTime = 0L;
    int noDelays = 0;
    long excess = 0L;

    gameStartTime = System.nanoTime();
    beforeTime = gameStartTime;
    
    running = true;

    while(running) {
      gameUpdate();
      gameRender();
      paintScreen();

      afterTime = System.nanoTime();
      timeDiff = afterTime - beforeTime;
      sleepTime = (period - timeDiff) - overSleepTime;

      if (sleepTime > 0) {   // some time left in this cycle
        try {
          Thread.sleep(sleepTime/1000000L);  // nano -> ms
        }
        catch(InterruptedException ex){}
        overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
      }
      else {    // sleepTime <= 0; the frame took longer than the period
        excess -= sleepTime;  // store excess time value
        overSleepTime = 0L;

        if (++noDelays >= NO_DELAYS_PER_YIELD) {
          Thread.yield();   // give another thread a chance to run
          noDelays = 0;
        }
      }

      beforeTime = System.nanoTime();

      /* If frame animation is taking too long, update the game state
         without rendering it, to get the updates/sec nearer to
         the required FPS. */
      int skips = 0;
      while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
        excess -= period;
        gameUpdate();    // update state but don't render
        skips++;
      }
    }
    System.exit(0);   // so window disappears
  } // end of run()


  private void gameUpdate()
  {
    if (!isPaused && !gameOver) {
    	  	
    	if (state == STATE_GAME)
    	{
    		menu.update();
    		// update stuff
    	}   	
    }
  }  // end of gameUpdate()


  private void gameRender()
  {
    if (dbImage == null){
      dbImage = createImage(PWIDTH, PHEIGHT);
      if (dbImage == null) {
        System.out.println("dbImage is null");
        return;
      }
      else
        dbg = dbImage.getGraphics();
    }

    // draw a white background
    dbg.setColor(Color.black);
    dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

    // draw the game elements: order is important
    //
    dbg.drawImage(backgroundImg, 0, 0, null);

    menu.Draw(dbg);
    
    if (!showHelp)
    	;


    
/*	if (state == STATE_OVER)
	{
		if (condition == CONDITION_LOSE)
		{
			String lose = "You Lose!";
			dbg.drawString(lose, PHEIGHT / 2, (PWIDTH/2)- (dbg.getFontMetrics(phraseObj.getFont()).stringWidth(lose) /2));
		}
	}
*/    
    	
	
    // take a screen shot of the current display.  Before debug stats are reported.
    if (screenshot == true)
    {
		takeScreenShot(dbImage);   	
		screenshot = false;
    }

    
  //  reportStats(dbg);

    if (gameOver)
      gameOverMessage(dbg);

    if (showHelp)    // draw the help at the very front (if switched on)
      dbg.drawImage(helpIm, (PWIDTH-helpIm.getWidth())/2,
                          (PHEIGHT-helpIm.getHeight())/2, null);
  }  // end of gameRender()


  private void reportStats(Graphics g)
  // Report the number of hits, and time spent playing
  {
    if (!gameOver)    // stop incrementing the timer once the game is over
      timeSpentInGame =
          (int) ((System.nanoTime() - gameStartTime)/1000000000L);  // ns --> secs
    g.setColor(Color.red);
    g.setFont(msgsFont);
//  g.drawString("Hits: " + numHits + "/" + MAX_HITS, 15, 25);
    g.drawString("Time: " + timeSpentInGame + " secs", 15, 50);
    g.setColor(Color.black);
  }  // end of reportStats()


  private void gameOverMessage(Graphics g)
  // Center the game-over message in the panel.
  {
    String msg = "Game Over. Your score: " + score;

    int x = (PWIDTH - metrics.stringWidth(msg))/2;
    int y = (PHEIGHT - metrics.getHeight())/2;
    g.setColor(Color.black);
    g.setFont(msgsFont);
    g.drawString(msg, x, y);
  }  // end of gameOverMessage()


  private void paintScreen()
  // use active rendering to put the buffered image on-screen
  {
    Graphics g;
    try {
      g = this.getGraphics();
      if ((g != null) && (dbImage != null))
        g.drawImage(dbImage, 0, 0, null);
      // Sync the display on some systems.
      // (on Linux, this fixes event queue problems)
      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }
    catch (Exception e)
    { System.out.println("Graphics context error: " + e);  }
  } // end of paintScreen()

  private void takeScreenShot(Image dbi)
  {
  	String nowString = "";
	synchronized(this)  {
    	Date now = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssS" );
    	nowString = df.format(now);
	}
	
	File ssout = new File(nowString + ".png");
    try {
		ImageIO.write((RenderedImage) dbi, "png", ssout);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  
  
  
}  // end of GamePanel class
