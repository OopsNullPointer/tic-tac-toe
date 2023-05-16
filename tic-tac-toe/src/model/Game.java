package model;

import control.GameController;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressWarnings("serial")
public class Game implements Comparable<Game>, Serializable{
	private Player player1;
	private Player player2;
	private float scorePl1;
	private float scorePl2;	
	private Player winner;
	private Player loser;
	private String result;
	private Date d1;
	private Date d2;
	private String startTime; 
	private String endTime;
	
	public Game(GameController gc , Player player1, Player player2) {
		super();
		this.player1 = player1 ;
		this.player2 = player2;
		DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  
		this.startTime = dtfTime.format(now);
	}
	
	//Second constructor in order to save all the data.
	public Game(Player player1, Player player2, float scorePl1, float scorePl2, Player winner, Player loser, String result, String startTime) {
		this.player1 = player1;
		this.player2 = player2;
		this.scorePl1 = scorePl1;
		this.scorePl2 = scorePl2;
		this.winner = winner;
		this.loser = loser;
		this.result = result;
		this.startTime = startTime;
		DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  
		System.out.println("The game started at: "+ startTime);
		System.out.println("The game ended at: " +dtfTime.format(now));		
		endTime = dtfTime.format(now);
		gameDuration(startTime,this.getEndTime());
	}
	
	//Finds game's duration.
	public void gameDuration(String startTime , String endTime) {
		 try {		  
	            // parse method is used to parse the text from a string to produce the date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            d1 = sdf.parse(startTime);
	            d2 = sdf.parse(endTime);  
	            // Calculate time difference in milliseconds
	            long difference_In_Time= d2.getTime() - d1.getTime();	  
	            // Calculate time difference in seconds, minutes, hours 
	            long difference_In_Seconds = (difference_In_Time/ 1000) % 60;	  
	            long difference_In_Minutes= (difference_In_Time/ (1000 * 60)) % 60;	  
	            long difference_In_Hours= (difference_In_Time/ (1000 * 60 * 60))% 24;	
	            System.out.println("..............................................................................................");
	            System.out.print("Game duration: ");	  
	            System.out.println(difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, "+ difference_In_Seconds+ " seconds");
	            System.out.println("..............................................................................................");
		 }catch (ParseException e) {
	            e.printStackTrace();
	     }
	}

	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}

	public float getScorePl1() {
		return scorePl1;
	}

	public void setScorePl1(float f) {
		this.scorePl1 = f;
	}

	public float getScorePl2() {
		return scorePl2;
	}

	public void setScorePl2(float f) {
		this.scorePl2 = f;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String resultPl1) {
		this.result = resultPl1;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(Player loser) {
		this.loser = loser;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	@Override
	public int compareTo(Game o) {
		// TODO Auto-generated method stub
		return 0;
	}	
}