import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class PongGame {
	
	public static void main(String[] args) {
		
		JOptionPane.showMessageDialog(null, "Greetings! Welcome to Pong!", "Instructions", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, 
				"The rules of this game are simple: whichever side gets 10 points first , wins!", 
				"Instructions", 
				JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "To access the left paddle, use W for up and S for down.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "Have fun with the game!", "Plain Message", JOptionPane.PLAIN_MESSAGE);
		
		GameFrame frame = new GameFrame();
		
	}

}
