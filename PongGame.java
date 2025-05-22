import javax.swing.*;

public class PongGame {
	
	public static void main(String[] args) {
		
		// Display the rules and other messages about the game
		JOptionPane.showMessageDialog(null, "Greetings! Welcome to Pong!", "Instructions", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, 
				"The rules of this game are simple: If the user scores 10 points first AND does it UNDER 5 MINUTES, they win!"
				+ " If they don't at least fulfill ONE of the conditions, they lose.", 
				"Instructions", 
				JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "To access the left paddle, use W for up and S for down.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "Have fun with the game!", "Plain Message", JOptionPane.PLAIN_MESSAGE);
		
		GameFrame frame = new GameFrame(); // Instantiate frame as an object of the GameFrame class
		
	}

}
