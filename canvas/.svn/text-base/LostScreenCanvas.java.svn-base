/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hiran.safewalker.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Hiran
 */
public class LostScreenCanvas  extends Canvas{

	MIDlet parent;
	Image offScreen;
    public LostScreenCanvas(MIDlet parent)
	{
		this.parent = parent;
    }

    protected void paint(Graphics g)
	{
		//offScreen.

		g.drawImage(offScreen, 0, 0, g.TOP | g.LEFT);
		g.drawString("Game Over!", 100, 100, g.TOP | g.LEFT);
    }

	public Image getOffScreen() {
		return offScreen;
	}

	public void setOffScreen(Image offScreen) {
		this.offScreen = offScreen;
	}


}
