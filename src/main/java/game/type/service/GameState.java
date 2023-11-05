package game.type.service;

import game.type.model.Prey;

public class GameState {
    private int preyPosition;   // Prey's position
    private int distance;      // Distance between chaser and prey
    private int playerProgress; // Player's progress, e.g., score or level

    public GameState(int initialPreyPosition, int initialDistance, int initialProgress) {
        this.preyPosition = initialPreyPosition;
        this.distance = initialDistance;
        this.playerProgress = initialProgress;
    }

    public int getPreyPosition() {
        return preyPosition;
    }
    
    public void updateState(Prey player) {
        int distance = player.getDistance(); // Get the distance from the player object

        // Scale the distance on a scale of 100 and assign a position state on a scale of 10
        int scaledDistance = distance / 10; // Scale to a range of 0 to 10
        scaledDistance = Math.max(0, Math.min(scaledDistance, 10)); // Ensure it's within [0, 10]

        // Update the player's position state
        this.setPosition(scaledDistance);
        this.setDistance(player.getDistance());
        this.setPlayerProgress(player.getLevel());
    }
    
    private void setPosition(int position) {
    	this.preyPosition = position;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPlayerProgress() {
        return playerProgress;
    }

    public void setPlayerProgress(int playerProgress) {
        this.playerProgress = playerProgress;
    }
}

