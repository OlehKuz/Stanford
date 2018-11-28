/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		Integer[][] scoreBoard = new Integer[17][nPlayers];
		int[]score = new int[5];
		int player = 1;
		int round = 1;
		while(round <= 13) {  
			display.waitForPlayerToClickRoll(player);
			for(int i = 0; i < score.length; i++) {
				score[i] = rgen.nextInt(1, 6);
			}
			display.displayDice(score);
			for(int j = 0; j < nRollsAllowed; j++) {
				display.waitForPlayerToSelectDice();
				for(int i = 0; i < score.length; i++) {
					if(display.isDieSelected(i)) {
						score[i] = rgen.nextInt(1, 6);
					}
				}
				display.displayDice(score);
			}
			int category = display.waitForPlayerToSelectCategory();
			while(scoreBoard[category-1][player-1]!=null) {		
				display.printMessage("You already have used that category. Select an unused one!");
				category = display.waitForPlayerToSelectCategory();				
			}
			boolean p = checkCategory(score, category); 
			if(p) {
				int points = calculateScore(score, category);
				display.updateScorecard(category, player, points);
				scoreBoard[category-1][player-1] = points;
			}
			else if(!p) {
				display.updateScorecard(category, player, 0);
				scoreBoard[category-1][player-1] = 0;
			}
			player++;
			if(player>nPlayers) {
				round ++;
				player = 1;
				
			}
		}
		calculateUpperScore(scoreBoard);
		calculateLowerScore(scoreBoard);
		for(int p = 1; p < nPlayers+1; p++) {
			display.updateScorecard(UPPER_SCORE, p, scoreBoard[UPPER_SCORE - 1][p-1]);
			display.updateScorecard(UPPER_BONUS, p, scoreBoard[UPPER_BONUS - 1][p-1]);
			display.updateScorecard(LOWER_SCORE, p, scoreBoard[LOWER_SCORE - 1][p-1]);
			display.updateScorecard(TOTAL, p, scoreBoard[TOTAL - 1][p-1]);
		}
		display.printMessage("Game over");
	}
	
	private boolean checkCategory(int[]score, int category) {
		boolean p = false;
		int unique;
		switch(category) {
			case ONES:case TWOS:case THREES:case FOURS:case FIVES:case SIXES:case CHANCE:
				p = true;
				break;	
			case YAHTZEE:
				unique = countUnique(score);
				if(unique==1) {
					p = true;					
				}
				break;
			case THREE_OF_A_KIND:
				unique = countUnique(score);
				if(unique<=3) {
					p = true;					
				}
				break;
			case FOUR_OF_A_KIND:
				unique = countUnique(score);
				if(unique<=2) {
					p = true;					
				}
				break;
			case FULL_HOUSE:
				unique = countUnique(score);
				if(unique==2) {
					int count = 1;
					int digit = score[0];
					for(int n = 1; n < score.length; n++) {
						if(digit == score[n]){
							count++;
						}
					}
					if(count == 2 || count == 3) {
						p = true;
					}
				}
				break;
			case SMALL_STRAIGHT: 
			case LARGE_STRAIGHT: 
				p = YahtzeeMagicStub.checkCategory(score, category);
				break;
		}
		return p;
	}
	

public int countUnique(int[] array){
    int count = 0;
    if(array.length > 0){   
        count = 1;
        for(int i = 1; i < array.length; i++){
            int copy = 0;
            for(int j = i - 1; j >= 0; j--){
                if(array[i] == array[j]){
                    copy++;                   
                }
            }
            if (copy == 0){
                count++;
            }
        }        
    }
    return count;
}
	
	private void calculateLowerScore(Integer[][] scoreBoard) {
		int score = 0;
		for(int plr = 0; plr < nPlayers; plr++) {
			for(int row = THREE_OF_A_KIND - 1; row < CHANCE; row++) {
				score+=scoreBoard[row][plr];
			}
			scoreBoard[LOWER_SCORE - 1][plr] = score;
			scoreBoard[TOTAL - 1][plr] = scoreBoard[LOWER_SCORE - 1][plr] + 
					scoreBoard[UPPER_SCORE - 1][plr] + scoreBoard[UPPER_BONUS - 1][plr];
			score = 0;
		}
	}
	
	
	private void calculateUpperScore(Integer[][] scoreBoard) {
		int score = 0;
		for(int plr = 0; plr < nPlayers; plr++) {
			for(int row = 0; row < SIXES; row++) {
				score+=scoreBoard[row][plr];
			}
			scoreBoard[UPPER_SCORE - 1][plr] = score;
			if(score>=63) {
				scoreBoard[UPPER_BONUS - 1][plr] = 35;
			}
			else if(score<63) {
				scoreBoard[UPPER_BONUS - 1][plr] = 0;
			}
			score = 0;
		}
	}
	
	private int calculateScore (int[] array, int cg){
		int points = 0;
		switch(cg) {
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				for(int p : array) {
					if(p == cg) points+=cg;
				};
				break;
		
			case THREE_OF_A_KIND:
			case FOUR_OF_A_KIND:
			case CHANCE:
				for(int p : array) points+=p; break;
			case FULL_HOUSE: points = 25; break;
			case SMALL_STRAIGHT: points = 30; break;
			case LARGE_STRAIGHT: points= 40; break;
			case YAHTZEE: points = 50; break;		
		}
		return points;
	}
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private static final int nRollsAllowed = 2;

}
