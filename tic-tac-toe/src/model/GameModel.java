package model;

import control.GameController;
import java.util.Random;
import javax.swing.Timer;
import model.GameModel;

public class GameModel {	
	public static final int MAX_NUM_OF_GAMES = 100;
	private static final int MAX_DEPTH = 6; //Calculates 6 moves ahead.
	private boolean play;
	private int[][] gameBoard;
	private int moves;	
	private int numOfGames;	
	private PlayersCatalogue  playerCatalogue;
	private GameController gc;
	private String [] gamePlayers;	
	private Game [] games;	
	private Boolean mover;	
	private Timer aiTimer;
			
	public GameModel(GameController gc) {
		this.gc=gc;
		gamePlayers = new String[2];
		gameBoard= null;
		playerCatalogue= new PlayersCatalogue();
		mover=true;
		moves=0;
		games = new Game[MAX_NUM_OF_GAMES];
		numOfGames=0;
		play = true;
	}
	
	//Prepares for a new game, by initializing variables.
	public void cleanSlate() {
		gameBoard= null;
		mover=true;
		moves=0;
		play = true;
	}
	
	public void smartAI() {
	if(anyMovesAvailable()) {
		int[] beastMove;
		int markOfSmartAI = 0;
		if(gamePlayers[0].equals("Hal")) {                             //Checks whether payer Hal plays with 'O' (1) or 'X' (2).
			markOfSmartAI = 2;                                             
		}else if(gamePlayers[1].equals("Hal"))
			markOfSmartAI = 1;
			beastMove = getBestMove(gameBoard, markOfSmartAI);	       //Calculates best move according to gameBoard.				
			gameBoard[beastMove[0]][beastMove[1]] = getMoverMark();    //Plays best move.
			mover=!mover;
			moves++;
			gc.getView().repaint();				                       //Paints the boardCell.
			findsWinner();	                                           //Checks for Winner.
			findsTie();	                                               //Checks for Tie.
			if(gamePlayers[0].equals("Mr.Bean") || gamePlayers[1].equals("Mr.Bean") ) {  //If the two AIs play together then this ensures that there is
				aiTimer = new Timer(1000, (evt) -> dumbAI());                            // time between their moves.
				aiTimer.setRepeats(false);
				aiTimer.restart();
			}
		}
	}
		
	public  int miniMax(int [][] board, int depth, boolean isMax, int markOfAI) {       
	       int boardVal = reviewBoard(board, markOfAI);
	        if (Math.abs(boardVal) == 1 || depth == 0   || !anyMovesAvailable()) {
	            return boardVal;
	        }
	        if (isMax) { //Maximazes boardValue for smartAi.
	            int highestVal = Integer.MIN_VALUE;      
	            for (int row = 0; row < 3; row++) {
	                for (int col = 0; col < 3; col++) {
						if(board[row][col]==0) {
							if(markOfAI == 2) {
								board[row][col] = 2;    
		                        highestVal = Math.max(highestVal, miniMax(board, depth - 1, false, markOfAI));  
		                        board[row][col] = 0;
							}
							else if(markOfAI == 1) {
								board[row][col] = 1;
		                        highestVal = Math.max(highestVal, miniMax(board, depth - 1, false, markOfAI));
		                        board[row][col] = 0;
							}						
	                    }
	                }
	            }
	            return highestVal;
	            // Minimising player, find the minimum boardValue for opponent.
	        } else {
	            int lowestVal = Integer.MAX_VALUE;
	            for (int row = 0; row < 3; row++) {
	                for (int col = 0; col <3; col++) {
						if(board[row][col]==0) {
							if(markOfAI == 2) {
								board[row][col] = 1;
		                        lowestVal = Math.min(lowestVal, miniMax(board, depth - 1, true, markOfAI));
		                        board[row][col] = 0;
							}
							if(markOfAI == 1) {
								board[row][col] = 2;
		                        lowestVal = Math.min(lowestVal, miniMax(board, depth - 1, true, markOfAI));
		                        board[row][col] = 0;
							}
	                    }
	                }
	            }
	            return lowestVal;
	        }
	    }

