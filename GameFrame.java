import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	
	GameFrame() {
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Pong Game");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); /* Causes this Window to be sized to fit 
		the preferred sizeand layouts of its subcomponents. 
		The resulting width andheight of the window are automatically enlarged 
		if either of dimensions is less than the minimum size 
		as specifiedby the previous call to the setMinimumSize method. */
		this.setVisible(true);
		this.setLocationRelativeTo(null); // Sets the frame in the middle of the screen
	
	}

}
