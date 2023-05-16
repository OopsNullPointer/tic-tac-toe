package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import control.GameController;

@SuppressWarnings("serial")
public class ResultWindow extends JFrame{
	private int gameBoard;
	private int result;
		
	public ResultWindow(GameController gc) {
		super(" RESULT ");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(400, 200));
	}
		
	public void setResultWindow(GameController gc) {
		if(result==1) {            // For the winner.
		     if(gameBoard==1) {     // Prints the winner's name if O is the winner.
		    	this.getContentPane().setBackground(new Color(192, 57, 43)); 
		    	JLabel label= new JLabel("   The winner is " +gc.getModel().getGamePlayers()[1]+" !") ;
		    	Font fnt =  new Font("SansSerif", Font.ITALIC,20);
		    	label.setFont(fnt);
		    	this.add(label,BorderLayout.CENTER);
		    	this.pack();
		    	this.setVisible(true);
		    	System.out.println("\n" + gc.getModel().getGamePlayers()[1] + " won!");
		    }else if(gameBoard==2) {                      //if the winner is X
		    	this.getContentPane().setBackground(new Color(192, 57, 43)); 
		    	JLabel label= new JLabel("   The winner is  " +gc.getModel().getGamePlayers()[0]+" !" ) ;
		    	Font fnt =  new Font("SansSerif", Font.ITALIC,20);
		    	label.setFont(fnt);
		    	this.add(label,BorderLayout.CENTER);	        
		    	this.pack();
		    	this.setVisible(true);	    
		    	System.out.println("\n" + gc.getModel().getGamePlayers()[0] + " won!");
		    	}	 
		}else if(result==0) {        //For tie.
		    this.getContentPane().setBackground(new Color(138,182,170)); 
		    JLabel label= new JLabel("                  TIE " ) ;
		    Font fnt =  new Font("SansSerif", Font.ROMAN_BASELINE,30);
		    label.setFont(fnt);
		    this.add(label,BorderLayout.CENTER);	
		    this.pack();
		    this.setVisible(true);
		    System.out.println("\nIt was a tie between " + gc.getModel().getGamePlayers()[0] + " and " + gc.getModel().getGamePlayers()[1]);
		}	
   }

	public void setGameBoard(int gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void setResult(int result) {
		this.result = result;
	}		
}