/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

    public void run() {
    	lexicon = new HangmanLexicon();
    	String word = lexicon.getWord(randy.nextInt(0,9));
		/* You fill this in */
    	setdashesLength(word);	
		playGame(word);
    }
    // Sets the length of a word that player needs to guess
    private void setdashesLength(String randomWord) {		
		for (int i = 0; i < randomWord.length(); i++){
			dashes += "-";
		}
    }
    
    
		private void playGame(String randomWord){
			canvas.reset();
			String chosenWordLowercase = randomWord.toLowerCase();
			String playerLetter = "";
			while (turns != 0 && !dashes.equals(chosenWordLowercase)){
				println("The word now looks like that: " + dashes);
				canvas.displayWord(dashes);
				println("You have " + turns + " guesses left");
				while(playerLetter.length() != 1) {
					playerLetter = readLine("Your guess: ");
				}
				checkingForLetter(playerLetter, chosenWordLowercase);
				handlingCorrectIncorectLetter(playerLetter, chosenWordLowercase);
				correctLetter = 0;
				playerLetter = "";
				}							
			}
		
		/* checks if player guessed a correct letter. Reveals all instances of that letter,
		if the guess was correct*/
		
		private void checkingForLetter(String letter, String chosenWord) {
			for (int i = 0; i < chosenWord.length(); i++){
				if(chosenWord.substring(i, i+1).equals(letter)){
					correctLetter++;
					dashes = dashes.substring(0, i) + chosenWord.substring(i, i+1) + dashes.substring(i + 1);
				}
			}
		}
		
		/* Informs a player if the guess was correct, adjusts number of turns left*/
		
		private void handlingCorrectIncorectLetter(String letter, String chosenWord) {
			if (correctLetter >= 1){
				println("That guess is correct");
				if(dashes.equals(chosenWord)) {
					canvas.displayWord(dashes);
					println("You guessed the word " + chosenWord);
					println("You win!");
					return;
				}
			}
			else {
				println("There are no " + letter + "'s in the word");
				canvas.noteIncorrectGuess(letter);
				turns--;
				if(turns == 0) {
					println("You are completely hung.");
					println("The word was " + chosenWord);
					println("You lose.");
				}
			}
		}
		
		public void init() {
			canvas = new HangmanCanvas();
			add(canvas);
		}
	
    
    /* instance vars*/
    private int turns = 8;
    private String dashes = "";
    private int correctLetter = 0;
    private HangmanLexicon lexicon;
    private RandomGenerator randy = RandomGenerator.getInstance();
    private HangmanCanvas canvas;

}
