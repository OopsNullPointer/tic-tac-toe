package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import control.GameController;

@SuppressWarnings("serial")
public class BoardCell extends GamePanel implements MouseListener {
	public static final int CELL_PADDING = 10;
	int row, col;	
	public boolean highlighted;

	public BoardCell(GameController gc, int row, int col) {
		super(gc);
		this.setBackground(new Color (204,255,229));
		this.row = row;
		this.col = col;
		this.addMouseListener(this);
		this.highlighted = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered cell " + this);
		this.highlight();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited on cell " + this);
		this.unHighlight();
	}

	private void highlight() {
		if (!highlighted && getModel().inPlay()) {
			highlighted = true;
			repaint();
		}
	}

	private void unHighlight() {
		if (highlighted && getModel().inPlay()) {
			highlighted = false;
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(gc.getModel().getGamePlayers()[0].equals("Hal") && gc.getModel().getGamePlayers()[1].equals("Mr.Bean")|| gc.getModel().getGamePlayers()[1].equals("Hal") && gc.getModel().getGamePlayers()[0].equals("Mr.Bean"))
			highlighted = false;
		
		int mark = getModel().getBoardMark(this.row, this.col);
		Graphics2D g2d = (Graphics2D) g;
		int size = this.getSize().width - 2 * CELL_PADDING;
		g2d.setStroke(new BasicStroke(6));
		if (mark == 0) {
			if (highlighted) {
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fillRect(CELL_PADDING, CELL_PADDING, size, size);
			}
			return;
		} else if (mark==2) {
			g2d.drawLine(CELL_PADDING, CELL_PADDING, CELL_PADDING + size, CELL_PADDING + size);
			g2d.drawLine(CELL_PADDING + size, CELL_PADDING, CELL_PADDING, CELL_PADDING + size);
		} else if(mark==1){
			g2d.drawOval(CELL_PADDING, CELL_PADDING, size, size);
		}
	}
	  
	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked on cell " + this);
		if (getModel().inPlay()) {
			//Checks if both players are AIs then the user cannot interferes in the gameBoard.
			if(gc.getModel().getGamePlayers()[0].equals("Hal") && gc.getModel().getGamePlayers()[1].equals("Mr.Bean")|| gc.getModel().getGamePlayers()[1].equals("Hal") && gc.getModel().getGamePlayers()[0].equals("Mr.Bean"))
				return;             
			getModel().makeMove(row, col); //If mouse clicks then plays moves that the user has selected.
			repaint();                     //Repaints the boardCell.
			if(gc.getModel().getGamePlayers()[0].equals("Hal") || gc.getModel().getGamePlayers()[1].equals("Hal"))
			gc.getModel().smartAI();       //If one player is Hal then the AI is called to make move.
			if(gc.getModel().getGamePlayers()[0].equals("Mr.Bean") || gc.getModel().getGamePlayers()[1].equals("Mr.Bean"))
			gc.getModel().dumbAI();        //If one player is Mr.Bean then the AI is called to make move.
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}