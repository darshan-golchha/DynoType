package game.type.model;

public class Prey {
    private String username; // Player's username
    private int typingSpeed; // Typing speed (e.g., words per minute)
    private int accuracy; // Typing accuracy (percentage)
    private int level; // Player's current level in the game
    private int distance; // Distance from the chaser
    private boolean isCaught; // Flag to indicate if the player is caught by the chaser

    public Prey(String username) {
        this.username = username;
        this.typingSpeed = 0;
        this.accuracy = 100;
        this.level = 1;
        this.distance = 100; // Initialize distance to 0
        this.isCaught = false; // Player is not caught initially
    }

    // Getters and setters for attributes

    public String getUsername() {
        return username;
    }

    public int getTypingSpeed() {
        return typingSpeed;
    }

    public void setTypingSpeed(int typingSpeed) {
        this.typingSpeed = typingSpeed;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isCaught() {
        return isCaught;
    }

    public void setCaught(boolean caught) {
        isCaught = caught;
    }

    // Additional methods for player actions and game interactions can be added here
}
