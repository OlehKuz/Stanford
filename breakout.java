/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** X coordinate of first brick */
	private static final double x1Brick = 0.5*(double)(WIDTH - (BRICK_WIDTH * NBRICKS_PER_ROW +(NBRICKS_PER_ROW - 1) * BRICK_SEP));

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		setSize(APPLICATION_WIDTH + 20, APPLICATION_HEIGHT + 50); // i messed up w calculations and need to set a bigger window
		addMouseListeners();
		createSetUP();
		while(turnsLeft != 0){
			getBallRolling();
		}
    
	}
	private void createSetUP(){
		drawBricks();
		drawPaddle();	
	}
	
	private void getBallRolling(){
		ball = new GOval((double)WIDTH / 2 - BALL_RADIUS, (double)HEIGHT / 2 - BALL_RADIUS, BALL_RADIUS*2,BALL_RADIUS*2);
		ball.setFilled(true);	
		add(ball);
		vy = 3;
		vx = rgen.nextDouble(1.0, 3.0);
			if(rgen.nextBoolean(0.5)) vx = - vx;
		while(true){	
			ball.move(vx, vy);
			GObject collider = getCollidingObject();
			if(collider != null){
				collision(collider);
			}
			bounceBall();
			pause(15);
      
      // Handles Game over situations
			// if ball touched lower border  
			if(ball.getY() >= HEIGHT - BALL_RADIUS *2){
				remove(ball);
				turnsLeft--;
				GLabel oups = new GLabel("You have " + turnsLeft + " turns left", WIDTH / 2, HEIGHT / 2);
				add(oups);
				pause(1000);
				return;
			}
			
			// i player destroyed all the bricks
			if(bricksLeft == 0){
				GLabel congrat = new GLabel("Congrats, you won!" ,WIDTH / 2,HEIGHT / 2);
				add(congrat);
				break;
			}
			
		}		
	}
	/* checking for collision on 4 sides of a ball (if we imagine that ball is inside a rectangle of the same size,
	possible points of colision as rectangle corners)*/
	
	private GObject getCollidingObject(){
		GObject bang = null;
		GObject brickCollided = getElementAt(ball.getX(), ball.getY());
		if(brickCollided != null) {
			return brickCollided;
		}
		brickCollided = getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY());
		if(brickCollided != null) {
			return brickCollided;
		}
		brickCollided = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
		if(brickCollided != null) {
			return brickCollided;
		}
		brickCollided = getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS);
		if(brickCollided != null) {
			return brickCollided;
		}
		return bang;
	}
	// changing direction of ball if it has collided. Removing a brick if ball collided w a brick. 
	
	private void collision(GObject brickCollider){			
		if(brickCollider == paddle){
			// sticky pedal - need to change only to negative direction
			if(vy > 0) {
				vy = -vy;
			}		
		}
		else{
			vy = -vy;
			remove(brickCollider);
			bricksLeft--;
		}
	}
	// bouncing ball if it touches a wall 
	private void bounceBall(){
		if(ball.getX() <= 0 || ball.getX() >= WIDTH - BALL_RADIUS *2){
			vx = - vx;
		}
		if(ball.getY() <= 0){
			vy = - vy;
		}
	}
	/*  Draw setup bricks of size " NBRICK_ROW X NBRICKS_PER_ROW "  using GRect private instance object. 
		Color two rows into the same color using setCol(i);
	*/
	private void drawBricks(){
		for(int i = 0; i < NBRICK_ROWS; i++){			
			for(int j = 0; j < NBRICKS_PER_ROW; j++){
				// x, y coordinates for each brick , shifted accordingly to its position 
				double xBrick = x1Brick + j * (BRICK_WIDTH + BRICK_SEP);
				int yBrick = BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP);
				brick = new GRect(xBrick,yBrick,BRICK_WIDTH,BRICK_HEIGHT);
				brick.setFilled(true);
				setCol(i);
				add(brick);		
			}
		}	
	}
	// creating a paddle
	
	private void drawPaddle(){
		double xPaddle = (WIDTH - PADDLE_WIDTH)/2;
		double yPaddle = HEIGHT - PADDLE_Y_OFFSET;
		paddle = new GRect(xPaddle, yPaddle,PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);	
	}
	
	// Mouse Listeners method to change the position of paddle in x direction when we move mouse
	public void mouseMoved(MouseEvent event){
		double xPad = event.getX();
		if(xPad + PADDLE_WIDTH >= WIDTH ){
			xPad = WIDTH - PADDLE_WIDTH;
		}
		paddle.setLocation(xPad, HEIGHT - PADDLE_Y_OFFSET);
	}
	
	// color current brick object according to its row number
	private void setCol(int i){
		switch(i){
			case 0: case 1:
			brick.setFillColor(Color.RED);
			break;
			case 2: case 3:
			brick.setFillColor(Color.ORANGE);
			break;
			case 4: case 5:
			brick.setFillColor(Color.YELLOW);
			break;
			case 6: case 7:
			brick.setFillColor(Color.GREEN);
			break;
			case 8: case 9:
			brick.setFillColor(Color.CYAN);
			break;
		}	
	}
	
	// instance variables
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int turnsLeft = NTURNS;
	private int bricksLeft = NBRICK_ROWS*NBRICKS_PER_ROW;


}
