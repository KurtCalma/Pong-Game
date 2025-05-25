import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {
	
	int id;
	int yVelocity;
	int paddle1_speed = 10; // Set the speed of the left/user paddle
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id = id;
	}
	
	public void keyPressed(KeyEvent e) {
		// If W key on the keyboard is pressed
		if (e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(-paddle1_speed); // Set the speed of the left paddle in the upward direction
			move();
		}
		
		// If S key on the keyboard is pressed
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(paddle1_speed); // Set the speed of the left paddle in the downward direction
			move();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		// If W key on the keyboard is released
		if (e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(0); // Stop the speed of the user paddle
			move();
		}
		
		// If S key on the keyboard is released
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(0); // Stop the speed of the user paddle
			move();
		}
		
	}
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection; // Initalize the yVelocity variable with the left paddle's vertical speed
	}
	
	public void move() {
		y += yVelocity; // Change the position of the paddles with the vertical velocity
	}
	
	public void draw(Graphics g) {
		// Set the color of the paddles
		if (id == 1) {
			g.setColor(Color.green);
		} else {
			g.setColor(Color.magenta);
		}
		
		// Fills the paddles with their specified colors
		g.fillRect(x, y, width, height);
	}

}
