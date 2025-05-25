import java.awt.*;

public class Score extends Rectangle {
	
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player;
	int right_paddle;
	
	// Construct the components of the score's position
	Score(int GAME_WIDTH, int GAME_HEIGHT) {
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT); // Draw the middle vertical line on the screen
		g.drawString(String.valueOf(player / 10) + String.valueOf(player % 10), (GAME_WIDTH / 2) - 85, 50); // Position the score of the player
		g.drawString(String.valueOf(right_paddle / 10) + String.valueOf(right_paddle % 10), (GAME_WIDTH / 2) + 20, 50); // Position the score of the right paddle
	}

}
