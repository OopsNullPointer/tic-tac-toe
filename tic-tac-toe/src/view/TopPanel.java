package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.GameController;

@SuppressWarnings("serial")
public class TopPanel extends GamePanel{	
	JButton quitBtn;
	JButton startGameBtn;
	JButton doneBtn;
	private JButton addPlayerBtn;

	
	public TopPanel(GameController gc) {
		super(gc);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setPreferredSize(new Dimension(MainWindow.WIDTH,MainWindow.TOP_HEIGHT));
		this.setBackground(new Color (192, 57, 43));
		
		quitBtn = new JButton("Quit App");	                                    //This button terminates the program.
		quitBtn.setPreferredSize(new Dimension(100, 40));
		quitBtn.addActionListener((e)->{this.gc.quit();});
		quitBtn.setBackground(new Color (138,182,170));
		
		startGameBtn = new JButton("Start Game");                              //This button starts the game (tic-tac-toe).
		startGameBtn.setPreferredSize(new Dimension(100, 40));
		startGameBtn.setEnabled(false);
		startGameBtn.addActionListener((e)->{this.gc.startGame();});
		startGameBtn.setBackground(new Color (138,182,170));

		addPlayerBtn  = new JButton("    Add Player    ");                   //This button lets the user add a player (with whatever name they want).
		addPlayerBtn.setBackground(new Color (138,182,170));
		addPlayerBtn.setPreferredSize(new Dimension(140,40));
		addPlayerBtn.setAlignmentX(CENTER_ALIGNMENT);
		addPlayerBtn.addActionListener((e)->{addPlayer();});
		
		doneBtn = new JButton("Done");		                               //This button lets the user "end" the game and return to the hall of fame.
		doneBtn.setPreferredSize(new Dimension(100, 40));		
		doneBtn.setEnabled(false);
		doneBtn.addActionListener((e)->{this.gc.Done();});
		doneBtn.setBackground(new Color (138,182,170));

		add(startGameBtn);
		add(doneBtn);
		add(addPlayerBtn);
		add(quitBtn);					
	}
	
	public void addPlayer() {
       	String name = JOptionPane.showInputDialog(null, "Add player");
       	if(name!=null) {
       		if(name.isEmpty()) {
       			JOptionPane.showMessageDialog(gc.getView(), 						
					"Enter a name !",
					"Ooops...",
					JOptionPane.ERROR_MESSAGE);
       			return;
       		}else if (name.length()>20) {
       			JOptionPane.showMessageDialog(gc.getView(), 						
					"Enter a shorter name with less than 20 characters ",
					"Ooops...",
					JOptionPane.ERROR_MESSAGE);
       			return;
       		}else if(name.endsWith(" ") || name.startsWith(" ")) {
       			JOptionPane.showMessageDialog(gc.getView(), 						
    					"The name should not start or end with spaces",
    					"Ooops...",
    					JOptionPane.ERROR_MESSAGE);
           		return;
       		}
       		for(int i=0; i<gc.getModel().getPlayerCatalogue().getCount(); i++) {
       			if(gc.getModel().getPlayerCatalogue().getPlayers()[i].getPlName().equals(name)) {
       				JOptionPane.showMessageDialog(gc.getView(), 						
        					"This player already exist",
        					"Ooops...",
        					JOptionPane.ERROR_MESSAGE);
               		return;
       			}
       		}      		 
       		gc.getModel().getPlayerCatalogue().createPl(name);
        }
    }
	
	public JButton getAddPlayerBtn() {
		return addPlayerBtn;
	}	
	public JButton getQuitBtn() {
		return quitBtn;
	}

	public JButton getStartBtn() {
		return startGameBtn;
	}
	
	public JButton getDoneBtn() {
		return doneBtn;
	}		
}