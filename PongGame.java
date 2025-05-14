import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PongGame {

	public static void main(String[] args) {
		
		JOptionPane.showMessageDialog(null, "Greetings! Welcome to Pong!");
		JOptionPane.showMessageDialog(null, "The rules of this game are simple: whichever side gets 10 points first, wins!");
		JOptionPane.showMessageDialog(null, "To access the left paddle (which is what the user is going to be using), use W for up and S for down.");
		JOptionPane.showMessageDialog(null, "Without further ado, have fun with the game!");
		
		GameFrame frame = new GameFrame();
		
	}

}
