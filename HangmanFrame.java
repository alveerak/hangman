import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
* class extends JFrame class and implements GUI 
*/
public class HangmanFrame extends JFrame{
	HangmanDrawing a;
	/** 
	* constructor creates HangmanDrawing object
	* 
	*/
	public HangmanFrame() {
		a = new HangmanDrawing();
		add(a);
        	setResizable(true);
        	setTitle("Hangman");
        	setSize(800, 700);
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	setVisible(true);
	}
	/**
	* main method which creates object app of the class
	*/
	public static void main(String[] args) {
		HangmanFrame app = new HangmanFrame();
	}
	
}