	//Finds and returns coordinates of the boardCell that has the bestMove.
	public int[] getBestMove(int [][] board, int hiMark) {
	        int[] bestMove = new int[]{-1, -1};
	        int bestValue = Integer.MIN_VALUE;

	        for (int row = 0; row <3; row++) {
	            for (int col = 0; col < 3; col++) {
	            	if(board[row][col] == 0) {	                   
	                    board[row][col] = hiMark;
	                    int moveValue = miniMax(board, MAX_DEPTH, false, hiMark);
	                    board[row][col] = 0;
	                    if (moveValue > bestValue) {
	                        bestMove[0] = row;
	                        bestMove[1] = col;
	                        bestValue = moveValue;
	                    }
	                }
	            }
	        }
	        return bestMove; 
	 }
	
   //Returns 1 if smartAI wins, 0 if it is a tie and -1 if smartAI loses
	public int reviewBoard(int [][] board, int markOfSmartAI) {      
		for(int j = 0; j < 3; j++) {
			if(board[0][j]!=0 && board[0][j]==(board[1][j]) && board[0][j]==(board[2][j])) {
				if(board[0][j]==markOfSmartAI) {
					return 1;
				}
				return -1;
			}	
		}	
		for(int i = 0; i < 3; i ++) {
			if( board[i][0]!=0 && board[i][0]==(board[i][1]) && board[i][0]==(board[i][2])) {
				if(board[i][0]==markOfSmartAI) {
					return 1;
				}
				return -1;
			}	
		}
		
		if( board[0][0]!=0 && board[0][0]==(board[1][1]) && board[1][1]==(board[2][2])) {
			if(board[0][0]==markOfSmartAI) {
					return 1;
			}
			return -1;
		}
		
		else if(board[2][0]!=0 && board[2][0]==(board[1][1]) && board[1][1]==(board[0][2])) {
			if(board[2][0]==markOfSmartAI) {
				return 1;
			}
		return -1;
		}
		
		return 0;
	}
	
	//Checks if there are any moves available.
	public boolean anyMovesAvailable() {
		try {
			for(int i = 0; i < 3; i++) {		
				for(int j = 0; j < 3; j++) {
					if(gameBoard[i][j] == 0)
						return true;
					}	
				}				
			}catch(NullPointerException e) {
				System.out.println("The game between the two AIs has ended before its time");
		}				
		return false;
	}	
	
	//Makes random moves and checks for winner and tie.
	public void dumbAI(){
		if (anyMovesAvailable()) {
			Random rand = new Random();
			boolean n = true;
	    	while (n) {
				int i = rand.nextInt(3) ; 
				int j = rand.nextInt(3);
				if(gameBoard[i][j]==0) {
					gameBoard[i][j]=getMoverMark();
					mover=!mover;
					moves++;
					gc.getView().repaint();				
					findsWinner();	
					findsTie();
					if(gamePlayers[0].equals("Hal") || gamePlayers[1].equals("Hal") ) {
						aiTimer = new Timer(1000, (evt) -> smartAI());
						aiTimer.setRepeats(false);
						aiTimer.restart();
					}
					return;
				}					
			}
		}
	}
 
	//Checks for tie and displays a window with the result.
	public String findsTie() {
		if(play && moves > 8  && gameBoard != null && findsWinner() == 0) {
			gc.getResultWindow().setGameBoard(0);
			gc.getResultWindow().setResult(0);
			gc.getResultWindow().setResultWindow(gc);
			gc.getResultWindow().setVisible(true);
			savesGameData(0);                                             //sets player and game with the gameData.
			play = false;
			setNonPlayableCell();
			return "tie";
		}
		return null;			
	}
	
