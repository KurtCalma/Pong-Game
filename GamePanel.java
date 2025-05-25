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
	
	
	// Declare variables that will serve as the game's components
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
		
		gameThread = new Thread(this); // Instantiate gameThread as an object of the Thread class
		gameThread.start(); // starts the game thread to manage the loop of the game and consistently update its state 
		
		// Initialize the variables that will display the countdown on the screen
		Countdown.GAME_WIDTH = GAME_WIDTH;
		Countdown.GAME_HEIGHT = GAME_HEIGHT;

		// Instantiates two objects in order to initialize the countdown
		timeRemaining = new Countdown();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timeRemaining, 0, 1000); // Schedule the task to run every 1000ms (1 second)
	}
	
	public void newBall() {
		// Set the ball at a random position
		random = new Random();
		ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT / 2 - BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddles() {
		// Set the positions of the paddles
		paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}
	
	public void paint(Graphics g) {
		// Create the graphics of the game
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
		
	}
	
	public void draw(Graphics g) {
		// draw the images of the following game components below
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		timeRemaining.draw(g);
	}
	
	public void move() {
		paddle1.move();
		ball.move();
		
		int ballY = ball.y; // holds the ball's instantaneous position in the y-axis
		int paddleCenter = paddle2.y + (paddle2.height / 2); // Calculates the position of paddle2's center
		
		if (paddleCenter < ballY) {
			paddle2.setYDirection(paddle2_speed); // Follow the ball downwards
		} else if (paddleCenter > ballY) {
			paddle2.setYDirection(-paddle2_speed); // Follow the ball upwards
		} else {
			paddle2.setYDirection(0); // Stop the movement of paddle2
		}
		
		paddle2.move();
	}
	
	public void checkCollision() {
		// bounce ball off top & window edges
		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity); // reverse the velocity of the ball once it hits the top edge
		}
		
		if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity); // reverse the velocity of the ball once it hits the bottom edge
		}
		
		// bounce ball off paddles
		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity); /* xVelocity is previously negative, 
			so it uses absolute value to turn it into a positive value */
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
			ball.xVelocity++; // increases the ball's horizontal velocity
			
			// increases the ball's vertical velocity
			if (ball.yVelocity > 0) {
				ball.yVelocity++; // increase in the downward direction if vertical velocity is in the positive Y direction
			} else {
				ball.yVelocity--; // increase in the upward direction if the vertical velocity is in the negative Y direction
			}
			
			// set the new X and Y directions of the ball with its updated X and Y velocities
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		// Stop the user paddle if it hits the top edge
		if (paddle1.y <= 0) {
			paddle1.y = 0;
		}
		
		// Stop the user paddle if it hits the bottom edge
		if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		// Stop the right paddle if it hits the top edge
		if (paddle2.y <= 0) {
			paddle2.y = 0;
		}
		
		// Stop the right paddle if it hits the bottom edge
		if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		// give the right paddle 1 point and create new paddles & a new ball
		if (ball.x <= 0) {
			score.right_paddle++;
			newPaddles();
			newBall();
			
			// If the right paddle wins
			if (score.player == 10) {
				JOptionPane.showMessageDialog(null, "Unfortunately, you lost the game. Better luck next time.");
				System.exit(0);
			}
		}
		
		// give the left paddle (player or user paddle) 1 point and create new paddles & a new ball
		if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
			score.player++;
			newPaddles();
			newBall();
			
			// If the player wins
			if (score.player == 10) {
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
