import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
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
 * class implements all GUI elements and runs the graphics of the Hangman Game
 */
public class HangmanDrawing extends JPanel implements ActionListener {
	private JLabel lbl; // displays the word being guessed with unguessed
						// letters as blanks
	private JLabel guessedLetters; // displays the incorrect letters guessed
	private JLabel clue; // displays clue after after 5 incorrect guesses
	private JTextField tf; // where letters are entered
	private JButton btn; // submit button
	private char l; // letter entered
	private int moves; // number of incorrect moves done
	private final int MAX_MOVES = 12;

	// below are related to the list of words (spells)
	private ArrayList<Word> list; // words randomly chosen for game
	private final String FILE_NAME = "spells.txt"; // name of file containing
													// guess words (spells)

	// count down variables
	private double REMAIN = 6000000;; // how many sec left in count down.
	private long update; // count last updated
	private JLabel timeLabel; // displays count
	private Timer time; // updates count every second
	private NumberFormat format; // in seconds

	private HangmanWord word; // word object version of hangman word
	private boolean gameStarted; // so that nothing is drawn when the drawing
									// object is initially created
	private Random r = new Random();

	private JPanel submitPanel, wordPanel, textPanel, guessPanel, cluePanel;
	private ImagePanel imagePanel;

	/**
	 * Constructor creates a Random object
	 */
	public HangmanDrawing() {
		gameStarted = false;
		moves = 0;

		initializeLists();
		// word = (HangmanWord)list.get(r.nextInt(list.size()));
		word = (HangmanWord) list.get(91);

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
		//btn.setLocation(125, 20);
		add(btn);
		btn.addActionListener(this);

		addPanels();
		time();
		moves = 0;
	}

	/**
	*
	*/
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == time) {
			updateDisplay();
		} else {
			// checks if new game is clicked
			if (lbl.getText().equals(word.getWonMessage()) || lbl.getText().equals(word.getLostMessage())) {
				reset();
			}

			if (tf.getText().equals("")) {
				return;
			}

			String wordString = word.getWord(word.getLength() - 1);

			if (tf.getText().length() > 1) {
				if (tf.getText().equals(wordString) && moves < MAX_MOVES + 1) {
					win();
				} else {
					wrongGuess();
					tf.setText("");
				}
			} else {
				l = tf.getText().charAt(0);

				if (wordString.indexOf(l) > -1) {
					word.addLetter(l);
					lbl.setText(word.makeLabel());
				} else {
					wrongGuess();
				}
				tf.setText("");
			}

			if (word.isComplete() && moves < MAX_MOVES + 1) {
				win();
			} else if (moves >= MAX_MOVES) {
				lose();
			}

		}
	}

	/**
	 * returns the number of incorrect moves made so far
	 * @return - the number of incorrect moves made so far
	 */
	public int getMoves() {
		return moves;
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
		REMAIN -= elapsed; //adjust remaining time
		update = now; //remember new update time

		//convert remaining ms to ss format and display
		int sec = (int)((REMAIN) / 1000);
		timeLabel.setText(format.format(sec));

		//stops the count down when it hits 0
		if (REMAIN <= 0) {
			time.stop();
			btn.hide();
			tf.hide();
			timeLabel.setVisible(false);
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			lbl.setText(word.getLostMessage());
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
			list = new ArrayList<Word>();
			while (in.hasNextLine()) {
				String spellName = in.nextLine().toLowerCase();
				in.nextLine();
				String spellDescription = in.nextLine();
				list.add(new SpellWord(spellName, spellDescription));
			}
			ArrayList<String> normals = new ArrayList<String>();
			normals.addAll(Arrays.asList("owl", "frog", "wand", "potion", "herbology", "divination", "broomstick"));

			Iterator iter = normals.iterator();
			while (iter.hasNext()) {
				list.add(new HangmanWord((String) iter.next()));
			}

		} catch (IOException e) {
			System.out.println("File not found.");
		}
	}

	private void reset() {
		word = (HangmanWord) list.get(r.nextInt(list.size()));
		moves = 0;
		lbl.setText(word.makeLabel());
		tf.setText("");
		clue.setText("");
		guessedLetters.setText("");
		tf.setVisible(true);
		btn.setText("Submit");
	}

	private void wrongGuess() {
		moves++;
		if (tf.getText().length() == 1) {
			guessedLetters.setText(guessedLetters.getText() + l + " ");
		}
		imagePanel.setMoves(moves);
		imagePanel.repaint();
		if (moves > MAX_MOVES / 2)
			clue.setText("Hint: " + word.getHint());
	}

	private void win() {
		tf.hide();
		lbl.setText(word.getWonMessage());
		lbl.setLocation(30, 100);
		btn.setText("New Game");
		time.stop();
	}

	private void lose() {
		tf.hide();
		lbl.setLocation(30, 100);
		// lbl.setFont(new Font("Calibri", Font.BOLD, 30));
		lbl.setText(word.getLostMessage());
		btn.setText("New Game");
		time.stop();
	}

	private void addPanels() {
		submitPanel = new JPanel();
		submitPanel.setBackground(new Color(225, 215, 178));
		submitPanel.setPreferredSize(new Dimension(300, 50));
		submitPanel.setLayout(new FlowLayout());
		submitPanel.add(tf);
		submitPanel.add(btn);
		// submitPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(submitPanel);

		wordPanel = new JPanel();
		wordPanel.setPreferredSize(new Dimension(300, 50));
		wordPanel.setLayout(new FlowLayout());
		wordPanel.setBackground(new Color(225, 215, 178));
		wordPanel.add(lbl);
		wordPanel.setVisible(true);
		// wordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(wordPanel);

		guessPanel = new JPanel();
		guessPanel.setPreferredSize(new Dimension(300, 50));
		guessPanel.setLayout(new FlowLayout());
		guessPanel.setBackground(new Color(225, 215, 178));
		guessPanel.add(guessedLetters);
		guessPanel.setVisible(true);
		// guessPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(guessPanel);

		cluePanel = new JPanel();
		cluePanel.setLayout(new FlowLayout());
		cluePanel.setPreferredSize(new Dimension(300, 50));
		cluePanel.setBackground(new Color(225, 215, 178));
		cluePanel.add(clue);
		cluePanel.setVisible(true);
		// cluePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		add(cluePanel);

		imagePanel = new ImagePanel();
		imagePanel.setLayout(new FlowLayout());
		imagePanel.setBackground(new Color(225, 215, 178));
		imagePanel.setPreferredSize(new Dimension(450, 300));
		imagePanel.setVisible(true);
		// imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(imagePanel);
	}

	private void time() {
		// create JLabel to display remaining time
		timeLabel = new JLabel();
		timeLabel.setSize(600, 200);
		timeLabel.setLocation(300, 300);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(timeLabel);

		format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(0);

		time = new Timer(0, this);
		time.setInitialDelay(0);
		this.start();
	}
}