	//Checks for winner and displays a window with the result.
	public int findsWinner() {
			for(int j = 0; j < 3; j++) {
				if(play && gameBoard[0][j]!=0 && gameBoard[0][j]==(gameBoard[1][j]) && gameBoard[0][j]==(gameBoard[2][j])) {
					play = false;
					gc.getResultWindow().setGameBoard(gameBoard[0][j]);
					gc.getResultWindow().setResult(1);
					gc.getResultWindow().setResultWindow(gc);
					gc.getResultWindow().setVisible(true);
					savesGameData(gameBoard[0][j]);                      //sets player and game with the gameData.
					setNonPlayableCell();
					return gameBoard[0][j];
				}	
				for(int i = 0; i < 3; i ++) {
					if(play && gameBoard[i][0]!=0 && gameBoard[i][0]==(gameBoard[i][1]) && gameBoard[i][0]==(gameBoard[i][2])) {
						play = false;
						gc.getResultWindow().setGameBoard(gameBoard[i][0]);
						gc.getResultWindow().setResult(1);
						gc.getResultWindow().setResultWindow(gc);
						gc.getResultWindow().setVisible(true);
						savesGameData(gameBoard[i][1]);
						setNonPlayableCell();
						return gameBoard[i][0];
					}	
				}
					if(play && gameBoard[0][0]!=0 && gameBoard[0][0]==(gameBoard[1][1]) && gameBoard[1][1]==(gameBoard[2][2])) {
						gc.getResultWindow().setGameBoard(gameBoard[0][0]);
						gc.getResultWindow().setResult(1);
						gc.getResultWindow().setResultWindow(gc);
						gc.getResultWindow().setVisible(true);
						savesGameData(gameBoard[0][0]);
						setNonPlayableCell();
						play = false;
						return  gameBoard[0][0];
					}
					else if(play && gameBoard[2][0]!=0 && gameBoard[2][0]==(gameBoard[1][1]) && gameBoard[1][1]==(gameBoard[0][2])) {
						gc.getResultWindow().setGameBoard(gameBoard[1][1]);
						gc.getResultWindow().setResult(1);
						gc.getResultWindow().setResultWindow(gc);
						gc.getResultWindow().setVisible(true);
						savesGameData(gameBoard[1][1]);
						setNonPlayableCell();
						play = false;
						return  gameBoard[1][1];
					}
		    	}	
			return 0;
	}
	
	public void savesGameData(int mark) {
		Player p1 =findPlInfo(gamePlayers[0]);
		Player p2 =findPlInfo(gamePlayers[1]);		
		if(mark==2) {
			games[numOfGames-1] = new Game(p1, p2, findPlInfo(gamePlayers[0]).getScore(), findPlInfo(gamePlayers[1]).getScore(), p1, p2, "win-lose", games[numOfGames-1].getStartTime());//Saves all the data of the current game.
			p1.setGames(games[numOfGames-1]);                   //Saves game in player
			p2.setGames(games[numOfGames-1]);                   //Saves game in player
			p1.setWins(p1.getWins()+1);
			p1.setSumOfGames(p1.getSumOfGames()+1);			
			p2.setLoses(p2.getLoses()+1);
			p2.setSumOfGames(p2.getSumOfGames()+1);			
		}else if(mark==1) {
			games[numOfGames-1] = new Game(p1, p2, findPlInfo(gamePlayers[0]).getScore(), findPlInfo(gamePlayers[1]).getScore(), p2, p1, "win-lose", games[numOfGames-1].getStartTime());
			p1.setGames(games[numOfGames-1]);
			p2.setGames(games[numOfGames-1]);
			p2.setWins(p2.getWins()+1);
			p2.setSumOfGames(p2.getSumOfGames()+1);		
			p1.setLoses(p1.getLoses()+1);
			p1.setSumOfGames(p1.getSumOfGames()+1);			
		}else if (mark==0) {
			games[numOfGames-1] = new Game(p1, p2, findPlInfo(gamePlayers[0]).getScore(), findPlInfo(gamePlayers[1]).getScore(), null, null, "tie", games[numOfGames-1].getStartTime());
			p1.setGames(games[numOfGames-1]);
			p2.setGames(games[numOfGames-1]);
			p1.setTies(p1.getTies()+1);
			p1.setSumOfGames(p1.getSumOfGames()+1);			
			p2.setTies(p2.getTies()+1);
			p2.setSumOfGames(p2.getSumOfGames()+1);			
		}
		p2.recentGames();
		p1.recentGames();
		p2.topGames();
		p1.topGames();
		play = false;
	}
	
	public void setNonPlayableCell(){
		for(int k=0; k<3; k++) {
			for(int l=0; l<3; l++) {
				if(gameBoard[k][l]==0){
						gameBoard[k][l]=3;
				}
			}
		}
	}
	
	public void selectPlayer(String player, int pos) {
		if (pos<0 && pos>1)return;
		gamePlayers[pos]=player;		
	}
		
	public boolean ready() {
		return (gamePlayers[0] != null && gamePlayers[1] !=null);
	}
		
	public void startGame() {
		gameBoard= new int[3][3];
	}
		
