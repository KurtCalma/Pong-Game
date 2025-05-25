import java.awt.*;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class Countdown extends TimerTask {
	
	private static int timeRemaining = 300;
	private static int minutes;
	private static int seconds;
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	
	public void run() {
		if (timeRemaining > 0) { 
			minutes = timeRemaining / 60; 
			seconds = timeRemaining % 60; // To properly display the seconds, the remainder of the minutes are the seconds
			timeRemaining--; // Decrement the time since it's a countdown
		} else {
			JOptionPane.showMessageDialog(null, "Time's up! As a result, you unfortunately lose, my friend.", "Time's Up", JOptionPane.WARNING_MESSAGE);
			cancel();
			System.exit(0);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		g.drawLine(GAME_WIDTH / 2, GAME_HEIGHT, GAME_WIDTH / 2, GAME_HEIGHT);
		g.drawString(String.valueOf(minutes / 10) + String.valueOf(minutes % 10) + ":", (GAME_WIDTH / 2) - 95, (GAME_HEIGHT - 50)); // Position the minutes of the countdown
		g.drawString(String.valueOf(seconds / 10) + String.valueOf(seconds % 10), (GAME_WIDTH / 2) + 20, (GAME_HEIGHT - 50)); // Position the seconds of the countdown
	}
}
