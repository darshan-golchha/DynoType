package game.type.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import game.type.model.Prey;
import game.type.service.DistanceCalculator;
import game.type.service.GameService;
import game.type.service.GameState;
import game.type.service.PlayerAnalyticsGen;
import game.type.service.TextGen;

@RestController
public class GameController {
	
	@Autowired
    private GameService gameService;
	
	
	@PostMapping("/initialize")
    public ResponseEntity<String> initializeGame(@RequestParam String username) {
        // Initialize the player with the provided details
		String sessionId = gameService.createGameSession(username);
        return ResponseEntity.ok(sessionId);
    }

    @GetMapping("/getState")
    public ResponseEntity<GameState> getGameState(@RequestParam String sessionId) {
    	// Retrieve the game state for the user identified by sessionId
        GameState gameState = gameService.getGameState(sessionId);
        if (gameState != null) {
            return ResponseEntity.ok(gameState);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/updateState")
    public ResponseEntity<GameState> updateGameState(@RequestParam String sessionId, @RequestParam int timeElapsed, @RequestParam int correct, @RequestParam int wrong) {
        // Update the game state for the user identified by sessionId
        GameState gameState = gameService.updateGameState(sessionId, timeElapsed, correct, wrong);
        if (gameState != null) {
            return ResponseEntity.ok(gameState);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/userAnalysis")
    public ResponseEntity<PlayerAnalyticsGen> getUserAnalysis(@RequestParam String sessionId, @RequestParam int timeElapsed, @RequestParam int correct, @RequestParam int wrong) {
        // Get user analytics for the user identified by sessionId
        PlayerAnalyticsGen analytics = gameService.calculateUserAnalytics(sessionId, timeElapsed, correct, wrong);
        if (analytics != null) {
            return ResponseEntity.ok(analytics);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/getText")
    public ResponseEntity<String> getRandomText(@RequestParam String sessionId) {
        // Retrieve random text for the user identified by sessionId
        String paragraph = gameService.getRandomText(sessionId);
        if (paragraph != null) {
            return ResponseEntity.ok(paragraph);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    
}
