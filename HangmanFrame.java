import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class HangmanFrame extends JFrame{
	HangmanDrawing a;
	public HangmanFrame() {
		a = new HangmanDrawing();
		add(a);
        
        setResizable(true);
        setTitle("Hangman");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

	public static void main(String[] args) {
		HangmanFrame app = new HangmanFrame();
	}
	
}
