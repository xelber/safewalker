/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hiran.safewalker.canvas;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Hiran
 */
public class MainScreenCanvas extends Canvas{

    String image_name = "/com/hiran/safewalker/images/spalsh.png";
    Image img;
    public MainScreenCanvas() {
	try {
            img = Image.createImage(image_name);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    protected void paint(Graphics g) {
		//g.drawString("ABC", 0, 0, UP);

		g.drawImage(img, 0, 0, g.TOP | g.LEFT);
    }
}
