package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Comparable<Player> , Serializable{	
	private float score;	
	private float recentScore;
	private int sumOfGames;
	private int numOfGames1;
	private int wins;
	private int ties;
	private int loses;
	private ChartList <Game> listOfGames;
	private ChartList <Game> recentGames;
	private ChartList <Game> topGames;
	private String plName;
	private Game [] games;
		
	public Player(String plName) {
		games = new Game[100];	//Every player can play 100 games.
		this.plName = plName;
		sumOfGames = 0;
		score = 0;
		wins = 0;
		ties = 0;
		loses = 0;		
	}
	
	//Finds top 5 Games and return a list of them (best to worst based on the algorithm that was provided).
	public ChartList<Game>  topGames(){
		topGames = new ChartList<Game>(5); 
		listOfGames = new  ChartList<>(100);
		listOfGames.addAll(games);
		
		    for (int i = 0; i < 5; i ++) {
		        int maxIndex = 0;	        
		        if(!listOfGames.isEmpty()) {
		        	for (int j = 1; j <listOfGames.size(); j++) {
		        		 {
		        			maxIndex = findsBestGame(j,maxIndex,listOfGames);
		        		}	
		        	}
		        	topGames.add( listOfGames.remove(maxIndex));
		        }
		    }		    
		 return topGames;	
 	}
	
	//Calculates the score of the last 5 games.
	public float calculateRecentScore() {
		int recentWins=0;
		int recentTies=0;
		for(int i=0; i<recentGames.size(); i++) {
			if(recentGames.get(i).getWinner()!=null && recentGames.get(i).getWinner().equals(this)) {
		        recentWins ++;
			}else if(recentGames.get(i).getResult().equals("tie")) {
				recentTies++;
			}
		}
		if(recentGames.size()!=0)
		recentScore=50*(2*recentWins +recentTies)/recentGames.size();
		return recentScore ;
	}
	
	//Finds a returns a list of the last 5 games (from most recent to least recent).
	public ChartList<Game>  recentGames(){
		recentGames = new ChartList<Game>(5); 
		listOfGames = new  ChartList<>(100);
		listOfGames.addAll(games);
		for (int i = 0; i < 5; i ++) {
		   int maxIndex = 0;	        
		   if(!listOfGames.isEmpty()) {
		       for (int j = 1; j <listOfGames.size(); j++) {
		          if(listOfGames.get(j).getEndTime().compareTo(listOfGames.get(maxIndex).getEndTime())>0) {
		        	  maxIndex = j;
		          }		        			
		        }
		      recentGames.add( listOfGames.remove(maxIndex));
		     }
		 }
		 System.out.println("For the player: "+this.plName +" the recent games  are: ");
		 for(int l=0; l<recentGames.size(); l++) {
		    System.out.println("• "+recentGames.get(l).getPlayer1().plName+" VS "+recentGames.get(l).getPlayer2().plName+" end time :"+recentGames.get(l).getEndTime());	    
		 }
		 System.out.println("..............................................................................................");
		 return recentGames;	
 	}
	
	//Calculates score of the player based on the algorithm that was provided.
	public float calculateScore() {
		if(sumOfGames==0)
			return 0;
		score =50*(2*wins+ties)/sumOfGames;
		return score;
	}
	
	//Finds and return best game based on the result (the best game is when this player has won and the worst one is when this player has lost).
	public int findsBestGame(int j, int maxIndex,ChartList<Game> listOfGames ) {
		if(listOfGames.get(j)!=null && listOfGames.get(maxIndex)!=null &&  castResultToInt(listOfGames.get(j))> castResultToInt(listOfGames.get(maxIndex))){
			return j;
		}
		if(listOfGames.get(j)!=null && listOfGames.get(maxIndex)!=null && castResultToInt(listOfGames.get(j))== castResultToInt(listOfGames.get(maxIndex))){
		return findsBestRival(j, maxIndex,listOfGames );
		}
		return maxIndex;
	}
		
	public int findsBestRival(int j, int maxIndex,ChartList<Game> listOfGames) {
		if(listOfGames.get(j)!=null && listOfGames.get(maxIndex)!=null && findRivalsScore(listOfGames.get(j)) > findRivalsScore(listOfGames.get(maxIndex))) {
			return j;
		}
		if(listOfGames.get(j)!=null && listOfGames.get(maxIndex)!=null &&findRivalsScore(listOfGames.get(j)) == findRivalsScore(listOfGames.get(maxIndex))) {
			return findsRescentGame(j, maxIndex,listOfGames);
		}
		return maxIndex;
	}
	
	private int findsRescentGame(int j, int maxIndex,ChartList<Game> listOfGames) {
		if(listOfGames.get(j)!=null && listOfGames.get(maxIndex)!=null && listOfGames.get(j).getEndTime().compareTo(listOfGames.get(maxIndex).getEndTime())>0) {
			return j;
		}
		return maxIndex;
	}
	
	//Finds and returns the opponent's score.
	public float findRivalsScore(Game games) {
		if(games!= null && games.getPlayer1().equals(this)) {
			return games.getScorePl2();
		}else if (games!= null && games.getPlayer2().equals(this)) {
			return games.getScorePl1();
		}
		return 0;
	}
	
	//This method returns 1 if the player wins, 0 if its a tie and -1 if player loses 
	public int castResultToInt(Game g) {	
		if(g.getWinner()!= null && g.getWinner().equals(this)  ) {
			return 1;
		}
		else if (g.getLoser()!= null && g.getLoser().equals(this) ) {
			return -1;
		}	
		return 0;
	}
		
	public String getPlName() {
		return plName;
	}
	
	public int getSumOfGames() {
		return sumOfGames;
	}

	public void setSumOfGames(int sumOfGames) {
		this.sumOfGames = sumOfGames;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int ties) {
		this.ties = ties;
	}

	public int getLoses() {
		return loses;	
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	public void setGames(Game game) {	
		games[numOfGames1]= game;
		numOfGames1++;
	}

	@Override
	public int compareTo(Player o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ChartList<Game> getRecentGames() {
		return recentGames;
	}

	public void setGames(Game[] games) {
		this.games = games;
	}

	public ChartList<Game> getTopGames() {
		return topGames;
	}	
}