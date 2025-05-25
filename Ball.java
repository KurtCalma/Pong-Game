import java.awt.*;
import java.util.*;

public class Ball extends Rectangle {
	
	Random random;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 5; // Set the initial speed of the ball
	
	Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		random = new Random();
		
		// Set the ball's horizontal direction randomly
		int randomXDirection = random.nextInt(2);
		if (randomXDirection == 0) {
			randomXDirection--;
		}
		
		setXDirection(randomXDirection*initialSpeed); /* Convert the ball's horizontal speed
		as a horizontal velocity (aka, a vector: Magnitude + Direction) */
		
		// Set the ball's vertical direction randomly
		int randomYDirection = random.nextInt(2);
		if (randomYDirection == 0) {
			randomYDirection--;
		}
		
		setYDirection(randomYDirection*initialSpeed); /* Convert the ball's vertical speed
		as a vertical velocity (aka, a vector: Magnitude + Direction) */
	}
	
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection; // Initialize the xVelocity variable as the ball's horizontal velocity
	}
	
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection; // Initialize the yVelocity variable as the ball's vertical velocity
	}
	
	public void move() {
		x += xVelocity; // Change the position of the ball with the horizontal velocity
		y += yVelocity; // Change the position of the ball with the vertical velocity
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white); // Set the color of the ball
		g.fillOval(x, y, height, width); // Fills the ball with its specified color
	}

}
