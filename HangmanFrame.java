import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class HangmanFrame extends Frame implements ActionListener {
	private Label lbl;
	private TextField tf;
	private Button btn;
	private char l;
	private int moves;

	private String[] list = { "math", "science", "english" };
	HangmanWord word;

	public HangmanFrame() {
		Random r = new Random();
		word = new HangmanWord(list[r.nextInt(list.length)]);
		setLayout(new FlowLayout());

		moves = 0;
		
		lbl = new Label();
		lbl.setText(word.makeLabel());
		lbl.setLocation(100, 100);
		lbl.setSize(600, 200);;
		lbl.setAlignment(Label.CENTER);
		add(lbl);

		tf = new TextField("", 10);
		tf.setLocation(100, 20);
		tf.setEditable(true);
		add(tf);

		btn = new Button("Submit");
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

	public void actionPerformed(ActionEvent evt) {
		if(tf.getText().equals("")){
			return;
		}
		l = tf.getText().charAt(0);
		if(word.getWord().indexOf(l)>-1){
			word.addLetter(l);
			lbl.setText(word.makeLabel());
			pack();
		}
		else
			moves++;
		tf.setText("");
		if (word.isComplete()&&moves<11){
			btn.hide();
			tf.hide();
			lbl.setText("YOU WON!!!!");
			lbl.setLocation(30, 100);
			lbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
			pack();
		}
	}
}