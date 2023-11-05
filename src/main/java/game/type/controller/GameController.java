package game.type.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import game.type.model.Prey;
import game.type.service.DistanceCalculator;
import game.type.service.GameState;
import game.type.service.PlayerAnalyticsGen;
import game.type.service.TextGen;

@RestController
public class GameController {
	private Prey player;
	private GameState gameState;
	private static final int initialPosition= 10;
	private static final int beginner= 1;
	private TextGen text;
	private String paragraph;
	
	
	@PostMapping("/initialize")
    public ResponseEntity<String> initializeGame(@RequestParam String username) {
        // Initialize the player with the provided details
        this.player = new Prey(username);
        this.gameState = new GameState(initialPosition, player.getDistance(), beginner);
        this.text = new TextGen();
        paragraph = text.generateRandomParagraph();
        return ResponseEntity.ok("Game initialized for " + username);
    }

    @GetMapping("/getState")
    public ResponseEntity<GameState> getGameState() {
        // Return the current game state, including player details
        if (player != null) {
            return ResponseEntity.ok(this.gameState);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/updateState")
    public ResponseEntity<GameState> setGameState
    (@RequestParam int timeElapsed, @RequestParam int correct, int wrong) {
    	// Return the current game state, including player details
        if (player != null) {
        	int distance = DistanceCalculator.calculateDistance(timeElapsed, correct, wrong, player.getDistance(), player);
        	player.setDistance(distance);
        	this.gameState.updateState(player);
        	paragraph = text.generateRandomParagraph();
            return ResponseEntity.ok(this.gameState);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/userAnalysis")
    public ResponseEntity<PlayerAnalyticsGen> getUserAnalysis(@RequestParam int timeElapsed, @RequestParam int correct, int wrong){
    	if (player !=null) {
    		return ResponseEntity.ok(new PlayerAnalyticsGen(timeElapsed, correct, wrong));
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
    
    @GetMapping("/getText")
    public ResponseEntity<String> randomTextGen(){
    	if (this.paragraph != null) {
    		return ResponseEntity.ok(paragraph);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
    
    
}
