package modelTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import control.GameController;
import model.Game;
import model.Player;
import model.PlayersCatalogue;

class TopGamesTest {
	Player [] testPlayers;
	PlayersCatalogue pc;
	GameController gc;
	Game[] testGames;
	
	@BeforeEach
	void init() {                                                                //Initializing players information.
		gc= new GameController();
		pc = new PlayersCatalogue();
		testPlayers = new Player[3];
		testGames= new Game[6];		
		testPlayers[0]= new Player("David");
		testPlayers[1]= new Player("Mike");
		testPlayers[2]= new Player("Maria");
	//
		pc.setPlayers(testPlayers);
		
		testGames[0]=new Game(gc, testPlayers[0],testPlayers[1]);//5
		testGames[0].setWinner(testPlayers[0]);
		testGames[0].setLoser(testPlayers[1]);
		testGames[0].setScorePl1(100);
		testGames[0].setScorePl2(80);
		
		testGames[1]=new Game(gc, testPlayers[2],testPlayers[1]);//6
		testGames[1].setWinner(testPlayers[2]);
		testGames[1].setLoser(testPlayers[1]);
		testGames[1].setScorePl1(20);
		testGames[1].setScorePl2(80);
		
		testGames[2]=new Game(gc, testPlayers[0],testPlayers[1]);//1
		testGames[2].setWinner(testPlayers[1]);
		testGames[2].setLoser(testPlayers[0]);
		testGames[2].setScorePl1(100);
		testGames[2].setScorePl2(80);
		
		testGames[3]=new Game(gc, testPlayers[2],testPlayers[1]);//2
		testGames[3].setWinner(testPlayers[1]);
		testGames[3].setLoser(testPlayers[2]);
		testGames[3].setScorePl1(20);
		testGames[3].setScorePl2(80);
		
		testGames[4]=new Game(gc, testPlayers[0],testPlayers[1]);//3
		testGames[4].setResult("tie");
		testGames[4].setScorePl1(100);
		testGames[4].setScorePl2(80);
		
		testGames[5]=new Game(gc, testPlayers[2],testPlayers[1]);//4
		testGames[5].setResult("tie");
		testGames[5].setScorePl1(20);
		testGames[5].setScorePl2(80);		
	}
		
	@Test
	@DisplayName("The method must find top games")
	void testTopGames() {
		testPlayers[1].setGames(testGames);      //We are testing Mike's top 5 games.
				
		assertEquals(testPlayers[1].topGames().get(0), testGames[2] , " the best game is Mike winning David");
		assertEquals(testPlayers[1].topGames().get(1), testGames[3] , " the second best game is Mike winning Maria");
		assertEquals(testPlayers[1].topGames().get(2), testGames[4] , " the third best game is the tie between Mike and David (David has better score at the time of the game than Maria)");
		assertEquals(testPlayers[1].topGames().get(3), testGames[5] , " the fourth best game is the tie between Mike and Maria");
		assertEquals(testPlayers[1].topGames().get(4), testGames[0] , " the worst game is Mike losing to Maria");
	}
}