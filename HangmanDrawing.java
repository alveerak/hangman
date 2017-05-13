import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
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

	//count down variables
	private double remain; //how many sec left in count down.
	private long update; //count last updated
	private JLabel label; //displays count
	private Timer time; //updates count every second
	private NumberFormat format; //in seconds

	private HangmanWord word; //word object version of hangman word
	private boolean gameStarted; //so that nothing is drawn when the drawing object is initially created

	private JPanel submitPanel, wordPanel, textPanel, guessPanel, cluePanel;
	private ImagePanel imagePanel;
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

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setVisible(true);
		this.setSize(900, 800);


		guessedLetters = new JLabel("");
		guessedLetters.setVisible(true);

		lbl = new JLabel();
		lbl.setText(word.makeLabel());
		lbl.setFont(new Font(lbl.getFont().getName(), Font.PLAIN, 20));
		lbl.setVisible(true);

		clue = new JLabel();
		clue.setVisible(true);
		clue.setAlignmentX(LEFT_ALIGNMENT);

		tf = new JTextField("", 10);
		tf.setEditable(true);
		tf.setVisible(true);

		btn = new JButton("Submit");
		btn.addActionListener(this);


		submitPanel = new JPanel();
		submitPanel.setBackground(new Color(225, 215, 178));
		submitPanel.setPreferredSize(new Dimension(300, 50));
		submitPanel.setLayout(new FlowLayout());
		submitPanel.add(tf);
		submitPanel.add(btn);
		//submitPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(submitPanel);

		wordPanel = new JPanel();
		wordPanel.setPreferredSize(new Dimension(300, 50));
		wordPanel.setLayout(new FlowLayout());
		wordPanel.setBackground(new Color(225, 215, 178));
		wordPanel.add(lbl);
		wordPanel.setVisible(true);
		//wordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(wordPanel);

		guessPanel = new JPanel();
		guessPanel.setPreferredSize(new Dimension(300, 50));
		guessPanel.setLayout(new FlowLayout());
		guessPanel.setBackground(new Color(225, 215, 178));
		guessPanel.add(guessedLetters);
		guessPanel.setVisible(true);
		//guessPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(guessPanel);

		cluePanel = new JPanel();
		cluePanel.setLayout(new FlowLayout());
		cluePanel.setPreferredSize(new Dimension(300, 50));
		cluePanel.setBackground(new Color(225, 215, 178));
		cluePanel.add(clue);
		cluePanel.setVisible(true);
		//cluePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		add(cluePanel);

		imagePanel = new ImagePanel();
		imagePanel.setLayout(new FlowLayout());
		imagePanel.setBackground(new Color(225, 215, 178));
		imagePanel.setPreferredSize(new Dimension(450, 300));
		imagePanel.setVisible(true);
		//imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(imagePanel);
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

	}
	//ALL DRAWING DONE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


	/**
	 *
	 */
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == time){
			updateDisplay();
		}
		else {

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
				imagePanel.setMoves(moves);
				imagePanel.repaint();
				if (moves > 4)
					clue.setText(spellDescriptions[wordIndex]);
			}
			tf.setText("");
			if (word.isComplete()&&moves<=12){
				submitPanel.hide();
				cluePanel.hide();
				guessPanel.hide();
				lbl.setText("YOU WON!!!! " + list[wordIndex] + " casted. ");
				lbl.setLocation(30, 100);
				lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			}
			else if (moves>=12){
				submitPanel.hide();
				cluePanel.hide();
				guessPanel.hide();
				lbl.setLocation(30, 100);
				lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
				lbl.setText("YOU LOST!!!! The spell was "+ word.getWord()+".");
			}
		}
	}

	public int getMoves(){
		return moves;
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

	/**
	 * Starts the timer
	 */
	public void start() { 
		update = System.currentTimeMillis();
		time.start();
	} 

	/**
	 * Update the displayed time. This method is called from actionPerformed().
	 */
	public void updateDisplay() {
		long now = System.currentTimeMillis(); //current time in ms
		long elapsed = now - update; //ms elapsed since last update
		remain -= elapsed; //adjust remaining time
		update = now; //remember new update time

		//convert remaining ms to ss format and display
		int sec = (int)((remain) / 1000);
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
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
