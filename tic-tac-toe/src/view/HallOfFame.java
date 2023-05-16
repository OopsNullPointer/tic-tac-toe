package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import control.GameController;
import model.ChartList;
import model.Player;

@SuppressWarnings("serial")
public class HallOfFame extends GamePanel{	
	private GameController gc;
	ChartList<Player> arrayOfPlayers;
	private ChartList<Player> listOfTopPlayers;
	JTextArea hallOfFameJTxt;

	public HallOfFame(GameController gc) {
		super(gc);
		this.gc=gc;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(MainWindow.WIDTH-2*MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT-MainWindow.TOP_HEIGHT));	
		this.setAlignmentX(CENTER_ALIGNMENT);
		
		hallOfFameJTxt = new JTextArea(589,718);	
		hallOfFameJTxt.setBackground(new Color (138,182,170));
		hallOfFameJTxt.setPreferredSize(new Dimension(MainWindow.WIDTH-2*MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT-MainWindow.TOP_HEIGHT));
		hallOfFameJTxt.setAlignmentX(CENTER_ALIGNMENT);
		Font statsf = new Font("SansSerif", Font.BOLD,17);
		hallOfFameJTxt.setFont(statsf);
		hallOfFameJTxt.setEnabled(true);		
		hallOfFameJTxt.setMargin(new Insets(50, 50, 50, 50));
		this.add(hallOfFameJTxt);
		
		bestPlayers(); 		 
	}
	
	@Override
	public void paintComponent(Graphics g)   {  
	    if(gc.getModel().getPlayerCatalogue().getPlayers() == null) {
	    	super.paintComponent(g);
	    	int x = this.getWidth()/2 - 50;
			int y = this.getHeight()/2;		
			g.drawString("Hall Of Fame", x, y);	    
	    }   
	}
	
	//Stores in a ChartList and displays them from best to worst.
	public ChartList<Player>  bestPlayers(){
		listOfTopPlayers = new ChartList<Player>(10); 
		arrayOfPlayers = new  ChartList<>(100);
		arrayOfPlayers.addAll(gc.getModel().getPlayerCatalogue().getPlayers());
		    for (int i = 0; i < 10; i ++) {
		        int maxIndex = 0;	        
		        if(!arrayOfPlayers.isEmpty()) {
		        	for (int j = 1; j <arrayOfPlayers.size(); j++) {
		        		if (arrayOfPlayers.get(j).calculateScore() > arrayOfPlayers.get(maxIndex).calculateScore()) {
		        			maxIndex = j;
		        		}
		        	}
		        	listOfTopPlayers.add( arrayOfPlayers.remove(maxIndex));
		        }
		    }
		    printBestPlayers(listOfTopPlayers);
		 return listOfTopPlayers;	
 	}
	
	public void printBestPlayers(ChartList<Player> topKList2) {	
		this.hallOfFameJTxt.setText(bestPlayersToString(topKList2));
		this.repaint();
	}
	
	public String bestPlayersToString(ChartList<Player> topKList2) {
		StringBuilder text = new StringBuilder();
		text.append("\t").append("    ").append("     HALL   OF   FAME");
		text.append("\n\nNAME").append("\t\t").append( "SCORE").append("\t").append("TOTAL GAMES").append("\n");
		for(int i = 0; i<topKList2.size(); i++) {
			if(topKList2.get(i).getPlName()!=null && topKList2.get(i).getPlName().length()<14) {
				text.append("\n\n").append((i+1)+") "+topKList2.get(i).getPlName()).append("\t\t").append(topKList2.get(i).calculateScore()).append("  \t  ").append(topKList2.get(i).getSumOfGames());
			}else if(topKList2.get(i).getPlName()!=null && topKList2.get(i).getPlName().length()>=14) {
				text.append("\n\n").append((i+1)+") "+topKList2.get(i).getPlName()).append("\t").append(topKList2.get(i).calculateScore()).append("  \t  ").append(topKList2.get(i).getSumOfGames());
			}				
		}
		return text.toString();
	}	
}