package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.Player;

class PlayerTest {	
	Player player;
		
	@BeforeEach
	void init() {
		player = new Player("TestPlayer");
		player.setWins(3);
		player.setTies(1);
		player.setSumOfGames(7);
	}
	
	@Test
	@DisplayName("New Player must have correct score")
	void testGetPlScre() {
		assertEquals(player.calculateScore(), 50,"Score should be 50");
	}
}