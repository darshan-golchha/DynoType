package game.type.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class GameService {
	
	private Map<String, GameSession> gameSessions = new ConcurrentHashMap<>();
	
	public String createGameSession(String username) {
        // Create a new game session for the user
        String sessionId = generateSessionId();
        GameSession session = new GameSession(username);
        gameSessions.put(sessionId, session);
        return sessionId;
    }

    public GameState getGameState(String sessionId) {
        // Retrieve the game state for the user identified by sessionId
        GameSession session = gameSessions.get(sessionId);
        if (session != null) {
            return session.getGameState();
        }
        return null;
    }

    public GameState updateGameState(String sessionId, int timeElapsed, int correct, int wrong) {
        // Update the game state for the user identified by sessionId
        GameSession session = gameSessions.get(sessionId);
        if (session != null) {
            return session.updateGameState(timeElapsed, correct, wrong);
        }
        return null;
    }

    public PlayerAnalyticsGen calculateUserAnalytics(String sessionId, int timeElapsed, int correct, int wrong) {
        // Calculate user analytics for the user identified by sessionId
        GameSession session = gameSessions.get(sessionId);
        if (session != null) {
            return session.calculateUserAnalytics(timeElapsed, correct, wrong);
        }
        return null;
    }

    public String getRandomText(String sessionId) {
        // Retrieve random text for the user identified by sessionId
        GameSession session = gameSessions.get(sessionId);
        if (session != null) {
            return session.getRandomText();
        }
        return null;
    }
	
	// Utility method to generate a unique session ID (you can implement your own logic)
    private String generateSessionId() {
        return "SESSION-" + java.util.UUID.randomUUID().toString();
    }
}
