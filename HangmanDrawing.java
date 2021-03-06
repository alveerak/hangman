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
import java.net.URL;
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
	private final int MAX_MOVES = 12; // max number of incorrect moves before
	// player loses

	// below are related to the list of words (spells)
	private ArrayList<Word> list; // words randomly chosen for game
	private final String FILE_NAME = "spells.txt"; // name of file containing
	// guess words (spells)

	// count down variables
	private final double MAX_SECS = 60000; // maximum time player has to win the
	// game in milliseconds
	private double remain = MAX_SECS; // how many sec left in count down.
	private long update; // count last updated
	private JLabel timeLabel; // displays count
	private Timer time; // updates count every second
	private NumberFormat format; // in seconds

	private HangmanWord word; // word object version of hangman word
	private Random r = new Random(); // random object to choose a random game
	// word

	private JPanel submitPanel, wordPanel, textPanel, guessPanel, cluePanel; // various panels of the game screen
	private ImagePanel imagePanel; // panel of game screen that contains
	// "hangman" wizard image

	// instance variables related to inspirational images
	private JLabel photo = new JLabel();
	private ArrayList<ImageIcon> motivationImages;
	private ArrayList<ImageIcon> congratsImages;
	private ArrayList<ImageIcon> wonImages;
	private ArrayList<ImageIcon> lostImages;
	private ImageIcon defaultImage;

	/**
	 * Constructor to initialize all necessary instance variables: JFrame
	 * panels, list of game words, this game word, all JFrame components, number
	 * of moves, timer
	 */
	public HangmanDrawing() {
		moves = 0;

		initializeLists();
		word = (HangmanWord) list.get(r.nextInt(list.size()));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setVisible(true);
		setBackground(new Color(225, 215, 178));
		this.setSize(900, 800);

		guessedLetters = new JLabel("");
		guessedLetters.setFont(new Font(guessedLetters.getFont().getName(), Font.PLAIN, 15));
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
		add(btn);
		btn.addActionListener(this);

		makePhotos();
		photo = new JLabel(defaultImage); // change to default image
		photo.setVisible(true);

		addPanels();
		time();
		moves = 0;
	}

	/**
	 * when a button is clicked, perform corresponding action
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == time) {
			updateDisplay();
		} else {
			// checks if new game is clicked
			if (lbl.getText().equals(word.getWonMessage())
					|| lbl.getText().equals(word.getLostMessage())) {
				reset();
			}

			if (tf.getText().equals("")) {
				return;
			}

			String wordString = word.getWord(word.getLength() - 1);

			if (tf.getText().length() > 1) {
				if (tf.getText().toLowerCase().equals(wordString) && moves < MAX_MOVES + 1) {
					win();
				} else {
					wrongGuess();
					tf.setText("");
				}
			} else {
				l = tf.getText().toLowerCase().charAt(0);

				if (wordString.indexOf(l) > -1) {
					rightGuess();

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
		imagePanel.repaint();
	}

	/**
	 * returns the number of incorrect moves made so far
	 * 
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
		long now = System.currentTimeMillis(); // current time in ms
		long elapsed = now - update; // ms elapsed since last update
		remain -= elapsed; // adjust remaining time
		update = now; // remember new update time

		// convert remaining ms to ss format and display
		int sec = (int) ((remain) / 1000);
		timeLabel.setText(format.format(sec));

		// stops the count down when it hits 0
		if (remain <= 0) {
			lose();
		}
	}

	/**
	 * Initializes list of Words to SpellWords and HangmanWords
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
			normals.addAll(Arrays.asList("owl", "frog", "wand", "potion",
					"herbology", "divination", "broomstick"));

			Iterator iter = normals.iterator();
			while (iter.hasNext()) {
				list.add(new HangmanWord((String) iter.next()));
			}

		} catch (IOException e) {
			System.out.println("File not found.");
		}
	}

	/**
	 * private helper method to reset all necessary instance variables when user
	 * elects to start a new game
	 */
	private void reset() {
		word = (HangmanWord) list.get(r.nextInt(list.size()));
		moves = 0;
		lbl.setText(word.makeLabel());
		tf.setText("");
		clue.setText("");
		guessedLetters.setText("");
		tf.setVisible(true);
		imagePanel.setMoves(0);
		imagePanel.repaint();
		btn.setText("Submit");
		remain = MAX_SECS;
		photo.setIcon(defaultImage);
		this.start();
	}

	/**
	 * private helper method to update game display when user makes an incorrect
	 * guess
	 */
	private void rightGuess() {
		word.addLetter(l);
		lbl.setText(word.makeLabel());
		remain += 5000;
		photo.setIcon(congratsImages.get((int) (Math.random() * congratsImages.size())));
	}

	/**
	 * private helper method to update game display when user makes an incorrect
	 * guess
	 */
	private void wrongGuess() {
		moves++;
		if (tf.getText().length() == 1) {
			guessedLetters.setText(guessedLetters.getText() + l + " ");
		}
		imagePanel.setMoves(moves);
		imagePanel.repaint();
		if (moves > MAX_MOVES / 2)
			clue.setText("Hint: " + word.getHint());
		photo.setIcon(motivationImages
				.get((int) (Math.random() * motivationImages.size())));
	}

	/**
	 * private helper method to update game display when user wins the game
	 */
	private void win() {
		tf.setVisible(false);
		imagePanel.repaint();
		lbl.setText(word.getWonMessage());
		lbl.setLocation(30, 100);
		btn.setText("New Game");
		time.stop();
		photo.setIcon(wonImages.get((int) (Math.random() * wonImages.size())));
	}

	/**
	 * private helper method to update game display when user loses the game
	 */
	private void lose() {
		tf.setVisible(false);
		imagePanel.repaint();
		lbl.setLocation(30, 100);
		lbl.setText(word.getLostMessage());
		btn.setText("New Game");
		time.stop();
		photo.setIcon(lostImages.get((int) (Math.random() * lostImages.size())));
	}

	/**
	 * private helper method to initialize the JPanels
	 */
	private void addPanels() {
		submitPanel = new JPanel();
		submitPanel.setBackground(new Color(225, 215, 178));
		submitPanel.setPreferredSize(new Dimension(300, 50));
		submitPanel.setLayout(new FlowLayout());
		submitPanel.add(tf);
		submitPanel.add(btn);
		add(submitPanel);

		wordPanel = new JPanel();
		wordPanel.setPreferredSize(new Dimension(300, 50));
		wordPanel.setLayout(new FlowLayout());
		wordPanel.setBackground(new Color(225, 215, 178));
		wordPanel.add(lbl);
		wordPanel.setVisible(true);
		add(wordPanel);

		guessPanel = new JPanel();
		guessPanel.setPreferredSize(new Dimension(300, 300));
		guessPanel.setLayout(new FlowLayout());
		guessPanel.setBackground(new Color(225, 215, 178));
		guessPanel.add(guessedLetters);
		guessPanel.add(photo);
		guessPanel.setVisible(true);
		add(guessPanel);

		cluePanel = new JPanel();
		cluePanel.setLayout(new FlowLayout());
		cluePanel.setPreferredSize(new Dimension(300, 30));
		cluePanel.setBackground(new Color(225, 215, 178));
		cluePanel.add(clue);
		cluePanel.setVisible(true);
		add(cluePanel);

		imagePanel = new ImagePanel();
		imagePanel.setLayout(new FlowLayout());
		imagePanel.setBackground(new Color(225, 215, 178));
		imagePanel.setPreferredSize(new Dimension(450, 300));
		imagePanel.setVisible(true);
		add(imagePanel);
	}

	/**
	 * private helper method to initialize the timer
	 */
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

	/**
	 * Private helper method to initialize ArrayList of inspirational images
	 */
	private void makePhotos() {
		defaultImage = new ImageIcon("yungharry.jpg");
		Image dim = defaultImage.getImage();
		defaultImage.setImage(dim.getScaledInstance(dim.getWidth(null) / 2, dim
				.getHeight(null) / 2, BufferedImage.TYPE_INT_ARGB));
		
		
		motivationImages = new ArrayList<ImageIcon>();
		congratsImages = new ArrayList<ImageIcon>();
		wonImages = new ArrayList<ImageIcon>();
		lostImages = new ArrayList<ImageIcon>();
		ArrayList<String> photoNames = new ArrayList<String>();
		photoNames
				.addAll(Arrays.asList("congrats2.jpg", "congrats3.jpg",
						"congrats4.jpg", "congrats5.jpg", "congrats6.jpg", "motivation1.jpg",
						"motivation2.jpg", "motivation3.jpg", "motivation4.jpg",
						"motivation5.jpg", "motivation6.jpg", "motivation7.jpg", "lost1.jpg",
						"lost2.jpg", "won1.jpg", "won2.jpg"));

		try {

			// add pics
			for (String s : photoNames) {
				ImageIcon i = new ImageIcon(s);
				Image im = i.getImage();
				i.setImage(im.getScaledInstance(im.getWidth(null) / 2, im
						.getHeight(null) / 2, BufferedImage.TYPE_INT_ARGB));
				if (s.indexOf("congrats") == 0) {
					congratsImages.add(i);
				} else if (s.indexOf("motivation") == 0) {
					motivationImages.add(i);
				} else if (s.indexOf("won") == 0) {
					wonImages.add(i);
				} else {
					lostImages.add(i);
				}
			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
