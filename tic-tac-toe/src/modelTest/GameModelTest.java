package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.GameModel;

class GameModelTest {
	GameModel gm;
	int[][] gb ;
	String [] names;
	
	@BeforeEach
	void init() {
		gm = new GameModel(null);
		names = new String[2];
		names[0] = "Hal";
		names[1] = "person";
		gm.setGamePlayers(names);
		gb = new int[3][3];
	}
		
	@Test
	@DisplayName("the method must find if there are any moves available")
	void testFinalTable() {
		
		assertFalse(gm.anyMovesAvailable()," the gameboard must not have available moves");		
	}
	
	@Test
	@DisplayName("The smart AI must not let player win.")
	void tesAI() {
		int[] testBestMove = new int[] {0,2};
		int markOfSmartAi = 2;
		gb[0][0] = 2;     
		gb[0][1] = 2;
		gb[1][0] = 1;
		gb[1][1] = 1;

		assertEquals(gm.getBestMove(gb,markOfSmartAi)[0], testBestMove[0], " Best move should be in the [0] [2] slot of gameBoard");
		assertEquals(gm.getBestMove(gb,markOfSmartAi)[1], testBestMove[1], " Best move should be in the [0] [2] slot of gameBoard");
	}
	
	@Test
	@DisplayName("finds winner")
	void testFinalBoard() {

		gb[0][0] = 2;
		gb[0][1] = 1;
		gb[0][2] = 2;
		
		gb[1][0] = 2;
		gb[1][1] = 1;
		gb[1][2] = 0;
		
		gb[2][0] = 0;
		gb[2][1] = 1;
		gb[2][2] = 0;
		
		gm.setGameBoard(gb);
	
	  assertEquals(gm.reviewBoard(gm.getGameBoard(),1) , 1,"the winner is 0 (1)");		
	}
}