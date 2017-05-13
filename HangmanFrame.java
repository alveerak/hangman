import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
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
        setTitle("Hangman");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBackground(new Color(225, 215, 178));
        
    }
	/**
	* main method which creates object app of the Hangman class
	*/
	public static void main(String[] args) {
		HangmanFrame app = new HangmanFrame();
	
	}
	
}
