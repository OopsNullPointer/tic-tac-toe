package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import control.GameController;
import model.Player;

@SuppressWarnings("serial")
public class PlayerPanel extends GamePanel{		
	private JButton selectPlayerBtn;
	private int pos;
	private Player currentPlayer;
	JTextField plName;
	JLabel plMark;
	JTextArea plStats;	
	
	public PlayerPanel(GameController c, int pos) {
		super(c);
		this.pos=pos;		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT-MainWindow.TOP_HEIGHT));		
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setBackground(new Color (192, 57, 43));
				
		selectPlayerBtn = new JButton("Choose Player ");                                  //This is the button that enables the user to choose a player.
		selectPlayerBtn.setBackground(new Color (138,182,170));
		selectPlayerBtn.setPreferredSize(new Dimension(150,30));
		selectPlayerBtn.setAlignmentX(CENTER_ALIGNMENT);
		selectPlayerBtn.addActionListener((e)->{changePlayer();});
		
		add(selectPlayerBtn);
		
		plName = new JTextField(); 
		plName.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH,40));
		plName.setMaximumSize(plName.getPreferredSize() );
		plName.setAlignmentX(CENTER_ALIGNMENT);
		plName.setHorizontalAlignment(JTextField.CENTER);
		plName.setBorder(null);
		plName.setEnabled(false);	
		plName.setBackground(new Color (192, 57, 43));

		plMark = new JLabel(pos==0? "X" : "O");                                           //The mark of each player.
		plMark.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH,80));
		plMark.setAlignmentX(CENTER_ALIGNMENT);
		plMark.setHorizontalAlignment(JLabel.CENTER);
		plMark.setEnabled(false);
		Font markf = new Font("SansSerif", Font.BOLD,90);
		plMark.setFont(markf);
						
		plStats = new JTextArea(10,100);		                                        //The statistics of each player.
		plStats.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH,400));
		plStats.setAlignmentX(CENTER_ALIGNMENT);
		Font statsf = new Font("SansSerif", Font.PLAIN,15);
		plStats.setFont(statsf);
		plStats.setEnabled(false);		
		plStats.setMargin(new Insets(10, 10, 10, 10));
		plStats.setBackground(new Color (192, 57, 43));
		
		this.add(plMark);
		this.add(plName);		
		this.add(plStats);
	}
	
	public void changePlayer() {                                                               //The method which enables the user to change player.
        String[] allPlayersName = gc.getModel().getPlayerCatalogue().getPlNames();
		//Arrays.sort(allPlayers);

		//Show Player Selection Dialog
		String selPlayer = (String) JOptionPane.showInputDialog(this, 
				"Choose a Player...",
				"Player selection",
				JOptionPane.PLAIN_MESSAGE,
				null,
				allPlayersName,
				currentPlayer
				);
		//Set the selected player		
		if(selPlayer != null) {
			if (selPlayer.equals(gc.getModel().getGamePlayers()[pos==0?1:0])) {
				JOptionPane.showMessageDialog(gc.getView(), 						
						"Player already selected",
						"Ooops...",
						JOptionPane.ERROR_MESSAGE);
				return;
			}			
			this.currentPlayer=gc.getModel().findPlInfo(selPlayer);			
			gc.selectPlayer(selPlayer,pos);
		
			this.plName.setText(currentPlayer.getPlName());

			this.setPlayerStats(gc.getModel().getPlayerStats(currentPlayer.getPlName()));
			this.repaint();
		}
	}	

	public void setPlayerStats(String stats) {
		this.plStats.setText(stats);
	}
	
	public JButton getSelectPlayerBtn() {
		return selectPlayerBtn;
	}

}