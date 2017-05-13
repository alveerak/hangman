import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.text.NumberFormat;

/**
 * class implemenents all GUI elements and runs the graphics of the Hangman Game
 */
public class HangmanDrawing extends JPanel  implements ActionListener {
	private JLabel lbl; //displays the word being guessed with unguessed letters as blanks
	private JLabel guessedLetters; //displays the incorrect letters guessed
	private JLabel clue; // displays clue after after 5 incorrect guesses
	private JTextField tf; //where letters are entered
	private JButton btn; //submit button
	private char l; //letter entered
	private int moves; //number of incorrect moves done

	// below are related to the list of words (spells)
	private String[] list; // words randomly chosen for game
	private final String FILE_NAME = "spells.txt"; // name of file containing												// guess words (spells)
	private final int NUM_SPELLS = 91; // number of spells in file
	private int wordIndex; // index of word randomly chosen in list
	private String[] spellTypes; // type of spell (curse, charm, etc.)
	private String[] spellDescriptions; // what the spell does

	private HangmanWord word; //word object version of hangman word
	private boolean gameStarted; //so that nothing is drawn when the drawing object is initially created

	//count down variables
	private double remain; //how many sec left in count down.
	private long update; //count last updated
	private JLabel label; //displays count
	private Timer time; //updates count every second
	private NumberFormat format; //in seconds

	/**
	 * Constructor creates a Random object 
	 */
	public HangmanDrawing() {
		Random r = new Random();
		gameStarted=false;
		moves = 0;

		initializeLists();
		wordIndex = r.nextInt(list.length);
		word = new HangmanWord(list[wordIndex].toLowerCase());

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

		clue = new JLabel();
		clue.setLocation(100, 150);
		clue.setSize(600, 200);;
		clue.setAlignmentX(LEFT_ALIGNMENT);
		add(clue);

		tf = new JTextField("", 10);
		tf.setLocation(100, 20);
		tf.setEditable(true);
		add(tf);

		btn = new JButton("Submit");
		add(btn);
		btn.addActionListener(this);

		setPreferredSize(new Dimension(500, 100));
		moves = 0;

		remain = 60000;
		//create JLabel to display remaining time
		label = new JLabel();
		label.setSize(600, 200);
		label.setLocation(300, 300);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);

		format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(0); 

		time = new Timer(0, this);
		time.setInitialDelay(0);
		this.start();
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
		if(evt.getSource() == time){
			updateDisplay();
		}else{
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
				if (moves > 4)
					clue.setText(spellDescriptions[wordIndex]);
			}
			tf.setText("");
			if (word.isComplete()&&moves<11){
				btn.hide();
				tf.hide();
				lbl.setText("YOU WON!!!! " + list[wordIndex] + " casted. ");
				lbl.setLocation(30, 100);
				lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			}
			else if (moves>=7){
				btn.hide();
				tf.hide();
				lbl.setLocation(30, 100);
				lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
				lbl.setText("YOU LOST!!!! The spell was "+ word.getWord()+".");
			}
		}
	}

	/**
	 * Initializes list to spell names, initializes spellTypes and
	 * spellDescriptions
	 * 
	 */
	private void initializeLists() {
		try {

			Scanner in = new Scanner(new File(FILE_NAME));
			int index = 0;
			list = new String[NUM_SPELLS];
			spellTypes = new String[NUM_SPELLS];
			spellDescriptions = new String[NUM_SPELLS];
			while (in.hasNextLine()) {
				list[index] = in.nextLine();
				spellTypes[index] = in.nextLine();
				spellDescriptions[index] = in.nextLine();
				index++;
			}

		} catch (IOException e) {
			System.out.println("File not found.");
		}	
	}

	public void start() { 
		update = System.currentTimeMillis();
		time.start();
	} 

	public void updateDisplay() {
		long now = System.currentTimeMillis(); //current time in ms
		long elapsed = now - update; //ms elapsed since last update
		remain -= elapsed; //adjust remaining time
		update = now; //remember new update time

		//convert remaining ms to ss format and display
		int sec = (int)((remain) / 1000);
		label.setText(format.format(sec));

		//stops the count down when it hits 0
		if (remain <= 0) {
			time.stop();
			btn.hide();
			tf.hide();
			label.setVisible(false);
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			lbl.setText("YOU LOST!!!! The spell was "+ word.getWord()+".");
		}
	}

}
