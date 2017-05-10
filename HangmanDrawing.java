import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* class implemenents all GUI elements and runs the graphics of the Hangman Game
*/
public class HangmanDrawing extends JPanel  implements ActionListener {
	private JLabel lbl; //displays the word being guessed with unguessed letters as blanks
	private JLabel guessedLetters; //displays the incorrect letters guessed
	private JTextField tf; //where letters are entered
	private JButton btn; //submit button
	private char l; //letter entered
	private int moves; //number of incorrect moves done
	private String[] list = { "math", "science", "english" }; //words randomly chosen for game
	private HangmanWord word; //word object version of hangman word
	private boolean gameStarted; //so that nothing is drawn when the drawing object is initially created
	
	/**
	* Constructor creates a Random object 
	*/
	public HangmanDrawing() {
		Random r = new Random();
		gameStarted=false;
		moves = 0;
		word = new HangmanWord(list[r.nextInt(list.length)]);
		setLayout(new FlowLayout());
		setVisible(true);
		this.setSize(600, 500);
		
		guessedLetters = new JLabel("");
		add(guessedLetters);
		lbl = new JLabel();
		lbl.setText(word.makeLabel());
		lbl.setLocation(100, 100);
		lbl.setSize(600, 200);;
		lbl.setAlignmentX(LEFT_ALIGNMENT);
		add(lbl);

		tf = new JTextField("", 10);
		tf.setLocation(100, 20);
		tf.setEditable(true);
		add(tf);

		btn = new JButton("Submit");
		add(btn);
		btn.addActionListener(this);
		
		setPreferredSize(new Dimension(500, 100));
		moves = 0;
	}
	
	public void paintComponent(Graphics g) {
        	super.paintComponent(g);
        	if (gameStarted)
        		drawDrawing(g);
        	else
        		gameStarted = true;
	}
	
	
	//ALL DRAWING DONE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	*
	*/
	public void drawDrawing(Graphics g){
		g.fillRect(50, 50, 50, 50);
	}
	//ALL DRAWING DONE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	/**
	*
	*/
	public void actionPerformed(ActionEvent evt) {
		if(tf.getText().equals("")){
			return;
		}
		l = tf.getText().charAt(0);
		if(word.getWord().indexOf(l)>-1){
			word.addLetter(l);
			lbl.setText(word.makeLabel());
		}
		else{
			moves++;
			guessedLetters.setText(guessedLetters.getText()+l);
			repaint();
		}
		tf.setText("");
		if (word.isComplete()&&moves<11){
			btn.hide();
			tf.hide();
			lbl.setText("YOU WON!!!!");
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
		}
		else if (moves>=10){
			btn.hide();
			tf.hide();
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			lbl.setText("YOU LOST!!!!");
		}
	}
}
