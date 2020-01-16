/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hiran.safewalker.midlet;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import com.hiran.safewalker.canvas.*;
/**
 * @author Hiran
 */
public class SafeWalkerMidlet extends MIDlet implements CommandListener{
    
	static final Command exitCommand = new Command("Exit", Command.STOP, 1);
    static final Command startGameCommand = new Command("Start", Command.ITEM, 1);
    static final Command backToMainCommand = new Command("Back", Command.ITEM, 1);
	static final Command holdGameCommand = new Command("Hold", Command.ITEM, 1);

	static final Command newGameCommand = new Command("New Game", Command.ITEM, 1);
    private Display theDisplay;
    MainScreenCanvas canvas;
    SafeWalkerGameCanvas gameCanvas;
	WonScreenCanvas wonScreenCanvas;
	LostScreenCanvas lostScreenCanvas;


    public SafeWalkerMidlet()
    {
        theDisplay = Display.getDisplay(this);
    }
    public void startApp() {
        canvas = new MainScreenCanvas();
        canvas.addCommand(exitCommand);
        canvas.addCommand(startGameCommand);
        canvas.setCommandListener(this);
        theDisplay.setCurrent(canvas);
    }

    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        }

        if (c == startGameCommand)
        {
            commandStartGame();
        }
        if (c == backToMainCommand)
        {
            backToMain();
        }
		if ( c == holdGameCommand )
		{
			holdGameAction();
		}
		if ( c == newGameCommand )
		{
			commandStartGame();
		}


    }


    public void displayGameWon(Image offScreen )
    {

		wonScreenCanvas = new WonScreenCanvas(this);
		wonScreenCanvas.setOffScreen(offScreen);
		wonScreenCanvas.setCommandListener(this);
		wonScreenCanvas.addCommand(newGameCommand);
		theDisplay.setCurrent(wonScreenCanvas);
    }

	public void displayGameLost(Image offScreen )
    {

		lostScreenCanvas = new LostScreenCanvas(this);
		lostScreenCanvas.setOffScreen(offScreen);
		lostScreenCanvas.setCommandListener(this);
		lostScreenCanvas.addCommand(newGameCommand);
		theDisplay.setCurrent(lostScreenCanvas);
    }

	public void backToMain()
    {
		theDisplay.setCurrent(canvas);
    }

    public void holdGameAction()
    {
		if (gameCanvas.isGameRunning())
		{
			gameCanvas.stop();
		}
		else
		{
			gameCanvas.start();
		}
    }

    public void commandStartGame()
    {
		
		gameCanvas = new SafeWalkerGameCanvas(this);
		gameCanvas.setCommandListener(this);
		gameCanvas.addCommand(holdGameCommand);
		gameCanvas.addCommand(backToMainCommand);
		theDisplay.setCurrent(gameCanvas);
		gameCanvas.start();
    }
}
