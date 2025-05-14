import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle {
	
	int id;
	int yVelocity;
	int speed = 10;
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id = id;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(-speed);
			move();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(speed);
			move();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			setYDirection(0);
			move();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setYDirection(0);
			move();
		}
	}
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	
	public void move() {
		y += yVelocity;
	}
	
	public void draw(Graphics g) {
		if (id == 1) {
			g.setColor(Color.green);
		} else {
			g.setColor(Color.magenta);
		}
		
		g.fillRect(x, y, width, height);
	}

}
