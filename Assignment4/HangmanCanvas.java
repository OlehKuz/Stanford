/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		double y = getHeight()/5;
		double x = getWidth()/2 - getWidth()*BEAM_LENGTH;
		GLine scaffold = new GLine(x,y, x, y + getHeight()*SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine(x, y, x + getWidth()*BEAM_LENGTH, y);
		add(beam);
		GLine rope = new GLine(x + getWidth()*BEAM_LENGTH, y, x + getWidth()*BEAM_LENGTH, y + getHeight()*ROPE_LENGTH);
		add(rope);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		// remove previous GLabel, to avoid overlapping old and new letters
		if(guessed != null) {
			remove(guessed);
		}
		if(wrongLetters != null) {
			remove(wrongLetters);
		} 
		guessed = new GLabel(word);
		guessed.setFont("SansSerif-42");
		double x = (getWidth() - guessed.getWidth())/2;
		double y = getHeight() - getHeight() / 7;
		add(guessed, x, y);
		wrongLetters = new GLabel(incorrectGuesses, x, getHeight() - getHeight()/11);
		wrongLetters.setFont("SansSerif-24");
		add(wrongLetters);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	
	private void head() {
		GOval head = new GOval(getWidth()/2 - HEAD_RADIUS * getHeight(), 
		getHeight()/5 + getHeight()*ROPE_LENGTH,
		2*HEAD_RADIUS * getHeight(),
		2*HEAD_RADIUS * getHeight());
		add(head);
	}
	
	private void body(double bodyY1Coord, double bodyY2Coord) {
		GLine body = new GLine(getWidth()/2, bodyY1Coord, getWidth()/2,bodyY2Coord);
		add(body);
	}
	
	private void leftArm(double bodyY1Coord) {
		GCompound leftArm = new GCompound();
		GLine leftUpperArm = new GLine(0,0,getWidth()*UPPER_ARM_LENGTH,0);
		leftArm.add(leftUpperArm);
		GLine leftLowerArm = new GLine(0,0,0,getHeight()*LOWER_ARM_LENGTH);
		leftArm.add(leftLowerArm);
		leftArm.setLocation(getWidth()/2-getWidth()*UPPER_ARM_LENGTH,
				ARM_OFFSET_FROM_HEAD*getHeight()+bodyY1Coord);
		add(leftArm);
	}
	
	private void rightArm(double bodyY1Coord) {
		GCompound rightArm = new GCompound();
		GLine rightUpperArm = new GLine(0,0,getWidth()*UPPER_ARM_LENGTH,0);
		rightArm.add(rightUpperArm);
		GLine rightLowerArm = new GLine(getWidth()*UPPER_ARM_LENGTH,0,
				getWidth()*UPPER_ARM_LENGTH,getHeight()*LOWER_ARM_LENGTH);
		rightArm.add(rightLowerArm);
		rightArm.setLocation(getWidth()/2, ARM_OFFSET_FROM_HEAD*getHeight()+bodyY1Coord);
		add(rightArm);
	}
	
	private void leftLeg(double bodyY2Coord) {
	//hip and left leg
		GCompound leftLeg = new GCompound();
		GLine hip = new GLine(0, 0, getWidth()*HIP_WIDTH, 0);
		leftLeg.add(hip);
		GLine leg = new GLine(0, 0, 0, LEG_LENGTH*getHeight());
		leftLeg.add(leg);
		leftLeg.setLocation(getWidth()/2 - getWidth()*HIP_WIDTH/2, bodyY2Coord);
		add(leftLeg);
	}
	
	private void rightLeg(double bodyY2Coord) {
		GLine rightLeg = new GLine (getWidth()/2 + getWidth()*HIP_WIDTH/2,bodyY2Coord,
		getWidth()/2 + getWidth()*HIP_WIDTH/2, bodyY2Coord +LEG_LENGTH*getHeight());
		add(rightLeg);
	}
	
	private void leftFoot(double bodyY2Coord) {
		GLine leftFoot = new GLine(getWidth()/2 - getWidth()*HIP_WIDTH/2 - FOOT_LENGTH*getWidth(),
		bodyY2Coord +LEG_LENGTH*getHeight(),
		getWidth()/2 - getWidth()*HIP_WIDTH/2,
		bodyY2Coord +LEG_LENGTH*getHeight());
		add(leftFoot);
	}
	
	private void rightFoot(double bodyY2Coord) {
		GLine rightFoot = new GLine (getWidth()/2 + getWidth()*HIP_WIDTH/2,
		bodyY2Coord +LEG_LENGTH*getHeight(),
		getWidth()/2 + getWidth()*HIP_WIDTH/2 + FOOT_LENGTH*getWidth(),
		bodyY2Coord +LEG_LENGTH*getHeight());
		add(rightFoot);
	}
	public void noteIncorrectGuess(String letter) {
		// shows the next body part 
		/* for some reason if i make bodyY1 and bodyY2 private static variables,
		 * instead of passing their values as a parameter to the appropriate methods,   
		   it doesnt read those variables correctly  when i use them in methods of building some body part,
		   all body parts are getting added at the very top of a window, a body is not added at all
		*/
		double bodyY1 = getHeight()/5 + getHeight()*ROPE_LENGTH + HEAD_RADIUS * 2*getHeight();
		double bodyY2 = getHeight()/5 + getHeight()*ROPE_LENGTH + 2*HEAD_RADIUS * getHeight()+BODY_LENGTH*getHeight();	
		
		switch(counter) {
		case 0: head(); break;
		case 1: body(bodyY1,bodyY2); break;
		case 2: leftArm(bodyY1); break;
		case 3: rightArm(bodyY1); break;
		case 4: leftLeg(bodyY2); break;
		case 5: rightLeg(bodyY2); break;
		case 6: leftFoot(bodyY2); break;
		case 7: rightFoot(bodyY2); break;
		}		
		counter++;
		
		// list of incorrect guesses
		incorrectGuesses += letter;
		
	}
	


/* Constants for the simple version of the picture (in pixels) */
	private static final double SCAFFOLD_HEIGHT = 0.48;
	private static final double BEAM_LENGTH = .29;
	private static final double ROPE_LENGTH = .04;
	private static final double HEAD_RADIUS = .06;
	private static final double BODY_LENGTH = 0.16;
	private static final double ARM_OFFSET_FROM_HEAD = 0.032;
	private static final double UPPER_ARM_LENGTH = 0.145;
	private static final double LOWER_ARM_LENGTH = .05;
	private static final double HIP_WIDTH = .1;
	private static final double LEG_LENGTH = .143;
	private static final double FOOT_LENGTH = .056;
	private int counter = 0;
	private String incorrectGuesses = "";
	private GLabel guessed;
	private GLabel wrongLetters;

}
