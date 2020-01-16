/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hiran.safewalker.helper;

import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Hiran
 */
public class WalkerSprite{

    private Sprite sprite;
    public int x=0,y=0;
    int maxWidth=200,maxHeight=200;
	boolean walkingToRight = true;
	int speed  = 1;
	float m = 1.0F;
	int c;

	int walkerSize = 16;
    public WalkerSprite(Image img, int x, int y)
    {
		sprite = new Sprite(img, x, y);
		sprite.defineReferencePixel(sprite.getWidth()/2, sprite.getHeight());
		sprite.setFrameSequence(new int[]{20,21,22,23});
		selectRandomPos();
    }


    public void selectRandomPos()
    {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		
		x = r.nextInt(maxWidth);
		y = r.nextInt(maxHeight);
		y = (y<12)? y+12:y;
		selectRandomPosOnMove(x,y);
    }

	private int selectPossibleC()
	{
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		int ay = r.nextInt(maxHeight*2) - maxHeight;
		return ay;
	}

	private float selectPossibleM()
	{
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		float temp = (float) r.nextInt(600);
		temp = temp - 300;
		temp = temp/100.0F;
		return temp;
	}

	public void selectRandomPosOnMove(int cx,int cy)
    {
		if ( cx<=0 )
		{
			m = selectPossibleM();
		}
		else
		{
			c = selectPossibleC();
			m = ((float)(y-c))/((float)x);
		}
    }

	public void nextStep()
	{
		
		int tempx=x, tempy=y;

		if (walkingToRight)
		{
			if ( Math.abs(m)>1 )
				tempy += speed;
			else
				tempx += speed;
		}
		else
		{
			if ( Math.abs(m)>1 )
				tempy -= speed;
			else
				tempx -= speed;
		}
		if ( Math.abs(m)>1 )
		{
			tempx = (tempy-c);
			tempx = (int)(tempx/m);// y=mx +c
		}
		else
		{
			float temp = m*tempx;
			int i = (int)temp;
			tempy = i + c; // y=mx +c
		}

		
		if (hasReachedWall(tempx,tempy))
		{
			walkingToRight = !walkingToRight;
			selectRandomPosOnMove(tempx,tempy);
		}
		else
		{
			x = tempx;
			y = tempy;
		}
	}

	private boolean hasReachedWall(int x, int y)
	{
		if ( x<=0 || x >=maxWidth || y <=12 || y>=maxHeight ) return true;

		return false;
	}

	public void paint(Graphics g)
	{
		sprite.setRefPixelPosition(x,y);
		sprite.nextFrame();
		sprite.paint(g);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
}
