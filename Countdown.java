import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class Countdown extends TimerTask {
	
	private static int timeRemaining = 60;
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	
	public void run() {
		if (timeRemaining > 0) {
			timeRemaining--;
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
		g.drawString(String.valueOf(timeRemaining / 10) + String.valueOf(timeRemaining % 10), (GAME_WIDTH / 2) - 85, (GAME_HEIGHT - 50));
		g.drawString(String.valueOf(timeRemaining / 10) + String.valueOf(timeRemaining % 10), (GAME_WIDTH / 2) + 20, (GAME_HEIGHT - 50));
	}
}
