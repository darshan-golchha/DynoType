package game.type.service;

import game.type.model.Prey;

public class GameSession {
    private String username;
    private GameState gameState;
    private TextGen randomText;
    private static final int initialPosition= 10;
	private static final int beginner= 1;
	private Prey player;
	private String paragraph;

    public GameSession(String username) {
    	player = new Prey(username);
        this.username = username;
        this.gameState = new GameState(initialPosition, player.getDistance(), beginner); // Initialize with default game state
        this.randomText = new TextGen(); // Generate random text for the session
        paragraph = randomText.generateRandomParagraph();
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState updateGameState(int timeElapsed, int correct, int wrong) {
        // Update the game state based on timeElapsed, correct, and wrong
    	
    	int distance = DistanceCalculator.calculateDistance(timeElapsed, correct, wrong, player.getDistance(), player);
    	player.setDistance(distance);
    	this.gameState.updateState(player);
        gameState.updateState(player);
        return gameState;
    }

    public PlayerAnalyticsGen calculateUserAnalytics(int timeElapsed, int correct, int wrong) {
        // Calculate user analytics based on the game data
        return new PlayerAnalyticsGen(timeElapsed, correct, wrong);
    }

    public String getRandomText() {
        // Return the random text for the session
        TextGen text = new TextGen();
    	paragraph = text.generateRandomParagraph();
        return paragraph;
    }
}