	public boolean inPlay() {
		return gameBoard !=null && moves <9;
	}
		
	public boolean NoPlay() {
		return !inPlay();
	}
		
	public int getMover() {
		return mover.compareTo(false);
	}
		
	public String[] getGamePlayers() {
		return gamePlayers;
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}
		
	public void checkDimValidity(int row, int col) {
		if (row <0 || col < 0 || row > 2 || col >2) {
			throw new IndexOutOfBoundsException(row + ","+col +" is not a valid board cell");
		}
	}
	
	public void checkMoveValidity(int row, int col) {
		checkDimValidity(row, col);
		if (gameBoard[row][col]!=0 ) {
			throw new IllegalArgumentException("Non playable cell");
		}
	}
	
	public int getBoardMark(int row, int col) {
		checkDimValidity(row, col);
		return gameBoard[row][col];
	}
	
	public void setGameBoard(int[][] gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public PlayersCatalogue getPlayerCatalogue() {
 		return playerCatalogue;
	}
	
	public void setPlayerCatalogue(PlayersCatalogue playerCatalogue) {
		this.playerCatalogue = playerCatalogue;
	}
		
	//Plays the next move.
	public void makeMove(int row, int col) {
		checkMoveValidity(row, col);
		gameBoard[row][col]=getMoverMark();
		mover=!mover;
		moves++;
		findsWinner();	
		findsTie();
	}
	
	public int getMoverMark() {
		return mover? 2 : 1;
	}
	
	//Searches players and finds (and returns) player's information based on their name.
	public Player findPlInfo(String s) {
		for(int i = 0; i < playerCatalogue.getCount(); i++)
			if(playerCatalogue.getPlayers()[i].getPlName() !=null && playerCatalogue.getPlayers()[i].getPlName().equals(s)){
				return playerCatalogue.getPlayers()[i];
			}
		return null;
	}
	
	//This method returns a string of player's statistics.
	public String getPlayerStats(String player) {
		Player p= findPlInfo(player);
		if(p.getSumOfGames()==0) {
			
			StringBuilder sb = new StringBuilder("");
			sb.append(player).append("\n\n\n");
			sb.append("Total:").append("\t").append(p.getSumOfGames()).append("\n");
			sb.append("Won:").append("\t").append( "0").append("%").append("\n");
			sb.append("Lost:").append("\t").append("0").append("%").append("\n\n");			
			sb.append("Score:").append("\t").append("0").append("\n");
			sb.append("Recent Score:").append("\t").append("--").append("\n\n");
			sb.append("Recent Games:     ").append("  --").append("\n\n\n");
			sb.append("Top Games:").append("\t").append("--").append("\n\n\n");
			return sb.toString();
		}
		int winsPRC =( p.getWins()*100)/p.getSumOfGames();
		int losesPRC =( p.getLoses()*100)/p.getSumOfGames();
		
		StringBuilder sb = new StringBuilder("");
		sb.append(player).append("\n\n");
		sb.append("Total:").append("\t").append(p.getSumOfGames()).append("\n");
		sb.append("Won:").append("\t").append( winsPRC).append("%").append("\n");
		sb.append("Lost:").append("\t").append(losesPRC).append("%").append("\n\n");	
		sb.append("Score:").append("\t").append(p.calculateScore()).append("\n");
		sb.append("Recent Score:").append("\t").append(p.calculateRecentScore()).append("\n\n");
		sb.append("Recent Games:").append("\n");					
			
		for(int i=0; i<p.getRecentGames().size() ; i++) {			
			sb.append(" • "+p.getRecentGames().get(i).getPlayer1().getPlName()).append(" VS ").append(p.getRecentGames().get(i).getPlayer2().getPlName()).append("\n");								
		}
	
		sb.append("\n").append("Top Games:").append("\n");
			
		for(int i=0; i<p.getTopGames().size()  ; i++) {			
			sb.append(" • "+p.getTopGames().get(i).getPlayer1().getPlName()).append(" VS ").append(p.getTopGames().get(i).getPlayer2().getPlName()).append("\n");								
		}

		return sb.toString();			
	}

	public void setGames(Game game) {
		this.games[numOfGames] = game;
		numOfGames++;
	}
	
	public void setGamePlayers(String[] gamePlayers) {
		this.gamePlayers = gamePlayers;
	}		
}