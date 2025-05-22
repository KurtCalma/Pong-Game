import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;

public class GamePanel extends JPanel implements Runnable {
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	Countdown timeRemaining;
	
	int paddle2_speed = 8;
	
	GamePanel() {
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true); // Ensures it receives keyboard input by setting the panel to receive focus as true
		this.addKeyListener(new ActionListener()); // Receives key events from the keys of the keyboard
		this.setPreferredSize(SCREEN_SIZE); // Sets the preferred size of the screen
		
		gameThread = new Thread(this); 
		gameThread.start();
		
		
		Countdown.GAME_WIDTH = GAME_WIDTH;
		Countdown.GAME_HEIGHT = GAME_HEIGHT;

		timeRemaining = new Countdown();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timeRemaining, 0, 1000); // Schedule the task to run every 1000ms (1 second)
	}
	
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT / 2 - BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddles() {
		paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
		
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		timeRemaining.draw(g);
	}
	
	public void move() {
		paddle1.move();
		ball.move();
		
		int ballY = ball.y; // creates a variable to contain the ball's velocity in the y-axis
		int paddleCenter = paddle2.y + (paddle2.height / 2);
		
		if (paddleCenter < ballY) {
			paddle2.setYDirection(paddle2_speed);
		} else if (paddleCenter > ballY) {
			paddle2.setYDirection(-paddle2_speed);
		} else {
			paddle2.setYDirection(0);
		}
		
		paddle2.move();
	}
	
	public void checkCollision() {
		// bounce ball off top & window edges
		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		// bounce ball off paddles
		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity); /* xVelocity is previously negative, 
			so it uses absolute value to turn into a positive value */
			ball.xVelocity++; // increases the speed of the ball for more difficulty
			
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if (ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity); /* xVelocity is previously negative, 
			so it uses absolute value to turn into a positive value */
			ball.xVelocity++; // increases the speed of the ball for more difficulty
			
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		// stops paddles at window edges
		if (paddle1.y <= 0) {
			paddle1.y = 0;
		}
		
		if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		if (paddle2.y <= 0) {
			paddle2.y = 0;
		}
		
		if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		// give a player 1 point and creates new paddles & ball
		if (ball.x <= 0) {
			score.player2++;
			newPaddles();
			newBall();
			
			if (score.player2 == 10) {
				JOptionPane.showMessageDialog(null, "Unfortunately, you lost the game. Better luck next time.");
				System.exit(0);
			}
		}
		
		if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			
			if (score.player1 == 10) {
				JOptionPane.showMessageDialog(null, "Congratulations! You win the game!");
				System.exit(0);
			}
		}
	}
	
	public void run() {
		// game loop
		long lastTime = System.nanoTime(); /* Saves the current time in nanoseconds. 
		This acts as a timestamp for when the loop last ran. */
		double amountOfTicks = 60.0; // The desired update rate: 60 ticks (updates) per second.
		double nanoseconds = 1000000000 / amountOfTicks; /* Calculates how many nanoseconds each tick should take.
		1,000,000,000 nanoseconds = 1 second → so each tick should take about 16.67 million nanoseconds. */
		double delta = 0;
		while (true) {
			long now = System.nanoTime(); // Records the current time.
			delta += (now - lastTime) / nanoseconds; /* Calculates how many ticks have passed since the last time.
			(now - lastTime) = how much time has passed in nanoseconds.
			Dividing it by nanoseconds gives how many "tick intervals" that equals.
			delta accumulates this value in case the program missed ticks. */
			lastTime = now; // Updates lastTime so the next iteration can compute the new time difference.
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--; // reduce the amount of leftover time (ticks) by one.
			}
		}
		
	}
	
	public class ActionListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}

}
