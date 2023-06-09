package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import control.GameController;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	static public final int WIDTH = 1200;
	static public final int HEIGHT = 800;
	static public final int TOP_HEIGHT = 80;
	static public final int PLAYER_WIDTH = 300;	
	private PlayerPanel leftPanel;
	private PlayerPanel rightPanel;
	private TopPanel topPanel;
	private MainAreaPanel mainPanel;
	private GameController gc;
		
	public MainWindow(GameController gc) {
		super("TUC-TAC-TOE");
		this.gc=gc;
		Container c = this.getContentPane();		
		c.setPreferredSize(new Dimension(WIDTH, HEIGHT));		
		
		topPanel = new TopPanel(this.gc);	
		this.add(topPanel,BorderLayout.PAGE_START);
		
		leftPanel = new PlayerPanel(gc,0);
		this.add(leftPanel,BorderLayout.LINE_START);
		
		rightPanel = new PlayerPanel(gc,1);
		this.add(rightPanel,BorderLayout.LINE_END);

		mainPanel = new MainAreaPanel(this.gc);
		this.add(mainPanel,BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.pack();		
	}

	public PlayerPanel getLeftPanel() {
		return leftPanel;
	}
	
	public PlayerPanel getRightPanel() {
		return rightPanel;
	}

	public TopPanel getTopPanel() {
		return topPanel;
	}
	
	public MainAreaPanel getMainPanel() {
		return mainPanel;
	}		
}