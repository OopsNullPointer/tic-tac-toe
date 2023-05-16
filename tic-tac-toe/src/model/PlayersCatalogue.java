package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class PlayersCatalogue implements  Serializable {
	private Player[] players;
	private int count;
	private int newCount;
	private String[] listOfPlNames;
	public static final int MAX_NUMOFPLAYERS = 100;
	
	public PlayersCatalogue() {
		listOfPlNames = new String[MAX_NUMOFPLAYERS];
		players = new Player[MAX_NUMOFPLAYERS];
		this.loadPlayers();
		count = newCount;
		File f = new File("TucTacToe.ser");		          
		if(!f.exists()){                                   //If file does not exist then it creates the initialized players.
			createPl("Hal");
			createPl("Mr.Bean");
			createPl("Janet");
			createPl("Jason");
		} 
	}
	
	//Creates player and adds them to the array of players.
	public void createPl(String PlName) {
		players [count] = new Player(PlName);
		count ++;
	}
	
	//Returns an array of all of the players of the game.
	public String[] getPlNames() {
		int i=0;
		try{			
			for(Player s: players) {
				listOfPlNames[i] = s.getPlName();
				i++;
				}
			return listOfPlNames;
		}catch(NullPointerException e) {
			System.out.println("------------------------------------------------------");
		}
	 return listOfPlNames;		
	}

	//Stores players and count (number of players).
	public void storePlayers() {
		ObjectOutputStream os = null;
		FileOutputStream fos = null;
		FileOutputStream fos2 = null;
		ObjectOutputStream os2 = null;
		try {
			fos = new FileOutputStream("TucTacToe.ser");
			fos2 = new FileOutputStream("Count.txt");	
			os = new ObjectOutputStream(fos);
			os2 = new ObjectOutputStream(fos2);		
			for(Player p1: players) {			
				os.writeObject(p1);
			}			
			os2.writeInt(count);			
			System.out.println("All " + count +" players stored successfully....");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {			
			e.printStackTrace();
		}finally {
			try {
					os.close(); fos.close();
					os2.close(); fos2.close();
				}catch (Exception e) {
			}
		}
	}
		
	//Loads players and count (number of players).
	public void loadPlayers() {
		ObjectInputStream is = null;
		FileInputStream fis = null;	
		ObjectInputStream is2 = null;
		FileInputStream fis2 = null;	
		int pos = 0;
		try {
			fis = new FileInputStream("TucTacToe.ser");
			fis2= new FileInputStream("Count.txt");
			is = new ObjectInputStream(fis);
			is2 = new ObjectInputStream(fis2);
			
			while(fis2.available()>0) {
				newCount = is2.readInt();
				System.out.println("The number of loaded players is: "+newCount);
			}			
			for(int i=0; i<newCount; i++) {
				Player p = (Player)is.readObject();	
				System.out.println(p.getPlName());
				this.players[pos++] = p;
			}
			System.out.println("players Loaded !");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found for read objects...");
		}finally {
			try {
				 is.close(); fis.close();
				 is2.close(); fis2.close();
				}catch (Exception e) {
			}
		}
	}
		
	public Player[] getPlayers() {
		return players;
	}

	public int getCount() {
		return count;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}	
}