package SnakeGameFullCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//Board of game
public class Board extends JPanel implements ActionListener {

	// constants
	private final int BWIDTH = 300;
	private final int BHEIGTH = 300;
	private  int DELAY = 250;
	private final int DOTSIZE = 10;
	private final int NUMOFDOTS = 900;

	// position of snake
	private final int x[] = new int[NUMOFDOTS];// x pos
	private final int y[] = new int[NUMOFDOTS];// y pos

	// apple
	private int xPosApple;
	private int yPosApple;

	// lenght of snake
	private int length;

	// direction macros
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean play = true;

	// images
	private Image head;
	private Image tail;
	private Image apple;

	// ref to timer
	private Timer timer;
	private Timer timer_score;
	
	
	//score
    public int score ;
    public JLabel label_score;
    private Font f ;
	
	public Board() {

		// addKeyListener(new TAdapter());
		setBackground(Color.black);
		setBounds(10, 10, BWIDTH, BHEIGTH);
		setVisible(true);

		//Label for Score
		f = new Font("Verdana",Font.BOLD ,14);
		label_score  = new JLabel();
		label_score.setFont(f);
		label_score.setBounds(320, 20, 150, 40);
		
	
		
		apple = new ImageIcon("src\\SnakeGameFullCode\\appleForSnake.png").getImage();
		tail = new ImageIcon("src\\SnakeGameFullCode\\TailOfSnake.png").getImage();
		head = new ImageIcon("src\\SnakeGameFullCode\\headOfSnake2.png").getImage();

		
		setApplePosition();
		setSnakePosition();
       
		
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		timer_score = new Timer(300,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			      
				//check score 
				if(score != 0 && score % 50 == 0) {
					DELAY -= 5;
					timer.setDelay(DELAY); //update timer of game
					if(DELAY <=150)
				      DELAY= 150 ;
				}
			}
		});
		timer_score.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (play) {
			eatApple();
			detectCollision();
			moveSnake();
		}

		repaint();
	}

	
	
	public void setSnakePosition() {

		length = 3; // initial length of snake

		for (int i = 0; i < length; i++) { //initial position of snake
			x[i] = 30 - i * 10;
			y[i] = 40;
		}

	}

	public void setApplePosition() {

		int xPos = (int) (Math.random() * 29);
		xPosApple = xPos * 10;

		int yPos = (int) (Math.random() * 29);
		yPosApple = yPos * 10;
	}

	// MOVEMENT
	public void moveSnake() {

		// direction of tails x[1],x[2],...
		for (int i = length; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		// direction of head x[0]
		if (right) {
			x[0] += DOTSIZE;
		}
		if (left) {
			x[0] -= DOTSIZE;
		}
		if (up) {
			y[0] -= DOTSIZE;
		}
		if (down) {
			y[0] += DOTSIZE;
		}

	}

	// if head and apple are at the same position
	public void eatApple() {

		if (x[0] == xPosApple && y[0] == yPosApple) {
			
			score +=10 ; // increase score
			length++; // increase length of snake
			label_score.setText("Score :" + score);
			setApplePosition(); // place another apple on the board
		}

	}

	public void detectCollision() {

		// if head hits borders stop game
		if (x[0] >= BWIDTH)
			play = false;
		if (x[0] < 0)
			play = false;
		if (y[0] >= BHEIGTH)
			play = false;
		if (y[0] < 0)
			play = false;

		// if head hits tails stop game
		for (int i = 1; i <length ; i++) {

			if (length >= 4) { // only condition to hit its tail
				if ((x[0] == x[i]) && (y[0] == y[i])) {

					play = false;
				}

			}

		}
		
		if(!play)
			timer.stop();

	}

	// RENDERING
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		g.setColor(Color.darkGray);
		//grids
		for(int i = 0 ; i<30; i++) {
			g.drawLine(0, i*10 , 900, i*10);
		}
		
		for(int i = 0 ; i<30; i++) {
			g.drawLine(i*10, 0 , i*10, 900);
		}
		
		if (play) {

			g.drawImage(apple, xPosApple, yPosApple,10,10,null);

			for (int i = 0; i < length; i++) {

				if (i == 0)
					g.drawImage(head, x[i], y[i],10,10, null);
				else
					g.drawImage(tail, x[i], y[i], 10,10,null);
			}

		} else {
			gameOver(g);
		}
	}

	public void gameOver(Graphics g) {

		Font f = new Font("Verdana", Font.BOLD, 24);

		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString("GAME OVER", BWIDTH / 2 - 75, BHEIGTH / 2);
		g.drawString("Press 'r' to restart", 30, BHEIGTH / 2+40);

	}

	// getters and setters
	public boolean getRight() {
		return this.right;
	}

	public boolean getLeft() {
		return this.left;
	}

	public boolean getUp() {
		return this.up;
	}

	public boolean getDown() {
		return this.down;
	}

	public boolean getPlayStatus() {
		return play;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
		;
	}

	public void setPlayStatus(boolean b) {
		play = b;
	}
	
	
	public Timer getTimer() {
		return timer ;
	}
}
