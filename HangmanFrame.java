import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class HangmanFrame extends JFrame implements ActionListener {
	private JLabel lbl;
	private JLabel guessedLetters;
	private JTextField tf;
	private JButton btn;
	private JPanel draw;
	private char l;
	private int moves;

	private String[] list = { "math", "science", "english" };
	HangmanWord word;

	public HangmanFrame() {
		Random r = new Random();
		word = new HangmanWord(list[r.nextInt(list.length)]);
		setLayout(new FlowLayout());
		draw = new JPanel();
		draw.setOpaque(true);
		draw.add(new HangmanDrawing());
		add(draw);
		moves = 0;
		
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
		setTitle("Hangman");
		setSize(600, 600);

		setVisible(true);
		pack();
	}

	public static void main(String[] args) {
		HangmanFrame app = new HangmanFrame();
		
	}
	
	public int getMoves(){
		return moves;
	}

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
		}
		tf.setText("");
		if (word.isComplete()&&moves<11){
			btn.hide();
			tf.hide();
			lbl.setText("YOU WON!!!!");
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
		}
		else if (moves>=10) {
			lbl.setText("YOU LOST!!!!");
		}
		draw.repaint();
		pack();
	}
}