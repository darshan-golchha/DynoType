package game.type.service;

import game.type.model.Prey;

public class DistanceCalculator {
	
	public static int calculateDistance(int timeElapsed, int numCorrect, int numWrong, int prevDistance, Prey player) {
	    // Calculate the words per second (WPS) based on time elapsed and correct words
	    // double wordsPerSecond = numCorrect / (double) timeElapsed;

	    // Deduct distance for incorrect words
	    int distanceDeduction = numWrong * 30; // Adjust the deduction rate as needed

	    // Increment distance for correct words
	    int distanceIncrement = numCorrect * 15;
	    int totalWords = numCorrect+numWrong;
	    
	    int timeDecrement = getTimeDecrement(timeElapsed, totalWords, getLevelTime(player))*10;

	    // Calculate the final distance
	    int distance =  prevDistance - distanceDeduction + distanceIncrement- timeDecrement;
	    
	    // Ensure that the distance is not negative
	    if (distance < 0) {
	        distance = 0;
	    }
	    
	    return distance;
	}
	
	private static int getLevelTime(Prey player) {
		if (player.getLevel() == 1) {
			return 13;
		}
		else if (player.getLevel() == 2) {
			return 9;
		}
		
		else if (player.getLevel() == 3) {
			return 7;
		}
		else return 4;
	}

	private static int getTimeDecrement(int timeElapsed, int targetWordCount, int levelTime) {
	    // Calculate the expected time for the target word count
	    int expectedTime = (targetWordCount * levelTime) / 15; // 15 words in 13 seconds
	    
	    // Check if the player's timeElapsed exceeds the expected time for the same word count
	    if (timeElapsed > expectedTime) {
	        // There was idle time, so decrement the timeElapsed
	        int idleTime = timeElapsed - expectedTime;
	        return idleTime;
	    }

	    // No time decrement is needed
	    return 0;
	}
}

