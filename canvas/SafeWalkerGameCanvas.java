/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hiran.safewalker.canvas;

import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.midlet.MIDlet;

import com.hiran.safewalker.helper.WalkerSprite;
import com.hiran.safewalker.midlet.SafeWalkerMidlet;
import java.util.Random;

public class SafeWalkerGameCanvas extends GameCanvas implements Runnable
{
    final int MAX_WALKERS = 15;
	int curruntLevelWalkers = 1; //Walkers for currunt level, will increase when level increases
    boolean gameRunning = false;
    private int width;
    private int height;
    int y = 0;
    int x = 0;
    Image offScreen;
    Sprite playerSprite;
	boolean isPlayerWalking = false;
	int playerSpeed = 5;
	WalkerSprite[] walkerSprite = new  WalkerSprite[MAX_WALKERS];;
	int walkerSpriteCount = 0;
	String msg = "";
	private String player_tile_img = "/com/hiran/safewalker/images/player_tiles.png";
	MIDlet parent;
	int scoreAreaSize = 40;
	int score = 0;
	int lastCollidedWalker = -1;
	Sprite priceSprite;
	int priceX, priceY;
	int hits = 3;
	
    public SafeWalkerGameCanvas(MIDlet parent)
    {
		super(true);
		width = getWidth();
		height = getHeight();
		x = width/2;
		y = height-16;

		this.parent = parent;
    }

	public void createNewWalterSprite()
	{
		if (walkerSpriteCount>=curruntLevelWalkers) return;
		try {
			walkerSprite[walkerSpriteCount] = new WalkerSprite(Image.createImage(player_tile_img), 16, 16);
			walkerSprite[walkerSpriteCount].setMaxWidth(width);
			walkerSprite[walkerSpriteCount].setMaxHeight(height-scoreAreaSize);
			lastCollidedWalker = walkerSpriteCount;// In case if the new one is close to the player
			walkerSpriteCount++;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

    public void start()
    {
		gameRunning = true;
		try {
			if (playerSprite == null)
			{
				playerSprite = new Sprite(Image.createImage(player_tile_img), 16, 16);
				playerSprite.defineReferencePixel(playerSprite.getWidth()/2, playerSprite.getHeight());
				playerSprite.setFrameSequence(new int[]{8,9,10,11});
			}
			if (priceSprite == null)
			{
				priceSprite = new Sprite(Image.createImage(player_tile_img), 16, 16);
				priceSprite.defineReferencePixel(priceSprite.getWidth()/2, priceSprite.getHeight());
				priceSprite.setFrameSequence(new int[]{0,1});
				placePrice();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Thread gameThread = new Thread(this);
		gameThread.start();
    }

    public void stop() { gameRunning = false; }


	public boolean isGameRunning()
	{
		return gameRunning;
	}

    public void run()
    {
		Graphics g = getGraphics();

		while (gameRunning) //is true
		{
			verifyGameState();
			checkUserInput();
			updateGameScreen(g);

			flushGraphics();
			try
			{
				Thread.sleep(20 );
			}catch (InterruptedException ie) { stop(); }
		}
    }
    private void verifyGameState()
    {
		if (hits==0)
		{
			stop();
			((SafeWalkerMidlet)parent).displayGameLost(offScreen);
			return;
		}
		if (score>=2000)
		{
			stop();
			((SafeWalkerMidlet)parent).displayGameWon(offScreen);
			return;
		}
		if (curruntLevelWalkers<MAX_WALKERS)
		{
			int level = (score/50) + 1;

			if (curruntLevelWalkers<level) curruntLevelWalkers++;
		}
    }

    private void checkUserInput()
    {
		int keyState = getKeyStates();
		if (keyState==UP_PRESSED)
		{
			if ( y>playerSpeed )y -= playerSpeed;
			isPlayerWalking = true;
		}
		if (keyState==DOWN_PRESSED)
		{
			if ( y<(height-scoreAreaSize) )y += playerSpeed;
			isPlayerWalking = true;
		}

		if (keyState==LEFT_PRESSED)
		{
			if ( x>playerSpeed )x -= playerSpeed;
			isPlayerWalking = true;
		}

		if (keyState==RIGHT_PRESSED)
		{
			if ( x<width-playerSpeed ) x += playerSpeed;
			isPlayerWalking = true;
		}
    }

	private void placePrice()
	{
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());

		priceX = r.nextInt(width);
		
		priceY = r.nextInt(height-scoreAreaSize);
		priceY = priceY<16? priceY+16 : priceY;
	}
    private void updateGameScreen(Graphics g)
    {
		offScreen = Image.createImage(width,height);
		updateWalkers();
		updatePlayer();
		renderPrice();
		if (checkCollision());// score -= 50;
		if (checkPriceCollection()) 
		{
			score +=10;
			lastCollidedWalker = -1;
		}
		//showMessage();
		renderScore();
		drawBoarders();
		renderScore();
		g.drawImage(offScreen, 0, 0, g.TOP | g.LEFT);
    }

	private void drawBoarders()
	{
		Graphics g = offScreen.getGraphics();
		g.drawRect(0, height-scoreAreaSize, width, height);
	}
	private void updateWalkers()
	{
		Graphics g = offScreen.getGraphics();
		createNewWalterSprite();
		for (int i=0;i<walkerSpriteCount;i++)
		{
			walkerSprite[i].nextStep();
			walkerSprite[i].paint(g);
		}
	}

    public void updatePlayer()
    {
		Graphics g = offScreen.getGraphics();
		playerSprite.setRefPixelPosition(x, y);
		if (isPlayerWalking)
		{
			playerSprite.nextFrame();
			isPlayerWalking = false;
		}
		playerSprite.paint(g);
    }

	public void renderPrice()
	{
		Graphics g = offScreen.getGraphics();
		priceSprite.setRefPixelPosition(priceX, priceY);
		priceSprite.nextFrame();
		priceSprite.paint(g);
	}

	public boolean checkCollision()
	{
		for (int i=0;i<walkerSpriteCount;i++)
		{
			int walkerX = walkerSprite[i].x;
			int walkerY = walkerSprite[i].y;
			if ( Math.abs(x - walkerX)<10 && Math.abs(y - walkerY)<10 && i != lastCollidedWalker )
			{
				Display.getDisplay(parent).vibrate(300);
				lastCollidedWalker = i;
				hits--;
				return true;
			}
		}
		return false;
	}

	public boolean checkPriceCollection()
	{
		
		if ( Math.abs(x - priceX)<10 && Math.abs(y - priceY)<10 )
		{
			placePrice();
			return true;
		}
		
		return false;
	}

	public void showMessage()
    {
		Graphics g = offScreen.getGraphics();
		g.drawString(msg, width/2, height/2, g.TOP | g.LEFT);
    }

	private void renderScore()
	{
		Graphics g = offScreen.getGraphics();
		String scoreStr = "SCORE: " + score;
		g.drawString(scoreStr, 0, height-scoreAreaSize, g.TOP | g.LEFT);

		String histStr = "HITS: " + hits;
		g.drawString(histStr, width/2, height-scoreAreaSize, g.TOP | g.LEFT);
	}
}