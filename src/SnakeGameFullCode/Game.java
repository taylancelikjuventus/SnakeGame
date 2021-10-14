package SnakeGameFullCode;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements KeyListener  {

	private Board board;
	
	
	
	public Game() {
		
		board=new Board();
		addKeyListener(this);
		setLayout(null);
		add(board);
		setResizable(false);
		
		
		board.label_score.setText("Score :" +board.score);
		add(board.label_score);
		
		setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100,100,450,400);
	    setVisible(true);
	}

	//KEY Events
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
       
		int k = e.getKeyCode();
		
		if(k==KeyEvent.VK_RIGHT && !board.getLeft()) {
			board.setRight(true);
			board.setLeft(false);
			board.setDown(false);
			board.setUp(false);
			
		}
		if(k==KeyEvent.VK_LEFT && !board.getRight()) {
			board.setRight(false);
			board.setLeft(true);
			board.setDown(false);
			board.setUp(false);
		}
		if(k==KeyEvent.VK_UP && !board.getDown()) {
			board.setRight(false);
			board.setLeft(false);
			board.setDown(false);
			board.setUp(true);
		}
		if(k==KeyEvent.VK_DOWN && !board.getUp()) {
			board.setRight(false);
			board.setLeft(false);
			board.setDown(true);
			board.setUp(false);
		}
		
		if(k == KeyEvent.VK_R ) {
			this.dispose();       //close current frame
			new Game();
		}
		
		//if space is pressed move faster
		if(k == KeyEvent.VK_SPACE) {
			board.getTimer().setDelay(60);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int k  = e.getKeyCode();
		if(k == KeyEvent.VK_SPACE) {
			board.getTimer().setDelay(250);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}   
	

		
	

		
}
