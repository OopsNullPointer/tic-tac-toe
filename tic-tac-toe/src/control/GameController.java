package control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.Game;
import model.GameModel;
import view.MainAreaPanel;
import view.MainWindow;
import view.ResultWindow;

public class GameController extends WindowAdapter {
	MainWindow view;
	GameModel model;
	ResultWindow resultWindow;
	
	@Override
	public void windowClosing(WindowEvent event) {
		quit();
	}
		
	public void start() {
		this.model = new GameModel(this);
		this.view = new MainWindow(this);
		this.resultWindow = new ResultWindow(this);
		this.view.addWindowListener(this);
		this.resultWindow.setVisible(false);
		this.view.setVisible(true);
	}
	
	public void quit() {		
		this.model.getPlayerCatalogue().storePlayers();                         //Stores players in a file (serialization).
		System.out.println("CIAO");		
		System.exit(0);
	}
	
	public void Done() {
		this.resultWindow = new ResultWindow(this);
		this.resultWindow.setVisible(false);
		this.view.getMainPanel().showCard(MainAreaPanel.HOF);
		this.view.getMainPanel().getHallOfFame().bestPlayers();                //Displays in gui the players from best to worst (based on score).
		this.view.getLeftPanel().getSelectPlayerBtn().setEnabled(true);
		this.view.getRightPanel().getSelectPlayerBtn().setEnabled(true);
		this.view.getTopPanel().setEnabled(true);
		this.view.getTopPanel().getAddPlayerBtn().setEnabled(true);
 		this.model.cleanSlate();                                           //Prepares for a new game, by initializing variables, for example moves.
 		this.view.getTopPanel().getStartBtn().setEnabled(true);
 		this.view.getLeftPanel().setPlayerStats(model.getPlayerStats(this.getModel().getGamePlayers()[0])); //Displays statistics of the player.
 		this.view.getLeftPanel().repaint(); 
 		this.view.getRightPanel().setPlayerStats(model.getPlayerStats(this.getModel().getGamePlayers()[1]));
 		this.view.getRightPanel().repaint();
		System.out.println("Done pressed");	
	}
		
	public void selectPlayer(String p, int pos) {
		this.model.selectPlayer(p, pos);	                                                //Calls the method to select players.
		System.out.println("Player " + pos + " set to " + p);
		System.out.println("------------------------------------------------------");
		this.view.getTopPanel().getStartBtn().setEnabled(model.ready());                   //After the players have been selectedthe game can start.
		this.view.getTopPanel().getDoneBtn().setEnabled(model.ready());
	}
	
	public void startGame() {
		this.model.setGameBoard(new int[3][3]);				
		this.view.getTopPanel().getStartBtn().setEnabled(false);
		this.view.getMainPanel().showCard(MainAreaPanel.BOARD);	
		this.view.getLeftPanel().getSelectPlayerBtn().setEnabled(model.NoPlay());   //When the game has started the user cannot select nor 
		this.view.getRightPanel().getSelectPlayerBtn().setEnabled(model.NoPlay());	//add another player.	
		this.view.getTopPanel().setEnabled(model.NoPlay());
		this.view.getTopPanel().getAddPlayerBtn().setEnabled(model.NoPlay());		
 		this.model.setGames(new Game(this,this.getModel().findPlInfo(this.getModel().getGamePlayers()[0]), this.getModel().findPlInfo(this.getModel().getGamePlayers()[1]))); //Creates new game with the selected players.
		if(this.getModel().getGamePlayers()[0].equals("Hal"))               // If Hal plays with X then he makes the first move.
		this.getModel().smartAI();
		if(this.getModel().getGamePlayers()[0].equals("Mr.Bean"))          // If Mr.Bean plays with X then he makes the first move.
		this.getModel().dumbAI();
	}
	
	public GameModel getModel() {
		return model;
	}
	
	public MainWindow getView() {
		return view;
	}

	public ResultWindow getResultWindow() {
		return resultWindow;
	}		
}
